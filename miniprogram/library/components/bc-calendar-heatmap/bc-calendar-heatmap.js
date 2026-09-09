const WEEKDAYS = ['一', '二', '三', '四', '五', '六', '日'];
const LEVELS = [
  { level: 'full', label: '全勤' },
  { level: 'partial', label: '部分' },
  { level: 'none', label: '未打' },
  { level: 'exempt', label: '豁免' },
];

function parseYearMonth(yearMonth) {
  const [y, m] = String(yearMonth || '').split('-').map(Number);
  if (!y || !m) {
    const now = new Date();
    return { year: now.getFullYear(), month: now.getMonth() + 1 };
  }
  return { year: y, month: m };
}

Component({
  properties: {
    yearMonth: { type: String, value: '' },
    cells: { type: Array, value: [] },
    showLegend: { type: Boolean, value: true },
    theme: { type: Object, value: {} },
  },
  data: {
    weekdays: WEEKDAYS,
    levels: LEVELS,
    slots: [],
  },
  observers: {
    'yearMonth, cells': function (yearMonth, cells) {
      const { year, month } = parseYearMonth(yearMonth);
      const levelMap = {};
      (Array.isArray(cells) ? cells : []).forEach((c) => {
        if (c && c.date) levelMap[c.date] = c.level || 'none';
      });
      const first = new Date(year, month - 1, 1);
      const daysInMonth = new Date(year, month, 0).getDate();
      const startPad = (first.getDay() + 6) % 7;
      const slots = [];
      for (let i = 0; i < startPad; i += 1) {
        slots.push({ empty: true, key: `empty-${i}` });
      }
      for (let d = 1; d <= daysInMonth; d += 1) {
        const date = `${year}-${String(month).padStart(2, '0')}-${String(d).padStart(2, '0')}`;
        const level = levelMap[date] || 'none';
        slots.push({ empty: false, key: date, day: d, date, level });
      }
      this.setData({ slots });
    },
  },
  methods: {
    onSelectDay(e) {
      const { date, level } = e.currentTarget.dataset;
      if (!date) return;
      this.triggerEvent('selectday', { date, level });
    },
  },
});
