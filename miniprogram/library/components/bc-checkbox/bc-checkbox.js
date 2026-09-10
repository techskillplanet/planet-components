Component({
  properties: {
    text: { type: String, value: '' },
    checked: { type: Boolean, value: false },
    disabled: { type: Boolean, value: false },
    variant: { type: String, value: 'default' },
    theme: { type: Object, value: {} },
  },
  methods: {
    onTap() {
      if (this.data.disabled) return;
      this.triggerEvent('change', { checked: !this.data.checked });
    },
  },
});
