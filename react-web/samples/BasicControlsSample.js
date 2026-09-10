/**
 * BasicControlsSample – Interactive component catalog for TechSkillPlanet React Web controls.
 * This sample renders all components in a navigable, themed showcase.
 */
import React, { useEffect, useMemo, useState } from 'react';
import { componentCategories, componentDocs } from '../shared/componentDocs.js';
import { localizeDocs } from '../shared/componentDocs.i18n.js';
import { sampleT } from '../shared/sampleLocales.js';
import {
  starPlanetThemes, themeVars,
  TspAlert, TspAmount, TspBadge, TspBottomTab, TspButton, TspCard, TspChip,
  TspEmpty, TspIconButton, TspInput, TspKeyValueLabel, TspListItem, TspLoadingDialog, TspModal,
  TspNotification, TspOptionSheet, TspPinInput, TspProgress, TspRefreshLayout, TspSelect,
  TspStepper, TspStickyFooter, TspSwitch, TspTabs, TspTextLink, TspToast, TspTopBar,
  TspDatePicker, TspChildSwitcher, TspScoreRuleGrid, TspRedeemCardGrid,
  TspCalendarHeatmap, TspPrintSheet, TspBalanceHero, TspCheckInStreakCard,
  TspCheckbox, TspCollapse, TspDivider, TspRadio, TspSearchBar, TspSegmentedControl, TspStarRating,
  TspAvatar, TspSkeleton, TspTooltip, TspSlider, TspTextArea, TspDrawer, TspInputNumber, TspSwiper,
  TspTag, TspFab, TspTimePicker, TspUpload, TspTable, TspTree, TspCascader
} from '@techskillplanet/planet-components-react';

const h = React.createElement;
const themed = (theme, style) => ({ ...themeVars(theme), ...style });

function detectPlatform() {
  if (typeof window === 'undefined' || typeof window.matchMedia !== 'function') return 'mobile';
  return window.matchMedia('(min-width: 1024px)').matches ? 'desktop' : 'mobile';
}

