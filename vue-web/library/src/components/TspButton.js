import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspButton = defineComponent({
  name: 'TspButton',
  props: {
    text: String,
    variant: { type: String, default: 'default' },
    disabled: Boolean,
    loading: Boolean,
    fullWidth: { type: Boolean, default: true },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['tap'],
  setup(props, { emit, slots }) {
    return () => {
      const busy = Boolean(props.loading);
      const inert = props.disabled || busy;
      return h('button', {
        type: 'button',
        disabled: inert,
        'aria-busy': busy || undefined,
        class: cx(
          'bc-button',
          `bc-button--${props.variant}`,
          props.fullWidth && 'bc-full',
          inert && 'bc-disabled',
          busy && 'bc-button--loading',
          props.theme.buttonRaisedShadowEnabled === false && 'bc-button--flat'
        ),
        style: themed(props.theme),
        onClick: inert ? undefined : () => emit('tap')
      }, [
        h('span', { class: 'bc-button__shadow', 'aria-hidden': true }),
        h('span', { class: 'bc-button__face' }, [
          busy ? h('span', { class: 'bc-button__spinner', 'aria-hidden': true }) : null,
          childrenOr(slots, props.text)
        ])
      ]);
    };
  }
});
