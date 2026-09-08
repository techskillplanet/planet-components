# 提示词速查（中文 · 复制即用）

配合技能 **`ai-build-with-planet`**。默认：**React 网页** + `@techskillplanet/planet-components-react@0.2.0` + Sky 主题。

---

## 万能开场（每个新对话先发）

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

---

## 页面模板

### 家长首页

```text
做一个「家长端首页」：
- 顶栏标题：本周任务
- 三张卡片：今日打卡、积分余额、兑换商城（每张有一句说明 + 按钮）
- 底部主按钮：去完成任务
全部用技趣星球组件和天空主题。
```

### 登录 / 验证码

```text
做登录页：手机号输入框、验证码输入、主按钮「登录」、次要文字链「还没有账号？」。
用 TspInput / TspButton / TspTextLink / TspCard / TspTopBar。
```

### 列表 + 空状态

```text
做「消息中心」：顶栏、消息列表（标题+摘要），当没有消息时用空状态组件提示「暂无消息」并带「刷新」操作。
```

### 表单提交

```text
做「新建任务」表单：任务名称、是否提醒（开关）、提交按钮（未填名称时禁用）。
用 TspInput、TspSwitch、TspStickyFooter、TspButton。
```

### 底部导航

```text
做带底部三个页签的壳子：首页 / 任务 / 我的。点击切换简单占位内容。用 TspBottomTab + TspTopBar。
```

---

## 微调指令

```text
把主题改成 night（夜空）。
```

```text
主色按钮文案改成「立即开始」，顶栏标题改成「技趣星球」。
```

```text
手机上看更舒服：主按钮全宽，卡片之间间距大一点（仍用组件，不要手写复杂 CSS）。
```

```text
加一个确认弹窗：点击删除时弹出 TspModal，确认后才删除。
```

---

## 排错

```text
样式丢失或组件难看：请检查 styles.css 是否引入、theme 是否传入，并给出最少修改的文件列表。
```

```text
我不会用命令行：请写出要复制的命令，一步一步，并告诉我成功时浏览器应出现什么。
```
