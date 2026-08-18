/**
 * BasicControlsSample – Interactive component catalog for TechSkillPlanet React Web controls.
 * This sample renders all components in a navigable, themed showcase.
 */
import React, { useEffect, useMemo, useState } from 'react';
import { componentCategories, componentDocs } from '../shared/componentDocs.js';
import {
  starPlanetTheme, starPlanetThemes, themeVars,
  TspAlert, TspAmount, TspBadge, TspBottomTab, TspButton, TspCard, TspChip,
  TspEmpty, TspIconButton, TspInput, TspKeyValueLabel, TspListItem, TspModal,
  TspNotification, TspOptionSheet, TspPinInput, TspProgress, TspSelect,
  TspStepper, TspStickyFooter, TspSwitch, TspTabs, TspTextLink, TspToast, TspTopBar
} from '@techskillplanet/planet-components-react';

const h = React.createElement;
const themed = (theme, style) => ({ ...themeVars(theme), ...style });

const sampleLocales = {
  'zh-CN': {
    title: '基础组件', themeSwitch: '主题切换', languageSwitch: '语言切换',
    pageSwitch: '页面切换', themeHint: '切换主题后，页面、组件、弹窗和 Toast 同步变更。',
    languageHint: '切换语言后，样例文案从内置 JSON 字典读取。',
    primary: '主按钮', default: '默认按钮', danger: '危险按钮',
    card: '卡片', cardBody: '星球主题内容卡片。', success: '成功',
    applied: '主题已应用。', badge: '徽标', chip: '标签', input: '输入框',
    switch: '开关', progress: '进度', notice: '通知', noticeBody: '继续学习。',
    listItem: '列表项', selectedState: '选中状态', empty: '空状态',
    emptyBody: '暂无记录。', action: '操作', showToast: '显示 Toast',
    openModal: '打开弹窗', stickyFooter: '底部固定操作', modalTitle: '确认',
    platformSwitch: '平台预览', platformHint: '切换手机 / 桌面布局，验证全场景展示。',
    modalBody: '弹窗遵循同一套组件契约，并验证移动端高度、滚动和底部按钮完整显示。',
    cancel: '取消', ok: '确定', auto: '自动', mobile: '手机', desktop: '桌面',
    learn: '学习', settings: '设置', selectTitle: '请选择'
  },
  en: {
    title: 'Basic Controls', themeSwitch: 'Theme Switch', languageSwitch: 'Language Switch',
    pageSwitch: 'Page Switch', themeHint: 'Theme changes update the page, components, modal and toast.',
    languageHint: 'Sample text is loaded from the built-in JSON dictionary.',
    primary: 'Primary', default: 'Default', danger: 'Danger', card: 'Card',
    cardBody: 'Star Planet surface card.', success: 'Success', applied: 'Theme is applied.',
    badge: 'Badge', chip: 'Chip', input: 'Input', switch: 'Switch', progress: 'Progress',
    notice: 'Notice', noticeBody: 'Keep learning.', listItem: 'List item',
    selectedState: 'Selected state', empty: 'Empty', emptyBody: 'No records yet.',
    action: 'Action', showToast: 'Show Toast', openModal: 'Open Modal',
    stickyFooter: 'Sticky Footer', modalTitle: 'Confirm',
    platformSwitch: 'Platform Preview', platformHint: 'Switch mobile / desktop layout for full coverage.',
    modalBody: 'The modal follows the same contract and verifies mobile height, scrolling and visible actions.',
    cancel: 'Cancel', ok: 'OK', auto: 'Auto', mobile: 'Mobile', desktop: 'Desktop',
    learn: 'Learn', settings: 'Settings', selectTitle: 'Choose'
  },
  ja: {
    title: '基本コンポーネント', themeSwitch: 'テーマ切替', languageSwitch: '言語切替',
    pageSwitch: 'ページ切替', themeHint: 'テーマ変更はページ、部品、モーダル、トーストへ反映されます。',
    languageHint: 'サンプル文言は内蔵 JSON 辞書から読み込みます。',
    primary: '主要ボタン', default: '標準ボタン', danger: '危険ボタン',
    card: 'カード', cardBody: '星球テーマのカード。', success: '成功',
    applied: 'テーマを適用しました。', badge: 'バッジ', chip: 'チップ',
    input: '入力', switch: 'スイッチ', progress: '進捗', notice: '通知',
    noticeBody: '学習を続けましょう。', listItem: 'リスト項目',
    selectedState: '選択状態', empty: '空状態', emptyBody: '記録はありません。',
    action: '操作', showToast: 'Toast 表示', openModal: 'モーダルを開く',
    stickyFooter: '固定フッター', modalTitle: '確認',
    platformSwitch: 'プラットフォーム', platformHint: 'モバイル / デスクトップ表示を切替えて検証します。',
    modalBody: 'モーダルは同じ契約に従い、モバイル高さとスクロールを検証します。',
    cancel: '取消', ok: 'OK', auto: '自動', mobile: 'モバイル', desktop: 'デスクトップ',
    learn: '学習', settings: '設定', selectTitle: '選択'
  },
  'zh-TW': {
    title: '基礎元件', themeSwitch: '主題切換', languageSwitch: '語言切換',
    pageSwitch: '頁面切換', themeHint: '切換主題後，頁面、元件、彈窗和 Toast 同步變更。',
    languageHint: '切換語言後，樣例文案從內建字典讀取。',
    primary: '主按鈕', default: '預設按鈕', danger: '危險按鈕',
    card: '卡片', cardBody: '星球主題內容卡片。', success: '成功',
    applied: '主題已套用。', badge: '徽標', chip: '標籤', input: '輸入框',
    switch: '開關', progress: '進度', notice: '通知', noticeBody: '繼續學習。',
    listItem: '列表項', selectedState: '選中狀態', empty: '空狀態',
    emptyBody: '暫無紀錄。', action: '操作', showToast: '顯示 Toast',
    openModal: '開啟彈窗', stickyFooter: '底部固定操作', modalTitle: '確認',
    platformSwitch: '平台預覽', platformHint: '切換手機 / 桌面佈局，驗證全場景展示。',
    modalBody: '彈窗遵循同一套元件契約，並驗證行動端高度、捲動和底部按鈕完整顯示。',
    cancel: '取消', ok: '確定', auto: '自動', mobile: '手機', desktop: '桌面',
    learn: '學習', settings: '設定', selectTitle: '請選擇'
  },
  ko: {
    title: '기본 컴포넌트', themeSwitch: '테마 전환', languageSwitch: '언어 전환',
    pageSwitch: '페이지 전환', themeHint: '테마를 바꾸면 페이지, 컴포넌트, 모달, Toast가 함께 변경됩니다.',
    languageHint: '언어를 바꾸면 샘플 문구가 내장 사전에서 읽힙니다.',
    primary: '기본 버튼', default: '기본값', danger: '위험 버튼',
    card: '카드', cardBody: 'Sky Planet 콘텐츠 카드.', success: '성공',
    applied: '테마가 적용되었습니다.', badge: '배지', chip: '칩', input: '입력',
    switch: '스위치', progress: '진행률', notice: '알림', noticeBody: '학습을 계속하세요.',
    listItem: '리스트 항목', selectedState: '선택 상태', empty: '빈 상태',
    emptyBody: '기록이 없습니다.', action: '작업', showToast: 'Toast 표시',
    openModal: '모달 열기', stickyFooter: '하단 고정 작업', modalTitle: '확인',
    platformSwitch: '플랫폼 미리보기', platformHint: '모바일 / 데스크톱 레이아웃을 전환해 전체 시나리오를 검증합니다.',
    modalBody: '모달은 동일한 계약을 따르며 모바일 높이, 스크롤, 하단 버튼 표시를 검증합니다.',
    cancel: '취소', ok: '확인', auto: '자동', mobile: '모바일', desktop: '데스크톱',
    learn: '학습', settings: '설정', selectTitle: '선택'
  },
  es: {
    title: 'Controles básicos', themeSwitch: 'Cambio de tema', languageSwitch: 'Cambio de idioma',
    pageSwitch: 'Cambio de página', themeHint: 'Al cambiar el tema se actualizan página, componentes, modal y toast.',
    languageHint: 'El texto del sample se carga desde el diccionario integrado.',
    primary: 'Principal', default: 'Predeterminado', danger: 'Peligro', card: 'Tarjeta',
    cardBody: 'Tarjeta de superficie Sky Planet.', success: 'Correcto', applied: 'Tema aplicado.',
    badge: 'Insignia', chip: 'Chip', input: 'Entrada', switch: 'Interruptor', progress: 'Progreso',
    notice: 'Aviso', noticeBody: 'Sigue aprendiendo.', listItem: 'Elemento de lista',
    selectedState: 'Estado seleccionado', empty: 'Vacío', emptyBody: 'Aún no hay registros.',
    action: 'Acción', showToast: 'Mostrar Toast', openModal: 'Abrir modal',
    stickyFooter: 'Pie fijo', modalTitle: 'Confirmar',
    platformSwitch: 'Vista de plataforma', platformHint: 'Cambia entre móvil y escritorio para cubrir todos los escenarios.',
    modalBody: 'El modal sigue el mismo contrato y verifica altura, desplazamiento y acciones visibles en móvil.',
    cancel: 'Cancelar', ok: 'Aceptar', auto: 'Auto', mobile: 'Móvil', desktop: 'Escritorio',
    learn: 'Aprender', settings: 'Ajustes', selectTitle: 'Elegir'
  },
  fr: {
    title: 'Contrôles de base', themeSwitch: 'Changement de thème', languageSwitch: 'Changement de langue',
    pageSwitch: 'Changement de page', themeHint: 'Le thème met à jour la page, les composants, la modale et le toast.',
    languageHint: 'Les textes du sample viennent du dictionnaire intégré.',
    primary: 'Principal', default: 'Par défaut', danger: 'Danger', card: 'Carte',
    cardBody: 'Carte de surface Sky Planet.', success: 'Succès', applied: 'Thème appliqué.',
    badge: 'Badge', chip: 'Chip', input: 'Saisie', switch: 'Interrupteur', progress: 'Progression',
    notice: 'Notification', noticeBody: 'Continuez à apprendre.', listItem: 'Élément de liste',
    selectedState: 'État sélectionné', empty: 'Vide', emptyBody: 'Aucun enregistrement.',
    action: 'Action', showToast: 'Afficher le toast', openModal: 'Ouvrir la modale',
    stickyFooter: 'Pied fixe', modalTitle: 'Confirmer',
    platformSwitch: 'Aperçu plateforme', platformHint: 'Basculez mobile / bureau pour couvrir tous les scénarios.',
    modalBody: 'La modale suit le même contrat et vérifie hauteur, défilement et actions visibles sur mobile.',
    cancel: 'Annuler', ok: 'OK', auto: 'Auto', mobile: 'Mobile', desktop: 'Bureau',
    learn: 'Apprendre', settings: 'Réglages', selectTitle: 'Choisir'
  },
  de: {
    title: 'Basis-Controls', themeSwitch: 'Thema wechseln', languageSwitch: 'Sprache wechseln',
    pageSwitch: 'Seite wechseln', themeHint: 'Themenwechsel aktualisiert Seite, Komponenten, Modal und Toast.',
    languageHint: 'Sample-Texte kommen aus dem integrierten Wörterbuch.',
    primary: 'Primär', default: 'Standard', danger: 'Gefahr', card: 'Karte',
    cardBody: 'Sky-Planet-Inhaltskarte.', success: 'Erfolg', applied: 'Thema angewendet.',
    badge: 'Badge', chip: 'Chip', input: 'Eingabe', switch: 'Schalter', progress: 'Fortschritt',
    notice: 'Hinweis', noticeBody: 'Weiterlernen.', listItem: 'Listeneintrag',
    selectedState: 'Ausgewählt', empty: 'Leer', emptyBody: 'Noch keine Einträge.',
    action: 'Aktion', showToast: 'Toast anzeigen', openModal: 'Modal öffnen',
    stickyFooter: 'Fixierte Fußleiste', modalTitle: 'Bestätigen',
    platformSwitch: 'Plattformvorschau', platformHint: 'Zwischen Mobil und Desktop wechseln, um alle Szenarien zu prüfen.',
    modalBody: 'Das Modal folgt demselben Vertrag und prüft Höhe, Scrollen und sichtbare Aktionen auf Mobilgeräten.',
    cancel: 'Abbrechen', ok: 'OK', auto: 'Auto', mobile: 'Mobil', desktop: 'Desktop',
    learn: 'Lernen', settings: 'Einstellungen', selectTitle: 'Auswählen'
  }
};

