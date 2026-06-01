import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspButton = defineComponent({
  name: 'TspButton',
  props: {
    text: String,
    variant: { type: String, default: 'default' },
    disabled: Boolean,
    fullWidth: { type: Boolean, default: true },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['tap'],
  setup(props, { emit, slots }) {
    return () => h('button', {
      type: 'button',
      disabled: props.disabled,
      class: cx('bc-button', `bc-button--${props.variant}`, props.fullWidth && 'bc-full', props.disabled && 'bc-disabled'),
      style: themed(props.theme),
      onClick: props.disabled ? undefined : () => emit('tap')
    }, [h('span', { class: 'bc-button__shadow' }), h('span', { class: 'bc-button__face' }, childrenOr(slots, props.text))]);
  }
});
