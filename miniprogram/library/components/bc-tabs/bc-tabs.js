Component({
  properties: {
    tabs: { type: Array, value: [] },
    selectedIndex: { type: Number, value: 0 },
    theme: { type: Object, value: {} },
  },
  methods: {
    onSelect(e) {
      this.triggerEvent('select', { index: Number(e.currentTarget.dataset.index) });
    },
  },
});
