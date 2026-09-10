import { defineComponent } from 'vue';
import { h, cx, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * Segmented control.
 * @param {Array<string|{label:string,value?:string}>} options
 */
export const TspSegmentedControl = defineComponent({
  name: 'TspSegmentedControl',
  props: {
    options: { type: Array, default: () => [] },
    selectedIndex: { type: Number, default: 0 },
    disabled: { type: Boolean, default: false },
    variant: { type: String, default: 'default' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['select'],
  setup(props, { emit }) {
    return () => {
      const items = props.options.map((opt, index) => {
        if (typeof opt === 'string') return { label: opt, value: opt, index };
        return {
          label: optionText(opt) || String(opt?.label ?? ''),
          value: opt?.value ?? String(index),
          index
        };
      });
      return h(
        'div',
        {
          class: cx('bc-segmented', `bc-segmented--${props.variant}`, props.disabled && 'bc-disabled'),
          style: themed(props.theme),
          role: 'tablist'
        },
        items.map((item, index) =>
          h(
            'button',
            {
              key: `${item.value}-${index}`,
              type: 'button',
              role: 'tab',
              'aria-selected': index === props.selectedIndex,
              disabled: props.disabled,
              class: cx('bc-segmented__item', index === props.selectedIndex && 'bc-selected'),
              onClick: props.disabled
                ? undefined
                : () => emit('select', index, item.label, item.value)
            },
            item.label
          )
        )
      );
    };
  }
});
