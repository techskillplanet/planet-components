# Publishing Plan

Each `library` directory is intended to become an independently published open-source package.

| Platform | Package manager | Current package identity |
| --- | --- | --- |
| Android View | Maven Central / GitHub Packages | `io.github.techskillplanet:planet-components-android:0.2.0`（脚本：`android/scripts/publish-maven-central.sh`） |
| React Native | npm | `@techskillplanet/planet-components-react-native` |
| React Web | npm | `@techskillplanet/planet-components-react` |
| Vue Web | npm | `@techskillplanet/planet-components-vue` |
| Flutter | pub.dev | `tech_skill_planet_components` |
| iOS SwiftUI | Swift Package Manager | `TechSkillPlanetBasicControls` |
| WeChat Mini Program | npm / miniprogram package | `@techskillplanet/planet-components-miniprogram` |
| Kuikly | Maven / internal Kuikly package | `com.techskillplanet:planet-components-kuikly` |

Before publishing a library:

1. Run the platform-specific sample.
2. Run the package build/dry-run command.
3. Confirm sample uses the local library dependency.
4. Confirm build caches are not included.
5. Confirm package metadata points to `techskillplanet/planet-components`.

## npm：React Web / Vue Web

Packages:

- `@techskillplanet/planet-components-react` → `react-web/library`
- `@techskillplanet/planet-components-vue` → `vue-web/library`

Script (checked into the repo):

```bash
./tools/publish-npm-web.sh --dry-run all
./tools/publish-npm-web.sh all
./tools/publish-npm-web.sh react
./tools/publish-npm-web.sh vue
```

### Auth（风险可控）

| 可以进仓库 | 禁止进仓库 |
| --- | --- |
| `tools/publish-npm-web.sh` | `.env`（真实 `NPM_TOKEN`） |
| `.env.example` | 任何含 `_authToken` 的项目级 `.npmrc` |
| 本文档与 `package.json` | 聊天记录里泄露的 token（应轮换） |

本机准备：

```bash
cp .env.example .env
# 编辑 .env，填入 NPM_TOKEN=npm_xxx
# 或：export NPM_TOKEN=npm_xxx
```

也可用用户级 `~/.npmrc`（不进仓库）：

```text
registry=https://registry.npmjs.org/
//registry.npmjs.org/:_authToken=npm_xxx
```

发布前确认：

```bash
npm whoami --registry https://registry.npmjs.org/
# 期望：techskillplanet（或具备 @techskillplanet publish 权限的账号）
```

### 脚本行为

1. 切到官方源 `https://registry.npmjs.org/`
2. 用 `NPM_TOKEN` / `.env` / 已有 npm 登录态鉴权
3. 对目标库执行 `check` → `test` → `pack:dry` → `publish --access public`
4. 退出时恢复进入脚本前的 registry（例如公司 `newznpm`）

同一版本号不可重复发布；升版请改对应 `library/package.json` 的 `version`。
