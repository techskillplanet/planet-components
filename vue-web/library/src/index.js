import { defineComponent, h, onBeforeUnmount, onMounted, ref } from 'vue';
import { componentCategories, componentDocs } from '../../shared/componentDocs.js';
import { starPlanetTheme, starPlanetThemes, themeVars } from './theme.js';
import { themed } from './components/_shared.js';
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
function renderVueDocPreview(name, theme, state) {
  const common = { theme };
  const example = (title, node) => h('div', { class: 'bc-example-item' }, [h('div', { class: 'bc-example-title' }, title), node]);
  switch (name) {
    case 'Button': return h('div', { class: 'bc-example-stack' }, ['primary', 'default', 'danger', 'text'].map((variant) => example(variant, h(TspButton, { text: variant, variant, ...common }))));
    case 'Card': return h('div', { class: 'bc-example-stack' }, [example('default', h(TspCard, common, () => [h('strong', null, 'Card'), h('p', null, 'Star Planet card.')])), example('selected', h(TspCard, { selected: true, ...common }, () => 'Selected card'))]);
    case 'Alert': return h('div', { class: 'bc-example-stack' }, ['info', 'success', 'warning', 'error'].map((variant) => example(variant, h(TspAlert, { title: variant, message: 'Theme is applied.', variant, ...common }))));
    case 'Badge': return h('div', { class: 'bc-row' }, ['default', 'primary', 'success', 'warning', 'danger'].map((variant) => h(TspBadge, { text: variant, variant, ...common })));
    case 'Chip': return h('div', { class: 'bc-row' }, [h(TspChip, { text: 'Default', ...common }), h(TspChip, { text: 'Selected', selected: true, ...common }), h(TspChip, { text: 'Disabled', disabled: true, ...common })]);
    case 'Input': return h('div', { class: 'bc-example-stack' }, [example('default', h(TspInput, { value: state.inputValue.value, placeholder: 'Input', onChange: (value) => { state.inputValue.value = value; }, ...common })), example('error', h(TspInput, { value: '', placeholder: 'Required', variant: 'error', ...common }))]);
    case 'Select': return h(TspSelect, { options: ['A', 'B', 'C'], selectedIndex: state.selectedOption.value, onSelect: (index) => { state.selectedOption.value = index; }, ...common });
    case 'OptionSheet': return h(TspButton, { text: 'Open OptionSheet', variant: 'primary', onTap: () => { state.showSheet.value = true; }, ...common });
    case 'Switch': return h('div', { class: 'bc-example-stack' }, [example('checked', h(TspSwitch, { text: 'Switch', checked: state.checked.value, onChange: (value) => { state.checked.value = value; }, ...common })), example('loading', h(TspSwitch, { text: 'Loading', checked: true, loading: true, ...common }))]);
    case 'Progress': return h('div', { class: 'bc-example-stack' }, ['primary', 'success', 'warning', 'danger'].map((variant, index) => example(variant, h(TspProgress, { progress: [38, 68, 52, 82][index], variant, ...common }))));
    case 'TopBar': return h(TspTopBar, { title: '基础组件', showBack: true, ...common });
    case 'BottomTab': return h('div', { class: 'bc-doc-sticky-demo' }, h(TspBottomTab, { tabs: state.tabs, selectedKey: state.tab.value, onSelect: (key) => { state.tab.value = key; }, ...common }));
    case 'Tabs': return h(TspTabs, { tabs: ['全部', '已学', '未学'], selectedIndex: state.selectedTab.value, onSelect: (index) => { state.selectedTab.value = index; }, ...common });
    case 'Amount': return h('div', { class: 'bc-example-stack' }, [example('monthly', h(TspAmount, { symbol: '$', value: '128.80', cycle: 'month', ...common })), example('strike', h(TspAmount, { symbol: '$', value: '199.00', strikeThrough: true, ...common }))]);
    case 'IconButton': return h('div', { class: 'bc-row' }, [h(TspIconButton, { icon: '♪', selected: true, ...common }), h(TspIconButton, { icon: '✓', variant: 'primary', ...common }), h(TspIconButton, { icon: '×', disabled: true, ...common })]);
    case 'KeyValueLabel': return h(TspKeyValueLabel, { label: 'Progress', value: '12/48', ...common });
    case 'Notification': return h('div', { class: 'bc-example-stack' }, [example('info', h(TspNotification, { title: '通知', message: '继续学习。', ...common })), example('alert', h(TspNotification, { title: '提醒', message: '今日任务未完成。', variant: 'alert', ...common }))]);
    case 'TextLink': return h('div', { class: 'bc-row' }, [h(TspTextLink, { text: 'Text Link', ...common }), h(TspTextLink, { text: 'Inverse', inverse: true, ...common })]);
    case 'Stepper': return h('div', { class: 'bc-example-stack' }, [example('3 steps', h(TspStepper, { stepCount: 3, currentStep: 2, ...common })), example('5 steps', h(TspStepper, { stepCount: 5, currentStep: 3, ...common }))]);
    case 'StickyFooter': return h('div', { class: 'bc-doc-sticky-demo' }, h(TspButton, { text: 'Sticky Footer', variant: 'primary', ...common }));
    case 'PinInput': return h('div', { class: 'bc-example-stack' }, [example('secure', h(TspPinInput, { value: state.pinValue.value, cellCount: 4, secure: true, onChange: (value) => { state.pinValue.value = value; }, ...common })), example('6 cells', h(TspPinInput, { value: '123', cellCount: 6, ...common }))]);
    case 'ListItem': return h('div', { class: 'bc-example-stack' }, [example('selected', h(TspListItem, { title: '列表项', message: '选中状态', trailing: '›', selected: true, ...common })), example('disabled', h(TspListItem, { title: '不可点击', message: '禁用状态', disabled: true, ...common }))]);
    case 'Empty': return h(TspEmpty, { title: '空状态', message: '暂无记录。', actionText: '操作', ...common });
    case 'Toast': return h(TspButton, { text: 'Show Toast', variant: 'primary', onTap: () => state.showToast('已保存', 'success'), ...common });
    case 'Modal': return h(TspButton, { text: 'Open Modal', onTap: () => { state.showModal.value = true; }, ...common });
    default: return null;
  }
}

