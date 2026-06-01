import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspToast = defineComponent({
  name: 'TspToast',
  props: {
    message: String,
    variant: { type: String, default: 'info' },
    duration: { type: Number, default: 1800 },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props) {
    return () => h('div', { class: cx('bc-toast', `bc-toast--${props.variant}`), style: themed(props.theme), role: 'status', 'data-duration': props.duration }, props.message);
  }
});
