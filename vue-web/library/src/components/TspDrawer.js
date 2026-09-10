import { defineComponent } from 'vue';
import { h, cx, themed, childrenOr, starPlanetTheme } from './_shared.js';

/** Side / bottom drawer overlay. */
export const TspDrawer = defineComponent({
  name: 'TspDrawer',
  props: {
    visible: { type: Boolean, default: false },
    title: { type: String, default: '' },
    placement: { type: String, default: 'bottom' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['close'],
  setup(props, { emit, slots }) {
    return () => {
      if (!props.visible) return null;
      return h(
        'div',
        {
          class: cx('bc-drawer', `bc-drawer--${props.placement}`),
          style: themed(props.theme),
          role: 'dialog',
          'aria-modal': true,
          'aria-label': props.title || 'Drawer'
        },
        [
          h('button', {
            type: 'button',
            class: 'bc-drawer__mask',
            'aria-label': 'Close',
            onClick: () => emit('close')
          }),
          h('div', { class: 'bc-drawer__panel' }, [
            props.title
              ? h('div', { class: 'bc-drawer__header' }, [
                  h('div', { class: 'bc-drawer__title' }, props.title),
                  h(
                    'button',
                    {
                      type: 'button',
                      class: 'bc-drawer__close',
                      'aria-label': 'Close',
                      onClick: () => emit('close')
                    },
                    '×'
                  )
                ])
              : null,
            h('div', { class: 'bc-drawer__body' }, childrenOr(slots, null))
          ])
        ]
      );
    };
  }
});
