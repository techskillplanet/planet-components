import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

/** Floating action button. */
export const TspFab = defineComponent({
  name: 'TspFab',
  props: {
    icon: { type: [String, Number], default: '+' },
    text: { type: String, default: '' },
    variant: { type: String, default: 'primary' },
    disabled: { type: Boolean, default: false },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['tap'],
  setup(props, { emit }) {
    return () =>
      h(
        'button',
        {
          type: 'button',
          class: cx(
            'bc-fab',
            `bc-fab--${props.variant}`,
            props.text && 'bc-fab--extended',
            props.disabled && 'bc-disabled'
          ),
          style: themed(props.theme),
          disabled: props.disabled,
          'aria-label': props.text || String(props.icon),
          onClick: props.disabled ? undefined : () => emit('tap')
        },
        [
          h('span', { class: 'bc-fab__icon', 'aria-hidden': true }, props.icon),
          props.text ? h('span', { class: 'bc-fab__text' }, props.text) : null
        ]
      );
  }
});
