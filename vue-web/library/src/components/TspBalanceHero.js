import { defineComponent, h } from 'vue';
import { cx, themed, starPlanetTheme } from './_shared.js';

const BREAKDOWN_LABELS = {
  balance: '余额',
  ruleScore: '规则分',
  streakBonus: '连续奖励',
  redeemTotal: '已兑换'
};

export const TspBalanceHero = defineComponent({
  name: 'TspBalanceHero',
  props: {
    total: { type: Number, default: 0 },
    breakdown: { type: Object, default: null },
    suffix: { type: String, default: '分' },
    variant: { type: String, default: 'default' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props) {
    return () => {
      const rows = props.breakdown
        ? Object.entries(props.breakdown).map(([key, value]) => ({
            key,
            label: BREAKDOWN_LABELS[key] || key,
            value
          }))
        : [];

      return h(
        'div',
        {
          class: cx('bc-balance-hero', props.variant === 'compact' && 'bc-balance-hero--compact'),
          style: themed(props.theme)
        },
        [
          h('div', { class: 'bc-balance-hero__label' }, '可用积分'),
          h('div', { class: 'bc-balance-hero__total' }, [
            h('span', null, String(props.total)),
            props.suffix ? h('span', { class: 'bc-balance-hero__suffix' }, props.suffix) : null
          ]),
          rows.length
            ? h(
                'div',
                { class: 'bc-balance-hero__breakdown' },
                rows.map((row) =>
                  h('div', { key: row.key, class: 'bc-balance-hero__row' }, [
                    h('span', null, row.label),
                    h('span', null, String(row.value))
                  ])
                )
              )
            : null
        ]
      );
    };
  }
});
