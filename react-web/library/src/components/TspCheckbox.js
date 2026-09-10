import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** Checkbox with brand checkmark box + label. */
export function TspCheckbox({
  text = '',
  checked = false,
  disabled = false,
  variant = 'default',
  theme = starPlanetTheme,
  onChange,
}) {
  return h(
    'button',
    {
      type: 'button',
      role: 'checkbox',
      'aria-checked': checked,
      disabled,
      className: cx(
        'bc-checkbox',
        `bc-checkbox--${variant}`,
        checked && 'bc-checked',
        disabled && 'bc-disabled'
      ),
      style: themed(theme),
      onClick: disabled ? undefined : () => onChange?.(!checked),
    },
    h('span', { className: 'bc-checkbox__box', 'aria-hidden': true }, checked ? '✓' : ''),
    text ? h('span', { className: 'bc-checkbox__label' }, text) : null
  );
}
