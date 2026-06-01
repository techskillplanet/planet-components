import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspKeyValueLabel = defineComponent({
  name: 'TspKeyValueLabel',
  props: {
    label: String,
    value: [String, Number],
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props) {
    return () => h('div', { class: 'bc-key-value', style: themed(props.theme) }, [h('span', null, props.label), h('strong', null, props.value)]);
  }
});
