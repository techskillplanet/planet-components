Component({
  properties: {
    columns: { type: Array, value: [] },
    rows: { type: Array, value: [] },
    variant: { type: String, value: 'default' },
    emptyText: { type: String, value: '暂无数据' },
    theme: { type: Object, value: {} },
  },
  observers: {
    'columns, rows': function (columns, rows) {
      this.setData({ viewRows: this.buildRows(columns, rows) });
    },
  },
  data: {
    viewRows: [],
  },
  lifetimes: {
    attached() {
      this.setData({ viewRows: this.buildRows(this.data.columns, this.data.rows) });
    },
  },
  methods: {
    headerTitle(col) {
      if (col == null) return '';
      if (typeof col === 'string') return col;
      return col.title || col.label || col.key || '';
    },
    cellText(row, col, index) {
      if (row == null) return '';
      if (typeof row === 'string' || typeof row === 'number') return String(row);
      if (Array.isArray(row)) return String(row[index] != null ? row[index] : '');
      const key = (col && (col.key || col.id)) != null ? (col.key || col.id) : index;
      return String(row[key] != null ? row[key] : '');
    },
    buildRows(columns, rows) {
      const cols = Array.isArray(columns) ? columns : [];
      const data = Array.isArray(rows) ? rows : [];
      return data.map((row) =>
        cols.map((col, ci) => this.cellText(row, col, ci))
      );
    },
  },
});
