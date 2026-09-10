Component({
  properties: {
    value: { type: Number, value: 0 },
    max: { type: Number, value: 5 },
    disabled: { type: Boolean, value: false },
    variant: { type: String, value: 'default' },
    theme: { type: Object, value: {} },
  },
  data: {
    stars: [],
  },
  observers: {
    'value, max, variant, disabled'(value, max, variant, disabled) {
      const count = Math.max(1, Math.min(10, Number(max) || 5));
      const current = Math.max(0, Math.min(count, Number(value) || 0));
      const readonly = variant === 'readonly' || disabled;
      const stars = [];
      for (let i = 1; i <= count; i += 1) {
        stars.push({ index: i, filled: i <= current, readonly });
      }
      this.setData({ stars });
    },
  },
  methods: {
    onSelect(e) {
      if (this.data.disabled || this.data.variant === 'readonly') return;
      const index = Number(e.currentTarget.dataset.index);
      this.triggerEvent('change', { value: index });
    },
  },
});
