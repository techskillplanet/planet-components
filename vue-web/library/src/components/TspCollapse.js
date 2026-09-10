import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

/** Expandable collapse / accordion panel. */
export const TspCollapse = defineComponent({
  name: 'TspCollapse',
  props: {
    title: { type: String, default: '' },
    message: { type: String, default: '' },
    expanded: { type: Boolean, default: false },
    disabled: { type: Boolean, default: false },
    variant: { type: String, default: 'default' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change'],
  setup(props, { emit }) {
    return () =>
      h(
        'div',
        {
          class: cx(
            'bc-collapse',
            `bc-collapse--${props.variant}`,
            props.expanded && 'bc-expanded',
            props.disabled && 'bc-disabled'
          ),
          style: themed(props.theme)
        },
        [
          h(
            'button',
            {
              type: 'button',
              class: 'bc-collapse__header',
              disabled: props.disabled,
              'aria-expanded': props.expanded,
              onClick: props.disabled ? undefined : () => emit('change', !props.expanded)
            },
            [
              h('span', { class: 'bc-collapse__title' }, props.title),
              h('span', { class: 'bc-collapse__mark', 'aria-hidden': true }, props.expanded ? '−' : '+')
            ]
          ),
          props.expanded ? h('div', { class: 'bc-collapse__body' }, props.message) : null
        ]
      );
  }
});
