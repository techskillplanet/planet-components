import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** Checkbox with brand checkmark box + label. */
export function TspCheckbox({
  text = '',
  checked = false,
  disabled = false,
  variant = 'default',
  size = 'md',
  interactive = true,
  theme = starPlanetTheme,
  onChange,
}) {
  const sizeKey = ['sm', 'md', 'lg'].includes(size) ? size : 'md';
  const hasLabel = Boolean(text);
  const Tag = interactive ? 'button' : 'span';
  return h(
    Tag,
    {
      type: interactive ? 'button' : undefined,
      role: 'checkbox',
      'aria-checked': checked,
      disabled: interactive ? disabled : undefined,
      className: cx(
        'bc-checkbox',
        `bc-checkbox--${variant}`,
        `bc-checkbox--size-${sizeKey}`,
        !hasLabel && 'bc-checkbox--box-only',
        checked && 'bc-checked',
        disabled && 'bc-disabled'
      ),
      style: themed(theme),
      onClick: interactive && !disabled ? () => onChange?.(!checked) : undefined,
    },
    h('span', { className: 'bc-checkbox__box', 'aria-hidden': true }, checked ? '✓' : ''),
    hasLabel ? h('span', { className: 'bc-checkbox__label' }, text) : null
  );
}
