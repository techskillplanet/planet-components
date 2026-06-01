Component({
  properties: {
    tabs: { type: Array, value: [] },
    selected: { type: Number, value: 0 },
    theme: { type: Object, value: {} },
  },
  methods: {
    onTap(e) {
      const { index, key } = e.currentTarget.dataset;
      this.triggerEvent('change', { index, key });
    },
  },
});
