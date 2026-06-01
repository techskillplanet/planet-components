import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspTopBar = defineComponent({
  name: 'TspTopBar',
  props: {
    title: String,
    showBack: Boolean,
    backgroundColor: String,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['back'],
  setup(props, { emit }) {
    return () => h('header', { class: 'bc-top-bar', style: themed(props.theme, props.backgroundColor ? { backgroundColor: props.backgroundColor } : undefined) }, [
      props.showBack && h('button', { type: 'button', class: 'bc-top-bar__back', 'aria-label': 'Back', onClick: () => emit('back') }, '‹'),
      h('div', { class: 'bc-top-bar__title' }, props.title)
    ]);
  }
});
