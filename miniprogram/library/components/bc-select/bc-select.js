Component({
  properties: {
    options: { type: Array, value: [] },
    selectedIndex: { type: Number, value: 0 },
    disabled: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  data: { open: false },
  methods: {
    onToggle() {
      if (this.data.disabled) return;
      this.setData({ open: !this.data.open });
    },
    onPick(e) {
      const index = Number(e.detail.index);
      this.setData({ open: false });
      this.triggerEvent('select', { index });
    },
    onCancel() {
      this.setData({ open: false });
      this.triggerEvent('cancel');
    },
  },
});
