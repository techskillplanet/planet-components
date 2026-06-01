import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspAmount = defineComponent({
  name: 'TspAmount',
  props: {
    symbol: { type: String, default: '¥' },
    value: [String, Number],
    cycle: String,
    symbolAfter: Boolean,
    strikeThrough: Boolean,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props) {
    return () => h('span', { class: cx('bc-amount', props.strikeThrough && 'bc-strike'), style: themed(props.theme) }, [
      !props.symbolAfter && h('span', { class: 'bc-amount__symbol' }, props.symbol),
      h('span', { class: 'bc-amount__value' }, props.value),
      props.symbolAfter && h('span', { class: 'bc-amount__symbol' }, props.symbol),
      props.cycle && h('span', { class: 'bc-amount__cycle' }, `/${props.cycle}`)
    ]);
  }
});