function detectPlatform() {
  if (typeof window === 'undefined' || typeof window.matchMedia !== 'function') return 'mobile';
  return window.matchMedia('(min-width: 1024px)').matches ? 'desktop' : 'mobile';
}

function TspDocPreview({ name, theme, state }) {
  const common = { theme };
  const example = (title, node) => h('div', { key: title, className: 'bc-example-item' }, h('div', { className: 'bc-example-title' }, title), node);
  switch (name) {
    case 'Button': return h('div', { className: 'bc-example-stack' }, ['primary', 'default', 'danger', 'text'].map((variant) => example(variant, h(TspButton, { text: variant, variant, ...common }))));
    case 'Card': return h('div', { className: 'bc-example-stack' }, [example('default', h(TspCard, common, h('strong', null, 'Card'), h('p', null, 'Star Planet card.'))), example('selected', h(TspCard, { selected: true, ...common }, 'Selected card'))]);
    case 'Alert': return h('div', { className: 'bc-example-stack' }, ['info', 'success', 'warning', 'error'].map((variant) => example(variant, h(TspAlert, { title: variant, message: 'Theme is applied.', variant, ...common }))));
    case 'Badge': return h('div', { className: 'bc-row' }, ['default', 'primary', 'success', 'warning', 'danger'].map((variant) => h(TspBadge, { key: variant, text: variant, variant, ...common })));
    case 'Chip': return h('div', { className: 'bc-row' }, [h(TspChip, { key: 'default', text: 'Default', ...common }), h(TspChip, { key: 'selected', text: 'Selected', selected: true, ...common }), h(TspChip, { key: 'disabled', text: 'Disabled', disabled: true, ...common })]);
    case 'Input': return h('div', { className: 'bc-example-stack' }, [example('default', h(TspInput, { value: state.inputValue, placeholder: 'Input', onChange: state.setInputValue, ...common })), example('error', h(TspInput, { value: '', placeholder: 'Required', variant: 'error', ...common }))]);
    case 'Select': return h(TspSelect, { options: ['A', 'B', 'C'], selectedIndex: state.selectedOption, onSelect: state.setSelectedOption, ...common });
    case 'OptionSheet': return h(TspButton, { text: 'Open OptionSheet', variant: 'primary', onTap: () => state.setShowSheet(true), ...common });
    case 'Switch': return h('div', { className: 'bc-example-stack' }, [
      example('md', h(TspSwitch, { text: 'Switch', checked: state.checked, onChange: state.setChecked, ...common })),
      example('sm', h(TspSwitch, { text: 'Small', checked: state.checked, onChange: state.setChecked, variant: 'sm', ...common })),
      example('loading', h(TspSwitch, { text: 'Loading', checked: true, loading: true, ...common })),
      example('disabled', h(TspSwitch, { text: 'Disabled', checked: false, disabled: true, ...common }))
    ]);
    case 'Progress': return h('div', { className: 'bc-example-stack' }, ['primary', 'success', 'warning', 'danger'].map((variant, index) => example(variant, h(TspProgress, { progress: [38, 68, 52, 82][index], variant, ...common }))));
    case 'TopBar': return h(TspTopBar, { title: '基础组件', showBack: true, ...common });
    case 'BottomTab': return h('div', { className: 'bc-doc-sticky-demo' }, h(TspBottomTab, { tabs: state.tabs, selectedKey: state.tab, onSelect: state.setTab, ...common }));
    case 'Tabs': return h(TspTabs, { tabs: ['全部', '已学', '未学'], selectedIndex: state.selectedTab, onSelect: state.setSelectedTab, ...common });
    case 'Amount': return h('div', { className: 'bc-example-stack' }, [example('monthly', h(TspAmount, { symbol: '$', value: '128.80', cycle: 'month', ...common })), example('strike', h(TspAmount, { symbol: '$', value: '199.00', strikeThrough: true, ...common }))]);
    case 'IconButton': return h('div', { className: 'bc-row' }, [h(TspIconButton, { key: 'selected', icon: '♪', selected: true, ...common }), h(TspIconButton, { key: 'primary', icon: '✓', variant: 'primary', ...common }), h(TspIconButton, { key: 'disabled', icon: '×', disabled: true, ...common })]);
    case 'KeyValueLabel': return h(TspKeyValueLabel, { label: 'Progress', value: '12/48', ...common });
    case 'Notification': return h('div', { className: 'bc-example-stack' }, [example('info', h(TspNotification, { title: '通知', message: '继续学习。', ...common })), example('alert', h(TspNotification, { title: '提醒', message: '今日任务未完成。', variant: 'alert', ...common }))]);
    case 'TextLink': return h('div', { className: 'bc-row' }, [h(TspTextLink, { key: 'default', text: 'Text Link', ...common }), h(TspTextLink, { key: 'inverse', text: 'Inverse', inverse: true, ...common })]);
    case 'Stepper': return h('div', { className: 'bc-example-stack' }, [example('3 steps', h(TspStepper, { stepCount: 3, currentStep: 2, ...common })), example('5 steps', h(TspStepper, { stepCount: 5, currentStep: 3, ...common }))]);
    case 'StickyFooter': return h('div', { className: 'bc-doc-sticky-demo' }, h(TspButton, { text: 'Sticky Footer', variant: 'primary', ...common }));
    case 'PinInput': return h('div', { className: 'bc-example-stack' }, [example('secure', h(TspPinInput, { value: state.pinValue, cellCount: 4, secure: true, onChange: state.setPinValue, ...common })), example('6 cells', h(TspPinInput, { value: '123', cellCount: 6, ...common }))]);
    case 'ListItem': return h('div', { className: 'bc-example-stack' }, [example('selected', h(TspListItem, { title: '列表项', message: '选中状态', trailing: '›', selected: true, ...common })), example('disabled', h(TspListItem, { title: '不可点击', message: '禁用状态', disabled: true, ...common }))]);
    case 'Empty': return h(TspEmpty, { title: '空状态', message: '暂无记录。', actionText: '操作', ...common });
    case 'Toast': return h(TspButton, { text: 'Show Toast', variant: 'primary', onTap: () => state.showToast('已保存', 'success'), ...common });
    case 'Modal': return h(TspButton, { text: 'Open Modal', onTap: () => state.setShowModal(true), ...common });
    default: return null;
  }
}

