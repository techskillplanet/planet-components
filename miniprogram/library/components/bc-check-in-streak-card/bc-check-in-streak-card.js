Component({
  properties: {
    streakDays: { type: Number, value: 0 },
    totalDays: { type: Number, value: 0 },
    weekProgress: { type: Number, value: 0 },
    disabled: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  data: {
    pct: 0,
  },
  observers: {
    weekProgress(weekProgress) {
      const pct = Math.max(0, Math.min(100, Math.round(Number(weekProgress) * 100)));
      this.setData({ pct });
    },
  },
  methods: {
    onOpen() {
      if (this.data.disabled) return;
      this.triggerEvent('open');
    },
  },
});
