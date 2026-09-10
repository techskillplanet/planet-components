function optionLabel(opt, index) {
  if (opt == null) return String(index);
  if (typeof opt === 'string' || typeof opt === 'number') return String(opt);
  return String(opt.label != null ? opt.label : opt.value != null ? opt.value : index);
}

function optionValue(opt, index) {
  if (opt == null) return String(index);
  if (typeof opt === 'string' || typeof opt === 'number') return String(opt);
  return String(opt.value != null ? opt.value : opt.label != null ? opt.label : index);
}

Component({
  properties: {
    options: { type: Array, value: [] },
    selectedIndex: { type: Number, value: 0 },
    disabled: { type: Boolean, value: false },
    variant: { type: String, value: 'default' },
    theme: { type: Object, value: {} },
  },
  data: {
    items: [],
  },
  observers: {
    options(options) {
      const list = Array.isArray(options) ? options : [];
      this.setData({
        items: list.map((opt, index) => ({
          label: optionLabel(opt, index),
          value: optionValue(opt, index),
          index,
        })),
      });
    },
  },
  methods: {
    onSelect(e) {
      if (this.data.disabled) return;
      const index = Number(e.currentTarget.dataset.index);
      const item = this.data.items[index] || {};
      this.triggerEvent('select', {
        index,
        label: item.label || '',
        value: item.value || '',
      });
    },
  },
});
