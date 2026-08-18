Component({
  properties: {
    refreshing: { type: Boolean, value: false },
    loadingMore: { type: Boolean, value: false },
    disabled: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  methods: {
    onRefresh() {
      if (this.data.disabled) return;
      this.triggerEvent('refresh');
    },
    onLoadMore() {
      if (this.data.disabled || this.data.loadingMore || this.data.refreshing) return;
      this.triggerEvent('loadmore');
    },
  },
});
