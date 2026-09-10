import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** Single radio option (group exclusivity is app-owned). */
export function TspRadio({
  text = '',
  checked = false,
  disabled = false,
  theme = starPlanetTheme,
  onChange,
}) {
  return h(
    'button',
    {
      type: 'button',
      role: 'radio',
      'aria-checked': checked,
      disabled,
      className: cx('bc-radio', checked && 'bc-checked', disabled && 'bc-disabled'),
      style: themed(theme),
      onClick: disabled ? undefined : () => onChange?.(true),
    },
    h('span', { className: 'bc-radio__dot', 'aria-hidden': true }),
    text ? h('span', { className: 'bc-radio__label' }, text) : null
  );
}
