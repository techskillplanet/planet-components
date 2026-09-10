import { React, h, cx, clamp, themed, starPlanetTheme } from './_shared.js';

/** Continuous value slider. */
export function TspSlider({
  value = 0,
  min = 0,
  max = 100,
  step = 1,
  disabled = false,
  theme = starPlanetTheme,
  onChange,
}) {
  const lo = Number(min);
  const hi = Number(max);
  const current = clamp(Number(value), lo, hi);
  const pct = hi === lo ? 0 : ((current - lo) / (hi - lo)) * 100;
  return h('input', {
    className: cx('bc-slider', disabled && 'bc-disabled'),
    style: {
      ...themed(theme),
      '--bc-slider-pct': `${pct}%`,
    },
    type: 'range',
    min: lo,
    max: hi,
    step,
    value: current,
    disabled,
    'aria-valuemin': lo,
    'aria-valuemax': hi,
    'aria-valuenow': current,
    onChange: (e) => onChange?.(Number(e.target.value)),
  });
}
