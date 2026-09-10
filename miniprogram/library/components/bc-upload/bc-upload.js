Component({
  properties: {
    files: { type: Array, value: [] },
    multiple: { type: Boolean, value: true },
    disabled: { type: Boolean, value: false },
    accept: { type: String, value: '' },
    theme: { type: Object, value: {} },
  },
  methods: {
    normalize(list) {
      if (!Array.isArray(list)) return [];
      return list.map((f, i) =>
        typeof f === 'string'
          ? { id: String(i), name: f }
          : { id: String(f.id != null ? f.id : i), name: f.name || 'file' }
      );
    },
    onPick() {
      if (this.data.disabled) return;
      const multiple = this.data.multiple;
      wx.chooseMessageFile({
        count: multiple ? 9 : 1,
        type: 'file',
        success: (res) => {
          const picked = (res.tempFiles || []).map((f, i) => ({
            id: `${Date.now()}-${i}`,
            name: f.name || 'file',
          }));
          const current = this.normalize(this.data.files);
          const next = multiple ? current.concat(picked) : picked.slice(0, 1);
          this.triggerEvent('change', { files: next });
        },
        fail: () => {
          // Fallback when chooseMessageFile is unavailable (devtools / older base lib).
          const current = this.normalize(this.data.files);
          const demo = { id: String(Date.now()), name: `file-${current.length + 1}.txt` };
          const next = multiple ? current.concat([demo]) : [demo];
          this.triggerEvent('change', { files: next });
        },
      });
    },
    onRemove(e) {
      if (this.data.disabled) return;
      const id = e.currentTarget.dataset.id;
      const current = this.normalize(this.data.files);
      const file = current.find((x) => x.id === id);
      const next = current.filter((x) => x.id !== id);
      if (file) this.triggerEvent('remove', { file });
      this.triggerEvent('change', { files: next });
    },
  },
});
