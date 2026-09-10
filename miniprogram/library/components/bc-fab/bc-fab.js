Component({
  properties: {
    icon: { type: String, value: '+' },
    text: { type: String, value: '' },
    variant: { type: String, value: 'primary' },
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
