#!/usr/bin/env bash
# Publish planet-components-kuikly to Maven Central (Central Portal).
# Mirrors android/scripts/publish-maven-central.sh for the Kuikly KMP shared module.
#
# Flow:
#   1) Gradle upload all KMP publications to OSSRH Staging API
#   2) Manual close/upload staging repo → Portal Deployments
#   3) Operator clicks Publish in Portal UI
#
# Credentials (prefer first match):
#   kuikly/gradle.properties
#   android/gradle.properties   (reuse Android Portal / GPG keys)
#   ~/.gradle/gradle.properties
# Template: kuikly/gradle.properties.example
#
# Usage:
#   kuikly/scripts/publish-maven-central.sh
#   kuikly/scripts/publish-maven-central.sh --dry-run
#   kuikly/scripts/publish-maven-central.sh --upload-only
#   kuikly/scripts/publish-maven-central.sh --list
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
REPO_ROOT="$(cd "$ROOT_DIR/.." && pwd)"
cd "$ROOT_DIR"

API="https://ossrh-staging-api.central.sonatype.com/manual"
PORTAL_DEPLOYMENTS="https://central.sonatype.com/publishing/deployments"
GRADLE_TASK=":shared:publishAllPublicationsToMavenCentralRepository"

DRY_RUN=0
UPLOAD_ONLY=0
LIST_ONLY=0
REPO_KEY=""

usage() {
  cat <<'EOF'
Usage: kuikly/scripts/publish-maven-central.sh [options]

Options:
  --dry-run       Print planned steps; do not publish or upload
  --upload-only   Skip Gradle; only search open staging repos and upload to Portal
  --list          List staging repositories and exit
  --key <key>     Upload a specific staging repository key
  -h, --help      Show help

Credentials (required for real runs):
  kuikly/gradle.properties
  or ../android/gradle.properties
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
  if [[ -f "$ROOT_DIR/gradle.properties" ]] \
    && grep -qE '^ossrhUsername=' "$ROOT_DIR/gradle.properties" 2>/dev/null; then
    echo "$ROOT_DIR/gradle.properties"
  elif [[ -f "$REPO_ROOT/android/gradle.properties" ]] \
    && grep -qE '^ossrhUsername=' "$REPO_ROOT/android/gradle.properties" 2>/dev/null; then
    echo "$REPO_ROOT/android/gradle.properties"
  elif [[ -f "${HOME}/.gradle/gradle.properties" ]] \
    && grep -qE '^ossrhUsername=' "${HOME}/.gradle/gradle.properties" 2>/dev/null; then
    echo "${HOME}/.gradle/gradle.properties"
  elif [[ -f "$ROOT_DIR/gradle.properties" ]]; then
    echo "$ROOT_DIR/gradle.properties"
  else
    echo ""
  fi
}

read_prop() {
  local file="$1" key="$2"
  grep -E "^${key}=" "$file" 2>/dev/null | tail -1 | cut -d= -f2- || true
}

urlencode() {
  python3 -c 'import sys,urllib.parse; print(urllib.parse.quote(sys.argv[1], safe=""))' "$1"
}

PROPS_FILE="$(find_props_file)"
if [[ -z "$PROPS_FILE" ]]; then
  echo "Missing credentials file." >&2
  echo "Copy kuikly/gradle.properties.example → kuikly/gradle.properties," >&2
  echo "or reuse android/gradle.properties (same Central Portal / GPG keys)." >&2
  exit 1
fi

ossrhUsername="$(read_prop "$PROPS_FILE" ossrhUsername)"
ossrhPassword="$(read_prop "$PROPS_FILE" ossrhPassword)"
if [[ -z "$ossrhUsername" || -z "$ossrhPassword" ]]; then
  echo "ossrhUsername / ossrhPassword missing in $PROPS_FILE" >&2
  exit 1
fi

# Resolve signing key file: honor property, then common Mac/Linux paths.
signingKeyFile="$(read_prop "$PROPS_FILE" signingKeyFile)"
if [[ -n "$signingKeyFile" && ! -f "$signingKeyFile" ]]; then
  for candidate in \
    "${HOME}/.gnupg/planet-components-signing.asc" \
    "/home/litingzhe/.gnupg/planet-components-signing.asc"; do
    if [[ -f "$candidate" ]]; then
      echo "==> signingKeyFile missing at configured path; using $candidate"
      signingKeyFile="$candidate"
      break
    fi
  done
fi

AUTH="$(printf '%s' "${ossrhUsername}:${ossrhPassword}" | base64 -w0 2>/dev/null || printf '%s' "${ossrhUsername}:${ossrhPassword}" | base64)"

VERSION="$(grep -E '^version\s*=\s*"' "$ROOT_DIR/library/shared/build.gradle.kts" | head -1 | sed -E 's/.*"([^"]+)".*/\1/')"
ARTIFACT="io.github.techskillplanet:planet-components-kuikly:${VERSION:-unknown}"

echo "==> Credentials file: $PROPS_FILE"
echo "==> Artifact: $ARTIFACT (KMP multi-publication)"
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

