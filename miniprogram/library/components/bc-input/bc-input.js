Component({
  properties: {
    value: { type: String, value: '' },
    placeholder: { type: String, value: '' },
    variant: { type: String, value: 'default' },
    disabled: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  methods: {
    onInput(e) {
      if (this.data.disabled) return;
      this.triggerEvent('change', { value: e.detail.value });
    },
  },
});
