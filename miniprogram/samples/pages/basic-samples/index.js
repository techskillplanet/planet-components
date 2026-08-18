const { getTheme } = require('../../planet-components/theme/theme');

Page({
  data: {
    theme: getTheme('sky'),
    tabs: [
      { key: 'learn', label: '学习', icon: '⌂' },
      { key: 'settings', label: '设置', icon: '⚙' },
    },
    segmentTabs: ['学习', '练习', '设置'],
    segmentIndex: 0,
    inputValue: '',
    selectOptions: ['天空蓝', '夜航', '薄荷'],
    selectIndex: 0,
    switchChecked: true,
    toastVisible: false,
    modalVisible: false,
    loadingVisible: false,
    refreshing: false,
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
  onRefresh() {
    this.setData({ refreshing: true });
    setTimeout(() => this.setData({ refreshing: false }), 800);
  },
});
