---
name: build-planet-components
description: Build, migrate, or audit TechSkillPlanet cross-platform component libraries. Use when creating reusable UI components across Android View, React Native, React Web, Vue Web, Flutter, iOS SwiftUI, WeChat Mini Program, or Kuikly with shared tokens, runnable samples, package-ready libraries, and one-component-one-file structure.
---

# Build Planet Components

## Core Rule

Build a package-ready component library and a runnable sample for every target stack.

Shared visual truth comes from:

- `design/tokens/color_token.json`
- `design/tokens/style_token.json`

Load [references/platform-component-workflow.md](references/platform-component-workflow.md) before major implementation or migration work.

## Workflow

1. Inspect the target stack before editing.
   - Find `library`, `samples`, package identity, build tool, router, token loading, and current component coverage.
   - Preserve local conventions and avoid unrelated refactors.

2. Keep structure consistent.
   - One component per file.
   - One sample page per file.
   - Dedicated route/navigation file.
   - Sample depends on the local library.
   - Barrel/index files only export public APIs.

3. Keep APIs aligned across stacks.
   - Prefer common names: `variant`, `text`, `title`, `message`, `disabled`, `selected`, `checked`, `loading`, `onTap`, `onChange`.
   - Platform-specific names are allowed only where the platform convention is stronger.
   - Keep all variants listed in `docs/COMPONENT_CONTRACT.md`.

4. Keep visual implementation token-driven.
   - Add semantic token keys before adding hardcoded visual constants.
   - Use meaningful token names, for example `semantic.control.switch.onBackground`.
   - Avoid numeric shade names as public skinning contract.

5. Samples must prove the library works.
   - Samples should show all components and important states.
   - React Native and Flutter samples must include Android and iOS platform projects.
   - Samples should be runnable without copying source from `library`.

6. Validate changed stacks.
   - Run `node tools/check-structure.cjs`.
   - Run stack-specific package/build/sample commands from `README.md`.
   - For native stacks, build real platform targets when the platform code changed.

## Visual Direction

Use the TechSkillPlanet Sky Planet style:

- sky-blue brand primary
- cloud-like raised surfaces
- island-style lifted controls
- organic rounded cards and dialogs
- soft borders
- playful but restrained motion
- state colors: aurora success, sun warning, coral danger

When referencing animal-island-ui:

- Preserve the interaction language: chunky borders, rounded shapes, switch track/handle motion, loading animation, and tactile controls.
- Recolor through Sky Planet tokens rather than copying source palette values.

## Platform Notes

- Android View: Java + XML + traditional View. No Compose or Kotlin unless explicitly requested.
- React Native: library exports JS components; samples include Expo/RN Android and iOS native projects.
- Flutter: library exports Dart widgets; samples include generated Android and iOS platform projects.
- iOS SwiftUI: Swift Package library; samples must build as a runnable Swift app or package sample.
- Kuikly: follow Kuikly DSL conventions and keep Kotlin files split by component/page.
- Mini Program: split each component into its own component folder.

## Reporting

When finishing, report:

- changed stacks
- generated or updated samples
- exact validation commands and results
- known warnings that do not block build
- any platform that was not validated and why

