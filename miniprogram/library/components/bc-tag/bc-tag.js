Component({
  properties: {
    text: { type: String, value: '' },
    closable: { type: Boolean, value: false },
    selected: { type: Boolean, value: false },
    disabled: { type: Boolean, value: false },
    variant: { type: String, value: 'default' },
    theme: { type: Object, value: {} },
  },
  methods: {
    onTap() {
      if (this.data.disabled) return;
      this.triggerEvent('tap');
    },
    onClose(e) {
      if (this.data.disabled) return;
      this.triggerEvent('close');
    },
  },
});
