#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT_DIR"

PROPS_FILE="$ROOT_DIR/gradle.properties"
if [[ ! -f "$PROPS_FILE" ]]; then
  echo "Missing $PROPS_FILE — copy gradle.properties.example and fill credentials." >&2
  exit 1
fi

ossrhUsername=$(grep '^ossrhUsername=' "$PROPS_FILE" | cut -d= -f2-)
ossrhPassword=$(grep '^ossrhPassword=' "$PROPS_FILE" | cut -d= -f2-)
AUTH=$(printf '%s' "${ossrhUsername}:${ossrhPassword}" | base64)
API="https://ossrh-staging-api.central.sonatype.com/manual"

echo "==> Gradle publish (staging)"
./gradlew :library:publishReleasePublicationToMavenCentralRepository

echo "==> Search staging repositories"
SEARCH=$(curl -sS -H "Authorization: Bearer ${AUTH}" "${API}/search/repositories?ip=any")
echo "$SEARCH" | python3 -m json.tool 2>/dev/null || echo "$SEARCH"

KEY=$(echo "$SEARCH" | python3 -c "import sys,json; repos=json.load(sys.stdin).get('repositories',[]); print(repos[0]['key'] if repos else '')")
if [[ -z "$KEY" ]]; then
  echo "No staging repository found." >&2
  exit 1
fi

echo "==> Upload staging repository to Central Portal: $KEY"
HTTP=$(curl -sS -w '%{http_code}' -o /tmp/portal-upload.json -X POST \
  -H "Authorization: Bearer ${AUTH}" \
  "${API}/upload/repository/${KEY}")
cat /tmp/portal-upload.json
echo
if [[ "$HTTP" != "200" && "$HTTP" != "204" ]]; then
  echo "Portal upload failed with HTTP $HTTP" >&2
  exit 1
fi

echo "Done. Open https://central.sonatype.com/publishing/deployments and click Publish."
