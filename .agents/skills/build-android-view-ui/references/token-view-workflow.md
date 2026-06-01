# Token-Driven Android View Workflow

## Target Structure

```text
android-view/
├── settings.gradle
├── build.gradle
├── library/
│   ├── build.gradle
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── assets/theme/color_token.json
│       ├── assets/theme/style_token.json
│       ├── java/<package>/theme/
│       ├── java/<package>/drawable/
│       ├── java/<package>/widget/
│       └── res/values/attrs.xml
└── samples/
    ├── build.gradle
    └── src/main/java/<sample-package>/MainActivity.java
```

## Android Runtime Mapping

Map JSON into typed runtime classes:

- `BasicColors`: resolved `int` colors.
- `BasicStyle`: resolved px/sp/duration/opacity values.
- `BasicTokenResolver`: path lookup, `{primitive.xxx}` reference resolution, color parsing, dp/sp conversion.
- `BasicThemeManager`: `init(Context)` and cached `colors()` / `style()`.

Components should call `BasicThemeManager.colors()` and `BasicThemeManager.style()` only. They should not parse JSON.

## Component Checklist

For each component:

- Public Java class in `widget/`.
- Chinese Javadoc for public class and public methods.
- Token-backed background, text, border, size, and motion.
- `refreshTheme()` applies current runtime theme.
- XML attrs if useful: `basicVariant`, `basicText`, `basicTitle`, `basicMessage`, `basicSelected`, `basicDisabled`.
- Samples coverage for normal, selected/focused, disabled, loading/error states as applicable.

## Animation Checklist

For custom animated Views:

- Use `ValueAnimator` or `ObjectAnimator`.
- Start in `onAttachedToWindow` when the animation is ambient.
- Stop and null out in `onDetachedFromWindow`.
- Keep duration and colors token-backed when themeable.
- Prefer Canvas for simple themed animation instead of static image assets.

## Switch Coverage Checklist

Animal-island-ui parity for Switch means:

- `checked` state via selected/isChecked.
- default and setter-controlled state.
- small and default size.
- disabled.
- loading blocks click and shows spinner.
- checked/unchecked inner text.
- state change callback.
- track background and border tokens.
- handle background and checked border tokens.
- inner text size token.
- handle motion duration token.
- loading/disabled opacity tokens.

## Refresh / Load / Loading Checklist

Refresh and loading components should include:

- pull-to-refresh state
- load-more state
- disabled state
- themed motion duration
- themed spinner/planet animation
- explicit finish APIs such as `finishRefresh()` and `finishLoadMore()`
- modal loading with semi-transparent scrim and explicit dismissible behavior

