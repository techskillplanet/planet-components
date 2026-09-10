import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

function cellText(row, col, index) {
  if (row == null) return '';
  if (typeof row === 'string' || typeof row === 'number') return String(row);
  if (Array.isArray(row)) return String(row[index] ?? '');
  const key = col?.key ?? col?.id ?? index;
  return String(row[key] ?? '');
}

/** Lightweight data table. */
export function TspTable({
  columns = [],
  rows = [],
  variant = 'default',
  emptyText = '暂无数据',
  theme = starPlanetTheme,
}) {
  const cols = Array.isArray(columns) ? columns : [];
  const data = Array.isArray(rows) ? rows : [];
  const headers = cols.map((c) => (typeof c === 'string' ? c : c.title ?? c.label ?? c.key ?? ''));

  return h(
    'div',
    {
      className: cx('bc-table-wrap', `bc-table--${variant}`),
      style: themed(theme),
    },
    h(
      'table',
      { className: 'bc-table' },
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
              h('td', { className: 'bc-table__empty', colSpan: Math.max(headers.length, 1) }, emptyText)
            )
      )
    )
  );
}
