# RN Sample 交互缺陷 — 测试用例

## TC-TOPBAR-01 返回按钮左对齐、标题居中
- 前置：渲染 `TspTopBar`，`showBack=true`，`title="详情"`
- 步骤：读取返回按钮与根容器 style
- 期望：返回容器 `position:absolute` 且 `left:0`；标题文本存在且根容器为居中布局

## TC-TOPBAR-02 无返回时标题仍居中
- 前置：`showBack=false`
- 期望：不出现返回符号 `‹`；标题仍渲染

## TC-TOPBAR-03 返回回调
- 前置：传入 `onBack` spy，`showBack=true`
- 步骤：触发返回按压
- 期望：`onBack` 被调用一次

## TC-I18N-01 语言表取值
- 前置：加载 `sample_strings.json`
- 步骤：`t('zh-CN','sample/app/title')` / `en` / `ja`
- 期望：分别为「基础组件」/「Basic Controls」/对应日文键值

## TC-I18N-02 格式化占位
- 步骤：`t('zh-CN','sample/settings/language/current','English')`
- 期望：包含 `English`

## TC-I18N-03 切换后 UI 键映射
- 步骤：对 home/settings tab 键分别取三语
- 期望：三语字符串互不相同（至少 home 标题与 tab）

## TC-TAB-01 bottomInset
- 前置：`TspBottomTab` 传入 `bottomInset={20}`
- 期望：根容器 paddingBottom 为 20（safe-area 外加，不吞掉内容区高度）

## TC-TAB-02 ReactNode 图标
- 前置：传入 ReactNode `icon`
- 期望：自定义图标节点可渲染

## TC-TAB-03 顶部内容间距（常规规范）
- 前置：读取 `styles.bottomTabContent`
- 期望：`paddingTop >= 8`、`paddingBottom >= 8`、`minHeight >= 52`；根容器仅顶部分割线（非四边全框）

## TC-BADGE-01 徽标文案居中契约
- 前置：读取 `styles.badge` / `styles.badgeText`
- 期望：容器 `alignItems`/`justifyContent` 为 `center`；文案 `textAlign:'center'`、`includeFontPadding:false`

## TC-BADGE-02 变体渲染
- 前置：渲染 `TspBadge` `text="primary" variant="primary"`
- 期望：节点文案为 `primary`；根节点使用居中容器结构（非裸 Text 承载 padding）

## TC-MODAL-01 操作区等分
- 前置：读取 `styles.modalActions` / `styles.modalActionItem`
- 期望：操作为横向；每个 action item `flex:1` 且 `minWidth:0`

## TC-MODAL-02 按钮面铺满以居中文案
- 前置：读取 `styles.buttonFace` / `styles.buttonText`
- 期望：`buttonFace.width` 为 `'100%'`；`buttonText` 具备 `textAlign:'center'` 与 `includeFontPadding:false`

## TC-MODAL-03 可见双按钮
- 前置：渲染 `TspModal` `visible`，`cancelText="取消"` `confirmText="确定"`
- 期望：树上同时出现「取消」「确定」文案；两颗按钮均在 modal 面板子树内

## TC-MODAL-04 Sample 场景（手动）
- 步骤：打开 Sample → Modal → Open Modal
- 期望：面板内左右双按钮完整可见；右侧屏幕边缘无蓝色按钮残片；「取消/确定」文字视觉居中

## TC-BUTTON-01 取消/确认岛屿阴影一致
- 前置：同主题下渲染 `variant=default` 与 `variant=primary`
- 期望：两者均有 raised shadow 层；default face 为不透明 `surfaceRaised`（非 transparent）；primary face 为 `brandPrimary`；face 均为 absolute 叠在 shadow 上
- 说明：避免 default 透出整块阴影托底、primary 只露出底边，造成「阴影逻辑不一致」观感
