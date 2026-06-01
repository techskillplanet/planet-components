import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspAlert = defineComponent({
  name: 'TspAlert',
  props: {
    title: String,
    message: String,
    variant: { type: String, default: 'info' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props) {
    return () => h('section', { class: cx('bc-alert', `bc-alert--${props.variant}`), style: themed(props.theme), role: 'status' }, [
      props.title && h('div', { class: 'bc-alert__title' }, props.title),
      props.message && h('div', { class: 'bc-alert__message' }, props.message)
    ]);
  }
});
