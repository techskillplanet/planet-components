Component({
  properties: {
    items: { type: Array, value: [] },
    selectedId: { type: null, value: null },
    variant: { type: String, value: 'chip' },
    disabled: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  data: {
    displayItems: [],
  },
  observers: {
    'items, selectedId': function (items, selectedId) {
      const list = Array.isArray(items) ? items : [];
      this.setData({
        displayItems: list.map((item) => {
          const id = item && item.id;
          return {
            id,
            label: (item && (item.label || item.name || item.text)) || '',
            iconSrc: (item && item.iconSrc) || '',
            emoji: (item && item.emoji) || '',
            selected: String(id) === String(selectedId),
          };
        }),
      });
    },
  },
  methods: {
    onSelect(e) {
      if (this.data.disabled) return;
      this.triggerEvent('change', { id: e.currentTarget.dataset.id });
    },
  },
});
