Component({
  properties: {
    value: { type: String, value: '' },
    cellCount: { type: Number, value: 6 },
    secure: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  observers: {
    'value,cellCount': function () {
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
      const count = Math.max(4, Math.min(8, this.data.cellCount || 6));
      const value = this.data.value || '';
      this.setData({ cells: Array.from({ length: count }, (_, i) => value[i] || '') });
    },
  },
});