function TspDocPreview({ name, theme, state, t }) {
  const common = { theme };
  const example = (title, node) => h('div', { key: title, className: 'bc-example-item' }, h('div', { className: 'bc-example-title' }, title), node);
  switch (name) {
    case 'Button': return h('div', { className: 'bc-example-stack' }, ['primary', 'default', 'danger', 'text'].map((variant) => example(variant, h(TspButton, { text: variant, variant, ...common }))));
    case 'Card': return h('div', { className: 'bc-example-stack' }, [example('default', h(TspCard, common, h('strong', null, t('card')), h('p', null, t('starPlanetCard')))), example('selected', h(TspCard, { selected: true, ...common }, t('cardSelected')))]);
    case 'Alert': return h('div', { className: 'bc-example-stack' }, ['info', 'success', 'warning', 'error'].map((variant) => example(variant, h(TspAlert, { title: variant, message: t('applied'), variant, ...common }))));
    case 'Badge': return h('div', { className: 'bc-row' }, ['default', 'primary', 'success', 'warning', 'danger'].map((variant) => h(TspBadge, { key: variant, text: variant, variant, ...common })));
    case 'Chip': return h('div', { className: 'bc-row' }, [h(TspChip, { key: 'default', text: t('default'), ...common }), h(TspChip, { key: 'selected', text: t('selected'), selected: true, ...common }), h(TspChip, { key: 'disabled', text: t('disabled'), disabled: true, ...common })]);
    case 'Input': return h('div', { className: 'bc-example-stack' }, [example('default', h(TspInput, { value: state.inputValue, placeholder: t('input'), onChange: state.setInputValue, ...common })), example('error', h(TspInput, { value: '', placeholder: t('required'), variant: 'error', ...common }))]);
    case 'Select': return h(TspSelect, { options: ['A', 'B', 'C'], selectedIndex: state.selectedOption, onSelect: state.setSelectedOption, ...common });
    case 'OptionSheet': return h(TspButton, { text: t('openOptionSheet'), variant: 'primary', onTap: () => state.setShowSheet(true), ...common });
    case 'Switch': return h('div', { className: 'bc-example-stack' }, [
      example('md', h(TspSwitch, { text: t('switch'), checked: state.checked, onChange: state.setChecked, ...common })),
      example('sm', h(TspSwitch, { text: t('small'), checked: state.checked, onChange: state.setChecked, variant: 'sm', ...common })),
      example('loading', h(TspSwitch, { text: t('loading'), checked: true, loading: true, ...common })),
      example('disabled', h(TspSwitch, { text: t('disabled'), checked: false, disabled: true, ...common }))
    ]);
    case 'Checkbox': return h('div', { className: 'bc-example-stack' }, [
      example('checked', h(TspCheckbox, { text: t('agree'), checked: state.checked, onChange: state.setChecked, ...common })),
      example('disabled', h(TspCheckbox, { text: t('disabled'), checked: true, disabled: true, ...common }))
    ]);
    case 'Radio': return h('div', { className: 'bc-example-stack' }, [
      example('a', h(TspRadio, { text: t('optionA'), checked: (state.selectedOption || 0) === 0, onChange: () => state.setSelectedOption(0), ...common })),
      example('b', h(TspRadio, { text: t('optionB'), checked: state.selectedOption === 1, onChange: () => state.setSelectedOption(1), ...common }))
    ]);
    case 'Collapse': return h(TspCollapse, { title: t('details'), message: t('cardBody'), expanded: Boolean(state.checked), onChange: state.setChecked, ...common });
    case 'Divider': return h(TspDivider, { text: t('sectionLabel'), ...common });
    case 'SearchBar': return h(TspSearchBar, { value: state.inputValue || '', placeholder: t('search'), onChange: state.setInputValue, ...common });
    case 'SegmentedControl': return h(TspSegmentedControl, { options: [t('day'), t('week'), t('month')], selectedIndex: state.selectedTab || 0, onSelect: state.setSelectedTab, ...common });
    case 'StarRating': return h(TspStarRating, { value: state.selectedOption || 3, max: 5, onChange: state.setSelectedOption, ...common });
    case 'Progress': return h('div', { className: 'bc-example-stack' }, ['primary', 'success', 'warning', 'danger'].map((variant, index) => example(variant, h(TspProgress, { progress: [38, 68, 52, 82][index], variant, ...common }))));
    case 'TopBar': return h(TspTopBar, { title: t('title'), showBack: true, ...common });
    case 'BottomTab': return h('div', { className: 'bc-doc-sticky-demo' }, h(TspBottomTab, { tabs: state.tabs, selectedKey: state.tab, onSelect: state.setTab, ...common }));
    case 'Tabs': return h(TspTabs, { tabs: [t('tabAll'), t('tabLearned'), t('tabTodo')], selectedIndex: state.selectedTab, onSelect: state.setSelectedTab, ...common });
    case 'Amount': return h('div', { className: 'bc-example-stack' }, [example(t('monthly'), h(TspAmount, { symbol: '$', value: '128.80', cycle: 'month', ...common })), example(t('strike'), h(TspAmount, { symbol: '$', value: '199.00', strikeThrough: true, ...common }))]);
    case 'IconButton': return h('div', { className: 'bc-row' }, [h(TspIconButton, { key: 'selected', icon: '♪', selected: true, ...common }), h(TspIconButton, { key: 'primary', icon: '✓', variant: 'primary', ...common }), h(TspIconButton, { key: 'disabled', icon: '×', disabled: true, ...common })]);
    case 'KeyValueLabel': return h(TspKeyValueLabel, { label: t('progressLabel'), value: '12/48', ...common });
    case 'Notification': return h('div', { className: 'bc-example-stack' }, [example('info', h(TspNotification, { title: t('notice'), message: t('noticeBody'), ...common })), example('alert', h(TspNotification, { title: t('reminder'), message: t('taskIncomplete'), variant: 'alert', ...common }))]);
    case 'TextLink': return h('div', { className: 'bc-row' }, [h(TspTextLink, { key: 'default', text: t('textLink'), ...common }), h(TspTextLink, { key: 'inverse', text: t('inverseLabel'), inverse: true, ...common })]);
    case 'Stepper': return h('div', { className: 'bc-example-stack' }, [example(t('steps3'), h(TspStepper, { stepCount: 3, currentStep: 2, ...common })), example(t('steps5'), h(TspStepper, { stepCount: 5, currentStep: 3, ...common }))]);
    case 'StickyFooter': return h('div', { className: 'bc-doc-sticky-demo' }, h(TspStickyFooter, { theme }, h(TspButton, { text: t('stickyFooter'), variant: 'primary', fullWidth: true, ...common })));
    case 'PinInput': return h('div', { className: 'bc-example-stack' }, [example(t('secure'), h(TspPinInput, { value: state.pinValue, cellCount: 4, secure: true, onChange: state.setPinValue, ...common })), example(t('cells6'), h(TspPinInput, { value: '123', cellCount: 6, ...common }))]);
    case 'ListItem': return h('div', { className: 'bc-example-stack' }, [example('selected', h(TspListItem, { title: t('listItem'), message: t('selectedState'), trailing: '›', selected: true, ...common })), example('disabled', h(TspListItem, { title: t('cannotClick'), message: t('disabledState'), disabled: true, ...common }))]);
    case 'Empty': return h(TspEmpty, { title: t('empty'), message: t('emptyBody'), actionText: t('action'), ...common });
    case 'Toast': return h(TspButton, { text: t('showToast'), variant: 'primary', onTap: () => state.showToast(t('saved'), 'success'), ...common });
    case 'Modal': return h(TspButton, { text: t('openModal'), onTap: () => state.setShowModal(true), ...common });
    case 'LoadingDialog': return h('div', { className: 'bc-example-stack' }, [
      example('default', h(TspButton, {
        text: t('showLoading'),
        variant: 'primary',
        onTap: () => {
          state.setShowLoading(true);
          setTimeout(() => state.setShowLoading(false), 1400);
        },
        ...common
      })),
      example('compact', h(TspButton, {
        text: t('compactLoading'),
        onTap: () => {
          state.setShowLoading('compact');
          setTimeout(() => state.setShowLoading(false), 1400);
        },
        ...common
      }))
    ]);
    case 'RefreshLayout': return h('div', { style: { height: 280 } }, h(TspRefreshLayout, {
      theme,
      refreshing: Boolean(state.refreshing),
      loadingMore: Boolean(state.loadingMore),
      onRefresh: () => {
        state.setRefreshing(true);
        setTimeout(() => {
          state.setRefreshItems([1, 2, 3, 4, 5, 6]);
          state.setRefreshing(false);
          state.showToast?.(t('refreshed'), 'success');
        }, 900);
      },
      onLoadMore: () => {
        if (state.loadingMore || state.refreshing) return;
        state.setLoadingMore(true);
        setTimeout(() => {
          const items = state.refreshItems || [1, 2, 3, 4, 5, 6];
          state.setRefreshItems([...items, items.length + 1, items.length + 2]);
          state.setLoadingMore(false);
        }, 900);
      }
    }, (state.refreshItems || [1, 2, 3, 4, 5, 6]).map((item) => h(TspListItem, {
      key: item,
      title: t('listItemN', { n: item }),
      message: t('pullRefreshHint'),
      trailing: '›',
      theme
    }))));
    case 'DatePicker': return h(TspDatePicker, { value: state.dateValue || '2026-08-26', onChange: state.setDateValue, placeholder: t('pickDate'), ...common });
    case 'ChildSwitcher': return h('div', { className: 'bc-example-stack' }, [
      example('chip', h(TspChildSwitcher, { items: [{ id: 1, label: t('childOne'), emoji: '👧' }, { id: 2, label: t('childTwo'), emoji: '👦' }], selectedId: state.childId || 1, onChange: state.setChildId, ...common })),
      example('tabs', h(TspChildSwitcher, { variant: 'tabs', items: [{ id: 1, label: t('childOne') }, { id: 2, label: t('childTwo') }], selectedId: state.childId || 1, onChange: state.setChildId, ...common }))
    ]);
    case 'ScoreRuleGrid': return h(TspScoreRuleGrid, {
      rules: [
        { id: 1, name: t('homeworkDone'), icon: '📝', value: 5, count: 1, dailyLimit: 1 },
        { id: 2, name: t('messyHomework'), icon: '✏️', value: -2, count: 0, dailyLimit: 2 }
      ],
      columns: 'auto',
      onIncrement: () => state.showToast?.('+1', 'success'),
      ...common
    });
    case 'RedeemCardGrid': return h(TspRedeemCardGrid, {
      items: [{ id: 1, name: t('snack'), icon: '🍬', cost: 15 }, { id: 2, name: t('game'), icon: '🎮', cost: 30 }],
      availablePoints: 20,
      onRedeem: () => state.showToast?.(t('redeem'), 'success'),
      ...common
    });
    case 'CalendarHeatmap': return h(TspCalendarHeatmap, {
      yearMonth: '2026-08',
      cells: [{ date: '2026-08-01', level: 'full' }, { date: '2026-08-02', level: 'partial' }, { date: '2026-08-03', level: 'exempt' }],
      ...common
    });
    case 'PrintSheet': return h(TspPrintSheet, {
      title: t('dictationTitle'),
      variant: 'pinyin',
      items: [{ prompt: 'dǐng' }, { prompt: 'lù' }, { prompt: 'yàn' }, { prompt: 'xīn' }, { prompt: 'wǎn' }],
      columns: 5,
      ...common
    });
    case 'BalanceHero': return h(TspBalanceHero, {
      total: 41,
      breakdown: { balance: 40, ruleScore: 11, streakBonus: 5, redeemTotal: 15 },
      ...common
    });
    case 'CheckInStreakCard': return h(TspCheckInStreakCard, {
      streakDays: 7,
      totalDays: 45,
      weekProgress: 0.85,
      onOpen: () => state.showToast?.(t('openCheckIn'), 'info'),
      ...common
    });
    case 'Avatar': return h('div', { className: 'bc-row' }, [
      h(TspAvatar, { key: 'default', text: '技趣', ...common }),
      h(TspAvatar, { key: 'primary', text: 'SP', variant: 'primary', size: 'lg', ...common }),
      h(TspAvatar, { key: 'subtle', text: 'A', variant: 'subtle', size: 'sm', ...common })
    ]);
    case 'Skeleton': return h(TspSkeleton, { rows: 3, animated: true, avatar: true, ...common });
    case 'Tooltip': return h(TspTooltip, { text: t('tooltipText'), placement: 'top', ...common },
      h(TspButton, { text: t('hoverMe'), variant: 'default', ...common })
    );
    case 'Slider': return h(TspSlider, {
      value: state.sliderValue ?? 40,
      min: 0,
      max: 100,
      step: 1,
      onChange: state.setSliderValue,
      ...common
    });
    case 'TextArea': return h(TspTextArea, {
      value: state.textAreaValue || '',
      placeholder: t('writeNote'),
      rows: 3,
      onChange: state.setTextAreaValue,
      ...common
    });
    case 'Drawer': return h(TspButton, {
      text: t('openDrawer'),
      variant: 'primary',
      onTap: () => state.setShowDrawer(true),
      ...common
    });
    case 'InputNumber': return h(TspInputNumber, {
      value: state.inputNumberValue ?? 3,
      min: 0,
      max: 10,
      step: 1,
      onChange: state.setInputNumberValue,
      ...common
    });
    case 'Swiper': return h(TspSwiper, {
      items: [t('slideA'), t('slideB'), t('slideC')],
      index: state.swiperIndex || 0,
      onChange: state.setSwiperIndex,
      ...common
    });
    case 'Tag': return h('div', { className: 'bc-row' }, [
      h(TspTag, { key: 'default', text: 'default', ...common }),
      h(TspTag, { key: 'primary', text: 'primary', variant: 'primary', ...common }),
      h(TspTag, { key: 'closable', text: t('closableLabel'), closable: true, onClose: () => state.showToast?.(t('closedToast'), 'info'), ...common })
    ]);
    case 'Fab': return h('div', { className: 'bc-row' }, [
      h(TspFab, { key: 'icon', icon: '+', ...common }),
      h(TspFab, { key: 'ext', icon: '+', text: t('createNew'), ...common }),
      h(TspFab, { key: 'default', icon: '✎', text: t('defaultFab'), variant: 'default', ...common })
    ]);
    case 'TimePicker': return h(TspTimePicker, {
      value: state.timeValue || '09:30',
      placeholder: 'HH:mm',
      onChange: state.setTimeValue,
      ...common
    });
    case 'Upload': return h(TspUpload, {
      files: state.uploadFiles || [{ id: '1', name: 'readme.md' }],
      onChange: state.setUploadFiles,
      ...common
    });
    case 'Table': return h(TspTable, {
      columns: [{ key: 'name', title: t('nameCol') }, { key: 'status', title: t('statusCol') }],
      rows: [{ name: 'Avatar', status: t('ready') }, { name: 'Tag', status: t('newlyAdded') }],
      variant: 'striped',
      ...common
    });
    case 'Tree': return h(TspTree, {
      items: [
        { id: 'a', label: t('planet'), children: [{ id: 'a1', label: t('sky') }, { id: 'a2', label: t('island') }] },
        { id: 'b', label: t('gameplay'), children: [{ id: 'b1', label: t('challenge') }] }
      ],
      selectedId: state.treeSelectedId || 'a1',
      expandedIds: state.treeExpandedIds || ['a'],
      onSelect: (id) => state.setTreeSelectedId?.(id),
      onExpand: state.setTreeExpandedIds,
      ...common
    });
    case 'Cascader': return h(TspCascader, {
      options: [
        { value: 'asia', label: t('asia'), children: [{ value: 'cn', label: t('china') }, { value: 'jp', label: t('japan') }] },
        { value: 'eu', label: t('europe'), children: [{ value: 'fr', label: t('france') }] }
      ],
      value: state.cascaderValue || [],
      placeholder: t('chooseRegion'),
      onChange: state.setCascaderValue,
      ...common
    });
    default: return null;
  }
}

