Component({
  properties: {
    icon: { type: String, value: '•' },
    selected: { type: Boolean, value: false },
    disabled: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  methods: {
    onTap() {
      if (!this.data.disabled) this.triggerEvent('tap');
    },
  },
});
