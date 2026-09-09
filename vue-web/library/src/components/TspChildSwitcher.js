import { defineComponent, h } from 'vue';
import { cx, themed, starPlanetTheme } from './_shared.js';

export const TspChildSwitcher = defineComponent({
  name: 'TspChildSwitcher',
  props: {
    items: { type: Array, default: () => [] },
    selectedId: { type: [String, Number], default: null },
    variant: { type: String, default: 'chip' },
    disabled: Boolean,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change'],
  setup(props, { emit }) {
    return () => {
      const useTabs = props.variant === 'tabs';
      return h(
        'div',
        {
          class: cx('bc-child-switcher', useTabs && 'bc-child-switcher--tabs'),
          style: themed(props.theme),
          role: 'tablist'
        },
        (props.items || []).map((item) => {
          const id = item?.id;
          const label = item?.label ?? item?.name ?? item?.text ?? '';
          const selected = String(id) === String(props.selectedId);
          const leading = item?.leading
            ? item.leading
            : item?.iconSrc
              ? h('img', {
                  class: 'bc-child-switcher__avatar',
                  src: item.iconSrc,
                  alt: '',
                  width: 22,
                  height: 22,
                  draggable: false
                })
              : item?.emoji
                ? h('span', { class: 'bc-child-switcher__emoji', 'aria-hidden': true }, item.emoji)
                : null;
          return h(
            'button',
            {
              key: String(id),
              type: 'button',
              disabled: props.disabled,
              role: 'tab',
              'aria-selected': selected,
              class: cx(
                useTabs ? 'bc-child-switcher__tab' : 'bc-child-switcher__chip',
                selected && 'bc-selected'
              ),
              onClick: props.disabled ? undefined : () => emit('change', id)
            },
            [leading, h('span', { class: 'bc-child-switcher__label' }, label)]
          );
        })
      );
    };
  }
});
