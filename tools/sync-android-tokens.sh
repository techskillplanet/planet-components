#!/usr/bin/env bash
# Rebuild runtime themes and sync Android token assets from design/tokens.
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
node "$ROOT/tools/build-tokens.cjs"
node "$ROOT/tools/check-token-drift.cjs"
