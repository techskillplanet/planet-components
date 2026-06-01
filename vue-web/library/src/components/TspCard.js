import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspCard = defineComponent({
  name: 'TspCard',
  props: {
    variant: { type: String, default: 'default' },
    selected: Boolean,
    disabled: Boolean,
    content: null,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props, { slots }) {
    return () => h('section', {
      class: cx('bc-card', `bc-card--${props.variant}`, props.selected && 'bc-selected', props.disabled && 'bc-disabled'),
      style: themed(props.theme)
    }, childrenOr(slots, props.content));
  }
});
