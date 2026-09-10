import { React, h, cx, clamp, themed, starPlanetTheme } from './_shared.js';

/** Numeric stepper field (+/−). */
export function TspInputNumber({
  value = 0,
  min = Number.NEGATIVE_INFINITY,
  max = Number.POSITIVE_INFINITY,
  step = 1,
  disabled = false,
  theme = starPlanetTheme,
  onChange,
}) {
  const current = Number(value) || 0;
  const s = Number(step) || 1;
  const dec = () => onChange?.(clamp(current - s, min, max));
  const inc = () => onChange?.(clamp(current + s, min, max));
  return h(
    'div',
    {
      className: cx('bc-input-number', disabled && 'bc-disabled'),
      style: themed(theme),
    },
    h(
      'button',
      {
        type: 'button',
        className: 'bc-input-number__btn',
        disabled: disabled || current <= min,
        'aria-label': 'Decrease',
        onClick: dec,
      },
      '−'
    ),
    h('span', { className: 'bc-input-number__value', 'aria-live': 'polite' }, String(current)),
    h(
      'button',
      {
        type: 'button',
        className: 'bc-input-number__btn',
        disabled: disabled || current >= max,
        'aria-label': 'Increase',
        onClick: inc,
      },
      '+'
    )
  );
}
