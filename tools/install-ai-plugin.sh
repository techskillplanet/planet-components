#!/usr/bin/env bash
# Install / unlink TechSkillPlanet Planet Components Agent Skills into local AI clients.
# Skills source: .agents/skills  (manifest: .agents/plugin-manifest.json)
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SRC="$ROOT/.agents/skills"
MANIFEST="$ROOT/.agents/plugin-manifest.json"
CURSOR_PROJECT_LINK="$ROOT/.cursor/skills"

DRY_RUN=0
UNLINK=0
FILTER=""

usage() {
  cat <<'EOF'
Usage: tools/install-ai-plugin.sh [options] [cursor|claude|codex|agents|all]

Install Planet Components Agent Skills (symlink) into local skill directories.

For public / everyday install without cloning this repo, prefer:
  npx skills add techskillplanet/planet-components

Options:
  --dry-run   Print actions only
  --unlink    Remove symlinks previously created for these skill names
  -h, --help  Show help

Examples:
  ./tools/install-ai-plugin.sh
  ./tools/install-ai-plugin.sh cursor
  ./tools/install-ai-plugin.sh --dry-run
  ./tools/install-ai-plugin.sh --unlink all
EOF
}

while [[ $# -gt 0 ]]; do
  case "$1" in
    --dry-run) DRY_RUN=1; shift ;;
    --unlink) UNLINK=1; shift ;;
    -h|--help) usage; exit 0 ;;
    cursor|claude|codex|agents|all) FILTER="$1"; shift ;;
    *) echo "Unknown arg: $1" >&2; usage; exit 1 ;;
  esac
done

FILTER="${FILTER:-all}"

if [[ ! -d "$SRC" ]]; then
  echo "Missing skills dir: $SRC" >&2
  exit 1
fi

if [[ ! -f "$MANIFEST" ]]; then
  echo "Missing manifest: $MANIFEST" >&2
  exit 1
fi

VERSION="$(python3 -c "import json; print(json.load(open('$MANIFEST'))['version'])" 2>/dev/null || echo unknown)"
SKILL_NAMES=()
while IFS= read -r line; do
  [[ -n "$line" ]] && SKILL_NAMES+=("$line")
done < <(python3 -c "import json; print('\n'.join(json.load(open('$MANIFEST'))['skills']))" 2>/dev/null || ls -1 "$SRC")

if [[ ${#SKILL_NAMES[@]} -eq 0 ]]; then
  echo "No skills found in manifest or $SRC" >&2
  exit 1
fi

echo "==> Planet Components Agent Skills v${VERSION}"
echo "==> Source: $SRC"

expand_home() {
  local p="$1"
  if [[ "$p" == ~* ]]; then
    echo "${HOME}${p#\~}"
  else
    echo "$p"
  fi
}

targets_for() {
  case "$1" in
    cursor) echo "$HOME/.cursor/skills" ;;
    claude) echo "$HOME/.claude/skills" ;;
    codex) echo "$HOME/.codex/skills" ;;
    agents) echo "$HOME/.agents/skills" ;;
    all)
      echo "$HOME/.cursor/skills"
      echo "$HOME/.claude/skills"
      echo "$HOME/.codex/skills"
      echo "$HOME/.agents/skills"
      ;;
  esac
}

ensure_project_cursor_link() {
  mkdir -p "$ROOT/.cursor"
  if [[ "$DRY_RUN" -eq 1 ]]; then
    echo "DRY-RUN: ln -sfn .agents/skills -> .cursor/skills (project)"
    return
  fi
  if [[ -L "$CURSOR_PROJECT_LINK" || ! -e "$CURSOR_PROJECT_LINK" ]]; then
    ln -sfn ../.agents/skills "$CURSOR_PROJECT_LINK"
    echo "Project link: .cursor/skills -> .agents/skills"
  else
    echo "Skip project .cursor/skills (exists and is not a symlink)"
  fi
}

link_or_unlink() {
  local dest_root="$1"
  mkdir -p "$dest_root"
  local name src dest
  for name in "${SKILL_NAMES[@]}"; do
    src="$SRC/$name"
    dest="$dest_root/$name"
    if [[ ! -d "$src" ]]; then
      echo "WARN: missing skill dir $src" >&2
      continue
    fi
    if [[ "$UNLINK" -eq 1 ]]; then
      if [[ -L "$dest" ]]; then
        if [[ "$DRY_RUN" -eq 1 ]]; then
          echo "DRY-RUN: rm $dest"
        else
          rm -f "$dest"
          echo "Unlinked $dest"
        fi
      fi
      continue
    fi
    if [[ "$DRY_RUN" -eq 1 ]]; then
      echo "DRY-RUN: ln -sfn $src $dest"
    else
      ln -sfn "$src" "$dest"
      echo "Linked $dest"
    fi
  done
}

if [[ "$UNLINK" -eq 0 ]]; then
  ensure_project_cursor_link
fi

while IFS= read -r t; do
  [[ -z "$t" ]] && continue
  echo "==> Target: $t"
  link_or_unlink "$t"
done < <(targets_for "$FILTER")

echo
if [[ "$DRY_RUN" -eq 1 ]]; then
  echo "Done (dry-run)."
elif [[ "$UNLINK" -eq 1 ]]; then
  echo "Done. Symlinks removed for selected targets."
else
  echo "Done. Restart or start a new Agent chat to pick up skills."
  echo "Docs: docs/AI_PLUGIN.md"
fi
