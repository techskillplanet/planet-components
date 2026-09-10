Component({
  properties: {
    value: { type: String, value: '' },
    placeholder: { type: String, value: '' },
    disabled: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  methods: {
    onChange(e) {
      if (this.data.disabled) return;
      this.triggerEvent('change', { value: e.detail.value });
    },
  },
});
