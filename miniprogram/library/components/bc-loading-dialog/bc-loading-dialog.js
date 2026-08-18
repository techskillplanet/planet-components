Component({
  properties: {
    visible: { type: Boolean, value: false },
    message: { type: String, value: '加载中...' },
    variant: { type: String, value: 'default' },
    dismissible: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  methods: {
    onDismiss() {
      if (!this.data.dismissible) return;
      this.triggerEvent('dismiss');
    },
    noop() {},
  },
});
