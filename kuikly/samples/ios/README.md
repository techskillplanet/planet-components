# Kuikly iOS Sample Host

This directory is the iOS host entry for the TechSkillPlanet Kuikly sample.

The shared Kuikly UI code now builds an iOS framework from `kuikly/library/shared`.

## Build

From `kuikly/`:

```bash
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

Generated framework:

```text
library/shared/build/bin/iosSimulatorArm64/debugFramework/PhonicsPlanetKuiklyShared.framework
```

## Host Integration

Create or open an Xcode app target, embed the generated framework, and initialize the Kuikly native renderer with page name:

```text
BasicControlsSample
```

The native iOS renderer host is intentionally separate from the shared Kotlin framework because it depends on app-level signing, bundle identifier, simulator/device selection, and the native Kuikly renderer dependency configuration.
