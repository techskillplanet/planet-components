import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/**
 * TspDatePicker – Themed YYYY-MM-DD date field.
 */
export function TspDatePicker({
  value = '',
  min,
  max,
  placeholder = '',
  disabled = false,
  theme = starPlanetTheme,
  onChange
}) {
  return h('input', {
    className: cx('bc-date-picker', disabled && 'bc-disabled'),
    style: themed(theme),
    type: 'date',
    value: value || '',
    min,
    max,
    placeholder,
    disabled,
    onChange: (event) => onChange?.(event.target.value)
  });
}
