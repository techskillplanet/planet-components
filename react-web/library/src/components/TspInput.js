import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspInput({ value = '', placeholder = '', variant = 'default', disabled = false, theme = starPlanetTheme, onChange }) {
  return h('input', {
    className: cx('bc-input', `bc-input--${variant}`),
    style: themed(theme),
    value,
    placeholder,
    disabled,
    onChange: (event) => onChange?.(event.target.value)
  });
}
