import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspStepper – Step progress indicator.
 *
 * Displays numbered steps (3-5) with completion states.
 *
 * @param {Object} props
 * @param {number} [props.stepCount=3] - Total number of steps (clamped 3-5).
 * @param {number} [props.currentStep=1] - Current active step (1-based).
 * @param {Object} [props.theme] - Theme object.
 * @returns {React.ReactElement} A <div> with step dots and connectors.
 */

export function TspStepper({ stepCount = 3, currentStep = 1, theme = starPlanetTheme }) {
  const count = clamp(stepCount, 3, 5);
  const current = clamp(currentStep, 1, count);
  return h(
    'div',
    { className: 'bc-stepper', style: themed(theme) },
    Array.from({ length: count }, (_, index) => {
      const step = index + 1;
      const done = step < current;
      const active = step === current;
      return h(React.Fragment, { key: step },
        h('span', { className: cx('bc-stepper__dot', done && 'bc-done', active && 'bc-active') }, done ? '✓' : step),
        step < count && h('span', { className: cx('bc-stepper__line', done && 'bc-done') })
      );
    })
  );
}
