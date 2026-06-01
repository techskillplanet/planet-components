import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspTextLink = defineComponent({
  name: 'TspTextLink',
  props: {
    text: String,
    inverse: Boolean,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['tap'],
  setup(props, { emit }) {
    return () => h('button', { type: 'button', class: cx('bc-text-link', props.inverse && 'bc-text-link--inverse'), style: themed(props.theme), onClick: () => emit('tap') }, props.text);
  }
});
