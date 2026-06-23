# Contributing to Planet Components

Thank you for contributing to TechSkillPlanet Planet Components. This repository hosts cross-platform basic UI libraries that share design tokens and a common component contract.

## Before You Start

Read these documents first:

- [README.md](README.md) — platform layout and local checks
- [AGENTS.md](AGENTS.md) — repository engineering rules
- [docs/PLATFORM_STRUCTURE.md](docs/PLATFORM_STRUCTURE.md) — one-component-one-file, routing, samples
- [docs/COMPONENT_CONTRACT.md](docs/COMPONENT_CONTRACT.md) — cross-platform API and variants
- [component_contract.json](component_contract.json) — machine-readable contract

Shared tokens live in `design/tokens/`. Platform libraries consume them through typed theme/runtime objects; do not hardcode visual values in components.

## Repository Layout

Every stack is a first-level directory with the same shape:

```text
<stack>/
├── library/   # publishable package
└── samples/   # runnable demo; depends on ../library only
```

Samples must not duplicate library implementation code.

## Adding or Changing a Component

1. **Pick one stack** (or align all stacks if the change is cross-platform).
2. **One component = one source file** under that stack's `library/`.
3. **Use semantic tokens** from `color_token.json` / `style_token.json`.
4. **Keep public APIs small and consistent**: `variant`, `disabled`, `selected` / `checked`, `text` / `title` / `message`, and `refreshTheme` (or platform equivalent).
5. **Update the contract** when adding variants or props: `docs/COMPONENT_CONTRACT.md` and `component_contract.json`.
6. **Add or extend a sample page** — one demo page per file; route through the stack's centralized router (for example `AppRouter.js`, `SampleRouter.java`).
7. **Barrel/index files** export public APIs only; no component logic in index files.

## Adding a Sample Page

- One page file per screen (home, settings, component detail, etc.).
- Navigation callbacks go through the router or a page host interface — not ad-hoc globals inside `MainActivity` / `App.js`.
- Sample copy belongs in i18n assets or locale files, not hardcoded strings in page code where i18n is already set up.

## Platform Constraints

| Stack | Constraint |
| --- | --- |
| Android View | Java + traditional View; no Compose / androidx in library |
| React Native / Flutter samples | Must include real Android and iOS sample projects |
| Kuikly | One Kotlin component class per component |

Do not introduce hidden UI framework dependencies beyond what each stack already uses.

## Verification

After structural changes, always run:

```bash
node tools/check-structure.cjs
```

Then run checks for the stacks you touched (from repository root):

```bash
cd android && ./gradlew :library:assembleRelease :samples:assembleDebug
cd react-native/library && npm run pack:dry
cd react-native/samples && npm install && npm run check
cd react-web/library && npm run check && npm run pack:dry
cd vue-web/library && npm run check && npm run pack:dry
cd flutter/library && flutter analyze
cd flutter/samples && flutter analyze
cd ios-swiftui/library && swift build
cd ios-swiftui/samples && swift build
cd miniprogram && node -e "const fs=require('fs'); JSON.parse(fs.readFileSync('samples/app.json','utf8')); if(!fs.lstatSync('samples/planet-components').isSymbolicLink()) throw new Error('samples/planet-components should be a symlink')"
cd kuikly && ./gradlew :shared:compileKotlinJs :miniApp:compileKotlinJs :shared:compileDebugKotlinAndroid
```

Fix failures in the same PR when possible.

## Pull Request Guidelines

1. **Scope**: Keep PRs focused on one stack or one cross-cutting concern (tokens, contract, docs).
2. **Description**: What changed, why, and which checks you ran.
3. **No secrets**: Do not commit `local.properties`, keystores, `.env`, or build artifacts (`dist/`, `build/`, `.gradle/`).
4. **No generated noise**: Avoid unrelated formatting or lockfile churn unless required by the change.

## License

By contributing, you agree that your contributions will be licensed under the [MIT License](LICENSE) that covers this project.
