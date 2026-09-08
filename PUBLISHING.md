# Publishing Plan

Each `library` directory is an independently published open-source package.

**Current public release: 0.2.0（八栈均已上架，2026-09-08 实查）。**

GitHub 主页多语言：[`README.md`](README.md)（English）· [`README.zh-CN.md`](README.zh-CN.md) · [`README.ja.md`](README.ja.md) · [`README.ko.md`](README.ko.md)。

Agent Skills：公共安装 `npx skills add techskillplanet/planet-components`；说明见 [`docs/AI_PLUGIN.md`](docs/AI_PLUGIN.md)。

| Platform | Package manager | Current package identity |
| --- | --- | --- |
| Android View | Maven Central / GitHub Packages | `io.github.techskillplanet:planet-components-android:0.2.0`（脚本：`android/scripts/publish-maven-central.sh`） |
| React Native | npm | `@techskillplanet/planet-components-react-native@0.2.0` |
| React Web | npm | `@techskillplanet/planet-components-react@0.2.0` |
| Vue Web | npm | `@techskillplanet/planet-components-vue@0.2.0` |
| Flutter | pub.dev | `tech_skill_planet_components@0.2.0`（脚本：`tools/verify-flutter-sample.sh`；发布：`cd flutter/library && dart pub publish`） |
| iOS SwiftUI | Swift Package Manager + CocoaPods | `PlanetComponents` 0.2.0（源码 `ios-swiftui/library`；SPM 根目录 `Package.swift`；CocoaPods 根目录 `PlanetComponents.podspec`；同一 git tag） |
| WeChat Mini Program | npm / miniprogram package | `@techskillplanet/planet-components-miniprogram@0.2.0` |
| Kuikly | Maven Central | `io.github.techskillplanet:planet-components-kuikly:0.2.0`（脚本：`kuikly/scripts/publish-maven-central.sh`；KMP 多 publication） |

Before publishing a library:

1. Run the platform-specific sample.
2. Run the package build/dry-run command.
3. Confirm sample uses the local library dependency.
4. Confirm build caches are not included.
5. Confirm package metadata points to `techskillplanet/planet-components`.

## npm：React Web / Vue Web / React Native

Packages:

- `@techskillplanet/planet-components-react` → `react-web/library`
- `@techskillplanet/planet-components-vue` → `vue-web/library`
- `@techskillplanet/planet-components-react-native` → `react-native/library`

Script (checked into the repo):

```bash
./tools/publish-npm-web.sh --dry-run all
./tools/publish-npm-web.sh all
./tools/publish-npm-web.sh react
./tools/publish-npm-web.sh vue
./tools/publish-npm-web.sh rn
```

### Auth（本地凭证，禁止入库）

`NPM_TOKEN` **不得**提交到 Git。GitHub Push Protection 会拦截含 npm token 的推送。

| 文件 | 是否进仓库 | 说明 |
| --- | --- | --- |
| `tools/publish-npm-web.sh` | 是 | 发布脚本 |
| `tools/publish-npm-web.env.example` | 是 | 字段模板（无密钥） |
| `tools/publish-npm-web.env` | 否（gitignore） | 本机真实 token |
| `.env.example` | 是 | 根目录备用模板（`NPM_TOKEN=`） |
| `.env` | 否（gitignore） | 可选本地覆盖 |

#### 首次本机配置

```bash
cp tools/publish-npm-web.env.example tools/publish-npm-web.env
# 编辑 tools/publish-npm-web.env，填入 NPM_TOKEN=
```

鉴权优先级（脚本读取顺序）：

1. 环境变量 `NPM_TOKEN`（若进入脚本前已导出，仍可能被下方文件覆盖）
2. 仓库根目录 `.env`
3. `tools/publish-npm-web.env`（**最终优先**，避免根目录旧 token 覆盖 Bypass-2FA 凭证）

建议 token 类型：npmjs **Automation**，或带 **Bypass 2FA** 的 Granular Access Token。  
创建/轮换：https://www.npmjs.com/settings/~/tokens

可选：把同一份内容备份到机器私有路径（勿进仓库），例如：

```bash
mkdir -p ~/.config/planet-components
cp tools/publish-npm-web.env ~/.config/planet-components/publish-npm-web.env
chmod 600 ~/.config/planet-components/publish-npm-web.env
# 需要时再拷回：
# cp ~/.config/planet-components/publish-npm-web.env tools/publish-npm-web.env
```

发布前可确认：

```bash
# 由脚本内部 whoami；或手动：
export NPM_TOKEN=...   # 或先 source tools/publish-npm-web.env
npm whoami --registry https://registry.npmjs.org/
```

若发布报 `403` / 需要 2FA bypass：到 npmjs 重建 Automation / Bypass-2FA token，只更新**本地** `tools/publish-npm-web.env` 后重跑。

### 脚本行为

1. 切到官方源 `https://registry.npmjs.org/`
2. 用本地 `NPM_TOKEN`（见上）鉴权；使用临时 userconfig，避免 `~/.npmrc` 旧会话覆盖
3. 对目标库执行 `check` → `test` → `pack:dry` → `publish --access public`
4. 退出时恢复进入脚本前的 registry（例如公司源）

同一版本号不可重复发布；升版请改对应 `library/package.json` 的 `version`。

## Flutter：pub.dev

Package: `tech_skill_planet_components` → `flutter/library`

```bash
# 本地/路径依赖验证
./tools/verify-flutter-sample.sh path

# 发布到 pub.dev（需能访问 Google；本机有 Clash 时走代理）
cd flutter/library
unset DART_PUB_TOKEN
export http_proxy=http://127.0.0.1:7897 https_proxy=http://127.0.0.1:7897
export no_proxy=localhost,127.0.0.1
PUB_HOSTED_URL=https://pub.dev dart pub publish --dry-run
PUB_HOSTED_URL=https://pub.dev dart pub publish --force

# 发布后：Sample 改用 hosted 动态依赖再验
./tools/verify-flutter-sample.sh hosted
```

Sample 动态依赖示例：

```yaml
dependencies:
  tech_skill_planet_components: ^0.2.0
```

## iOS：SPM + CocoaPods

Package: `PlanetComponents` → 源码 `ios-swiftui/library`；根目录 `Package.swift` + `PlanetComponents.podspec`；同一 git tag 双发。

完整步骤见：[`ios-swiftui/library/PUBLISHING.md`](ios-swiftui/library/PUBLISHING.md)

**0.2.0 状态：SPM git tag 与 CocoaPods Trunk 均已发布。**

## Kuikly：Maven Central

Package: `io.github.techskillplanet:planet-components-kuikly` → `kuikly/library/shared`（KMP：Android / iOS / JS）

流程与 Android 相同（可复用 `android/gradle.properties` 的 Portal Token / GPG）：

```bash
kuikly/scripts/publish-maven-central.sh --dry-run
kuikly/scripts/publish-maven-central.sh
# → https://central.sonatype.com/publishing/deployments 点 Publish
```

凭证模板：`kuikly/gradle.properties.example`。库说明：[`kuikly/library/README.md`](kuikly/library/README.md)。

Gradle 任务：`:shared:publishAllPublicationsToMavenCentralRepository`（多 publication，勿只用单一 android release）。

**0.2.0 状态：已发布到 Maven Central。**
