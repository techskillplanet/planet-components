Component({
  properties: {
    value: { type: Number, value: 0 },
    min: { type: Number, value: Number.NEGATIVE_INFINITY },
    max: { type: Number, value: Number.POSITIVE_INFINITY },
    step: { type: Number, value: 1 },
    disabled: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  methods: {
    clamp(next) {
      const lo = Number(this.data.min);
      const hi = Number(this.data.max);
      return Math.max(lo, Math.min(hi, next));
    },
    emit(next) {
      this.triggerEvent('change', { value: next });
    },
    onMinus() {
      if (this.data.disabled) return;
      const step = Number(this.data.step) || 1;
      this.emit(this.clamp(Number(this.data.value) - step));
    },
    onPlus() {
      if (this.data.disabled) return;
      const step = Number(this.data.step) || 1;
      this.emit(this.clamp(Number(this.data.value) + step));
    },
  },
});
