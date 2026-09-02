# Planet Components Kuikly

TechSkillPlanet 基础组件库（Planet Components）的 Kuikly / Kotlin Multiplatform 实现。

公开控件：`src/commonMain/kotlin/.../phonics/controls/Tsp*.kt`（与 `docs/COMPONENT_CONTRACT.md` 对齐）。

## 开源与发布地址

| 类型 | 地址 |
| --- | --- |
| 源码 | https://github.com/techskillplanet/planet-components/tree/main/kuikly/library/shared |
| Maven 坐标（根 / metadata） | `io.github.techskillplanet:planet-components-kuikly:0.2.0` |
| Maven Central（发布后） | https://central.sonatype.com/artifact/io.github.techskillplanet/planet-components-kuikly |

KMP 还会发布平台产物（`planet-components-kuikly-android`、`…-iosarm64`、`…-js` 等），由 Gradle metadata 自动选择。

## Gradle 依赖

```gradle
repositories {
    mavenCentral()
    // Kuikly 运行时来自腾讯仓库（传递依赖）
    maven { url = uri("https://mirrors.tencent.com/repository/maven-tencent/") }
}

dependencies {
    implementation("io.github.techskillplanet:planet-components-kuikly:0.2.0")
}
```

iOS framework 名（本地 assemble）：`PlanetComponentsKuiklyShared`。

## 维护者发布

流程与 Android View 一致：Gradle → OSSRH Staging → Portal Deployments → 人工 Publish。

凭证（任选其一，脚本会自动探测）：

1. `android/gradle.properties`（推荐复用同一套 Portal Token / GPG）
2. `kuikly/gradle.properties`（模板：`../gradle.properties.example`）
3. `~/.gradle/gradle.properties`

```bash
# 仓库根目录或 kuikly/ 下均可
kuikly/scripts/publish-maven-central.sh --dry-run
kuikly/scripts/publish-maven-central.sh
kuikly/scripts/publish-maven-central.sh --list
kuikly/scripts/publish-maven-central.sh --upload-only
```

脚本成功后，在 [Sonatype Central Portal Deployments](https://central.sonatype.com/publishing/deployments) 点 **Publish**。

> 仅跑 `./gradlew :shared:publishAllPublicationsToMavenCentralRepository` 时，包会停在 staging，**不会**出现在 Deployments；需再执行脚本的 upload 步骤。

## 目录

```text
library/shared/
├── build.gradle.kts          # KMP + maven-publish + signing
└── src/commonMain/kotlin/…/phonics/controls/   # Tsp* 一组件一文件
```

Samples：`kuikly/samples/androidApp`、`kuikly/samples/miniApp`（依赖本地 `:shared`）。
