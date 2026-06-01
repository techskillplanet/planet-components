# TechSkillPlanet Planet Components

This repository contains cross-platform basic component libraries for TechSkillPlanet.

## Product Direction

- Brand: 技趣星球
- Slogan: 用技术创造乐趣
- UI direction: Sky Planet, with blue-sky primary color, cloud-like surfaces, rounded island controls, soft borders, and playful but usable motion.
- Visual reference: animal-island-ui interaction language, recolored and re-tokenized through TechSkillPlanet tokens.

## Repository Rules

- Every technology stack is a first-level directory.
- Every stack contains an independent publishable `library` and a runnable `samples` project.
- Samples must depend on the local library. They must not duplicate component implementation code.
- One component lives in one source file.
- One sample/demo page lives in one page file.
- Routing/navigation is centralized in a dedicated file or folder.
- Barrel/index files only export public APIs.
- Shared theme values come from `design/tokens/color_token.json` and `design/tokens/style_token.json`.

## Supported Stacks

| Stack | Directory | Package target |
| --- | --- | --- |
| Android View | `android-view/` | Maven / Gradle |
| React Native | `react-native/` | npm |
| React Web | `react-web/` | npm |
| Vue Web | `vue-web/` | npm |
| Flutter | `flutter/` | pub.dev |
| iOS SwiftUI | `ios-swiftui/` | Swift Package Manager |
| WeChat Mini Program | `miniprogram/` | npm / miniprogram package |
| Kuikly | `kuikly/` | Maven / internal package |

## Token Rules

- `color_token.json` owns primitive colors and semantic color usage.
- `style_token.json` owns radius, spacing, typography, component size, border width, opacity, motion, and shadow values.
- Token keys must be meaningful and semantic, for example `semantic.control.button.primaryBackground`, not `blue.500`.
- Platform code should consume typed theme/runtime objects instead of hardcoding visual values.
- Figma, Penpot, Android, iOS, Flutter, Web, React Native, Mini Program, and Kuikly implementations should use the same semantic token names.

## Implementation Rules

- Keep edits scoped to the requested stack or shared token contract.
- Do not introduce hidden UI framework dependencies.
- Android View stays Java + XML + traditional View, without Compose or Kotlin unless explicitly requested.
- React Native and Flutter samples must include real Android and iOS platform projects.
- Public component APIs should be small and consistent across stacks:
  - `variant`
  - `disabled`
  - `selected` / `checked`
  - `text` / `title` / `message`
  - `refreshTheme` or platform equivalent when runtime theming is supported

## Verification

Always run the broad structure check after structural changes:

```bash
node tools/check-structure.cjs
```

Run platform checks for changed stacks. Current expected commands are documented in `README.md` and `docs/PLATFORM_STRUCTURE.md`.

