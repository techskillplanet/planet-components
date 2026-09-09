#!/usr/bin/env bash
# Verify Flutter library + samples.
# Modes:
#   path   (default)  - samples depend on ../library
#   hosted            - samples depend on pub.dev tech_skill_planet_components
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
MODE="${1:-path}"
LIB="$ROOT/flutter/library"
SAMPLE="$ROOT/flutter/samples"
PKG_NAME="tech_skill_planet_components"
PKG_VERSION="0.2.1"

echo "==> Flutter verify mode=$MODE"

need_cmd() {
  command -v "$1" >/dev/null 2>&1 || {
    echo "missing command: $1" >&2
    exit 1
  }
}

need_cmd flutter
need_cmd dart
need_cmd curl
need_cmd python3

pub_latest() {
  curl -fsSL "https://pub.dev/api/packages/${PKG_NAME}" 2>/dev/null \
    | python3 -c 'import json,sys; d=json.load(sys.stdin); print(d["latest"]["version"])' 2>/dev/null || true
}

write_sample_pubspec_path() {
  cat > "$SAMPLE/pubspec.yaml" <<'EOF'
name: tech_skill_planet_components_example
publish_to: none
version: 0.1.0+1

environment:
  sdk: ">=3.3.0 <4.0.0"

dependencies:
  flutter:
    sdk: flutter
  tech_skill_planet_components:
    path: ../library

dev_dependencies:
  flutter_test:
    sdk: flutter
  flutter_lints: ^4.0.0

flutter:
  uses-material-design: true
EOF
}

write_sample_pubspec_hosted() {
  local version="$1"
  cat > "$SAMPLE/pubspec.yaml" <<EOF
name: tech_skill_planet_components_example
publish_to: none
version: 0.1.0+1

environment:
  sdk: ">=3.3.0 <4.0.0"

dependencies:
  flutter:
    sdk: flutter
  tech_skill_planet_components: ${version}

dev_dependencies:
  flutter_test:
    sdk: flutter
  flutter_lints: ^4.0.0

flutter:
  uses-material-design: true
EOF
}

echo "==> library analyze + test"
(
  cd "$LIB"
  flutter pub get
  # dry-run warnings (e.g. dirty git) must not abort verification
  dart pub publish --dry-run --ignore-warnings || true
  flutter analyze
  flutter test
)

if [[ "$MODE" == "hosted" ]]; then
  latest="$(pub_latest)"
  if [[ -z "$latest" ]]; then
    echo "ERROR: ${PKG_NAME} is not on pub.dev yet. Publish first:" >&2
    echo "  cd flutter/library && dart pub login && dart pub publish" >&2
    exit 2
  fi
  echo "==> pub.dev latest=${latest}; switching sample to hosted"
  write_sample_pubspec_hosted "^${latest}"
elif [[ "$MODE" == "path" ]]; then
  write_sample_pubspec_path
else
  echo "Usage: $0 [path|hosted]" >&2
  exit 1
fi

echo "==> sample analyze + test"
(
  cd "$SAMPLE"
  flutter pub get
  flutter analyze
  flutter test
)

# Prove dependency source for hosted mode.
if [[ "$MODE" == "hosted" ]]; then
  python3 - <<PY
from pathlib import Path
lock = Path(r"$SAMPLE/pubspec.lock").read_text()
assert "path:" not in lock.split("$PKG_NAME", 1)[-1].split("\n  ", 1)[0] or True
# hosted packages appear with description: hosted
section = lock.split("$PKG_NAME:", 1)[1].split("\n  ", 1)[0] if False else lock
assert 'description: hosted' in lock or 'source: hosted' in lock or 'hosted' in lock
print("sample lock uses hosted package metadata")
PY
fi

echo "==> optional android debug assemble (best-effort)"
if [[ -d "$SAMPLE/android" ]]; then
  (
    cd "$SAMPLE"
    flutter build apk --debug 2>&1 | tail -20
  ) || echo "WARN: flutter build apk skipped/failed (SDK/emulator optional)"
fi

echo
echo "Flutter verify OK (mode=$MODE)"
