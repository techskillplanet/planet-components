import { defineComponent, h } from 'vue';
import { cx, themed, starPlanetTheme } from './_shared.js';

export const TspScoreRuleGrid = defineComponent({
  name: 'TspScoreRuleGrid',
  props: {
    rules: { type: Array, default: () => [] },
    columns: { type: [String, Number], default: 'auto' },
    variant: { type: String, default: 'default' },
    disabled: Boolean,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['increment'],
  setup(props, { emit }) {
    return () => {
      const readOnly = props.variant === 'readOnly' || props.disabled;
      const colClass =
        props.columns === 1 || props.columns === '1'
          ? 'bc-score-rule-grid--cols-1'
          : props.columns === 2 || props.columns === '2'
            ? 'bc-score-rule-grid--cols-2'
            : 'bc-score-rule-grid--cols-auto';

      return h(
        'div',
        { class: cx('bc-score-rule-grid', colClass), style: themed(props.theme) },
        (props.rules || []).map((rule) => {
          const value = Number(rule.value) || 0;
          const count = Number(rule.count) || 0;
          const limit = rule.dailyLimit == null ? null : Number(rule.dailyLimit);
          const atLimit = limit != null && count >= limit;
          return h('div', { key: String(rule.id ?? rule.name), class: 'bc-score-rule-grid__card' }, [
            h('div', { class: 'bc-score-rule-grid__head' }, [
              rule.icon ? h('span', null, rule.icon) : null,
              h('span', { class: 'bc-score-rule-grid__name' }, rule.name || '')
            ]),
            h(
              'div',
              {
                class: cx(
                  'bc-score-rule-grid__value',
                  value >= 0 ? 'bc-score-rule-grid__value--pos' : 'bc-score-rule-grid__value--neg'
                )
              },
              value > 0 ? `+${value}` : String(value)
            ),
            h(
              'div',
              { class: 'bc-score-rule-grid__meta' },
              limit == null ? `已记 ${count}` : `已记 ${count}/${limit}`
            ),
            readOnly
              ? null
              : h(
                  'button',
                  {
                    type: 'button',
                    class: 'bc-score-rule-grid__add',
                    disabled: atLimit || props.disabled,
                    onClick: () => emit('increment', rule)
                  },
                  '+1'
                )
          ]);
        })
      );
    };
  }
});
