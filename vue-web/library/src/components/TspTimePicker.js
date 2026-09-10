import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

/** HH:mm time field. */
export const TspTimePicker = defineComponent({
  name: 'TspTimePicker',
  props: {
    value: { type: String, default: '' },
    placeholder: { type: String, default: '' },
    disabled: { type: Boolean, default: false },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change'],
  setup(props, { emit }) {
    return () =>
      h('input', {
        class: cx('bc-time-picker', props.disabled && 'bc-disabled'),
        style: themed(props.theme),
        type: 'time',
        value: props.value || '',
        placeholder: props.placeholder,
        disabled: props.disabled,
        'aria-label': props.placeholder || 'Time',
        onChange: (e) => emit('change', e.target.value)
      });
  }
});
