Component({
  properties: {
    value: { type: String, value: '' },
    placeholder: { type: String, value: '搜索…' },
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
