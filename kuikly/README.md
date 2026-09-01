# Kuikly · Planet Components

Publishable identity:

- Gradle group: `io.github.techskillplanet`
- Artifact: `planet-components-kuikly`
- Version: `0.2.0`
- iOS framework: `PlanetComponentsKuiklyShared`
- Android namespace: `com.techskillplanet.planetcomponents.kuikly`

Public UI controls live under `library/shared/.../phonics/controls/Tsp*.kt` (one component per file, `Tsp*` names matching `docs/COMPONENT_CONTRACT.md`). Phonics lesson pages remain in the `phonics` packages as extra sample content that consumes those controls.

## Maven Central

坐标：`io.github.techskillplanet:planet-components-kuikly:0.2.0`

发布流程对齐 Android（同一 Central Portal / GPG 凭证）：

```bash
kuikly/scripts/publish-maven-central.sh --dry-run
kuikly/scripts/publish-maven-central.sh
# → https://central.sonatype.com/publishing/deployments 点 Publish
```

凭证模板：`gradle.properties.example`（也可直接复用 `android/gradle.properties`）。库说明：[`library/README.md`](library/README.md)。

## Samples

- WeChat Mini Program: `samples/miniApp`
- Android host: `samples/androidApp` (opens `@Page("BasicControlsSample")`)

```bash
cd kuikly
./gradlew :androidApp:installDebug
adb shell am start -n com.techskillplanet.planetcomponents.kuikly.sample/.BasicControlsActivity
```
