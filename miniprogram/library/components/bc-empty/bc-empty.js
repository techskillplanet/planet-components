Component({
  properties: {
    title: { type: String, value: '' },
    message: { type: String, value: '' },
    actionText: { type: String, value: '' },
    theme: { type: Object, value: {} },
  },
  methods: {
    onAction() { this.triggerEvent('action'); },
  },
});
