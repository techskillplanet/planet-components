# Planet Components Android View

TechSkillPlanet 基础组件库（Planet Components）的 Android View 实现：Java + 传统 View，无 Compose / androidx 依赖。

## 开源与发布地址

| 类型 | 地址 |
| --- | --- |
| 源码 | https://github.com/techskillplanet/planet-components |
| Maven Central（当前坐标） | https://central.sonatype.com/artifact/io.github.techskillplanet/planet-components-android |
| Maven 仓库目录 | https://repo1.maven.org/maven2/io/github/techskillplanet/planet-components-android/ |

Maven 坐标：

```text
io.github.techskillplanet:planet-components-android:0.2.0
```

> 历史包名：`io.github.techskillplanet:basic-controls-android:0.1.0`（已停用，请迁移到上方新坐标）。

## Gradle 依赖

```gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation "io.github.techskillplanet:planet-components-android:0.2.0"
}
```

初始化主题后再使用组件：

```java
BasicThemeManager.init(context, "sky_planet_day", "island_raised");
```

## 维护者发布

凭证见已入库的 `../gradle.properties`（模板：`../gradle.properties.example`）。

推荐一键脚本（Gradle 上传 staging → 手动 upload 到 Portal Deployments）：

```bash
# 仓库根目录或 android/ 下均可
android/scripts/publish-maven-central.sh --dry-run
android/scripts/publish-maven-central.sh
android/scripts/publish-maven-central.sh --list
android/scripts/publish-maven-central.sh --upload-only
```

脚本成功后，在 [Sonatype Central Portal Deployments](https://central.sonatype.com/publishing/deployments) 点 **Publish**。

> 仅跑 `./gradlew :library:publishReleasePublicationToMavenCentralRepository` 时，包会停在 staging，**不会**出现在 Deployments；需再执行脚本的 upload 步骤。

## 目录

```text
library/src/main/java/com/techskillplanet/planetcomponents/
├── widget/          # 一个组件一个 Java 类（BasicButton、BasicCardView …）
├── theme/           # BasicThemeManager、BasicColors、BasicStyle、Token 解析
├── i18n/            # BasicI18nManager
├── drawable/        # 共享 Drawable 工厂
└── system/          # 系统辅助（如 edge-to-edge）

library/src/main/assets/theme/
├── color_token.json
└── style_token.json
```

设计 Token 源文件在仓库根目录 `design/tokens/`，各平台 assets 与之保持同名语义键。

## 主题 API

```java
BasicThemeManager.init(context, "sky_planet_day", "island_raised");
BasicColors colors = BasicThemeManager.colors();
BasicStyle style = BasicThemeManager.style();
```

内置配色：`sky_planet_day`、`star_planet_night`、`mint_planet_day`。  
内置风格：`island_raised`（岛屿阴影）或 `island_flat`（扁平无阴影）。

组件在主题切换后调用 `refreshTheme()` 刷新外观。

## 组件约定

公共 API 与跨平台契约见仓库根目录：

- `docs/COMPONENT_CONTRACT.md`
- `component_contract.json`

常见属性：`variant`、`disabled`、`selected` / `checked`、`text` / `title` / `message`。

## Sample

可运行示例在 `../samples`，采用 Shell + Router + Page 导航，见 `../samples/README.md`。
