Component({
  properties: {
    value: { type: Number, value: 0 },
    theme: { type: Object, value: {} },
  },
  observers: {
    value(v) {
      this.setData({ percent: Math.max(0, Math.min(100, Math.round(v * 100))) });
    },
  },
  data: { percent: 0 },
});
