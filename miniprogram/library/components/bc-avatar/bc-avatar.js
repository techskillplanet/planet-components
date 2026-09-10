Component({
  properties: {
    text: { type: String, value: '' },
    src: { type: String, value: '' },
    size: { type: String, value: 'md' },
    variant: { type: String, value: 'default' },
    theme: { type: Object, value: {} },
  },
  data: {
    initial: '?',
  },
  observers: {
    text(value) {
      const raw = String(value || '').trim();
      this.setData({
        initial: raw ? raw.slice(0, 2).toUpperCase() : '?',
      });
    },
  },
});