function ComponentDocPage({ doc, theme, state, onBack, layoutClass, t }) {
  return h(
    'main', {
      className: `bc-sample ${layoutClass}`,
      style: themed(theme),
      'data-platform': layoutClass.includes('desktop') ? 'desktop' : 'mobile'
    },
    h(TspTopBar, { title: doc.component, showBack: true, theme, onBack }),
    h('section', { className: 'bc-sample__grid bc-doc-page' },
      h(TspCard, { theme },
        h('div', { className: 'bc-doc-eyebrow' }, doc.categoryLabel || doc.category),
        h('h1', { className: 'bc-doc-title' }, doc.component),
        h('p', { className: 'bc-doc-desc' }, doc.description)
      ),
      h(TspCard, { theme },
        h('strong', null, t('usageExamples')),
        h('div', { className: 'bc-doc-preview' }, h(TspDocPreview, { name: doc.name, theme, state, t }))
      ),
      h(TspCard, { theme },
        h('strong', null, t('apiSection')),
        h('div', { className: 'bc-doc-section-title' }, t('propsSection')),
        h('div', { className: 'bc-doc-chip-row' }, doc.props.map((prop) => h('span', { key: prop, className: 'bc-doc-chip' }, prop))),
        h('div', { className: 'bc-doc-section-title' }, t('variantsSection')),
        h('div', { className: 'bc-doc-chip-row' }, doc.variants.map((variant) => h('span', { key: variant, className: 'bc-doc-chip' }, variant)))
      ),
      h(TspCard, { theme },
        h('strong', null, t('platformsSync')),
        h('div', { className: 'bc-doc-chip-row' }, doc.platforms.map((platform) => h('span', { key: platform, className: 'bc-doc-chip' }, platform)))
      )
    ),
    state.showSheet && h(TspOptionSheet, { title: t('selectTitle'), options: ['A', 'B', 'C'], selectedIndex: state.selectedOption, theme, visible: true, onCancel: () => state.setShowSheet(false), onSelect: (index) => { state.setSelectedOption(index); state.setShowSheet(false); } }),
    state.toast && h('div', { className: 'bc-toast-layer', key: state.toast.id }, h(TspToast, { message: state.toast.message, variant: state.toast.variant, theme })),
    state.showModal && h(TspModal, { title: t('modalTitle'), message: t('modalBody'), confirmText: t('ok'), cancelText: t('cancel'), theme, onConfirm: () => state.setShowModal(false), onCancel: () => state.setShowModal(false) }),
    h(TspLoadingDialog, {
      visible: Boolean(state.showLoading),
      variant: state.showLoading === 'compact' ? 'compact' : 'default',
      message: t('loadingSync'),
      theme
    }),
    h(TspDrawer, {
      visible: Boolean(state.showDrawer),
      title: t('drawerTitle'),
      placement: 'bottom',
      theme,
      onClose: () => state.setShowDrawer(false)
    }, t('drawerBody'))
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
    { key: 'zh-CN', title: '简体中文' },
    { key: 'zh-TW', title: '繁體中文' },
    { key: 'en', title: 'English' },
    { key: 'ja', title: '日本語' },
    { key: 'ko', title: '한국어' },
    { key: 'es', title: 'Español' },
    { key: 'fr', title: 'Français' },
    { key: 'de', title: 'Deutsch' }
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
  const t = (key, vars) => sampleT(locale, key, vars);
  const { localizedCategories, localizedDocs } = useMemo(
    () => localizeDocs(componentDocs, componentCategories, locale),
    [locale]
  );
  const platformOptions = useMemo(() => [
    { key: 'auto', title: t('auto') }, { key: 'mobile', title: t('mobile') }, { key: 'desktop', title: t('desktop') }
  ], [locale]);
  const [checked, setChecked] = useState(true);
  const [tab, setTab] = useState('learn');
  const [selectedTab, setSelectedTab] = useState(0);
  const [selectedOption, setSelectedOption] = useState(1);
  const [pinValue, setPinValue] = useState('12');
  const [inputValue, setInputValue] = useState('');
  const [dateValue, setDateValue] = useState('2026-08-26');
  const [childId, setChildId] = useState(1);
  const [showSheet, setShowSheet] = useState(false);
  const [selectedDocName, setSelectedDocName] = useState(() => {
    if (forcePlatform || typeof window === 'undefined') return '';
    return window.location.hash.replace(/^#/, '');
  });
  const [showModal, setShowModal] = useState(false);
  const [showLoading, setShowLoading] = useState(false);
  const [refreshing, setRefreshing] = useState(false);
  const [loadingMore, setLoadingMore] = useState(false);
  const [refreshItems, setRefreshItems] = useState([1, 2, 3, 4, 5, 6]);
  const [toast, setToast] = useState(null);
  const [showDrawer, setShowDrawer] = useState(false);
  const [sliderValue, setSliderValue] = useState(40);
  const [textAreaValue, setTextAreaValue] = useState('');
  const [inputNumberValue, setInputNumberValue] = useState(3);
  const [swiperIndex, setSwiperIndex] = useState(0);
  const [timeValue, setTimeValue] = useState('09:30');
  const [uploadFiles, setUploadFiles] = useState([{ id: '1', name: 'readme.md' }]);
  const [treeSelectedId, setTreeSelectedId] = useState('a1');
  const [treeExpandedIds, setTreeExpandedIds] = useState(['a']);
  const [cascaderValue, setCascaderValue] = useState([]);
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
    : localizedDocs.find((doc) => doc.name === routeName);
  const previewState = { checked, setChecked, inputValue, setInputValue, dateValue, setDateValue, childId, setChildId, selectedOption, setSelectedOption, selectedTab, setSelectedTab, pinValue, setPinValue, showSheet, setShowSheet, showModal, setShowModal, showLoading, setShowLoading, refreshing, setRefreshing, loadingMore, setLoadingMore, refreshItems, setRefreshItems, toast, tabs, tab, setTab, showToast, showDrawer, setShowDrawer, sliderValue, setSliderValue, textAreaValue, setTextAreaValue, inputNumberValue, setInputNumberValue, swiperIndex, setSwiperIndex, timeValue, setTimeValue, uploadFiles, setUploadFiles, treeSelectedId, setTreeSelectedId, treeExpandedIds, setTreeExpandedIds, cascaderValue, setCascaderValue };
  const resolvedPlatform = forcePlatform ?? (platformMode === 'auto' ? autoPlatform : platformMode);
  const layoutClass = resolvedPlatform === 'desktop' ? 'bc-sample--force-desktop' : 'bc-sample--force-mobile';
  if (selectedDoc) {
    return h(ComponentDocPage, { doc: selectedDoc, theme, state: previewState, onBack: closeDoc, layoutClass, t });
  }
  const settingsPage = h(React.Fragment, null,
    !forcePlatform && h(TspCard, { theme }, h('strong', null, t('platformSwitch')), h('p', null, t('platformHint')), h(TspSelect, { options: platformOptions, selectedIndex: platformOptions.findIndex((item) => item.key === platformMode), theme, title: t('selectTitle'), onSelect: (_, option) => setPlatformMode(option.key) })),
    h(TspCard, { theme }, h('strong', null, t('themeSwitch')), h('p', null, t('themeHint')), h(TspSelect, { options: themeOptions, selectedIndex: themeOptions.findIndex((item) => item.key === themeKey), theme, title: t('themeSwitch'), onSelect: (_, option) => setThemeKey(option.key) })),
    h(TspCard, { theme }, h('strong', null, t('languageSwitch')), h('p', null, t('languageHint')), h(TspSelect, { options: languageOptions, selectedIndex: languageOptions.findIndex((item) => item.key === locale), theme, title: t('languageSwitch'), onSelect: (_, option) => setLocale(option.key) }))
  );
  const learnPage = componentCategories.map((category, categoryIndex) => h('section', { key: category, className: 'bc-doc-home-section' },
    h('div', { className: 'bc-doc-section-title' }, localizedCategories[categoryIndex] || category),
    h('div', { className: 'bc-doc-list' },
      localizedDocs.filter((doc) => doc.category === category).map((doc) => h(TspListItem, { key: doc.name, title: doc.component, message: doc.description, trailing: '›', theme, onTap: () => openDoc(doc.name) }))
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
