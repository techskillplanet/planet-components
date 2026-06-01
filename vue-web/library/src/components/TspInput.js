import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspInput = defineComponent({
  name: 'TspInput',
  props: {
    value: { type: String, default: '' },
    placeholder: String,
    variant: { type: String, default: 'default' },
    disabled: Boolean,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change', 'update:value'],
  setup(props, { emit }) {
    return () => h('input', {
      class: cx('bc-input', `bc-input--${props.variant}`),
      style: themed(props.theme),
      value: props.value,
      placeholder: props.placeholder,
      disabled: props.disabled,
      onInput: (event) => {
        emit('update:value', event.target.value);
        emit('change', event.target.value);
      }
    });
  }
});
