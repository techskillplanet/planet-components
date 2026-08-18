Component({
  properties: {
    title: { type: String, value: '' },
    options: { type: Array, value: [] },
    selectedIndex: { type: Number, value: 0 },
    visible: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  methods: {
    onPick(e) {
      this.triggerEvent('select', { index: Number(e.currentTarget.dataset.index) });
    },
    onCancel() {
      this.triggerEvent('cancel');
    },
    noop() {},
  },
});
