import { defineComponent } from 'vue';
import { h, cx, clamp, themed, starPlanetTheme } from './_shared.js';

/** Continuous value slider. */
export const TspSlider = defineComponent({
  name: 'TspSlider',
  props: {
    value: { type: [Number, String], default: 0 },
    min: { type: [Number, String], default: 0 },
    max: { type: [Number, String], default: 100 },
    step: { type: [Number, String], default: 1 },
    disabled: { type: Boolean, default: false },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change'],
  setup(props, { emit }) {
    return () => {
      const lo = Number(props.min);
      const hi = Number(props.max);
      const current = clamp(Number(props.value), lo, hi);
      const pct = hi === lo ? 0 : ((current - lo) / (hi - lo)) * 100;
      return h('input', {
        class: cx('bc-slider', props.disabled && 'bc-disabled'),
        style: themed(props.theme, { '--bc-slider-pct': `${pct}%` }),
        type: 'range',
        min: lo,
        max: hi,
        step: props.step,
        value: current,
        disabled: props.disabled,
        'aria-valuemin': lo,
        'aria-valuemax': hi,
        'aria-valuenow': current,
        onInput: (e) => emit('change', Number(e.target.value))
      });
    };
  }
});
