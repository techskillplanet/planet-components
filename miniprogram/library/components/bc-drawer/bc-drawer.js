Component({
  properties: {
    visible: { type: Boolean, value: false },
    title: { type: String, value: '' },
    placement: { type: String, value: 'bottom' },
    theme: { type: Object, value: {} },
  },
  methods: {
    onClose() {
      this.triggerEvent('close');
    },
    noop() {},
  },
});
