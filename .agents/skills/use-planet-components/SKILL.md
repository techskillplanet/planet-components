---
name: use-planet-components
description: >-
  Consume published TechSkillPlanet Planet Components in an application.
  Use when installing or integrating Tsp*/bc-* UI from npm, Maven Central,
  pub.dev, SPM, or CocoaPods across Android, iOS, React, Vue, RN, Flutter,
  Mini Program, or Kuikly.
---

# Use Planet Components

## For non-engineers (plain language)

Prefer skill **`ai-build-with-planet`**: build screens with the published **React Web** package and Sky theme.  
Copy-paste prompts (also when this skill is installed outside the monorepo):  
https://github.com/techskillplanet/planet-components/blob/main/docs/prompts/zh-quickstart.md  
Non-engineer guide: https://github.com/techskillplanet/planet-components/blob/main/docs/AI_FOR_EVERYONE.md

## When to use

- App team wants **published** packages (not editing this monorepo’s `library/`).
- User mentions install / integrate / 接入 for TechSkillPlanet / 技趣星球 / `Tsp*` / `bc-*`.

## Version

Current public release: **0.2.1** (quality gate; publish after green checks). Prefer `^0.2.1` / `~> 0.2.1` / `from: "0.2.1"`.

Local `main` may include **DatePicker + Domain-7** ahead of registries until the next publish bump — check monorepo / git if you need those APIs before they land on npm / Maven / pub.dev.

## Public API surface

≈ **35** `Tsp*` / `bc-*` / `Basic*View` controls (React Web inventory baseline), including:

- **DatePicker**
- **Domain-7**: ChildSwitcher, ScoreRuleGrid, RedeemCardGrid, CalendarHeatmap, PrintSheet, BalanceHero, CheckInStreakCard

## Themes

Built-in color keys: **sky** / **night** / **mint** / **sunrise** (platform key names differ; see each `integrate-*` skill).

## Button loading

`TspButton` / `BasicButton` / `bc-button` support **`loading`** across stacks (spinner + blocked taps). Platform event / prop names differ (`onTap` vs `bindtap` vs listeners).

## Route by stack

| Stack | Package | Load skill |
| --- | --- | --- |
| React Web | `@techskillplanet/planet-components-react` | [integrate-react-components](../integrate-react-components/SKILL.md) |
| Vue Web | `@techskillplanet/planet-components-vue` | [integrate-vue-components](../integrate-vue-components/SKILL.md) |
| React Native | `@techskillplanet/planet-components-react-native` | [integrate-react-native-components](../integrate-react-native-components/SKILL.md) |
| Flutter | `tech_skill_planet_components` | [integrate-flutter-components](../integrate-flutter-components/SKILL.md) |
| Android View | `io.github.techskillplanet:planet-components-android` | [integrate-android-view](../integrate-android-view/SKILL.md) |
| iOS SwiftUI | `PlanetComponents` (SPM/CocoaPods) | [integrate-ios-swiftui](../integrate-ios-swiftui/SKILL.md) |
| WeChat Mini Program | `@techskillplanet/planet-components-miniprogram` | [integrate-miniprogram](../integrate-miniprogram/SKILL.md) |
| Kuikly | `io.github.techskillplanet:planet-components-kuikly` | [integrate-kuikly](../integrate-kuikly/SKILL.md) |

1. Identify the host stack.
2. Read the matching integrate skill and follow it.
3. Do **not** copy component source from this repo into the app.

## Shared rules

- Import / init theme once (Sky Planet tokens).
- Prefer public APIs: `variant`, `disabled`, `selected`/`checked`, `text`/`title`/`message`, `loading` (Button).
- Samples in **this** repo must keep local `library` deps; apps use registry coords.

## Extending the libraries

If the task is to change components inside `planet-components` itself, use [build-planet-components](../build-planet-components/SKILL.md) instead.
