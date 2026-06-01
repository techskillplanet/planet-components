import React, { useEffect, useMemo, useState } from 'react';
import { componentCategories, componentDocs } from '../../shared/componentDocs.js';
import { starPlanetTheme, starPlanetThemes, themeVars } from './theme.js';
import { h, themed } from './components/_shared.js';
import {
  TspAlert,
  TspAmount,
  TspBadge,
  TspBottomTab,
  TspButton,
  TspCard,
  TspChip,
  TspEmpty,
  TspIconButton,
  TspInput,
  TspKeyValueLabel,
  TspListItem,
  TspModal,
  TspNotification,
  TspOptionSheet,
  TspPinInput,
  TspProgress,
  TspSelect,
  TspStepper,
  TspStickyFooter,
  TspSwitch,
  TspTabs,
  TspTextLink,
  TspToast,
  TspTopBar
} from './components/index.js';

export { starPlanetTheme, starPlanetThemes, themeVars };
export * from './components/index.js';

const sampleLocales = {
  'zh-CN': {
    title: '基础组件',
    themeSwitch: '主题切换',
    languageSwitch: '语言切换',
    pageSwitch: '页面切换',
    themeHint: '切换主题后，页面、组件、弹窗和 Toast 同步变更。',
    languageHint: '切换语言后，样例文案从内置 JSON 字典读取。',
    primary: '主按钮',
    default: '默认按钮',
    danger: '危险按钮',
    card: '卡片',
    cardBody: '星球主题内容卡片。',
    success: '成功',
    applied: '主题已应用。',
    badge: '徽标',
    chip: '标签',
    input: '输入框',
    switch: '开关',
    progress: '进度',
    notice: '通知',
    noticeBody: '继续学习。',
    listItem: '列表项',
    selectedState: '选中状态',
    empty: '空状态',
    emptyBody: '暂无记录。',
    action: '操作',
    showToast: '显示 Toast',
    openModal: '打开弹窗',
    stickyFooter: '底部固定操作',
    modalTitle: '确认',
    modalBody: '弹窗遵循同一套组件契约，并验证移动端高度、滚动和底部按钮完整显示。',
    cancel: '取消',
    ok: '确定'
  },
  en: {
    title: 'Basic Controls',
    themeSwitch: 'Theme Switch',
    languageSwitch: 'Language Switch',
    pageSwitch: 'Page Switch',
    themeHint: 'Theme changes update the page, components, modal and toast.',
    languageHint: 'Sample text is loaded from the built-in JSON dictionary.',
    primary: 'Primary',
    default: 'Default',
    danger: 'Danger',
    card: 'Card',
    cardBody: 'Star Planet surface card.',
    success: 'Success',
    applied: 'Theme is applied.',
    badge: 'Badge',
    chip: 'Chip',
    input: 'Input',
    switch: 'Switch',
    progress: 'Progress',
    notice: 'Notice',
    noticeBody: 'Keep learning.',
    listItem: 'List item',
    selectedState: 'Selected state',
    empty: 'Empty',
    emptyBody: 'No records yet.',
    action: 'Action',
    showToast: 'Show Toast',
    openModal: 'Open Modal',
    stickyFooter: 'Sticky Footer',
    modalTitle: 'Confirm',
    modalBody: 'The modal follows the same contract and verifies mobile height, scrolling and visible actions.',
    cancel: 'Cancel',
    ok: 'OK'
  },
  ja: {
    title: '基本コンポーネント',
    themeSwitch: 'テーマ切替',
    languageSwitch: '言語切替',
    pageSwitch: 'ページ切替',
    themeHint: 'テーマ変更はページ、部品、モーダル、トーストへ反映されます。',
    languageHint: 'サンプル文言は内蔵 JSON 辞書から読み込みます。',
    primary: '主要ボタン',
    default: '標準ボタン',
    danger: '危険ボタン',
    card: 'カード',
    cardBody: '星球テーマのカード。',
    success: '成功',
    applied: 'テーマを適用しました。',
    badge: 'バッジ',
    chip: 'チップ',
    input: '入力',
    switch: 'スイッチ',
    progress: '進捗',
    notice: '通知',
    noticeBody: '学習を続けましょう。',
    listItem: 'リスト項目',
    selectedState: '選択状態',
    empty: '空状態',
    emptyBody: '記録はありません。',
    action: '操作',
    showToast: 'Toast 表示',
    openModal: 'モーダルを開く',
    stickyFooter: '固定フッター',
    modalTitle: '確認',
    modalBody: 'モーダルは同じ契約に従い、モバイル高さとスクロールを検証します。',
    cancel: '取消',
    ok: 'OK'
  }
};

