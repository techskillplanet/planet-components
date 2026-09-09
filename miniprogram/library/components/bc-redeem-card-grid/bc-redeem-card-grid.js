Component({
  properties: {
    items: { type: Array, value: [] },
    availablePoints: { type: Number, value: 0 },
    frozen: { type: Boolean, value: false },
    disabled: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  data: {
    displayItems: [],
  },
  observers: {
    'items, availablePoints, frozen, disabled': function (items, availablePoints, frozen, disabled) {
      const list = Array.isArray(items) ? items : [];
      this.setData({
        displayItems: list.map((item) => {
          const cost = Number(item.cost) || 0;
          const insufficient = availablePoints < cost;
          const blocked = frozen || disabled || insufficient;
          return {
            id: item.id != null ? item.id : item.name,
            icon: item.icon || '🎁',
            name: item.name || '',
            costText: `${cost} 分`,
            insufficient,
            blocked,
            showHint: insufficient && !frozen,
          };
        }),
      });
    },
  },
  methods: {
    onRedeem(e) {
      if (this.data.frozen || this.data.disabled) return;
      const index = Number(e.currentTarget.dataset.index);
      const item = (this.data.items || [])[index];
      if (!item) return;
      const cost = Number(item.cost) || 0;
      if (this.data.availablePoints < cost) return;
      this.triggerEvent('redeem', { item });
    },
  },
});
