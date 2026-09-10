import { defineComponent } from 'vue';
import { h, cx, clamp, themed, starPlanetTheme } from './_shared.js';

/** Numeric stepper field (+/−). */
export const TspInputNumber = defineComponent({
  name: 'TspInputNumber',
  props: {
    value: { type: [Number, String], default: 0 },
    min: { type: [Number, String], default: Number.NEGATIVE_INFINITY },
    max: { type: [Number, String], default: Number.POSITIVE_INFINITY },
    step: { type: [Number, String], default: 1 },
    disabled: { type: Boolean, default: false },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change'],
  setup(props, { emit }) {
    return () => {
      const current = Number(props.value) || 0;
      const s = Number(props.step) || 1;
      const lo = Number(props.min);
      const hi = Number(props.max);
      const dec = () => emit('change', clamp(current - s, lo, hi));
      const inc = () => emit('change', clamp(current + s, lo, hi));
      return h(
        'div',
        {
          class: cx('bc-input-number', props.disabled && 'bc-disabled'),
          style: themed(props.theme)
        },
        [
          h(
            'button',
            {
              type: 'button',
              class: 'bc-input-number__btn',
              disabled: props.disabled || current <= lo,
              'aria-label': 'Decrease',
              onClick: dec
            },
            '−'
          ),
          h('span', { class: 'bc-input-number__value', 'aria-live': 'polite' }, String(current)),
          h(
            'button',
            {
              type: 'button',
              class: 'bc-input-number__btn',
              disabled: props.disabled || current >= hi,
              'aria-label': 'Increase',
              onClick: inc
            },
            '+'
          )
        ]
      );
    };
  }
});
