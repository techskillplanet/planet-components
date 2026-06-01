import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspStickyFooter = defineComponent({
  name: 'TspStickyFooter',
  props: {
    content: null,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props, { slots }) {
    return () => h('footer', { class: 'bc-sticky-footer', style: themed(props.theme) }, childrenOr(slots, props.content));
  }
});
