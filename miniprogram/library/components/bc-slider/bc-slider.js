Component({
  properties: {
    value: { type: Number, value: 0 },
    min: { type: Number, value: 0 },
    max: { type: Number, value: 100 },
    step: { type: Number, value: 1 },
    disabled: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  methods: {
    onChanging(e) {
      if (this.data.disabled) return;
      this.triggerEvent('change', { value: Number(e.detail.value) });
    },
    onChange(e) {
      if (this.data.disabled) return;
      this.triggerEvent('change', { value: Number(e.detail.value) });
    },
  },
});
