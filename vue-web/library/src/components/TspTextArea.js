import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

/** Multi-line text input. */
export const TspTextArea = defineComponent({
  name: 'TspTextArea',
  props: {
    value: { type: String, default: '' },
    placeholder: { type: String, default: '' },
    rows: { type: [Number, String], default: 3 },
    maxLength: { type: [Number, String], default: undefined },
    disabled: { type: Boolean, default: false },
    variant: { type: String, default: 'default' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change'],
  setup(props, { emit }) {
    return () =>
      h('textarea', {
        class: cx('bc-textarea', `bc-textarea--${props.variant}`, props.disabled && 'bc-disabled'),
        style: themed(props.theme),
        value: props.value,
        placeholder: props.placeholder,
        rows: props.rows,
        maxLength: props.maxLength,
        disabled: props.disabled,
        onInput: (e) => emit('change', e.target.value)
      });
  }
});
