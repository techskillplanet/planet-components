---
name: build-android-view-ui
description: Build or extend the Android View implementation of TechSkillPlanet Planet Components using Java, XML, and the traditional Android View system. Use when adding token-driven Android components, samples, animations, or migration support without Compose or Kotlin.
---

# Build Android View UI

## Core Rule

Build with Java + Android View only. Do not introduce Compose, Kotlin, AppCompat, Material, or hidden external UI dependencies unless the user explicitly asks for them.

Use two JSON files as the source of visual truth:

- `design/tokens/color_token.json`: primitive colors plus semantic component/state colors.
- `design/tokens/style_token.json`: radius, spacing, typography, component sizes, border widths, opacity, motion, and shadow values.

Load [references/token-view-workflow.md](references/token-view-workflow.md) when implementing or migrating Android View components.

## Workflow

1. Inspect `android-view/library` and `android-view/samples` before writing code.
   - Find Gradle structure, package names, SDK versions, token files, and existing sample coverage.
   - Preserve conventions and avoid unrelated refactors.

2. Establish token infrastructure first.
   - Keep token keys meaningful and semantic, e.g. `semantic.control.button.primaryBackground`, not numeric shade names.
   - Runtime theme classes should resolve JSON references before widgets use them.
   - Component code should read typed runtime theme values, not raw JSON paths.

3. Implement components as reusable Views.
   - Public classes and public methods need concise Chinese Javadoc.
   - Component code should read from runtime token classes.
   - Expose a small common API when useful:
     - `setVariant(String variant)`
     - `setBasicText(CharSequence text)`
     - `setSelectedState(boolean selected)`
     - `setBasicDisabled(boolean disabled)`
     - `refreshTheme()`

4. Keep design and Android aligned.
   - When adding a component token, add it to shared token JSON.
   - Use the same semantic names Penpot/Figma generation scripts can consume.
   - Prefer token additions over one-off Java constants when a visual value is themeable.

5. Add or update runnable samples.
   - Samples must depend on `android-view/library`.
   - Samples should cover all component states.
   - Include real scenarios: default, selected, disabled, loading, error, success, warning, empty, refresh/load-more.

6. Validate.
   - Parse both token JSON files.
   - Run Gradle assemble for library and samples.
   - Search for accidental Compose/Kotlin/AppCompat/Material dependencies.
   - Report unavoidable platform warnings.

## Component Standards

Prefer this architecture:

- `theme/`: token parsing and typed runtime models.
- `drawable/`: `GradientDrawable`, `StateListDrawable`, and common shape factories.
- `widget/`: public View components.
- `samples/`: runnable Android application for manual visual review.

For interaction-heavy components:

- Use Android state (`enabled`, `selected`, `pressed`, `focused`) where it maps cleanly.
- For animated custom views, stop animators in `onDetachedFromWindow`.
- Keep animation colors, durations, sizes, opacity, and dimensions token-driven.
- Avoid image assets for simple spinners or planets; draw with Canvas when feasible.

## Visual Direction

Use the 技趣星球 / Sky Planet style:

- Blue-sky brand primary.
- Cloud-like raised surfaces.
- Organic rounded cards/dialogs.
- Island-style lifted controls.
- Soft borders and playful motion.
- State colors: aurora success, sun warning, coral danger.

When referencing animal-island-ui:

- Preserve the interaction language: chunky borders, rounded shapes, handle/track motion, loading spinner, and playful affordances.
- Recolor through Sky Planet tokens rather than copying beige/brown source colors.

## Verification Commands

```bash
node -e "JSON.parse(require('fs').readFileSync('design/tokens/color_token.json','utf8')); JSON.parse(require('fs').readFileSync('design/tokens/style_token.json','utf8')); console.log('token json ok')"
cd android-view && ./gradlew :library:assembleRelease :samples:assembleDebug
rg -n "Compose|compose|kotlin|org.jetbrains|androidx.appcompat|material" android-view -g '!**/build/**' -g '!**/.gradle/**'
```

