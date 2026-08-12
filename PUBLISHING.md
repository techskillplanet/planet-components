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

1. `tools/publish-npm-web.env`
2. 仓库根目录 `.env`
3. 环境变量 `NPM_TOKEN`

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
