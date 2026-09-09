Component({
  properties: {
    rules: { type: Array, value: [] },
    columns: { type: null, value: 'auto' },
    variant: { type: String, value: 'default' },
    disabled: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  data: {
    displayRules: [],
    colClass: 'bc-score-rule-grid--cols-auto',
    readOnly: false,
  },
  observers: {
    'rules, columns, variant, disabled': function (rules, columns, variant, disabled) {
      const list = Array.isArray(rules) ? rules : [];
      const readOnly = variant === 'readOnly' || disabled;
      let colClass = 'bc-score-rule-grid--cols-auto';
      if (columns === 1 || columns === '1') colClass = 'bc-score-rule-grid--cols-1';
      else if (columns === 2 || columns === '2') colClass = 'bc-score-rule-grid--cols-2';

      this.setData({
        readOnly,
        colClass,
        displayRules: list.map((rule) => {
          const value = Number(rule.value) || 0;
          const count = Number(rule.count) || 0;
          const limit = rule.dailyLimit == null ? null : Number(rule.dailyLimit);
          const atLimit = limit != null && count >= limit;
          return {
            id: rule.id != null ? rule.id : rule.name,
            icon: rule.icon || '',
            name: rule.name || '',
            valueText: value > 0 ? `+${value}` : String(value),
            valuePos: value >= 0,
            meta: limit == null ? `已记 ${count}` : `已记 ${count}/${limit}`,
            atLimit,
            raw: rule,
          };
        }),
      });
    },
  },
  methods: {
    onIncrement(e) {
      if (this.data.readOnly || this.data.disabled) return;
      const index = Number(e.currentTarget.dataset.index);
      const rule = (this.data.rules || [])[index];
      if (!rule) return;
      const count = Number(rule.count) || 0;
      const limit = rule.dailyLimit == null ? null : Number(rule.dailyLimit);
      if (limit != null && count >= limit) return;
      this.triggerEvent('increment', { rule });
    },
  },
});
