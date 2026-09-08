---
name: publish-planet-components
description: >-
  Publish TechSkillPlanet Planet Components libraries to public registries
  (npm, Maven Central, pub.dev, SPM/CocoaPods). Use when releasing a new version,
  dry-running packs, or debugging publish auth (NPM_TOKEN, Sonatype, GPG, dart pub).
---

# Publish Planet Components

## Before any publish

1. Align version in the target `library` (`package.json` / `pubspec.yaml` / Gradle `version` / tag).
2. `node tools/check-structure.cjs`
3. Run stack checks (test / pack:dry / assemble) from `PUBLISHING.md` / `README.zh-CN.md`.
4. This repo’s **samples must stay on local library** — do not point samples at the new registry version for release verification unless using a dedicated verify script.

## Stack playbooks

| Stack | Command / flow |
| --- | --- |
| React / Vue / RN / Mini Program | `./tools/publish-npm-web.sh [--dry-run] [react\|vue\|rn\|miniprogram\|all]` — needs `tools/publish-npm-web.env` with Bypass-2FA `NPM_TOKEN` |
| Android View | `android/scripts/publish-maven-central.sh` → Portal Deployments → Publish |
| Kuikly KMP | `kuikly/scripts/publish-maven-central.sh` → Portal Deployments → Publish (reuse `android/gradle.properties` Portal + GPG) |
| Flutter | `unset PUB_HOSTED_URL`; proxy if needed; `cd flutter/library && dart pub publish` |
| iOS | git annotated tag → push tag (SPM); `pod trunk push PlanetComponents.podspec` (CocoaPods) |

Details (works when this skill is installed outside the monorepo):

- https://github.com/techskillplanet/planet-components/blob/main/PUBLISHING.md
- https://github.com/techskillplanet/planet-components/blob/main/android/library/README.md
- https://github.com/techskillplanet/planet-components/blob/main/kuikly/library/README.md
- https://github.com/techskillplanet/planet-components/blob/main/ios-swiftui/library/PUBLISHING.md

If you are editing this repo locally, the same files are at the repo root (`PUBLISHING.md`, etc.).

## Auth checklist

- **npm**: Automation / Bypass-2FA token in `tools/publish-npm-web.env` (gitignored).
- **Maven Central**: `ossrhUsername` / `ossrhPassword` + GPG (`signing.keyId`, `signing.password`, `signingKeyFile`) in `android/gradle.properties` or `~/.gradle/gradle.properties`.
- **pub.dev**: `dart pub login` (credentials on disk; prefer official `pub.dev`, not `pub.flutter-io.cn` for publish).
- **CocoaPods**: `pod trunk register` / session for the owner account.

## After publish

- Verify registry latest matches the intended version.
- Update root `README.md` / `README.zh-CN.md` status table if needed.
- Do **not** commit secrets (npm tokens, raw GPG private keys beyond the project’s established properties policy).