// Keep every component page visual-first: examples exercise states without exposing code snippets.
function TspDocPreview({ name, theme, state }) {
  const common = { theme };
  const example = (title, node) => h('div', { className: 'bc-example-item' }, h('div', { className: 'bc-example-title' }, title), node);
  switch (name) {
    case 'Button': return h('div', { className: 'bc-example-stack' }, ['primary', 'default', 'danger', 'text'].map((variant) => example(variant, h(TspButton, { text: variant, variant, ...common }))));
    case 'Card': return h('div', { className: 'bc-example-stack' }, example('default', h(TspCard, common, h('strong', null, 'Card'), h('p', null, 'Star Planet card.'))), example('selected', h(TspCard, { selected: true, ...common }, 'Selected card')));
    case 'Alert': return h('div', { className: 'bc-example-stack' }, ['info', 'success', 'warning', 'error'].map((variant) => example(variant, h(TspAlert, { title: variant, message: 'Theme is applied.', variant, ...common }))));
    case 'Badge': return h('div', { className: 'bc-row' }, ['default', 'primary', 'success', 'warning', 'danger'].map((variant) => h(TspBadge, { key: variant, text: variant, variant, ...common })));
    case 'Chip': return h('div', { className: 'bc-row' }, h(TspChip, { text: 'Default', ...common }), h(TspChip, { text: 'Selected', selected: true, ...common }), h(TspChip, { text: 'Disabled', disabled: true, ...common }));
    case 'Input': return h('div', { className: 'bc-example-stack' }, example('default', h(TspInput, { value: state.inputValue, placeholder: 'Input', onChange: state.setInputValue, ...common })), example('error', h(TspInput, { value: '', placeholder: 'Required', variant: 'error', ...common })));
    case 'Select': return h(TspSelect, { options: ['A', 'B', 'C'], selectedIndex: state.selectedOption, onSelect: state.setSelectedOption, ...common });
    case 'OptionSheet': return h(TspButton, { text: 'Open OptionSheet', variant: 'primary', onTap: () => state.setShowSheet(true), ...common });
    case 'Switch': return h('div', { className: 'bc-example-stack' }, example('checked', h(TspSwitch, { text: 'Switch', checked: state.checked, onChange: state.setChecked, ...common })), example('loading', h(TspSwitch, { text: 'Loading', checked: true, loading: true, ...common })));
    case 'Progress': return h('div', { className: 'bc-example-stack' }, ['primary', 'success', 'warning', 'danger'].map((variant, index) => example(variant, h(TspProgress, { progress: [38, 68, 52, 82][index], variant, ...common }))));
    case 'TopBar': return h(TspTopBar, { title: '基础组件', showBack: true, ...common });
    case 'BottomTab': return h('div', { className: 'bc-doc-sticky-demo' }, h(TspBottomTab, { tabs: state.tabs, selectedKey: state.tab, onSelect: state.setTab, ...common }));
    case 'Tabs': return h(TspTabs, { tabs: ['全部', '已学', '未学'], selectedIndex: state.selectedTab, onSelect: state.setSelectedTab, ...common });
    case 'Amount': return h('div', { className: 'bc-example-stack' }, example('monthly', h(TspAmount, { symbol: '$', value: '128.80', cycle: 'month', ...common })), example('strike', h(TspAmount, { symbol: '$', value: '199.00', strikeThrough: true, ...common })));
    case 'IconButton': return h('div', { className: 'bc-row' }, h(TspIconButton, { icon: '♪', selected: true, ...common }), h(TspIconButton, { icon: '✓', variant: 'primary', ...common }), h(TspIconButton, { icon: '×', disabled: true, ...common }));
    case 'KeyValueLabel': return h(TspKeyValueLabel, { label: 'Progress', value: '12/48', ...common });
    case 'Notification': return h('div', { className: 'bc-example-stack' }, example('info', h(TspNotification, { title: '通知', message: '继续学习。', ...common })), example('alert', h(TspNotification, { title: '提醒', message: '今日任务未完成。', variant: 'alert', ...common })));
    case 'TextLink': return h('div', { className: 'bc-row' }, h(TspTextLink, { text: 'Text Link', ...common }), h(TspTextLink, { text: 'Inverse', inverse: true, ...common }));
    case 'Stepper': return h('div', { className: 'bc-example-stack' }, example('3 steps', h(TspStepper, { stepCount: 3, currentStep: 2, ...common })), example('5 steps', h(TspStepper, { stepCount: 5, currentStep: 3, ...common })));
    case 'StickyFooter': return h('div', { className: 'bc-doc-sticky-demo' }, h(TspButton, { text: 'Sticky Footer', variant: 'primary', ...common }));
    case 'PinInput': return h('div', { className: 'bc-example-stack' }, example('secure', h(TspPinInput, { value: state.pinValue, cellCount: 4, secure: true, onChange: state.setPinValue, ...common })), example('6 cells', h(TspPinInput, { value: '123', cellCount: 6, ...common })));
    case 'ListItem': return h('div', { className: 'bc-example-stack' }, example('selected', h(TspListItem, { title: '列表项', message: '选中状态', trailing: '›', selected: true, ...common })), example('disabled', h(TspListItem, { title: '不可点击', message: '禁用状态', disabled: true, ...common })));
    case 'Empty': return h(TspEmpty, { title: '空状态', message: '暂无记录。', actionText: '操作', ...common });
    case 'Toast': return h(TspButton, { text: 'Show Toast', variant: 'primary', onTap: () => state.showToast('已保存', 'success'), ...common });
    case 'Modal': return h(TspButton, { text: 'Open Modal', onTap: () => state.setShowModal(true), ...common });
    default: return null;
  }
}

