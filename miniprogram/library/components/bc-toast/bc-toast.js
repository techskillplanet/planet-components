Component({
  properties: {
    message: { type: String, value: '' },
    variant: { type: String, value: 'info' },
    visible: { type: Boolean, value: false },
    duration: { type: Number, value: 1600 },
    theme: { type: Object, value: {} },
  },
  observers: {
    visible(v) {
      if (!v) return;
      const ms = this.data.duration || 1600;
      clearTimeout(this._hide);
      this._hide = setTimeout(() => this.triggerEvent('dismiss'), ms);
    },
  },
  detached() { clearTimeout(this._hide); },
  methods: {},
});
