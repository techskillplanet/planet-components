import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspInput – Single-line text input field.
 *
 * Supports error state styling and controlled value pattern.
 *
 * @param {Object} props
 * @param {string} [props.value=''] - Controlled input value.
 * @param {string} [props.placeholder=''] - Placeholder text.
 * @param {'default'|'error'} [props.variant='default'] - Appearance variant.
 * @param {boolean} [props.disabled=false] - Disable input.
 * @param {Object} [props.theme] - Theme object.
 * @param {Function} [props.onChange] - Callback with new value string.
 * @returns {React.ReactElement} A styled <input> element.
 */

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