# Build -P flags so Kuikly can reuse android/gradle.properties without copying secrets.
build_gradle_props_args() {
  local file="$1"
  local args=()
  local line key val
  while IFS= read -r line || [[ -n "$line" ]]; do
    [[ -z "$line" || "$line" =~ ^[[:space:]]*# ]] && continue
    case "$line" in
      ossrhUsername=*|ossrhPassword=*|signing.keyId=*|signing.password=*|signingKeyFile=*|signing.secretKeyRingFile=*|signingInMemoryKey=*|signing.secretKey=*)
        key="${line%%=*}"
        val="${line#*=}"
        if [[ "$key" == "signingKeyFile" && -n "$signingKeyFile" ]]; then
          val="$signingKeyFile"
        fi
        args+=("-P${key}=${val}")
        ;;
    esac
  done < "$file"
  if [[ -n "$signingKeyFile" ]]; then
    local has_keyfile=0
    for a in "${args[@]+"${args[@]}"}"; do
      [[ "$a" == -PsigningKeyFile=* ]] && has_keyfile=1 && break
    done
    if [[ "$has_keyfile" -eq 0 ]]; then
      args+=("-PsigningKeyFile=${signingKeyFile}")
    fi
  fi
  printf '%s\n' "${args[@]+"${args[@]}"}"
}

resolve_gradle() {
  if [[ -x "$ROOT_DIR/gradlew" ]]; then
    echo "$ROOT_DIR/gradlew"
    return
  fi
  if command -v gradle >/dev/null 2>&1; then
    echo "gradle"
    return
  fi
  echo ""
}

if [[ "$UPLOAD_ONLY" -eq 0 ]]; then
  GRADLE_BIN="$(resolve_gradle)"
  if [[ -z "$GRADLE_BIN" ]]; then
    echo "No Gradle binary found (gradlew / PATH)." >&2
    exit 1
  fi
  # Bash 3 (macOS /bin/bash) has no mapfile; collect -P flags in an array.
  GRADLE_P_ARGS=()
  while IFS= read -r _gp; do
    [[ -n "$_gp" ]] && GRADLE_P_ARGS+=("$_gp")
  done < <(build_gradle_props_args "$PROPS_FILE")

  # Forward shell proxy to the Gradle JVM when present (plugin/dep download).
  GRADLE_PROXY_ARGS=()
  _proxy="${https_proxy:-${HTTPS_PROXY:-${http_proxy:-${HTTP_PROXY:-}}}}"
  if [[ -n "$_proxy" ]]; then
    # http://host:port or host:port
    _proxy_noproto="${_proxy#*://}"
    _proxy_host="${_proxy_noproto%%:*}"
    _proxy_port="${_proxy_noproto##*:}"
    if [[ -n "$_proxy_host" && -n "$_proxy_port" && "$_proxy_port" != "$_proxy_host" ]]; then
      GRADLE_PROXY_ARGS+=(
        "-Dhttps.proxyHost=${_proxy_host}"
        "-Dhttps.proxyPort=${_proxy_port}"
        "-Dhttp.proxyHost=${_proxy_host}"
        "-Dhttp.proxyPort=${_proxy_port}"
      )
    fi
  fi

  echo "==> Gradle: $GRADLE_BIN"
  echo "==> Task: $GRADLE_TASK"
  if [[ "$DRY_RUN" -eq 1 ]]; then
    echo "DRY-RUN: would run: $GRADLE_BIN -PossrhUsername=*** -PossrhPassword=*** -Psigning.*=*** $GRADLE_TASK"
    echo "DRY-RUN: then search staging repos and POST ${API}/upload/repository/<key>"
    echo "DRY-RUN: then open $PORTAL_DEPLOYMENTS and Publish for $ARTIFACT"
    exit 0
  fi
  echo "==> Gradle publish → OSSRH Staging API"
  if ((${#GRADLE_PROXY_ARGS[@]} > 0)); then
    "$GRADLE_BIN" "${GRADLE_PROXY_ARGS[@]}" "${GRADLE_P_ARGS[@]}" "$GRADLE_TASK"
  else
    "$GRADLE_BIN" "${GRADLE_P_ARGS[@]}" "$GRADLE_TASK"
  fi
fi

echo "==> Search staging repositories"
SEARCH_JSON="$(search_repos)"
print_repos "$SEARCH_JSON"

if [[ -z "$REPO_KEY" ]]; then
  REPO_KEY="$(pick_open_key "$SEARCH_JSON")"
fi

if [[ -z "$REPO_KEY" ]]; then
  if [[ "$DRY_RUN" -eq 1 ]]; then
    echo "DRY-RUN: no staging key yet (expected before a real Gradle upload)."
    echo "DRY-RUN: would POST ${API}/upload/repository/<key>"
    echo "DRY-RUN: then open $PORTAL_DEPLOYMENTS and Publish."
    exit 0
  fi
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
echo "Consumer note: also add Tencent Kuikly Maven mirror for com.tencent.kuikly-open transitive deps."
