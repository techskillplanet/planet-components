Component({
  properties: {
    stepCount: { type: Number, value: 3 },
    currentStep: { type: Number, value: 1 },
    theme: { type: Object, value: {} },
  },
  observers: {
    'stepCount,currentStep': function () {
      this.build();
    },
  },
  lifetimes: {
    attached() {
      this.build();
    },
  },
  methods: {
    build() {
      const count = Math.max(3, Math.min(5, this.data.stepCount || 3));
      const current = Math.max(1, Math.min(count, this.data.currentStep || 1));
      this.setData({ steps: Array.from({ length: count }, (_, i) => ({ n: i + 1, done: i + 1 < current, active: i + 1 === current })) });
    },
  },
});
