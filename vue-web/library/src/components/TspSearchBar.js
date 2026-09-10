import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

/** Pill search field. */
export const TspSearchBar = defineComponent({
  name: 'TspSearchBar',
  props: {
    value: { type: String, default: '' },
    placeholder: { type: String, default: 'Search…' },
    disabled: { type: Boolean, default: false },
    variant: { type: String, default: 'default' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change'],
  setup(props, { emit }) {
    return () =>
      h(
        'label',
        {
          class: cx('bc-search-bar', `bc-search-bar--${props.variant}`, props.disabled && 'bc-disabled'),
          style: themed(props.theme)
        },
        [
          h('span', { class: 'bc-search-bar__icon', 'aria-hidden': true }, '⌕'),
          h('input', {
            class: 'bc-search-bar__input',
            type: 'search',
            value: props.value,
            placeholder: props.placeholder,
            disabled: props.disabled,
            onInput: (event) => emit('change', event.target.value)
          })
        ]
      );
  }
});
