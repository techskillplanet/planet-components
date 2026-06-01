Component({
  properties: {
    text: { type: String, value: '' },
    variant: { type: String, value: 'default' },
    disabled: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  data: { pressed: false },
  methods: {
    onTouchStart() {
      if (!this.data.disabled) this.setData({ pressed: true });
    },
    onTouchEnd() {
      this.setData({ pressed: false });
    },
    onTap() {
      if (this.data.disabled) return;
      this.triggerEvent('tap');
    },
  },
});
