import { defineComponent, h } from 'vue';
import { cx, themed, starPlanetTheme } from './_shared.js';

export const TspRedeemCardGrid = defineComponent({
  name: 'TspRedeemCardGrid',
  props: {
    items: { type: Array, default: () => [] },
    availablePoints: { type: Number, default: 0 },
    frozen: Boolean,
    disabled: Boolean,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['redeem'],
  setup(props, { emit }) {
    return () =>
      h('div', { class: 'bc-redeem-card-grid', style: themed(props.theme) }, [
        props.frozen
          ? h('div', { class: 'bc-redeem-card-grid__banner' }, '今日已冻结，暂不可兑换')
          : null,
        ...(props.items || []).map((item) => {
          const cost = Number(item.cost) || 0;
          const insufficient = props.availablePoints < cost;
          const blocked = props.frozen || props.disabled || insufficient;
          return h(
            'button',
            {
              key: String(item.id ?? item.name),
              type: 'button',
              class: cx('bc-redeem-card-grid__card', blocked && 'bc-redeem-card-grid__card--blocked'),
              disabled: blocked,
              onClick: blocked ? undefined : () => emit('redeem', item)
            },
            [
              h('div', { class: 'bc-redeem-card-grid__icon' }, item.icon || '🎁'),
              h('div', { class: 'bc-redeem-card-grid__name' }, item.name || ''),
              h('div', { class: 'bc-redeem-card-grid__cost' }, `${cost} 分`),
              insufficient && !props.frozen
                ? h('div', { class: 'bc-redeem-card-grid__hint' }, '积分不足')
                : null
            ]
          );
        })
      ]);
  }
});
