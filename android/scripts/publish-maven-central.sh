#!/usr/bin/env bash
# Publish planet-components-android to Maven Central (Central Portal).
#
# Flow:
#   1) Gradle upload to OSSRH Staging API
#   2) Manual close/upload staging repo → Portal Deployments
#   3) Operator clicks Publish in Portal UI (or --auto-publish if Portal supports it later)
#
# Credentials (gitignored): android/gradle.properties  OR  ~/.gradle/gradle.properties
# Template: android/gradle.properties.example
#
# Usage:
#   android/scripts/publish-maven-central.sh
#   android/scripts/publish-maven-central.sh --dry-run
#   android/scripts/publish-maven-central.sh --upload-only
#   android/scripts/publish-maven-central.sh --list
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"

API="https://ossrh-staging-api.central.sonatype.com/manual"
PORTAL_DEPLOYMENTS="https://central.sonatype.com/publishing/deployments"

DRY_RUN=0
UPLOAD_ONLY=0
LIST_ONLY=0
REPO_KEY=""

usage() {
  cat <<'EOF'
Usage: android/scripts/publish-maven-central.sh [options]

Options:
  --dry-run       Print planned steps; do not publish or upload
  --upload-only   Skip Gradle; only search open staging repos and upload to Portal
  --list          List staging repositories and exit
  --key <key>     Upload a specific staging repository key
  -h, --help      Show help

Credentials (required for real runs):
  android/gradle.properties  (preferred, gitignored)
  or ~/.gradle/gradle.properties

Required properties:
  ossrhUsername / ossrhPassword
  signing.keyId / signing.password / signingKeyFile (or other signing.* form)
EOF
}

while [[ $# -gt 0 ]]; do
  case "$1" in
    --dry-run) DRY_RUN=1; shift ;;
    --upload-only) UPLOAD_ONLY=1; shift ;;
    --list) LIST_ONLY=1; shift ;;
    --key) REPO_KEY="${2:-}"; shift 2 ;;
    -h|--help) usage; exit 0 ;;
    *) echo "Unknown arg: $1" >&2; usage; exit 1 ;;
  esac
done

find_props_file() {
  if [[ -f "$ROOT_DIR/gradle.properties" ]]; then
    echo "$ROOT_DIR/gradle.properties"
  elif [[ -f "${HOME}/.gradle/gradle.properties" ]]; then
    echo "${HOME}/.gradle/gradle.properties"
  else
    echo ""
  fi
}

read_prop() {
  local file="$1" key="$2"
  # last matching assignment wins
  grep -E "^${key}=" "$file" 2>/dev/null | tail -1 | cut -d= -f2- || true
}

urlencode() {
  python3 -c 'import sys,urllib.parse; print(urllib.parse.quote(sys.argv[1], safe=""))' "$1"
}

PROPS_FILE="$(find_props_file)"
if [[ -z "$PROPS_FILE" ]]; then
  echo "Missing credentials file." >&2
  echo "Copy android/gradle.properties.example → android/gradle.properties and fill values." >&2
  exit 1
fi

ossrhUsername="$(read_prop "$PROPS_FILE" ossrhUsername)"
ossrhPassword="$(read_prop "$PROPS_FILE" ossrhPassword)"
if [[ -z "$ossrhUsername" || -z "$ossrhPassword" ]]; then
  echo "ossrhUsername / ossrhPassword missing in $PROPS_FILE" >&2
  exit 1
fi

AUTH="$(printf '%s' "${ossrhUsername}:${ossrhPassword}" | base64 -w0 2>/dev/null || printf '%s' "${ossrhUsername}:${ossrhPassword}" | base64)"

VERSION="$(grep -E '^version\s*=\s*"' "$ROOT_DIR/library/build.gradle" | head -1 | sed -E 's/.*"([^"]+)".*/\1/')"
ARTIFACT="io.github.techskillplanet:planet-components-android:${VERSION:-unknown}"

echo "==> Credentials file: $PROPS_FILE"
echo "==> Artifact: $ARTIFACT"
echo "==> Portal: $PORTAL_DEPLOYMENTS"

search_repos() {
  curl -sS -H "Authorization: Bearer ${AUTH}" "${API}/search/repositories?ip=any"
}

pick_open_key() {
  python3 -c '
import json, sys
data = json.loads(sys.argv[1])
repos = data.get("repositories") or []
open_repos = [r for r in repos if (r.get("state") or "").lower() == "open"]
candidates = open_repos or repos
print((candidates[0].get("key") or "") if candidates else "")
' "$1"
}

