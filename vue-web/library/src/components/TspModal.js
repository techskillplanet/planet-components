import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';
import { TspButton } from './TspButton.js';

export const TspModal = defineComponent({
  name: 'TspModal',
  props: {
    title: String,
    message: String,
    confirmText: { type: String, default: 'OK' },
    cancelText: { type: String, default: 'Cancel' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['confirm', 'cancel'],
  setup(props, { emit }) {
    return () => h('div', { class: 'bc-modal', style: themed(props.theme), role: 'dialog', 'aria-modal': 'true' }, h('section', { class: 'bc-modal__panel' }, [
      props.title && h('h3', null, props.title),
      props.message && h('p', null, props.message),
      h('div', { class: 'bc-modal__actions' }, [
        h(TspButton, { text: props.cancelText, variant: 'default', theme: props.theme, onTap: () => emit('cancel') }),
        h(TspButton, { text: props.confirmText, variant: 'primary', theme: props.theme, onTap: () => emit('confirm') })
      ])
    ]));
  }
});
