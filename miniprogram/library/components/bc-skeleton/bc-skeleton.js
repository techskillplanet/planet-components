Component({
  properties: {
    rows: { type: Number, value: 3 },
    animated: { type: Boolean, value: true },
    avatar: { type: Boolean, value: false },
    variant: { type: String, value: 'default' },
    theme: { type: Object, value: {} },
  },
  data: {
    lineIndexes: [0, 1, 2],
  },
  observers: {
    rows(value) {
      const count = Math.max(1, Math.min(12, Number(value) || 3));
      const lineIndexes = [];
      for (let i = 0; i < count; i += 1) lineIndexes.push(i);
      this.setData({ lineIndexes });
    },
  },
});
