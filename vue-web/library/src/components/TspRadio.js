import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

/** Single radio option (group exclusivity is app-owned). */
export const TspRadio = defineComponent({
  name: 'TspRadio',
  props: {
    text: { type: String, default: '' },
    checked: { type: Boolean, default: false },
    disabled: { type: Boolean, default: false },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change'],
  setup(props, { emit }) {
    return () =>
      h(
        'button',
        {
          type: 'button',
          role: 'radio',
          'aria-checked': props.checked,
          disabled: props.disabled,
          class: cx('bc-radio', props.checked && 'bc-checked', props.disabled && 'bc-disabled'),
          style: themed(props.theme),
          onClick: props.disabled ? undefined : () => emit('change', true)
        },
        [
          h('span', { class: 'bc-radio__dot', 'aria-hidden': true }),
          props.text ? h('span', { class: 'bc-radio__label' }, props.text) : null
        ]
      );
  }
});
