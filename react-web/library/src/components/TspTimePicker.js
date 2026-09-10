import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** HH:mm time field. */
export function TspTimePicker({
  value = '',
  placeholder = '',
  disabled = false,
  theme = starPlanetTheme,
  onChange,
}) {
  return h('input', {
    className: cx('bc-time-picker', disabled && 'bc-disabled'),
    style: themed(theme),
    type: 'time',
    value: value || '',
    placeholder,
    disabled,
    'aria-label': placeholder || 'Time',
    onChange: (e) => onChange?.(e.target.value),
  });
}
