Component({
  properties: {
    title: { type: String, value: '' },
    message: { type: String, value: '' },
    expanded: { type: Boolean, value: false },
    disabled: { type: Boolean, value: false },
    variant: { type: String, value: 'default' },
    theme: { type: Object, value: {} },
  },
  methods: {
    onToggle() {
      if (this.data.disabled) return;
      this.triggerEvent('change', { expanded: !this.data.expanded });
    },
  },
});
