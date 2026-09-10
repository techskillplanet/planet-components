Component({
  properties: {
    text: { type: String, value: '' },
    placement: { type: String, value: 'top' },
    visible: { type: null, value: null },
    theme: { type: Object, value: {} },
  },
  data: {
    open: false,
  },
  methods: {
    onTap() {
      if (this.data.visible != null) return;
      this.setData({ open: !this.data.open });
    },
  },
});
