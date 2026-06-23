const { getTheme } = require('../../planet-components/theme/theme');

Page({
  data: {
    theme: getTheme('sky'),
    tabs: [
      { key: 'learn', label: '学习', icon: '⌂' },
      { key: 'settings', label: '设置', icon: '⚙' },
    ],
  },
  onTap() {
    wx.showToast({ title: 'Basic Controls', icon: 'none' });
  },
  onBack() {
    wx.navigateBack();
  },
});
