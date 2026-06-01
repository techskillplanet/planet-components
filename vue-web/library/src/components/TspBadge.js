import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspBadge = defineComponent({
  name: 'TspBadge',
  props: {
    text: String,
    variant: { type: String, default: 'default' },
    disabled: Boolean,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props) {
    return () => h('span', { class: cx('bc-badge', `bc-badge--${props.variant}`, props.disabled && 'bc-disabled'), style: themed(props.theme) }, props.text);
  }
});
