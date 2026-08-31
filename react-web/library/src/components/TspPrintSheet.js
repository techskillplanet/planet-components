import { React, h, themed, starPlanetTheme } from './_shared.js';

/**
 * TspPrintSheet – A4-style dictation / fill-in print layout.
 */
export function TspPrintSheet({
  title = '',
  items = [],
  columns = 5,
  footerFields = ['姓名', '日期', '得分'],
  variant = 'pinyin',
  theme = starPlanetTheme
}) {
  const cols = Math.max(1, Number(columns) || 5);
  return h(
    'div',
    {
      className: `bc-print-sheet bc-print-sheet--${variant}`,
      style: themed(theme, { '--bc-print-cols': String(cols) }),
      'data-variant': variant
    },
    [
      title ? h('h3', { key: 'title', className: 'bc-print-sheet__title' }, title) : null,
      h(
        'div',
        { key: 'grid', className: 'bc-print-sheet__grid' },
        items.map((item, index) => {
          const prompt = typeof item === 'string' ? item : item?.prompt ?? '';
          return h('div', { key: index, className: 'bc-print-sheet__cell' }, [
            h('div', { key: 'prompt', className: 'bc-print-sheet__prompt' }, prompt),
            h('div', { key: 'blank', className: 'bc-print-sheet__blank' })
          ]);
        })
      ),
      footerFields?.length
        ? h(
            'div',
            { key: 'footer', className: 'bc-print-sheet__footer' },
            footerFields.map((field) => h('span', { key: field }, `${field}：________`))
          )
        : null
    ]
  );
}
