import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspChip = defineComponent({
  name: 'TspChip',
  props: {
    text: String,
    variant: { type: String, default: 'default' },
    selected: Boolean,
    disabled: Boolean,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['tap'],
  setup(props, { emit }) {
    return () => h('button', {
      type: 'button',
      disabled: props.disabled,
      class: cx('bc-chip', `bc-chip--${props.variant}`, props.selected && 'bc-selected', props.disabled && 'bc-disabled'),
      style: themed(props.theme),
      onClick: props.disabled ? undefined : () => emit('tap')
    }, props.text);
  }
});
