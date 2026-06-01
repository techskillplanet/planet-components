import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';
import { TspButton } from './TspButton.js';

export const TspEmpty = defineComponent({
  name: 'TspEmpty',
  props: {
    title: String,
    message: String,
    actionText: String,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['action'],
  setup(props, { emit }) {
    return () => h('section', { class: 'bc-empty', style: themed(props.theme) }, [
      h('div', { class: 'bc-empty__mark' }, '○'),
      props.title && h('strong', null, props.title),
      props.message && h('span', null, props.message),
      props.actionText && h(TspButton, { text: props.actionText, variant: 'primary', fullWidth: false, theme: props.theme, onTap: () => emit('action') })
    ]);
  }
});
