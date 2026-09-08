# AI Agent Plugin · Planet Components Skills

> **非技术同学？** 先看 [`AI_FOR_EVERYONE.md`](AI_FOR_EVERYONE.md)，复制 [`prompts/zh-quickstart.md`](prompts/zh-quickstart.md) 里的话给 AI 即可。默认用 **React 网页 + 技趣星球组件**。

技趣星球（TechSkillPlanet）为本仓库提供的 **Agent Skills**：标准 `SKILL.md` 格式，与 Cursor / Claude Code / Codex 日常使用的 skill 相同。

源码位置：

```text
.agents/
├── plugin-manifest.json    # 版本与 skill 清单
├── README.md
└── skills/
    ├── ai-build-with-planet/
    ├── use-planet-components/
    ├── build-planet-components/
    ├── publish-planet-components/
    └── integrate-*/ …
```

当前插件版本与组件库对齐：**0.2.0**（见 `plugin-manifest.json`）。

## 公共安装（推荐 · 日常用法）

任意项目目录（不必克隆本仓）：

```bash
# 安装本仓全部 Agent Skills（Cursor / Claude / Codex 等）
npx skills add techskillplanet/planet-components

# 全局安装
npx skills add techskillplanet/planet-components -g

# 只装常用消费向 skill
npx skills add techskillplanet/planet-components \
  --skill use-planet-components ai-build-with-planet integrate-react-components -y

# 先看仓库里有哪些 skill
npx skills add techskillplanet/planet-components -l
```

这与安装 `frontend-design`、`vercel-react-best-practices` 等公共 skill 的方式相同：注册表是 **公开 GitHub**，CLI 是 [skills](https://www.npmjs.com/package/skills)（`npx skills`）。

安装后 **新开对话**；Agent 会根据 skill `description` 自动选用（或你显式点名 skill 名）。

## 本仓维护者安装（符号链接）

在仓库根目录，把本地 `.agents/skills` 链到用户目录（改 skill 即时生效）：

```bash
./tools/install-ai-plugin.sh           # Cursor + Claude + Codex + ~/.agents
./tools/install-ai-plugin.sh cursor    # 仅 Cursor
./tools/install-ai-plugin.sh --dry-run
./tools/install-ai-plugin.sh --unlink  # 移除本插件创建的符号链接
```

| 产品 | 默认目录 |
| --- | --- |
| Cursor | `~/.cursor/skills/<skill-name>` |
| Claude Code | `~/.claude/skills/<skill-name>` |
| Codex | `~/.codex/skills/<skill-name>` |
| 通用 | `~/.agents/skills/<skill-name>` |

并在仓库内维护 `.cursor/skills` → `.agents/skills`（项目级发现）。

## 不安装时

只要打开本仓库，Cursor 也可通过项目内 `.agents/skills` / `.cursor/skills` 使用（视客户端版本而定）。克隆即用。

## Skill 如何「发布」成公共 skill

Agent Skill **不是** npm 包。公开路径：

1. 在 `.agents/skills/<name>/SKILL.md` 新增或修改 skill（YAML：`name` + `description`）。  
2. 把 `<name>` 写入 `.agents/plugin-manifest.json` 的 `skills` 数组；升 `version`（建议与组件发版对齐）。  
3. **自包含**：消费向 skill 不要依赖 `../../../docs/...` 这种 monorepo 相对路径；外链用  
   `https://github.com/techskillplanet/planet-components/blob/main/...`，或把短提示词写进 `SKILL.md`。  
4. 提交并推送到 `techskillplanet/planet-components`（public）；打 git tag（例如随 `0.2.0`）。  
5. 使用者：`npx skills add techskillplanet/planet-components`。

[skills.sh](https://skills.sh) 靠 `npx skills add` 的安装遥测进入索引，没有单独的「上架表单」。装的人多了才会出现在搜索/排行。

### 仅项目内生效

保证仓库里有 `.agents/skills` 即可；协作者 clone 后由支持该约定的 Agent 加载。

### 可选：npm 薄包

若业务仓强制只要 npm 坐标，可另做 `@techskillplanet/planet-components-agent-skills`（当前未强制）。优先用 GitHub + `npx skills`。

### 不要做的事

- 不要把 skill 发到 Maven / pub.dev。  
- 不要把密钥写进 `SKILL.md`。  
- 不要把 skill 打进业务应用运行时 bundle。

## 编写规范

- Frontmatter 必填：`name`（小写+连字符）、`description`（WHAT + WHEN，第三人称）。  
- `SKILL.md` 尽量 &lt; 500 行；细节放 `references/`。  
- 消费类只写**已发布坐标**；改库用 `build-*`；发版用 `publish-planet-components`。  
- 改完：`./tools/install-ai-plugin.sh --dry-run`，并用  
  `npx skills add . -l`（本地）或 push 后 `npx skills add techskillplanet/planet-components -l` 确认可发现。

## 与组件发版的关系

| 产物 | 渠道 |
| --- | --- |
| UI 组件库 | npm / Maven Central / pub.dev / SPM / CocoaPods（见 `PUBLISHING.md`） |
| Agent Skills | 公开 GitHub + `npx skills add techskillplanet/planet-components` |

组件 `0.2.0` 已上架时，保持 `plugin-manifest.json` 的 `version` 为 `0.2.0`，发版说明中提一句「Agent skills 已更新」。

## 校验

```bash
node -e "
const m=require('./.agents/plugin-manifest.json');
const fs=require('fs'); const path=require('path');
for (const s of m.skills) {
  const p=path.join('.agents/skills', s, 'SKILL.md');
  if (!fs.existsSync(p)) throw new Error('missing '+p);
}
console.log('skills ok', m.skills.length, 'version', m.version);
"
./tools/install-ai-plugin.sh --dry-run
npx skills add . -l
```
