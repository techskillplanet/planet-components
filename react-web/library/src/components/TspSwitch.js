import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/**
 * TspSwitch – Flat conventional toggle (no track-inner ON/OFF).
 *
 * Hierarchy: Label? + Control > Track > Thumb > Spinner?
 *
 * @param {Object} props
 * @param {string} [props.text=''] - Outer label on the left.
 * @param {boolean} [props.checked=false] - Whether the switch is on.
 * @param {string} [props.checkedText='ON'] - Kept for API compat; not rendered.
 * @param {string} [props.uncheckedText='OFF'] - Kept for API compat; not rendered.
 * @param {boolean} [props.loading=false] - Show spinner in thumb; blocks interaction.
 * @param {boolean} [props.disabled=false] - Disable the switch.
 * @param {'md'|'sm'} [props.variant='md'] - Size variant.
 * @param {Object} [props.theme] - Theme object.
 * @param {Function} [props.onChange] - Callback with new boolean value.
 * @returns {React.ReactElement}
 */

export function TspSwitch({
  text = '',
  checked = false,
  checkedText: _checkedText = 'ON',
  uncheckedText: _uncheckedText = 'OFF',
  loading = false,
  disabled = false,
  variant = 'md',
  theme = starPlanetTheme,
  onChange
}) {
  const size = variant === 'sm' ? 'sm' : 'md';
  const blocked = disabled || loading;
  return h(
    'button',
    {
      type: 'button',
      role: 'switch',
      'aria-checked': checked,
      disabled: blocked,
      className: cx(
        'bc-switch',
        `bc-switch--${size}`,
        checked && 'bc-checked',
        loading && 'bc-loading',
        disabled && 'bc-disabled'
      ),
      style: themed(theme),
      onClick: blocked ? undefined : () => onChange?.(!checked)
    },
    text ? h('span', { className: 'bc-switch__label' }, text) : null,
    h(
      'span',
      { className: 'bc-switch__control' },
      h(
        'span',
        { className: 'bc-switch__track' },
        h(
          'span',
          { className: 'bc-switch__thumb' },
          loading ? h('span', { className: 'bc-switch__spinner', 'aria-hidden': true }) : null
        )
      )
    )
  );
}
