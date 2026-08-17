import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

/**
 * TspSwitch – Flat conventional toggle (no track-inner ON/OFF).
 * Hierarchy: Label? + Control > Track > Thumb > Spinner?
 * checkedText / uncheckedText kept for API compat; not rendered.
 */
export const TspSwitch = defineComponent({
  name: 'TspSwitch',
  props: {
    text: { type: String, default: '' },
    checked: { type: Boolean, default: false },
    checkedText: { type: String, default: 'ON' },
    uncheckedText: { type: String, default: 'OFF' },
    loading: { type: Boolean, default: false },
    disabled: { type: Boolean, default: false },
    variant: { type: String, default: 'md' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change'],
  setup(props, { emit }) {
    return () => {
      const size = props.variant === 'sm' ? 'sm' : 'md';
      const blocked = props.disabled || props.loading;
      return h(
        'button',
        {
          type: 'button',
          role: 'switch',
          'aria-checked': props.checked,
          disabled: blocked,
          class: cx(
            'bc-switch',
            `bc-switch--${size}`,
            props.checked && 'bc-checked',
            props.loading && 'bc-loading',
            props.disabled && 'bc-disabled'
          ),
          style: themed(props.theme),
          onClick: blocked ? undefined : () => emit('change', !props.checked)
        },
        [
          props.text ? h('span', { class: 'bc-switch__label' }, props.text) : null,
          h(
            'span',
            { class: 'bc-switch__control' },
            h(
              'span',
              { class: 'bc-switch__track' },
              h(
                'span',
                { class: 'bc-switch__thumb' },
                props.loading ? h('span', { class: 'bc-switch__spinner', 'aria-hidden': true }) : null
              )
            )
          )
        ]
      );
    };
  }
});
