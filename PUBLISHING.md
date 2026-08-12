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

### Auth（起步阶段：方便优先）

当前仓库直接提交 `tools/publish-npm-web.env`（含 `NPM_TOKEN`），方便本地/同事一键发布。

| 文件 | 是否进仓库 | 说明 |
| --- | --- | --- |
| `tools/publish-npm-web.sh` | 是 | 发布脚本 |
| `tools/publish-npm-web.env` | 是（起步） | 含 token；随时可在 npmjs 删除重建 |
| `.env.example` | 是 | 备用模板 |
| `.env` | 否（gitignore） | 可选本地覆盖 |

发布前可确认：

```bash
npm whoami --registry https://registry.npmjs.org/
```

若发布报 `403` / 需要 2FA bypass：到 npmjs 创建 **Automation** 或带 **Bypass 2FA** 的 Granular Token，更新 `tools/publish-npm-web.env` 后重跑。

### 脚本行为

1. 切到官方源 `https://registry.npmjs.org/`
2. 用 `NPM_TOKEN` / `.env` / 已有 npm 登录态鉴权
3. 对目标库执行 `check` → `test` → `pack:dry` → `publish --access public`
4. 退出时恢复进入脚本前的 registry（例如公司 `newznpm`）

同一版本号不可重复发布；升版请改对应 `library/package.json` 的 `version`。
