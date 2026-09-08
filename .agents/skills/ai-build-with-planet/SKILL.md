---
name: ai-build-with-planet
description: >-
  Help non-engineers and product people build Sky Planet UI pages with AI using
  the published React Web stack (@techskillplanet/planet-components-react).
  Use when the user wants to make a page/app in plain language, without coding
  jargon, or says 小白/非技术/用AI做页面/技趣星球界面.
---

# AI Build with Planet（非技术友好）

## Goal

Let someone describe a screen in **everyday Chinese or English**. You produce a
**working React Web page** that only uses TechSkillPlanet Planet Components
(`Tsp*`), Sky Planet theme, and minimal glue code.

Default stack (do not switch unless the user insists):

- React 18+ + Vite (or existing React app)
- `@techskillplanet/planet-components-react@0.2.0`
- Import `@techskillplanet/planet-components-react/styles.css` once

## Hard rules

1. **Prefer library components** over custom CSS/div soup. Use `TspButton`, `TspCard`, `TspInput`, `TspTopBar`, `TspListItem`, `TspModal`, `TspEmpty`, `TspSwitch`, `TspTabs`, `TspBottomTab`, etc.
2. **Always pass `theme`** from `starPlanetThemes.sky` (or night/mint/sunrise if asked).
3. **No backend** unless the user asks — use local React state.
4. **Explain in plain language** what you did (3–5 short bullets). Avoid jargon; if you must use a term, add a one-line meaning.
5. If the project is empty, scaffold Vite React in the smallest way, then add the package.
6. Do **not** copy component source from the monorepo; install the **npm package**.

## Conversation flow

1. Ask only if blocked: **页面要给谁用？**（家长 / 老师 / 孩子 / 运营）和 **一句话目标**.
2. Propose a simple structure: 顶栏 + 卡片内容 + 底部按钮（或列表）.
3. Implement one screen end-to-end.
4. Tell the user how to preview: `npm install` → `npm run dev` → open the local URL.
5. Offer next steps as **copy-paste prompts** (section below + full list online).

## Starter prompts (self-contained)

**万能开场（每个新对话先发）：**

```text
请使用技趣星球 Planet Components（npm：@techskillplanet/planet-components-react@0.2.0）帮我做网页界面。
必须遵守：
1. 使用 Tsp* 组件，不要自己用 div 重造按钮/顶栏/卡片；
2. 入口引入 styles.css；
3. 使用 starPlanetThemes.sky 作为 theme 传给每个组件；
4. 用通俗中文解释你做了什么、我怎么打开预览（npm install / npm run dev）；
5. 先完成一个能跑的单页，再问我要不要加功能。
如果项目还没有前端，请用 Vite + React 最小化创建。
请加载并遵循 skill：ai-build-with-planet。
```

**示例 · 家长首页：**

```text
做一个「家长端首页」：顶栏标题「本周任务」；三张卡片（今日打卡、积分余额、兑换商城）；底部主按钮「去完成任务」。全部用技趣星球组件和天空主题。
```

More templates: https://github.com/techskillplanet/planet-components/blob/main/docs/prompts/zh-quickstart.md

## Page recipe (default)

```text
TspTopBar (title)
  TspCard
    TspAlert / text
    TspInput / TspSwitch / TspListItem …
  TspStickyFooter
    TspButton primary
```

## Install snippet (when needed)

```bash
npm install @techskillplanet/planet-components-react
```

```jsx
import '@techskillplanet/planet-components-react/styles.css';
import { TspButton, TspCard, TspTopBar, starPlanetThemes } from '@techskillplanet/planet-components-react';

const theme = starPlanetThemes.sky;
```

## Forbidden for this skill

- Rewriting the component library itself (use `build-planet-components`).
- Publishing packages (use `publish-planet-components`).
- Switching to Vue/RN/Flutter unless the user clearly wants that stack — then hand off to the matching `integrate-*` skill after a plain-language warning.

## Success check

- Page runs with one theme.
- Primary actions use `TspButton`.
- User can change copy/layout by pasting another prompt from the starter section above or zh-quickstart.md on GitHub.
