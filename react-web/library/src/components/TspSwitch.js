import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

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
