import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspSwitch – Toggle switch with loading and disabled states.
 *
 * Binary on/off control with optional label text.
 *
 * @param {Object} props
 * @param {string} [props.text] - Label text displayed beside the switch.
 * @param {boolean} [props.checked=false] - Whether the switch is on.
 * @param {string} [props.checkedText=''] - Text shown when checked.
 * @param {string} [props.uncheckedText=''] - Text shown when unchecked.
 * @param {boolean} [props.loading=false] - Show loading state (disables interaction).
 * @param {boolean} [props.disabled=false] - Disable the switch.
 * @param {Object} [props.theme] - Theme object.
 * @param {Function} [props.onChange] - Callback with new boolean value.
 * @returns {React.ReactElement} A styled <button> element with track/thumb.
 */

export function TspSwitch({
  text,
  checked = false,
  checkedText = '',
  uncheckedText = '',
  loading = false,
  disabled = false,
  theme = starPlanetTheme,
  onChange
}) {
  const label = checked ? checkedText : uncheckedText;
  return h(
    'button',
    {
      type: 'button',
      disabled: disabled || loading,
      className: cx('bc-switch', checked && 'bc-checked', loading && 'bc-loading', disabled && 'bc-disabled'),
      style: themed(theme),
      onClick: disabled || loading ? undefined : () => onChange?.(!checked)
    },
    h('span', { className: 'bc-switch__track' }, h('span', { className: 'bc-switch__thumb' })),
    (text || label) && h('span', { className: 'bc-switch__text' }, text || label)
  );
}
