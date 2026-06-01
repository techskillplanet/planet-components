import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspStepper = defineComponent({
  name: 'TspStepper',
  props: {
    stepCount: { type: Number, default: 3 },
    currentStep: { type: Number, default: 1 },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props) {
    return () => {
      const count = clamp(props.stepCount, 3, 5);
      const current = clamp(props.currentStep, 1, count);
      return h('div', { class: 'bc-stepper', style: themed(props.theme) }, Array.from({ length: count }, (_, index) => {
        const step = index + 1;
        const done = step < current;
        const active = step === current;
        return [h('span', { class: cx('bc-stepper__dot', done && 'bc-done', active && 'bc-active') }, done ? '✓' : step), step < count && h('span', { class: cx('bc-stepper__line', done && 'bc-done') })];
      }));
    };
  }
});
