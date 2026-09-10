import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

/** Closable / selectable tag (Chip remains for filter chips). */
export const TspTag = defineComponent({
  name: 'TspTag',
  props: {
    text: { type: String, default: '' },
    closable: { type: Boolean, default: false },
    selected: { type: Boolean, default: false },
    disabled: { type: Boolean, default: false },
    variant: { type: String, default: 'default' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['close', 'tap'],
  setup(props, { emit }) {
    return () =>
      h(
        'span',
        {
          class: cx(
            'bc-tag',
            `bc-tag--${props.variant}`,
            props.selected && 'bc-selected',
            props.disabled && 'bc-disabled',
            props.closable && 'bc-tag--closable'
          ),
          style: themed(props.theme),
          role: props.disabled ? undefined : 'button',
          tabindex: props.disabled ? undefined : 0,
          onClick: props.disabled ? undefined : () => emit('tap'),
          onKeydown: (e) => {
            if (props.disabled) return;
            if (e.key === 'Enter' || e.key === ' ') {
              e.preventDefault();
              emit('tap');
            }
          }
        },
        [
          h('span', { class: 'bc-tag__text' }, props.text),
          props.closable
            ? h(
                'button',
                {
                  type: 'button',
                  class: 'bc-tag__close',
                  disabled: props.disabled,
                  'aria-label': 'Remove',
                  onClick: (e) => {
                    e.stopPropagation();
                    if (!props.disabled) emit('close');
                  }
                },
                '×'
              )
            : null
        ]
      );
  }
});
