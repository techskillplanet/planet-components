Component({
  properties: {
    title: { type: String, value: '' },
    message: { type: String, value: '' },
    trailing: { type: String, value: '' },
    selected: { type: Boolean, value: false },
    disabled: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  methods: {
    onTap() {
      if (this.data.disabled) return;
      this.triggerEvent('tap');
    },
  },
});
