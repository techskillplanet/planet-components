const icons = require('../../theme/planet-icons.json');

Component({
  properties: {
    name: { type: String, value: 'check' },
    size: { type: Number, value: 20 },
    color: { type: String, value: '' },
    label: { type: String, value: '' },
  },
  data: {
    glyph: '✓',
  },
  observers: {
    name(name) {
      const spec = icons[name] || icons.check;
      this.setData({ glyph: (spec && spec.glyph) || '✓' });
    },
  },
  lifetimes: {
    attached() {
      const spec = icons[this.data.name] || icons.check;
      this.setData({ glyph: (spec && spec.glyph) || '✓' });
    },
  },
});
