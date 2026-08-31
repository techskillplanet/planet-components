import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

const BREAKDOWN_LABELS = {
  balance: '余额',
  ruleScore: '规则分',
  streakBonus: '连续奖励',
  redeemTotal: '已兑换'
};

/**
 * TspBalanceHero – Available points hero.
 */
export function TspBalanceHero({
  total = 0,
  breakdown,
  suffix = '分',
  variant = 'default',
  theme = starPlanetTheme
}) {
  const rows = breakdown
    ? Object.entries(breakdown).map(([key, value]) => ({
        key,
        label: BREAKDOWN_LABELS[key] || key,
        value
      }))
    : [];

  return h(
    'div',
    {
      className: cx('bc-balance-hero', variant === 'compact' && 'bc-balance-hero--compact'),
      style: themed(theme)
    },
    [
      h('div', { key: 'label', className: 'bc-balance-hero__label' }, '可用积分'),
      h('div', { key: 'total', className: 'bc-balance-hero__total' }, [
        h('span', { key: 'n' }, String(total)),
        suffix ? h('span', { key: 's', className: 'bc-balance-hero__suffix' }, suffix) : null
      ]),
      rows.length
        ? h(
            'div',
            { key: 'bd', className: 'bc-balance-hero__breakdown' },
            rows.map((row) =>
              h('div', { key: row.key, className: 'bc-balance-hero__row' }, [
                h('span', { key: 'l' }, row.label),
                h('span', { key: 'v' }, String(row.value))
              ])
            )
          )
        : null
    ]
  );
}
