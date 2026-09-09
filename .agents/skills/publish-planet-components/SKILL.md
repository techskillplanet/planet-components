---
name: publish-planet-components
description: >-
  Publish TechSkillPlanet Planet Components libraries to public registries
  (npm, Maven Central, pub.dev, SPM/CocoaPods). Use when releasing a new version,
  dry-running packs, bumping versions after Domain-7/theme work, or debugging
  publish auth (NPM_TOKEN, Sonatype, GPG, dart pub).
---

# Publish Planet Components

## Scope

Publish **library** packages only. Samples stay on path/local library deps.

| Stack | Package | Registry |
| --- | --- | --- |
| React Web | `@techskillplanet/planet-components-react` | npm |
| Vue Web | `@techskillplanet/planet-components-vue` | npm |
| React Native | `@techskillplanet/planet-components-react-native` | npm |
| Mini Program | `@techskillplanet/planet-components-miniprogram` | npm |
| Android View | `io.github.techskillplanet:planet-components-android` | Maven Central |
| Kuikly | `io.github.techskillplanet:planet-components-kuikly` | Maven Central |
| Flutter | `tech_skill_planet_components` | pub.dev |
| iOS SwiftUI | `PlanetComponents` | SPM git tag + CocoaPods |

**Target release: **0.2.1**.** Local `main` may include DatePicker + Domain-7 + sunrise + `Button.loading` not yet on registries — **bump version before the next publish** (recommended `0.2.1` or `0.3.0`).

Catalog baseline: React Web ≈ **35** `Tsp*` (see `component_contract.json`). Themes: `sky` / `night` / `mint` / `sunrise` (Android keys: `sky_planet_day`, `star_planet_night`, `mint_planet_day`, `sunrise_planet_day`).

## Pre-flight (mandatory)

1. Align version in every target `library` (`package.json` / `pubspec.yaml` / Gradle `version` / iOS tag + podspec).
2. `node tools/check-structure.cjs`
3. Headless suite (preferred):

```bash
./tools/headless-check.sh
```

Or per-stack (from `PUBLISHING.md` / `README.zh-CN.md` Local checks):

| Stack | Headless check |
| --- | --- |
| React / Vue | `npm run check && npm test && npm run pack:dry` (+ `samples` `npm run build`) |
| RN | `npm test && npm run pack:dry` |
| Mini Program | `npm run pack:dry` + `node --check` on component JS |
| Flutter | `flutter analyze` + `flutter test` in `flutter/library` |
| iOS | `cd ios-swiftui/library && swift build` |
| Android | `./gradlew :library:assembleRelease :samples:assembleDebug` |
| Kuikly | `./gradlew :shared:compileDebugKotlinAndroid :androidApp:assembleDebug` |

4. Samples **must** keep local library deps — do not point samples at the new registry version for release verification (except `./tools/verify-flutter-sample.sh hosted` after Flutter publish).

5. Confirm build caches / secrets are not packaged.

## Stack playbooks

| Stack | Command / flow |
| --- | --- |
| React / Vue / RN / Mini Program | `./tools/publish-npm-web.sh [--dry-run] [react\|vue\|rn\|miniprogram\|all]` — needs `tools/publish-npm-web.env` with Bypass-2FA `NPM_TOKEN` |
| Android View | `android/scripts/publish-maven-central.sh` → Portal Deployments → Publish |
| Kuikly KMP | `kuikly/scripts/publish-maven-central.sh` → Portal Deployments → Publish (reuse `android/gradle.properties` Portal + GPG) |
| Flutter | `unset PUB_HOSTED_URL` / use official `pub.dev`; proxy if needed; `cd flutter/library && dart pub publish` |
| iOS | annotated git tag → push tag (SPM); `pod trunk push PlanetComponents.podspec` (CocoaPods) |

Docs (also work when this skill is installed outside the monorepo):

- https://github.com/techskillplanet/planet-components/blob/main/PUBLISHING.md
- https://github.com/techskillplanet/planet-components/blob/main/android/library/README.md
- https://github.com/techskillplanet/planet-components/blob/main/kuikly/library/README.md
- https://github.com/techskillplanet/planet-components/blob/main/ios-swiftui/library/PUBLISHING.md

Local paths: repo-root `PUBLISHING.md`, etc.

## Auth checklist

- **npm**: Automation / Bypass-2FA token in `tools/publish-npm-web.env` (gitignored). Script prefers this file over root `.env`.
- **Maven Central**: `ossrhUsername` / `ossrhPassword` + GPG (`signing.keyId`, `signing.password`, `signingKeyFile`) in `android/gradle.properties` or `~/.gradle/gradle.properties`.
- **pub.dev**: `dart pub login`; publish against official `pub.dev` (not `pub.flutter-io.cn`).
- **CocoaPods**: `pod trunk register` / owner session.

Never commit tokens, raw private keys, or `.env` files.

## Version bump checklist (next release after Domain-7)

- [ ] Bump all eight libraries to the same semver
- [ ] Update `component_contract.json` / README status tables if needed
- [ ] `./tools/headless-check.sh` green
- [ ] Publish npm (`all`) → Maven Android → Maven Kuikly → Flutter → iOS tag + CocoaPods
- [ ] Smoke-install one consumer per registry
- [ ] Bump `.agents/plugin-manifest.json` `version` to match (or `0.2.x` skills-only)
- [ ] Push git + refresh Agent Skills: `npx skills add techskillplanet/planet-components -g` / `./tools/install-ai-plugin.sh`

## After publish

- Verify registry “latest” equals intended version.
- Update root `README.md` / `README.zh-CN.md` status tables.
- Announce Agent Skills install: `npx skills add techskillplanet/planet-components`.
- Do **not** commit secrets.

## Agent Skills vs UI packages

| Artifact | Channel |
| --- | --- |
| UI libraries | npm / Maven Central / pub.dev / SPM / CocoaPods |
| Agent Skills | Public GitHub + `npx skills add techskillplanet/planet-components` |

Skills ship with the repo; they are **not** published to Maven/pub.dev.
