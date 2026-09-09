# Cross-stack walkthrough vs React Web (2026-09-08)

Reference: `react-web/library` — **35** public `Tsp*` components + Sky/Night/Mint/Sunrise themes.

## Gaps found (before)

| Gap | Stacks |
| --- | --- |
| Domain-7 + DatePicker missing | Vue (Domain-7 only), RN, Flutter, Android, iOS, Mini, Kuikly |
| Theme: old `selectedFill`/`success` + no sunrise | RN, Flutter, iOS, Android tokens, Kuikly; Vue theme drift; Mini `themeClass` incomplete |
| `Button.loading` | All non-React stacks |

Domain-7: ChildSwitcher, ScoreRuleGrid, RedeemCardGrid, CalendarHeatmap, PrintSheet, BalanceHero, CheckInStreakCard.

## Filled (this pass)

| Area | Result |
| --- | --- |
| Component inventory | All stacks now expose DatePicker + Domain-7 (matrix verified Y) |
| Theme | sky/night/mint semantic colors aligned to React; **sunrise** added where missing; Mini theme classes; Android `sunrise_planet_day` |
| Button.loading | Vue / RN / Flutter / iOS / Mini / Android / Kuikly |
| Vue Web | Full parity with React (35 exports + sample previews) |
| Structure | `node tools/check-structure.cjs` PASS |

## Headless verification (2026-09-09)

Runner: `./tools/headless-check.sh`

| Check | Result |
| --- | --- |
| structure | PASS |
| react-web `check` + vitest + pack:dry | PASS (99 tests) |
| react-web sample esbuild | PASS (fixed `preview-entry` → `BasicControlsSample`) |
| vue-web `check` + vitest + pack:dry | PASS (98 tests) |
| vue-web sample esbuild | PASS |
| react-native vitest + pack:dry | PASS (20 tests, Domain-7 smoke) |
| react-native samples vitest | PASS (4 tests) |
| miniprogram pack:dry + js `--check` | PASS (35 components) |
| flutter analyze + test (lib) / analyze (samples) | PASS (11 tests) |
| ios `swift build` | PASS |
| android `:library:assembleRelease` + `:samples:assembleDebug` | PASS |
| kuikly `:shared:compileDebugKotlinAndroid` + `:androidApp:assembleDebug` | PASS |

## 0.2.1 quality gate (2026-09-09)

Version pins across eight libraries → **0.2.1**. Registry publish deferred until commit + explicit publish request.

| Check | Result |
| --- | --- |
| structure | PASS |
| react/vue/rn/mini pack + tests | PASS |
| web/RN samples | PASS |
| flutter analyze/test + pub dry-run | PASS (git dirty warning until commit) |
| ios swift build | PASS |
| android/kuikly assemble | PASS |

## Remaining (P2/P3, not blocking inventory)

- Sample pages for Domain-7 on RN / Flutter / Android / Kuikly (docs/i18n heavier) — Vue/React samples covered
- Event naming docs (`onTap` vs `onPress` / `onClick` / `action`) — platform idioms kept
- Flutter `standard` ↔ `default` variant naming; Android-only extras not backported to React
- Kuikly `PhonicsTheme` brand rename vs `StarPlanetTheme`
- Island style profiles still thinner on Flutter / iOS / Mini / Kuikly than React/Vue/RN

## Verify

```bash
node tools/check-structure.cjs
```
