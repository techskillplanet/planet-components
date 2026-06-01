Component({
  properties: {
    text: { type: String, value: '' },
    inverse: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  methods: {
    onTap() {
      this.triggerEvent('tap');
    },
  },
});
