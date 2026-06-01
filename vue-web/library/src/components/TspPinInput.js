import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspPinInput = defineComponent({
  name: 'TspPinInput',
  props: {
    value: { type: String, default: '' },
    cellCount: { type: Number, default: 4 },
    secure: Boolean,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change', 'complete', 'update:value'],
  setup(props, { emit }) {
    return () => {
      const count = clamp(props.cellCount, 4, 6);
      const chars = props.value.slice(0, count).split('');
      return h('label', { class: 'bc-pin-input', style: themed(props.theme) }, [
        h('input', {
          value: props.value,
          maxLength: count,
          inputmode: 'numeric',
          onInput: (event) => {
            const next = event.target.value.slice(0, count);
            emit('update:value', next);
            emit('change', next);
            if (next.length === count) emit('complete', next);
          }
        }),
        Array.from({ length: count }, (_, index) => h('span', { class: 'bc-pin-input__cell' }, props.secure && chars[index] ? '•' : chars[index] ?? ''))
      ]);
    };
  }
});
