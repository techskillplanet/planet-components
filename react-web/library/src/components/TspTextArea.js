import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** Multi-line text input. */
export function TspTextArea({
  value = '',
  placeholder = '',
  rows = 3,
  maxLength,
  disabled = false,
  variant = 'default',
  theme = starPlanetTheme,
  onChange,
}) {
  return h('textarea', {
    className: cx('bc-textarea', `bc-textarea--${variant}`, disabled && 'bc-disabled'),
    style: themed(theme),
    value,
    placeholder,
    rows,
    maxLength,
    disabled,
    onChange: (e) => onChange?.(e.target.value),
  });
}
