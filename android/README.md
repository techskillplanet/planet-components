# Android View

TechSkillPlanet Planet Components 的 Android View 栈：可发布 `library` + 可运行 `samples`。

## 开源与 Maven

| 项 | 值 |
| --- | --- |
| 源码 | https://github.com/techskillplanet/planet-components/tree/main/android/library |
| Maven 坐标 | `io.github.techskillplanet:planet-components-android:0.2.1` |
| Maven Central | https://central.sonatype.com/artifact/io.github.techskillplanet/planet-components-android |
| 仓库目录 | https://repo1.maven.org/maven2/io/github/techskillplanet/planet-components-android/ |

```gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation "io.github.techskillplanet:planet-components-android:0.2.1"
}
```

库说明与发布步骤见 [`library/README.md`](library/README.md)。

## 发布到 Maven Central

```bash
# 1) 配置凭证（勿提交）
cp android/gradle.properties.example android/gradle.properties
# 编辑 ossrhUsername / ossrhPassword / signing.*

# 2) 可控发布脚本（已入库）
android/scripts/publish-maven-central.sh --dry-run
android/scripts/publish-maven-central.sh

# 3) Portal Deployments 点 Publish
# https://central.sonatype.com/publishing/deployments
```

发布脚本与 `android/gradle.properties`（含 Portal Token / 签名配置）均入库，便于可控发版。

## 本地构建

```bash
cd android
./gradlew :library:assembleRelease :samples:assembleDebug
```

历史包名 `basic-controls-android:0.1.0` 已停用，请改用 `planet-components-android:0.2.1`。
