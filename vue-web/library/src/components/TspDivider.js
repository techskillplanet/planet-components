import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

/** Horizontal hairline divider. */
export const TspDivider = defineComponent({
  name: 'TspDivider',
  props: {
    text: { type: String, default: '' },
    disabled: { type: Boolean, default: false },
    variant: { type: String, default: 'default' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props) {
    return () =>
      h('hr', {
        class: cx('bc-divider', `bc-divider--${props.variant}`, props.disabled && 'bc-disabled'),
        style: themed(props.theme),
        role: 'separator',
        'aria-label': props.text || undefined
      });
  }
});