function ComponentDocPage({ doc, theme, state, onBack }) {
  return h(
    'main',
    { className: 'bc-sample', style: themed(theme) },
    h(TspTopBar, { title: doc.component, showBack: true, theme, onBack }),
    h('section', { className: 'bc-sample__grid bc-doc-page' },
      h(TspCard, { theme },
        h('div', { className: 'bc-doc-eyebrow' }, doc.category),
        h('h1', { className: 'bc-doc-title' }, doc.component),
        h('p', { className: 'bc-doc-desc' }, doc.description)
      ),
      h(TspCard, { theme },
        h('strong', null, '使用案例'),
        h('div', { className: 'bc-doc-preview' }, h(TspDocPreview, { name: doc.name, theme, state }))
      ),
      h(TspCard, { theme },
        h('strong', null, 'API'),
        h('div', { className: 'bc-doc-section-title' }, 'Props'),
        h('div', { className: 'bc-doc-chip-row' }, doc.props.map((prop) => h('span', { key: prop, className: 'bc-doc-chip' }, prop))),
        h('div', { className: 'bc-doc-section-title' }, 'Variants'),
        h('div', { className: 'bc-doc-chip-row' }, doc.variants.map((variant) => h('span', { key: variant, className: 'bc-doc-chip' }, variant)))
      ),
      h(TspCard, { theme },
        h('strong', null, '技术栈同步'),
        h('div', { className: 'bc-doc-chip-row' }, doc.platforms.map((platform) => h('span', { key: platform, className: 'bc-doc-chip' }, platform)))
      )
    ),
    state.showSheet && h(TspOptionSheet, {
      title: '请选择',
      options: ['A', 'B', 'C'],
      selectedIndex: state.selectedOption,
      theme,
      visible: true,
      onCancel: () => state.setShowSheet(false),
      onSelect: (index) => {
        state.setSelectedOption(index);
        state.setShowSheet(false);
      }
    }),
    state.toast && h('div', { className: 'bc-toast-layer', key: state.toast.id }, h(TspToast, { message: state.toast.message, variant: state.toast.variant, theme })),
    state.showModal && h(TspModal, {
      title: '确认',
      message: '组件弹窗在详情页中也需要完整显示。',
      confirmText: '确定',
      cancelText: '取消',
      theme,
      onConfirm: () => state.setShowModal(false),
      onCancel: () => state.setShowModal(false)
    })
  );
}