function renderVueDocPage(doc, theme, state, onBack) {
  return h('main', { class: 'bc-sample', style: themed(theme) }, [
    h(TspTopBar, { title: doc.component, showBack: true, theme, onBack }),
    h('section', { class: 'bc-sample__grid bc-doc-page' }, [
      h(TspCard, { theme }, () => [
        h('div', { class: 'bc-doc-eyebrow' }, doc.category),
        h('h1', { class: 'bc-doc-title' }, doc.component),
        h('p', { class: 'bc-doc-desc' }, doc.description)
      ]),
      h(TspCard, { theme }, () => [
        h('strong', null, '使用案例'),
        h('div', { class: 'bc-doc-preview' }, renderVueDocPreview(doc.name, theme, state))
      ]),
      h(TspCard, { theme }, () => [
        h('strong', null, 'API'),
        h('div', { class: 'bc-doc-section-title' }, 'Props'),
        h('div', { class: 'bc-doc-chip-row' }, doc.props.map((prop) => h('span', { class: 'bc-doc-chip' }, prop))),
        h('div', { class: 'bc-doc-section-title' }, 'Variants'),
        h('div', { class: 'bc-doc-chip-row' }, doc.variants.map((variant) => h('span', { class: 'bc-doc-chip' }, variant)))
      ]),
      h(TspCard, { theme }, () => [
        h('strong', null, '技术栈同步'),
        h('div', { class: 'bc-doc-chip-row' }, doc.platforms.map((platform) => h('span', { class: 'bc-doc-chip' }, platform)))
      ])
    ]),
    state.showSheet.value && h(TspOptionSheet, {
      title: '请选择',
      options: ['A', 'B', 'C'],
      selectedIndex: state.selectedOption.value,
      theme,
      visible: true,
      onCancel: () => { state.showSheet.value = false; },
      onSelect: (index) => {
        state.selectedOption.value = index;
        state.showSheet.value = false;
      }
    }),
    state.toast.value && h('div', { class: 'bc-toast-layer', key: state.toast.value.id }, h(TspToast, { message: state.toast.value.message, variant: state.toast.value.variant, theme })),
    state.showModal.value && h(TspModal, {
      title: '确认',
      message: '组件弹窗在详情页中也需要完整显示。',
      confirmText: '确定',
      cancelText: '取消',
      theme,
      onConfirm: () => { state.showModal.value = false; },
      onCancel: () => { state.showModal.value = false; }
    })
  ]);
}