portal_deployment_id_for_key() {
  python3 -c '
import json, sys
data = json.loads(sys.argv[1])
key = sys.argv[2]
repos = data.get("repositories") or []
for r in repos:
    if r.get("key") == key and r.get("portal_deployment_id"):
        print(r["portal_deployment_id"])
        break
else:
    if repos and repos[0].get("portal_deployment_id"):
        print(repos[0]["portal_deployment_id"])
' "$1" "$2"
}

print_repos() {
  local raw="$1"
  echo "$raw" | python3 -m json.tool 2>/dev/null || echo "$raw"
}

if [[ "$LIST_ONLY" -eq 1 ]]; then
  echo "==> Staging repositories"
  print_repos "$(search_repos)"
  exit 0
fi

resolve_gradle() {
  if [[ -x "$ROOT_DIR/gradlew" ]]; then
    # Prefer wrapper when distribution is available; fall back if download hangs.
    if [[ -d "${HOME}/.gradle/wrapper/dists/gradle-9.0-milestone-1-bin" ]] \
      && compgen -G "${HOME}/.gradle/wrapper/dists/gradle-9.0-milestone-1-bin/*/gradle-9.0-milestone-1/bin/gradle" >/dev/null 2>&1; then
      echo "$ROOT_DIR/gradlew"
      return
    fi
  fi
  local cached
  cached="$(ls -d "${HOME}/.gradle/wrapper/dists/gradle-9.3.1-bin/*/gradle-9.3.1/bin/gradle" 2>/dev/null | head -1 || true)"
  if [[ -n "$cached" && -x "$cached" ]]; then
    echo "$cached"
    return
  fi
  if command -v gradle >/dev/null 2>&1; then
    echo "gradle"
    return
  fi
  if [[ -x "$ROOT_DIR/gradlew" ]]; then
    echo "$ROOT_DIR/gradlew"
    return
  fi
  echo ""
}

if [[ "$UPLOAD_ONLY" -eq 0 ]]; then
  GRADLE_BIN="$(resolve_gradle)"
  if [[ -z "$GRADLE_BIN" ]]; then
    echo "No Gradle binary found (gradlew / cached gradle / PATH)." >&2
    exit 1
  fi
  echo "==> Gradle: $GRADLE_BIN"
  if [[ "$DRY_RUN" -eq 1 ]]; then
    echo "DRY-RUN: would run: $GRADLE_BIN :library:publishReleasePublicationToMavenCentralRepository"
  else
    echo "==> Gradle publish → OSSRH Staging API"
    "$GRADLE_BIN" :library:publishReleasePublicationToMavenCentralRepository
  fi
fi

echo "==> Search staging repositories"
SEARCH_JSON="$(search_repos)"
print_repos "$SEARCH_JSON"

if [[ -z "$REPO_KEY" ]]; then
  REPO_KEY="$(pick_open_key "$SEARCH_JSON")"
fi

if [[ -z "$REPO_KEY" ]]; then
  echo "No staging repository key found. Use --list / --key." >&2
  exit 1
fi

KEY_ENC="$(urlencode "$REPO_KEY")"
echo "==> Selected staging key: $REPO_KEY"

if [[ "$DRY_RUN" -eq 1 ]]; then
  echo "DRY-RUN: would POST ${API}/upload/repository/${KEY_ENC}"
  echo "DRY-RUN: then open $PORTAL_DEPLOYMENTS and Publish."
  exit 0
fi

echo "==> Upload staging repository → Central Portal Deployments"
TMP_BODY="$(mktemp)"
HTTP="$(curl -sS -w '%{http_code}' -o "$TMP_BODY" -X POST \
  -H "Authorization: Bearer ${AUTH}" \
  "${API}/upload/repository/${KEY_ENC}")"
if [[ -s "$TMP_BODY" ]]; then
  python3 -m json.tool <"$TMP_BODY" 2>/dev/null || cat "$TMP_BODY"
  echo
fi
rm -f "$TMP_BODY"

if [[ "$HTTP" != "200" && "$HTTP" != "204" ]]; then
  echo "Portal upload failed with HTTP $HTTP" >&2
  echo "Tip: --list to inspect repos; --key <key> to retry a specific open repo." >&2
  exit 1
fi

echo "==> After upload status"
AFTER="$(search_repos)"
print_repos "$AFTER"
DEP_ID="$(portal_deployment_id_for_key "$AFTER" "$REPO_KEY" || true)"
if [[ -n "${DEP_ID:-}" ]]; then
  echo "==> Portal deployment id: $DEP_ID"
fi

echo
echo "Done. Open $PORTAL_DEPLOYMENTS and click Publish for $ARTIFACT"
