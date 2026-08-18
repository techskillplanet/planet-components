Component({
  properties: {
    title: { type: String, value: '' },
    message: { type: String, value: '' },
    confirmText: { type: String, value: 'OK' },
    cancelText: { type: String, value: 'Cancel' },
    visible: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  methods: {
    onConfirm() { this.triggerEvent('confirm'); },
    onCancel() { this.triggerEvent('cancel'); },
    noop() {},
  },
});