function ComponentDocPage({ doc, theme, state, onBack, layoutClass }) {
  return h(
    'main', {
      className: `bc-sample ${layoutClass}`,
      style: themed(theme),
      'data-platform': layoutClass.includes('desktop') ? 'desktop' : 'mobile'
    },
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
    state.showSheet && h(TspOptionSheet, { title: '请选择', options: ['A', 'B', 'C'], selectedIndex: state.selectedOption, theme, visible: true, onCancel: () => state.setShowSheet(false), onSelect: (index) => { state.setSelectedOption(index); state.setShowSheet(false); } }),
    state.toast && h('div', { className: 'bc-toast-layer', key: state.toast.id }, h(TspToast, { message: state.toast.message, variant: state.toast.variant, theme })),
    state.showModal && h(TspModal, { title: '确认', message: '组件弹窗在详情页中也需要完整显示。', confirmText: '确定', cancelText: '取消', theme, onConfirm: () => state.setShowModal(false), onCancel: () => state.setShowModal(false) })
  );
}

export function BasicControlsSample({
  forcePlatform,
  themeKey: themeKeyProp,
  locale: localeProp,
  onThemeKeyChange,
  onLocaleChange
} = {}) {
  const themeOptions = useMemo(() => [
    { key: 'sky', title: 'Sky' }, { key: 'night', title: 'Night' },
    { key: 'mint', title: 'Mint' }, { key: 'sunrise', title: 'Sunrise' }
  ], []);
  const languageOptions = useMemo(() => [
    { key: 'zh-CN', title: '简体中文' }, { key: 'en', title: 'English' }, { key: 'ja', title: '日本語' }
  ], []);
  const [themeKeyState, setThemeKeyState] = useState('sky');
  const [localeState, setLocaleState] = useState('zh-CN');
  const themeKey = themeKeyProp ?? themeKeyState;
  const locale = localeProp ?? localeState;
  const setThemeKey = (key) => {
    if (themeKeyProp === undefined) setThemeKeyState(key);
    onThemeKeyChange?.(key);
  };
  const setLocale = (next) => {
    if (localeProp === undefined) setLocaleState(next);
    onLocaleChange?.(next);
  };
  const [platformMode, setPlatformMode] = useState('auto');
  const [autoPlatform, setAutoPlatform] = useState(() => detectPlatform());
  const theme = starPlanetThemes[themeKey];
  const t = (key) => (sampleLocales[locale] || sampleLocales.en)[key] ?? sampleLocales.en[key] ?? key;
  const platformOptions = useMemo(() => [
    { key: 'auto', title: t('auto') }, { key: 'mobile', title: t('mobile') }, { key: 'desktop', title: t('desktop') }
  ], [locale]);
  const [checked, setChecked] = useState(true);
  const [tab, setTab] = useState('learn');
  const [selectedTab, setSelectedTab] = useState(0);
  const [selectedOption, setSelectedOption] = useState(1);
  const [pinValue, setPinValue] = useState('12');
  const [inputValue, setInputValue] = useState('');
  const [showSheet, setShowSheet] = useState(false);
  const [selectedDocName, setSelectedDocName] = useState(() => {
    if (forcePlatform || typeof window === 'undefined') return '';
    return window.location.hash.replace(/^#/, '');
  });
  const [showModal, setShowModal] = useState(false);
  const [toast, setToast] = useState(null);
  const tabs = useMemo(() => [{ key: 'learn', title: t('learn'), icon: '⌂' }, { key: 'settings', title: t('settings'), icon: '⚙' }], [locale]);
  const showToast = (message, variant = 'info') => {
    setToast({ message, variant, id: Date.now() });
    window.clearTimeout(showToast.timer);
    showToast.timer = window.setTimeout(() => setToast(null), 1800);
  };
  useEffect(() => {
    if (forcePlatform) return undefined;
    const syncFromHash = () => setSelectedDocName(window.location.hash.replace(/^#/, ''));
    window.addEventListener('hashchange', syncFromHash);
    const mediaQuery = typeof window.matchMedia === 'function' ? window.matchMedia('(min-width: 1024px)') : null;
    const syncPlatform = () => setAutoPlatform(detectPlatform());
    mediaQuery?.addEventListener('change', syncPlatform);
    return () => {
      window.removeEventListener('hashchange', syncFromHash);
      mediaQuery?.removeEventListener('change', syncPlatform);
    };
  }, [forcePlatform]);
  const openDoc = (name) => {
    if (!forcePlatform) window.location.hash = name;
    setSelectedDocName(name);
  };
  const closeDoc = () => {
    if (!forcePlatform) history.pushState('', document.title, window.location.pathname + window.location.search);
    setSelectedDocName('');
  };
  const routeName = selectedDocName;
  const selectedDoc = ['', 'top', 'preview', 'platforms', 'features', 'skills', 'install'].includes(routeName)
    ? undefined
    : componentDocs.find((doc) => doc.name === routeName);
  const previewState = { checked, setChecked, inputValue, setInputValue, selectedOption, setSelectedOption, selectedTab, setSelectedTab, pinValue, setPinValue, showSheet, setShowSheet, showModal, setShowModal, toast, tabs, tab, setTab, showToast };
  const resolvedPlatform = forcePlatform ?? (platformMode === 'auto' ? autoPlatform : platformMode);
  const layoutClass = resolvedPlatform === 'desktop' ? 'bc-sample--force-desktop' : 'bc-sample--force-mobile';
  if (selectedDoc) {
    return h(ComponentDocPage, { doc: selectedDoc, theme, state: previewState, onBack: closeDoc, layoutClass });
  }
  const settingsPage = h(React.Fragment, null,
    !forcePlatform && h(TspCard, { theme }, h('strong', null, t('platformSwitch')), h('p', null, t('platformHint')), h(TspSelect, { options: platformOptions, selectedIndex: platformOptions.findIndex((item) => item.key === platformMode), theme, title: t('selectTitle'), onSelect: (_, option) => setPlatformMode(option.key) })),
    h(TspCard, { theme }, h('strong', null, t('themeSwitch')), h('p', null, t('themeHint')), h(TspSelect, { options: themeOptions, selectedIndex: themeOptions.findIndex((item) => item.key === themeKey), theme, title: t('themeSwitch'), onSelect: (_, option) => setThemeKey(option.key) })),
    h(TspCard, { theme }, h('strong', null, t('languageSwitch')), h('p', null, t('languageHint')), h(TspSelect, { options: languageOptions, selectedIndex: languageOptions.findIndex((item) => item.key === locale), theme, title: t('languageSwitch'), onSelect: (_, option) => setLocale(option.key) }))
  );
  const learnPage = componentCategories.map((category) => h('section', { key: category, className: 'bc-doc-home-section' },
    h('div', { className: 'bc-doc-section-title' }, category),
    h('div', { className: 'bc-doc-list' },
      componentDocs.filter((doc) => doc.category === category).map((doc) => h(TspListItem, { key: doc.name, title: doc.component, message: doc.description, trailing: '›', theme, onTap: () => openDoc(doc.name) }))
    )
  ));
  return h(
    'main', {
      className: `bc-sample ${layoutClass}`,
      style: themed(theme),
      'data-platform': resolvedPlatform
    },
    h(TspTopBar, { title: t('title'), showBack: !forcePlatform, theme }),
    h('section', { className: 'bc-sample__grid' }, tab === 'settings' && !forcePlatform ? settingsPage : learnPage),
    toast && h('div', { className: 'bc-toast-layer', key: toast.id }, h(TspToast, { message: toast.message, variant: toast.variant, theme })),
    !forcePlatform && h(TspBottomTab, { tabs, selectedKey: tab, theme, onSelect: setTab }),
    showModal && h(TspModal, { title: t('modalTitle'), message: t('modalBody'), confirmText: t('ok'), cancelText: t('cancel'), theme, onConfirm: () => setShowModal(false), onCancel: () => setShowModal(false) })
  );
}
