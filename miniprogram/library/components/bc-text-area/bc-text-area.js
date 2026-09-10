Component({
  properties: {
    value: { type: String, value: '' },
    placeholder: { type: String, value: '' },
    rows: { type: Number, value: 3 },
    maxLength: { type: Number, value: -1 },
    disabled: { type: Boolean, value: false },
    variant: { type: String, value: 'default' },
    theme: { type: Object, value: {} },
  },
  methods: {
    onInput(e) {
      if (this.data.disabled) return;
      this.triggerEvent('change', { value: e.detail.value });
    },
  },
});
