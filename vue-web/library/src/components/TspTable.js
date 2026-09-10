import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

function cellText(row, col, index) {
  if (row == null) return '';
  if (typeof row === 'string' || typeof row === 'number') return String(row);
  if (Array.isArray(row)) return String(row[index] ?? '');
  const key = col?.key ?? col?.id ?? index;
  return String(row[key] ?? '');
}

/** Lightweight data table. */
export const TspTable = defineComponent({
  name: 'TspTable',
  props: {
    columns: { type: Array, default: () => [] },
    rows: { type: Array, default: () => [] },
    variant: { type: String, default: 'default' },
    emptyText: { type: String, default: '暂无数据' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props) {
    return () => {
      const cols = Array.isArray(props.columns) ? props.columns : [];
      const data = Array.isArray(props.rows) ? props.rows : [];
      const headers = cols.map((c) => (typeof c === 'string' ? c : c.title ?? c.label ?? c.key ?? ''));

      return h(
        'div',
        {
          class: cx('bc-table-wrap', `bc-table--${props.variant}`),
          style: themed(props.theme)
        },
        h('table', { class: 'bc-table' }, [
          h(
            'thead',
            null,
            h(
              'tr',
              null,
              headers.map((title, i) => h('th', { key: i }, title))
            )
          ),
          h(
            'tbody',
            null,
            data.length
              ? data.map((row, ri) =>
                  h(
                    'tr',
                    { key: ri },
                    cols.map((col, ci) => h('td', { key: ci }, cellText(row, col, ci)))
                  )
                )
              : h(
                  'tr',
                  null,
                  h(
                    'td',
                    { class: 'bc-table__empty', colSpan: Math.max(headers.length, 1) },
                    props.emptyText
                  )
                )
          )
        ])
      );
    };
  }
});
