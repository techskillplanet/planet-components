import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/**
 * TspRedeemCardGrid – Redeem item cards.
 */
export function TspRedeemCardGrid({
  items = [],
  availablePoints = 0,
  frozen = false,
  disabled = false,
  theme = starPlanetTheme,
  onRedeem
}) {
  return h(
    'div',
    { className: 'bc-redeem-card-grid', style: themed(theme) },
    [
      frozen
        ? h('div', { key: 'banner', className: 'bc-redeem-card-grid__banner' }, '今日已冻结，暂不可兑换')
        : null,
      ...items.map((item) => {
        const cost = Number(item.cost) || 0;
        const insufficient = availablePoints < cost;
        const blocked = frozen || disabled || insufficient;
        return h(
          'button',
          {
            key: String(item.id ?? item.name),
            type: 'button',
            className: cx('bc-redeem-card-grid__card', blocked && 'bc-redeem-card-grid__card--blocked'),
            disabled: blocked,
            onClick: blocked ? undefined : () => onRedeem?.(item)
          },
          [
            h('div', { key: 'icon', className: 'bc-redeem-card-grid__icon' }, item.icon || '🎁'),
            h('div', { key: 'name', className: 'bc-redeem-card-grid__name' }, item.name || ''),
            h('div', { key: 'cost', className: 'bc-redeem-card-grid__cost' }, `${cost} 分`),
            insufficient && !frozen
              ? h('div', { key: 'hint', className: 'bc-redeem-card-grid__hint' }, '积分不足')
              : null
          ]
        );
      })
    ]
  );
}
