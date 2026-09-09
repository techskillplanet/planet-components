import { defineComponent, h } from 'vue';
import { cx, themed, starPlanetTheme } from './_shared.js';

const WEEKDAYS = ['一', '二', '三', '四', '五', '六', '日'];
const LEVELS = [
  { level: 'full', label: '全勤' },
  { level: 'partial', label: '部分' },
  { level: 'none', label: '未打' },
  { level: 'exempt', label: '豁免' }
];

function parseYearMonth(yearMonth) {
  const [y, m] = String(yearMonth || '').split('-').map(Number);
  if (!y || !m) {
    const now = new Date();
    return { year: now.getFullYear(), month: now.getMonth() + 1 };
  }
  return { year: y, month: m };
}

export const TspCalendarHeatmap = defineComponent({
  name: 'TspCalendarHeatmap',
  props: {
    yearMonth: String,
    cells: { type: Array, default: () => [] },
    showLegend: { type: Boolean, default: true },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['selectDay'],
  setup(props, { emit }) {
    return () => {
      const { year, month } = parseYearMonth(props.yearMonth);
      const levelMap = new Map((props.cells || []).map((c) => [c.date, c.level || 'none']));
      const first = new Date(year, month - 1, 1);
      const daysInMonth = new Date(year, month, 0).getDate();
      const startPad = (first.getDay() + 6) % 7;
      const slots = [];
      for (let i = 0; i < startPad; i += 1) slots.push(null);
      for (let d = 1; d <= daysInMonth; d += 1) {
        const date = `${year}-${String(month).padStart(2, '0')}-${String(d).padStart(2, '0')}`;
        slots.push({ day: d, date, level: levelMap.get(date) || 'none' });
      }

      return h('div', { class: 'bc-calendar-heatmap', style: themed(props.theme) }, [
        props.showLegend
          ? h(
              'div',
              { class: 'bc-calendar-heatmap__legend' },
              LEVELS.map((item) =>
                h(
                  'span',
                  {
                    key: item.level,
                    class: cx('bc-calendar-heatmap__legend-item', `bc-calendar-heatmap__cell--${item.level}`)
                  },
                  item.label
                )
              )
            )
          : null,
        h(
          'div',
          { class: 'bc-calendar-heatmap__weekdays' },
          WEEKDAYS.map((w) => h('span', { key: w }, w))
        ),
        h(
          'div',
          { class: 'bc-calendar-heatmap__grid' },
          slots.map((slot, index) => {
            if (!slot) {
              return h('span', {
                key: `empty-${index}`,
                class: 'bc-calendar-heatmap__cell bc-calendar-heatmap__cell--empty'
              });
            }
            return h(
              'button',
              {
                key: slot.date,
                type: 'button',
                class: cx('bc-calendar-heatmap__cell', `bc-calendar-heatmap__cell--${slot.level}`),
                onClick: () => emit('selectDay', slot.date, slot.level)
              },
              slot.day
            );
          })
        )
      ]);
    };
  }
});
