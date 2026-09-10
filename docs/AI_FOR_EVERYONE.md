# 用 AI 做技趣星球界面（非技术指南）

面向：**产品、运营、老师、家长、设计师** 等非软件研发同学。  
你不需要会写代码——把下面的话复制给 Cursor / Claude / Codex 即可。

默认技术栈（已选好，不用纠结）：

- **网页版 React** + 技趣星球组件库 `@techskillplanet/planet-components-react` **0.2.1**
- 蓝天白云「Sky Planet」视觉

---

## 三步开始

### ① 准备工具（一次性）

1. 安装 [Cursor](https://cursor.com/)（或你已有的 Claude / Codex）。  
2. 用下面 **任一方式** 加载技趣星球 Agent Skills：

| 方式 | 怎么做 |
| --- | --- |
| 推荐 | 终端执行 `npx skills add techskillplanet/planet-components` |
| Cursor | Settings → Plugins，添加本仓库；或直接打开本仓库 |
| 维护者 | 在本仓库根目录执行 `./tools/install-ai-plugin.sh` |

更多细节见 [`AI_PLUGIN.md`](AI_PLUGIN.md#加载方式一览任选其一)。

3. **新开一个 AI 对话**（很重要，旧对话可能读不到新技能）。

### ② 复制「总开关」提示词（每个新项目先发一次）

打开 [`docs/prompts/zh-quickstart.md`](prompts/zh-quickstart.md)，复制 **「万能开场」** 整段，发给 AI。

### ③ 用「人话」描述页面

例如：

> 做一个家长端首页：顶栏标题「本周任务」，下面三张卡片（打卡、积分、兑换），底部一个蓝色主按钮「去完成」。

AI 应自动用技趣星球的 `Tsp*` 组件搭好，并告诉你怎么在浏览器打开。

---

## 你可以说的话（示例）

| 你想要的 | 直接复制给 AI |
| --- | --- |
| 登录页 | 做登录页：手机号输入、验证码、主按钮「登录」，用技趣星球组件和天空主题 |
| 列表页 | 做消息列表：顶栏、可点击列表行、没有数据时显示空状态 |
| 设置页 | 做设置页：夜间模式开关、语言两项，底部保存按钮 |
| 换主题 | 把主题从天空改成夜空（night） |
| 改文案 | 把主按钮改成「立即开始」，标题改成「技趣星球」 |

更多模板：[`docs/prompts/zh-quickstart.md`](prompts/zh-quickstart.md)

---

## AI 会替你做什么 / 不会做什么

| 会 | 不会（除非你明确要求） |
| --- | --- |
| 安装组件包、搭页面、调布局 | 改组件库底层源码 |
| 用官方主题与按钮/卡片/顶栏 | 随便画一套完全不同的颜色体系 |
| 用中文解释怎么预览 | 默认上架到应用商店 / 接真实支付 |

---

## 预览失败时

把下面整段发给 AI：

> 页面打不开或样式没有了。请检查：是否安装了 `@techskillplanet/planet-components-react`，是否在入口文件导入了 `styles.css`，是否每个组件传了 `theme={starPlanetThemes.sky}`，然后给出最短修复步骤。

---

## 进阶（可选）

- 完整技术接入：[`README.zh-CN.md`](../README.zh-CN.md)  
- Agent 技能安装与发布：[`AI_PLUGIN.md`](AI_PLUGIN.md)  
- 组件契约（有哪些控件）：[`COMPONENT_CONTRACT.md`](COMPONENT_CONTRACT.md)