export const BasicControlsSample = defineComponent({
  name: 'BasicControlsSample',
  setup() {
    const themeOptions = [
      { key: 'sky', title: 'Sky' },
      { key: 'night', title: 'Night' },
      { key: 'mint', title: 'Mint' },
      { key: 'sunrise', title: 'Sunrise' }
    ];
    const languageOptions = [
      { key: 'zh-CN', title: '简体中文' },
      { key: 'en', title: 'English' },
      { key: 'ja', title: '日本語' }
    ];
    const themeKey = ref('sky');
    const locale = ref('zh-CN');
    const checked = ref(true);
    const tab = ref('learn');
    const selectedTab = ref(0);
    const selectedOption = ref(1);
    const pinValue = ref('12');
    const inputValue = ref('');
    const showSheet = ref(false);
    const selectedDocName = ref(window.location.hash.replace(/^#/, ''));
    const showModal = ref(false);
    const toast = ref(null);
    const tabs = [{ key: 'learn', title: '学习', icon: '⌂' }, { key: 'settings', title: '设置', icon: '⚙' }];
    let toastTimer = 0;
    const showToast = (message, variant = 'info') => {
      toast.value = { message, variant, id: Date.now() };
      window.clearTimeout(toastTimer);
      toastTimer = window.setTimeout(() => { toast.value = null; }, 1800);
    };
    const syncFromHash = () => { selectedDocName.value = window.location.hash.replace(/^#/, ''); };
    const openDoc = (name) => {
      window.location.hash = name;
      selectedDocName.value = name;
    };
    const closeDoc = () => {
      history.pushState('', document.title, window.location.pathname + window.location.search);
      selectedDocName.value = '';
    };
    onMounted(() => window.addEventListener('hashchange', syncFromHash));
    onBeforeUnmount(() => window.removeEventListener('hashchange', syncFromHash));
    return () => {
      const theme = starPlanetThemes[themeKey.value];
      const t = (key) => sampleLocales[locale.value][key] ?? sampleLocales.en[key] ?? key;
      // Hash routing keeps each component page directly shareable without adding a router dependency.
      const selectedDoc = componentDocs.find((doc) => doc.name === selectedDocName.value);
      const previewState = {
        checked,
        inputValue,
        selectedOption,
        selectedTab,
        pinValue,
        showSheet,
        showModal,
        toast,
        tabs,
        tab,
        showToast
      };
      if (selectedDoc) {
        return renderVueDocPage(selectedDoc, theme, previewState, closeDoc);
      }
      const settingsPage = [
        h(TspCard, { theme }, () => [h('strong', null, t('themeSwitch')), h('p', null, t('themeHint')), h(TspSelect, {
          options: themeOptions,
          selectedIndex: themeOptions.findIndex((item) => item.key === themeKey.value),
          theme,
          onSelect: (_, option) => { themeKey.value = option.key; }
        })]),
        h(TspCard, { theme }, () => [h('strong', null, t('languageSwitch')), h('p', null, t('languageHint')), h(TspSelect, {
          options: languageOptions,
          selectedIndex: languageOptions.findIndex((item) => item.key === locale.value),
          theme,
          onSelect: (_, option) => { locale.value = option.key; }
        })])
      ];
      const learnPage = componentCategories.map((category) => h('section', { class: 'bc-doc-home-section' }, [
        h('div', { class: 'bc-doc-section-title' }, category),
        h('div', { class: 'bc-doc-list' }, componentDocs.filter((doc) => doc.category === category).map((doc) => h(TspListItem, {
          title: doc.component,
          message: doc.description,
          trailing: '›',
          theme,
          onTap: () => openDoc(doc.name)
        })))
      ]));
      return h('main', { class: 'bc-sample', style: themed(theme) }, [
      h(TspTopBar, { title: t('title'), showBack: true, theme }),
      h('section', { class: 'bc-sample__grid' }, [
        ...(tab.value === 'settings' ? settingsPage : learnPage)
      ]),
      toast.value && h('div', { class: 'bc-toast-layer', key: toast.value.id }, h(TspToast, { message: toast.value.message, variant: toast.value.variant, theme })),
      h(TspBottomTab, { tabs, selectedKey: tab.value, theme, onSelect: (key) => { tab.value = key; } }),
      showModal.value && h(TspModal, {
        title: t('modalTitle'),
        message: t('modalBody'),
        confirmText: t('ok'),
        cancelText: t('cancel'),
        theme,
        onConfirm: () => { showModal.value = false; },
        onCancel: () => { showModal.value = false; }
      })
    ]);
    };
  }
});
