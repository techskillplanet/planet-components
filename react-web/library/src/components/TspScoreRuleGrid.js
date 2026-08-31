import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/**
 * TspScoreRuleGrid – Score rule cards with optional +1.
 */
export function TspScoreRuleGrid({
  rules = [],
  columns = 'auto',
  variant = 'default',
  disabled = false,
  theme = starPlanetTheme,
  onIncrement
}) {
  const readOnly = variant === 'readOnly' || disabled;
  const colClass =
    columns === 1 || columns === '1'
      ? 'bc-score-rule-grid--cols-1'
      : columns === 2 || columns === '2'
        ? 'bc-score-rule-grid--cols-2'
        : 'bc-score-rule-grid--cols-auto';

  return h(
    'div',
    { className: cx('bc-score-rule-grid', colClass), style: themed(theme) },
    rules.map((rule) => {
      const value = Number(rule.value) || 0;
      const count = Number(rule.count) || 0;
      const limit = rule.dailyLimit == null ? null : Number(rule.dailyLimit);
      const atLimit = limit != null && count >= limit;
      return h(
        'div',
        { key: String(rule.id ?? rule.name), className: 'bc-score-rule-grid__card' },
        h('div', { className: 'bc-score-rule-grid__head' }, [
          rule.icon ? h('span', { key: 'icon' }, rule.icon) : null,
          h('span', { key: 'name', className: 'bc-score-rule-grid__name' }, rule.name || '')
        ]),
        h(
          'div',
          {
            className: cx(
              'bc-score-rule-grid__value',
              value >= 0 ? 'bc-score-rule-grid__value--pos' : 'bc-score-rule-grid__value--neg'
            )
          },
          value > 0 ? `+${value}` : String(value)
        ),
        h(
          'div',
          { className: 'bc-score-rule-grid__meta' },
          limit == null ? `已记 ${count}` : `已记 ${count}/${limit}`
        ),
        readOnly
          ? null
          : h(
              'button',
              {
                type: 'button',
                className: 'bc-score-rule-grid__add',
                disabled: atLimit || disabled,
                onClick: () => onIncrement?.(rule)
              },
              '+1'
            )
      );
    })
  );
}
