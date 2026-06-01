# Platform Structure Rules

These rules apply to every Planet Components implementation.

## Component Files

- One component must live in one source file.
- The component file should contain the platform class/function/widget for that component.
- Barrel/index files may only re-export public APIs.
- Shared code belongs in a `theme`, `utils`, `styles`, `navigation`, or equivalent shared folder.

## Page Files

- One sample/demo page must live in one page file.
- A page file may compose multiple components, but it must not contain component implementations.
- Detail/demo pages should be route targets, not anonymous inline blocks inside a single large sample file.

## Routing

- Route definitions and route state should live in a dedicated navigation/router file.
- Page files should receive navigation callbacks through props, bindings, or the platform router.
- Sample apps should make the route tree easy to scan before reading page implementation details.

## Platform Mapping

- Android View: one Java class per component, one Activity/Fragment/View page per sample screen.
- React Native: one JS component file, one JS sample page file, centralized router.
- React Web: one component file per component, one route/page file per sample page.
- Vue Web: one component file per component, one route/page file per sample page.
- Flutter: one Dart widget file per component, one Dart page file per sample page.
- iOS SwiftUI: one Swift type per component, one SwiftUI page file per sample screen.
- WeChat Mini Program: one component folder per component, one page folder per sample screen.
- Kuikly: one Kotlin component class/file per component, one page file per sample screen.

## Current Target Layout

```text
<stack>/
├── library/      # independent package source
└── samples/      # runnable project depending on ../library
```

## Verification

```bash
node tools/check-structure.cjs
```

Platform-specific commands are listed in the root `README.md`.

