import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspNotification = defineComponent({
  name: 'TspNotification',
  props: {
    title: String,
    message: String,
    variant: { type: String, default: 'info' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props) {
    return () => h('section', { class: cx('bc-notification', `bc-notification--${props.variant}`), style: themed(props.theme) }, [
      props.title && h('strong', null, props.title),
      props.message && h('span', null, props.message)
    ]);
  }
});
