import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

/** TspDatePicker – Themed YYYY-MM-DD date field. */
export const TspDatePicker = defineComponent({
  name: 'TspDatePicker',
  props: {
    value: { type: String, default: '' },
    min: String,
    max: String,
    placeholder: { type: String, default: '' },
    disabled: Boolean,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change', 'update:value'],
  setup(props, { emit }) {
    return () => h('input', {
      class: cx('bc-date-picker', props.disabled && 'bc-disabled'),
      style: themed(props.theme),
      type: 'date',
      value: props.value || '',
      min: props.min,
      max: props.max,
      placeholder: props.placeholder,
      disabled: props.disabled,
      onInput: (event) => {
        emit('update:value', event.target.value);
        emit('change', event.target.value);
      }
    });
  }
});
