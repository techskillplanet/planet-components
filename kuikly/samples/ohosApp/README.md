# Kuikly HarmonyOS Sample Host

This directory is the HarmonyOS host entry for the TechSkillPlanet Kuikly sample.

The current Gradle build uses standard Kotlin `2.1.21`, which does not provide an OHOS Kotlin target. A full runnable HarmonyOS Kuikly host requires the OHOS Kotlin toolchain, DevEco Studio/Hvigor, and the Kuikly OHOS renderer dependency set.

The page to load from the shared Kuikly business code is:

```text
BasicControlsSample
```

## Expected Local Check

After installing the OHOS Kotlin/DevEco toolchain and wiring the Kuikly OHOS renderer, run the host from this directory with Hvigor:

```bash
hvigorw assembleHap
```
