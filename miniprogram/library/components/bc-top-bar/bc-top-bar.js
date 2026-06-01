Component({
  properties: {
    title: { type: String, value: '' },
    showBack: { type: Boolean, value: false },
    bgColor: { type: String, value: '#DDF4FF' },
    theme: { type: Object, value: {} },
  },
  data: {
    statusBarHeight: 20,
    navBarHeight: 44,
  },
  lifetimes: {
    attached() {
      const info = wx.getSystemInfoSync();
      let navBarHeight = 44;
      if (wx.getMenuButtonBoundingClientRect) {
        const rect = wx.getMenuButtonBoundingClientRect();
        if (rect && rect.height && rect.top) {
          navBarHeight = rect.height + Math.max(0, rect.top - (info.statusBarHeight || 0)) * 2;
        }
      }
      this.setData({
        statusBarHeight: info.statusBarHeight || 20,
        navBarHeight,
      });
    },
  },
  methods: {
    onBack() {
      this.triggerEvent('back');
    },
  },
});
