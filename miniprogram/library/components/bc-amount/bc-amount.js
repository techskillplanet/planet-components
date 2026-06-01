Component({
  properties: {
    symbol: { type: String, value: '¥' },
    value: { type: String, value: '0' },
    cycle: { type: String, value: '' },
    symbolAfter: { type: Boolean, value: false },
    strikeThrough: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
});