export function BasicControlsSample() {
  const themeOptions = useMemo(() => [
    { key: 'sky', title: 'Sky' },
    { key: 'night', title: 'Night' },
    { key: 'mint', title: 'Mint' },
    { key: 'sunrise', title: 'Sunrise' }
  ], []);
  const languageOptions = useMemo(() => [
    { key: 'zh-CN', title: '简体中文' },
    { key: 'en', title: 'English' },
    { key: 'ja', title: '日本語' }
  ], []);
  const [themeKey, setThemeKey] = useState('sky');
  const [locale, setLocale] = useState('zh-CN');
  const theme = starPlanetThemes[themeKey];
  const t = (key) => sampleLocales[locale][key] ?? sampleLocales.en[key] ?? key;
  const [checked, setChecked] = useState(true);
  const [tab, setTab] = useState('learn');
  const [selectedTab, setSelectedTab] = useState(0);
  const [selectedOption, setSelectedOption] = useState(1);
  const [pinValue, setPinValue] = useState('12');
  const [inputValue, setInputValue] = useState('');
  const [showSheet, setShowSheet] = useState(false);
  const [selectedDocName, setSelectedDocName] = useState(() => window.location.hash.replace(/^#/, ''));
  const [showModal, setShowModal] = useState(false);
  const [toast, setToast] = useState(null);
  const tabs = useMemo(() => [{ key: 'learn', title: '学习', icon: '⌂' }, { key: 'settings', title: '设置', icon: '⚙' }], []);
  const showToast = (message, variant = 'info') => {
    setToast({ message, variant, id: Date.now() });
    window.clearTimeout(showToast.timer);
    showToast.timer = window.setTimeout(() => setToast(null), 1800);
  };
  useEffect(() => {
    const syncFromHash = () => setSelectedDocName(window.location.hash.replace(/^#/, ''));
    window.addEventListener('hashchange', syncFromHash);
    return () => window.removeEventListener('hashchange', syncFromHash);
  }, []);
  const openDoc = (name) => {
    window.location.hash = name;
    setSelectedDocName(name);
  };
  const closeDoc = () => {
    history.pushState('', document.title, window.location.pathname + window.location.search);
    setSelectedDocName('');
  };
  // Hash routing keeps each component page directly shareable without adding a router dependency.
  const selectedDoc = componentDocs.find((doc) => doc.name === selectedDocName);
  const previewState = {
    checked,
    setChecked,
    inputValue,
    setInputValue,
    selectedOption,
    setSelectedOption,
    selectedTab,
    setSelectedTab,
    pinValue,
    setPinValue,
    showSheet,
    setShowSheet,
    showModal,
    setShowModal,
    toast,
    tabs,
    tab,
    setTab,
    showToast
  };
  if (selectedDoc) {
    return h(ComponentDocPage, {
      doc: selectedDoc,
      theme,
      state: previewState,
      onBack: closeDoc
    });
  }
  const settingsPage = h(React.Fragment, null,
    h(TspCard, { theme }, h('strong', null, t('themeSwitch')), h('p', null, t('themeHint')), h(TspSelect, {
      options: themeOptions,
      selectedIndex: themeOptions.findIndex((item) => item.key === themeKey),
      theme,
      onSelect: (_, option) => setThemeKey(option.key)
    })),
    h(TspCard, { theme }, h('strong', null, t('languageSwitch')), h('p', null, t('languageHint')), h(TspSelect, {
      options: languageOptions,
      selectedIndex: languageOptions.findIndex((item) => item.key === locale),
      theme,
      onSelect: (_, option) => setLocale(option.key)
    }))
  );
  const learnPage = componentCategories.map((category) => h('section', { key: category, className: 'bc-doc-home-section' },
    h('div', { className: 'bc-doc-section-title' }, category),
    h('div', { className: 'bc-doc-list' },
      componentDocs.filter((doc) => doc.category === category).map((doc) => h(TspListItem, {
        key: doc.name,
        title: doc.component,
        message: doc.description,
        trailing: '›',
        theme,
        onTap: () => openDoc(doc.name)
      }))
    )
  ));
  return h(
    'main',
    { className: 'bc-sample', style: themed(theme) },
    h(TspTopBar, { title: t('title'), showBack: true, theme }),
    h('section', { className: 'bc-sample__grid' },
      tab === 'settings' ? settingsPage : learnPage
    ),
    toast && h('div', { className: 'bc-toast-layer', key: toast.id }, h(TspToast, { message: toast.message, variant: toast.variant, theme })),
    h(TspBottomTab, { tabs, selectedKey: tab, theme, onSelect: setTab }),
    showModal && h(TspModal, {
      title: t('modalTitle'),
      message: t('modalBody'),
      confirmText: t('ok'),
      cancelText: t('cancel'),
      theme,
      onConfirm: () => setShowModal(false),
      onCancel: () => setShowModal(false)
    })
  );
}
