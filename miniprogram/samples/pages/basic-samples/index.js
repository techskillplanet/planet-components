const { getTheme } = require('../../planet-components/theme/theme');

Page({
  data: {
    theme: getTheme('sky'),
    tabs: [
      { key: 'learn', label: '学习', icon: '⌂' },
      { key: 'settings', label: '设置', icon: '⚙' },
    ],
    segmentTabs: ['学习', '练习', '设置'],
    segmentIndex: 0,
    inputValue: '',
    selectOptions: ['天空蓝', '夜航', '薄荷'],
    selectIndex: 0,
    switchChecked: true,
    checkboxChecked: true,
    radioValue: 'A',
    searchValue: '',
    segmentedOptions: ['日', '周', '月'],
    segmentedIndex: 0,
    starValue: 3,
    collapseExpanded: true,
    optionSheetVisible: false,
    toastVisible: false,
    modalVisible: false,
    loadingVisible: false,
    buttonBusy: false,
    refreshing: false,
    dateValue: '2026-08-26',
    childId: 1,
    childItems: [
      { id: 1, label: '悦悦', emoji: '👧' },
      { id: 2, label: '佑佑', emoji: '👦' },
    ],
    childTabItems: [
      { id: 1, label: '悦悦' },
      { id: 2, label: '佑佑' },
    ],
    scoreRules: [
      { id: 1, name: '按时认真完成作业', icon: '📝', value: 5, count: 1, dailyLimit: 1 },
      { id: 2, name: '作业潦草', icon: '✏️', value: -2, count: 0, dailyLimit: 2 },
    ],
    redeemItems: [
      { id: 1, name: '零食', icon: '🍬', cost: 15 },
      { id: 2, name: '游戏', icon: '🎮', cost: 30 },
    ],
    heatmapCells: [
      { date: '2026-08-01', level: 'full' },
      { date: '2026-08-02', level: 'partial' },
      { date: '2026-08-03', level: 'exempt' },
    ],
    printItems: [
      { prompt: 'dǐng' },
      { prompt: 'lù' },
      { prompt: 'yàn' },
      { prompt: 'xīn' },
      { prompt: 'wǎn' },
    ],
    balanceBreakdown: {
      balance: 40,
      ruleScore: 11,
      streakBonus: 5,
      redeemTotal: 15,
    },
    sliderValue: 40,
    textAreaValue: '',
    drawerVisible: false,
    inputNumberValue: 2,
    swiperIndex: 0,
    swiperItems: ['Slide A', 'Slide B', 'Slide C'],
    timeValue: '09:30',
    uploadFiles: [{ id: '1', name: 'readme.md' }],
    tableColumns: [
      { key: 'name', title: '名称' },
      { key: 'status', title: '状态' },
    ],
    tableRows: [
      { name: 'Avatar', status: '就绪' },
      { name: 'Tag', status: '新增' },
    ],
    treeItems: [
      { id: 'a', label: '星球', children: [{ id: 'a1', label: '天空' }, { id: 'a2', label: '岛屿' }] },
      { id: 'b', label: '玩法', children: [{ id: 'b1', label: '闯关' }] },
    ],
    treeSelectedId: 'a1',
    treeExpandedIds: ['a'],
    cascaderOptions: [
      { value: 'asia', label: '亚洲', children: [{ value: 'cn', label: '中国' }, { value: 'jp', label: '日本' }] },
      { value: 'eu', label: '欧洲', children: [{ value: 'fr', label: '法国' }] },
    ],
    cascaderValue: [],
  },
  onTap() {
    this.setData({ toastVisible: true });
  },
  onToastDismiss() {
    this.setData({ toastVisible: false });
  },
  onBack() {
    wx.navigateBack();
  },
  onInputChange(e) {
    this.setData({ inputValue: e.detail.value });
  },
  onSelect(e) {
    this.setData({ selectIndex: e.detail.index });
  },
  onSwitchChange(e) {
    this.setData({ switchChecked: e.detail.checked });
  },
  onCheckboxChange(e) {
    this.setData({ checkboxChecked: e.detail.checked });
  },
  onRadioA() {
    this.setData({ radioValue: 'A' });
  },
  onRadioB() {
    this.setData({ radioValue: 'B' });
  },
  onSearchChange(e) {
    this.setData({ searchValue: e.detail.value });
  },
  onSegmentedSelect(e) {
    this.setData({ segmentedIndex: e.detail.index });
  },
  onStarChange(e) {
    this.setData({ starValue: e.detail.value });
  },
  onCollapseChange(e) {
    this.setData({ collapseExpanded: e.detail.expanded });
  },
  onOpenOptionSheet() {
    this.setData({ optionSheetVisible: true });
  },
  onCloseOptionSheet() {
    this.setData({ optionSheetVisible: false });
  },
  onOptionSheetSelect(e) {
    this.setData({ selectIndex: e.detail.index, optionSheetVisible: false });
  },
  onSegmentSelect(e) {
    this.setData({ segmentIndex: e.detail.index });
  },
  onOpenModal() {
    this.setData({ modalVisible: true });
  },
  onCloseModal() {
    this.setData({ modalVisible: false });
  },
  onShowLoading() {
    this.setData({ loadingVisible: true });
    setTimeout(() => this.setData({ loadingVisible: false }), 1200);
  },
  onBusyTap() {
    if (this.data.buttonBusy) return;
    this.setData({ buttonBusy: true });
    setTimeout(() => this.setData({ buttonBusy: false }), 1200);
  },
  onRefresh() {
    this.setData({ refreshing: true });
    setTimeout(() => this.setData({ refreshing: false }), 800);
  },
  onDateChange(e) {
    this.setData({ dateValue: e.detail.value });
  },
  onChildChange(e) {
    this.setData({ childId: e.detail.id });
  },
  onScoreIncrement() {
    this.setData({ toastVisible: true });
  },
  onRedeem() {
    this.setData({ toastVisible: true });
  },
  onCheckInOpen() {
    this.setData({ toastVisible: true });
  },
  onSliderChange(e) {
    this.setData({ sliderValue: e.detail.value });
  },
  onTextAreaChange(e) {
    this.setData({ textAreaValue: e.detail.value });
  },
  onOpenDrawer() {
    this.setData({ drawerVisible: true });
  },
  onCloseDrawer() {
    this.setData({ drawerVisible: false });
  },
  onInputNumberChange(e) {
    this.setData({ inputNumberValue: e.detail.value });
  },
  onSwiperChange(e) {
    this.setData({ swiperIndex: e.detail.index });
  },
  onTagClose() {
    this.setData({ toastVisible: true });
  },
  onTimeChange(e) {
    this.setData({ timeValue: e.detail.value });
  },
  onUploadChange(e) {
    this.setData({ uploadFiles: e.detail.files || [] });
  },
  onTreeSelect(e) {
    this.setData({ treeSelectedId: e.detail.id });
  },
  onTreeExpand(e) {
    this.setData({ treeExpandedIds: e.detail.expandedIds || [] });
  },
  onCascaderChange(e) {
    this.setData({ cascaderValue: e.detail.value || [] });
  },
});
