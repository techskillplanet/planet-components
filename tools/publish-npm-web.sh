#!/usr/bin/env bash
# Publish React Web + Vue Web packages to npmjs.org.
# Auth: NPM_TOKEN env, or project .env, or ~/.npmrc auth for registry.npmjs.org.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"

OFFICIAL_REGISTRY="https://registry.npmjs.org/"
COMPANY_REGISTRY="http://npm.iwhalecloud.com:8081/repository/npm-all/"
PREV_REGISTRY="$(npm config get registry 2>/dev/null || true)"

DRY_RUN=0
TARGETS=()

usage() {
  cat <<'EOF'
Usage: tools/publish-npm-web.sh [--dry-run] [react|vue|all]

Examples:
  tools/publish-npm-web.sh --dry-run all
  tools/publish-npm-web.sh react
  tools/publish-npm-web.sh all
EOF
}

while [[ $# -gt 0 ]]; do
  case "$1" in
    --dry-run) DRY_RUN=1; shift ;;
    -h|--help) usage; exit 0 ;;
    react|vue|all) TARGETS+=("$1"); shift ;;
    *) echo "Unknown arg: $1" >&2; usage; exit 1 ;;
  esac
done

if [[ ${#TARGETS[@]} -eq 0 ]]; then
  TARGETS=(all)
fi

if [[ -f "$ROOT/tools/publish-npm-web.env" ]]; then
  # shellcheck disable=SC1091
  set -a
  source "$ROOT/tools/publish-npm-web.env"
  set +a
fi

if [[ -f "$ROOT/.env" ]]; then
  # shellcheck disable=SC1091
  set -a
  source "$ROOT/.env"
  set +a
fi

if [[ -z "${NPM_TOKEN:-}" ]]; then
  echo "NPM_TOKEN missing. Set it in tools/publish-npm-web.env, .env, or the environment." >&2
  exit 1
fi

# Isolated userconfig avoids ~/.npmrc session/old tokens overriding Bypass-2FA tokens.
USERCONFIG="$(mktemp)"
cleanup() {
  rm -f "$USERCONFIG"
  local target="${PREV_REGISTRY:-$COMPANY_REGISTRY}"
  if [[ -n "$target" && "$target" != "null" ]]; then
    npm config set registry "$target" >/dev/null 2>&1 || true
  fi
}
trap cleanup EXIT

cat > "$USERCONFIG" <<EOF
registry=${OFFICIAL_REGISTRY}
//registry.npmjs.org/:_authToken=${NPM_TOKEN}
EOF

echo "==> npm whoami"
npm whoami --registry "$OFFICIAL_REGISTRY" --userconfig "$USERCONFIG"

publish_one() {
  local name="$1"
  local dir="$2"
  echo
  echo "==> [$name] check / test / pack"
  (
    cd "$dir"
    npm run check
    npm test
    npm run pack:dry
    if [[ "$DRY_RUN" -eq 1 ]]; then
      echo "==> [$name] dry-run publish"
      npm publish --access public --dry-run --userconfig "$USERCONFIG"
    else
      echo "==> [$name] publish"
      npm publish --access public --userconfig "$USERCONFIG"
    fi
  )
}

for target in "${TARGETS[@]}"; do
  case "$target" in
    react) publish_one "react" "$ROOT/react-web/library" ;;
    vue) publish_one "vue" "$ROOT/vue-web/library" ;;
    all)
      publish_one "react" "$ROOT/react-web/library"
      publish_one "vue" "$ROOT/vue-web/library"
      ;;
  esac
done

echo
if [[ "$DRY_RUN" -eq 1 ]]; then
  echo "Done (dry-run). No packages were published."
else
  echo "Done. Published requested package(s)."
fi
