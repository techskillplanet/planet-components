export const componentGroups = [
  ['Actions', ['Button', 'Chip', 'IconButton', 'TextLink']],
  ['Surfaces', ['Card', 'ListItem', 'Empty']],
  ['Feedback', ['Alert', 'Badge', 'Progress', 'Notification', 'Toast', 'Modal']],
  ['Inputs', ['Input', 'Select', 'OptionSheet', 'Switch', 'PinInput']],
  ['Navigation', ['TopBar', 'BottomTab', 'Tabs', 'StickyFooter']],
  ['Data', ['Amount', 'KeyValueLabel', 'Stepper']],
];

export const componentDescriptions = {
  Button: '主要操作按钮，支持主按钮、默认、危险、文本和链接样式。',
  Card: '内容容器，用于承载分组内容、选中态和弱化背景。',
  Alert: '页面内提示，适合成功、警告、错误和普通信息。',
  Badge: '短文本状态徽标，用于标记数量、状态或风险等级。',
  Chip: '可点击标签，用于筛选、选择和轻量操作。',
  Input: '单行输入框，支持错误态、禁用态和受控输入。',
  Select: '选择入口，移动端默认打开底部 OptionSheet。',
  OptionSheet: '移动端底部选择弹窗，和 Select 共用选项渲染逻辑。',
  Switch: '二元开关，支持加载、禁用和开关文案。',
  Progress: '进度条，支持主色、成功、警告和危险色。',
  TopBar: '顶部导航栏，支持标题、返回按钮和背景色覆盖。',
  BottomTab: '一级页面底部 Tab，通常承载 3 到 5 个入口。',
  Tabs: '内容区分段切换，适合页面内筛选和分类。',
  Amount: '金额或数值展示，支持币种前后置、周期和删除线。',
  IconButton: '图标按钮，用于工具栏、快捷动作和选中态。',
  KeyValueLabel: '键值对展示，用于摘要信息和表单确认。',
  Notification: '通知卡片，用于强调当前任务或状态提醒。',
  TextLink: '文本链接按钮，适合弱操作和辅助跳转。',
  Stepper: '步骤进度，支持 3 到 5 步。',
  StickyFooter: '固定底部操作区，用于主操作按钮。',
  PinInput: '验证码或密码输入，支持 4 到 6 位和安全显示。',
  ListItem: '列表项，支持描述、尾部内容、选中和禁用。',
  Empty: '空状态展示，支持说明文案和操作按钮。',
  Toast: '轻提示，用于短时反馈。',
  Modal: '确认弹窗，支持确认和取消按钮。',
};

export function categoryOf(name) {
  return componentGroups.find(([, names]) => names.includes(name))?.[0] || '';
}

