import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

export const TspLoadingDialog = defineComponent({
  name: 'TspLoadingDialog',
  props: {
    visible: { type: Boolean, default: false },
    message: { type: String, default: '加载中...' },
    variant: { type: String, default: 'default' },
    dismissible: { type: Boolean, default: false },
    theme: { type: Object, default: () => starPlanetTheme },
  },
  emits: ['dismiss'],
  setup(props, { emit }) {
    return () => {
      if (!props.visible) return null;
      const compact = props.variant === 'compact';
      return h(
        'div',
        {
          class: cx('bc-loading-dialog', compact && 'bc-loading-dialog--compact'),
          style: themed(props.theme),
          role: 'dialog',
          'aria-modal': 'true',
          onClick: props.dismissible ? () => emit('dismiss') : undefined,
        },
        h(
          'section',
          {
            class: 'bc-loading-dialog__panel',
            onClick: (event) => event.stopPropagation(),
          },
          [
            h('div', { class: 'bc-loading-dialog__spinner', 'aria-hidden': 'true' }),
            props.message ? h('p', { class: 'bc-loading-dialog__message' }, props.message) : null,
          ]
        )
      );
    };
  },
});
