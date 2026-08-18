Component({
  properties: {
    text: { type: String, value: '' },
    checked: { type: Boolean, value: false },
    checkedText: { type: String, value: 'ON' },
    uncheckedText: { type: String, value: 'OFF' },
    loading: { type: Boolean, value: false },
    disabled: { type: Boolean, value: false },
    variant: { type: String, value: 'md' },
    theme: { type: Object, value: {} },
  },
  methods: {
    onTap() {
      if (this.data.disabled || this.data.loading) return;
      this.triggerEvent('change', { checked: !this.data.checked });
    },
  },
});
