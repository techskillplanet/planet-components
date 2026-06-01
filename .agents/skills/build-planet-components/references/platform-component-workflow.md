# Platform Component Workflow

## Target Repository Shape

```text
planet-components/
├── design/tokens/
│   ├── color_token.json
│   └── style_token.json
├── android-view/
│   ├── library/
│   └── samples/
├── react-native/
│   ├── library/
│   └── samples/
├── react-web/
│   ├── library/
│   └── samples/
├── vue-web/
│   ├── library/
│   └── samples/
├── flutter/
│   ├── library/
│   └── samples/
├── ios-swiftui/
│   ├── library/
│   └── samples/
├── miniprogram/
│   ├── library/
│   └── samples/
└── kuikly/
    ├── library/
    └── samples/
```

## Token Naming

Use stable semantic names that explain purpose:

- Good: `semantic.control.button.primaryBackground`
- Good: `semantic.control.switch.handleCheckedBorder`
- Good: `size.switch.md.handle`
- Good: `motion.switch.duration`
- Avoid: `blue.500`, `gray.100`, `switchColor1`

Primitive tokens describe brand language. Semantic tokens describe UI usage.

## Component Checklist

For each component:

- Component lives in its own source file.
- Public props/API are documented in the platform style.
- Background, text, border, size, and motion are token-backed.
- Disabled, selected/checked, loading, and error states are implemented where applicable.
- The component is exported by the library barrel/index.
- The component appears in samples with all important states.

## Animation Checklist

For animated components:

- Prefer platform-native animation primitives.
- Stop timers/animators/subscriptions when the component leaves the view tree.
- Keep duration, opacity, size, and colors token-backed when themeable.
- Prefer vector/canvas/platform drawing for simple themed animations instead of static image assets.

## Native Platform Checklist

React Native and Flutter samples must include:

- Android project files.
- iOS project files.
- Stable Android application id.
- Stable iOS bundle id.
- A successful Android debug build.
- A successful iOS simulator build when Xcode is available.

## Package Readiness Checklist

Before declaring a stack publish-ready:

- Package metadata points to `techskillplanet/planet-components`.
- Generated build outputs are ignored.
- Sample depends on the local library.
- Dry-run packaging command succeeds when supported.
- README explains installation and basic usage.

