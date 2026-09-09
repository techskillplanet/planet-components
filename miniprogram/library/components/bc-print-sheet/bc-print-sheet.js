Component({
  properties: {
    title: { type: String, value: '' },
    items: { type: Array, value: [] },
    columns: { type: Number, value: 5 },
    footerFields: { type: Array, value: ['姓名', '日期', '得分'] },
    variant: { type: String, value: 'pinyin' },
    theme: { type: Object, value: {} },
  },
  data: {
    displayItems: [],
    footerTexts: [],
    cols: 5,
  },
  observers: {
    'items, columns, footerFields': function (items, columns, footerFields) {
      const cols = Math.max(1, Number(columns) || 5);
      const list = Array.isArray(items) ? items : [];
      const fields = Array.isArray(footerFields) ? footerFields : [];
      this.setData({
        cols,
        displayItems: list.map((item, index) => ({
          key: index,
          prompt: typeof item === 'string' ? item : (item && item.prompt) || '',
        })),
        footerTexts: fields.map((field) => `${field}：________`),
      });
    },
  },
});
