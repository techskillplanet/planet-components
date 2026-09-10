Component({
  properties: {
    items: { type: Array, value: [] },
    index: { type: Number, value: 0 },
    autoplay: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  data: {
    current: 0,
  },
  observers: {
    index(value) {
      this.setData({ current: Number(value) || 0 });
    },
  },
  methods: {
    labelOf(item) {
      if (item == null) return '';
      if (typeof item === 'string' || typeof item === 'number') return String(item);
      return item.content || item.label || '';
    },
    onChange(e) {
      const next = Number(e.detail.current) || 0;
      this.setData({ current: next });
      this.triggerEvent('change', { index: next });
    },
  },
});
