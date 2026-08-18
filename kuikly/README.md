# Kuikly · Planet Components

Publishable identity:

- Gradle group: `io.github.techskillplanet`
- Version: `0.2.0`
- iOS framework: `PlanetComponentsKuiklyShared`
- Android namespace: `com.techskillplanet.planetcomponents.kuikly`

Public UI controls live under `library/shared/.../phonics/controls/Tsp*.kt` (one component per file, `Tsp*` names matching `docs/COMPONENT_CONTRACT.md`). Phonics lesson pages remain in the `phonics` packages as extra sample content that consumes those controls.

## Samples

- WeChat Mini Program: `samples/miniApp`
- Android host: `samples/androidApp` (opens `@Page("BasicControlsSample")`)

```bash
cd kuikly
./gradlew :androidApp:installDebug
adb shell am start -n com.techskillplanet.planetcomponents.kuikly.sample/.BasicControlsActivity
```
