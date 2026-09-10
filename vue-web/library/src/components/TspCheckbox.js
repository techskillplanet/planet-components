import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

/** Checkbox with brand checkmark box + label. */
export const TspCheckbox = defineComponent({
  name: 'TspCheckbox',
  props: {
    text: { type: String, default: '' },
    checked: { type: Boolean, default: false },
    disabled: { type: Boolean, default: false },
    variant: { type: String, default: 'default' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change'],
  setup(props, { emit }) {
    return () =>
      h(
        'button',
        {
          type: 'button',
          role: 'checkbox',
          'aria-checked': props.checked,
          disabled: props.disabled,
          class: cx(
            'bc-checkbox',
            `bc-checkbox--${props.variant}`,
            props.checked && 'bc-checked',
            props.disabled && 'bc-disabled'
          ),
          style: themed(props.theme),
          onClick: props.disabled ? undefined : () => emit('change', !props.checked)
        },
        [
          h('span', { class: 'bc-checkbox__box', 'aria-hidden': true }, props.checked ? '✓' : ''),
          props.text ? h('span', { class: 'bc-checkbox__label' }, props.text) : null
        ]
      );
  }
});
