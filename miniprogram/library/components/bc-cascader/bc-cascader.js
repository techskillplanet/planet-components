Component({
  properties: {
    options: { type: Array, value: [] },
    value: { type: Array, value: [] },
    placeholder: { type: String, value: '请选择' },
    disabled: { type: Boolean, value: false },
    theme: { type: Object, value: {} },
  },
  observers: {
    'options, value, open, draft': function () {
      this.refreshView();
    },
  },
  data: {
    open: false,
    draft: [],
    display: '',
    columns: [],
  },
  lifetimes: {
    attached() {
      this.setData({ draft: (this.data.value || []).map(String) }, () => this.refreshView());
    },
  },
  methods: {
    optId(opt, i) {
      if (!opt) return String(i);
      if (opt.value != null) return String(opt.value);
      if (opt.id != null) return String(opt.id);
      if (opt.key != null) return String(opt.key);
      return String(i);
    },
    optLabel(opt) {
      if (!opt) return '';
      return opt.label || opt.title || opt.text || String(opt.value || opt.id || '');
    },
    findPathLabels(options, valuePath) {
      const labels = [];
      let level = options || [];
      for (let i = 0; i < valuePath.length; i += 1) {
        const v = String(valuePath[i]);
        const hit = (level || []).find((o, idx) => this.optId(o, idx) === v);
        if (!hit) break;
        labels.push(this.optLabel(hit));
        level = hit.children || [];
      }
      return labels;
    },
    buildColumns(options, walk) {
      const columns = [];
      let level = options || [];
      for (let i = 0; i <= walk.length; i += 1) {
        if (!level || !level.length) break;
        columns.push(
          level.map((opt, oi) => ({
            id: this.optId(opt, oi),
            label: this.optLabel(opt),
            hasChildren: !!(opt.children && opt.children.length),
            active: walk[i] === this.optId(opt, oi),
            raw: opt,
          }))
        );
        const cur = walk[i];
        if (cur == null || cur === '') break;
        const hit = level.find((o, idx) => this.optId(o, idx) === String(cur));
        level = hit && hit.children ? hit.children : [];
      }
      return columns;
    },
    refreshView() {
      const opts = Array.isArray(this.data.options) ? this.data.options : [];
      const path = (this.data.value || []).map(String);
      const walk = this.data.open ? (this.data.draft || []) : path;
      const labels = this.findPathLabels(opts, path);
      this.setData({
        display: labels.length ? labels.join(' / ') : '',
        columns: this.data.open ? this.buildColumns(opts, walk) : [],
      });
    },
    onToggle() {
      if (this.data.disabled) return;
      const nextOpen = !this.data.open;
      this.setData(
        {
          open: nextOpen,
          draft: nextOpen ? (this.data.value || []).map(String) : this.data.draft,
        },
        () => this.refreshView()
      );
    },
    onPick(e) {
      const colIndex = Number(e.currentTarget.dataset.col);
      const optIndex = Number(e.currentTarget.dataset.opt);
      const col = (this.data.columns || [])[colIndex] || [];
      const item = col[optIndex];
      if (!item) return;
      const next = (this.data.draft || []).slice(0, colIndex);
      next.push(item.id);
      if (!item.hasChildren) {
        const labels = this.findPathLabels(this.data.options, next);
        this.setData({ draft: next, open: false }, () => this.refreshView());
        this.triggerEvent('change', { value: next, labels });
      } else {
        this.setData({ draft: next }, () => this.refreshView());
      }
    },
  },
});
