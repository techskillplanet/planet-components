import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspButton – Primary action button with island-raised 3D shadow.
 *
 * Supports primary, default, danger, and text variants.
 * Automatically injects theme CSS variables for consistent styling.
 *
 * @param {Object} props
 * @param {string} [props.text] - Button label text.
 * @param {'primary'|'default'|'danger'|'text'} [props.variant='default'] - Visual variant.
 * @param {boolean} [props.disabled=false] - Disable interaction and dim appearance.
 * @param {boolean} [props.loading=false] - Show spinner and block taps.
 * @param {boolean} [props.fullWidth=true] - Whether button takes full container width.
 * @param {Object} [props.theme] - Theme object for CSS variable injection.
 * @param {Function} [props.onTap] - Click handler callback.
 * @param {React.ReactNode} [props.children] - Custom content (overrides text).
 * @returns {React.ReactElement} A styled <button> element.
 */

export function TspButton({
  text,
  variant = 'default',
  disabled = false,
  loading = false,
  fullWidth = true,
  theme = starPlanetTheme,
  onTap,
  children
}) {
  const busy = Boolean(loading);
  const inert = disabled || busy;
  const label = children ?? text;
  return h(
    'button',
    {
      type: 'button',
      disabled: inert,
      'aria-busy': busy || undefined,
      className: cx(
        'bc-button',
        `bc-button--${variant}`,
        fullWidth && 'bc-full',
        inert && 'bc-disabled',
        busy && 'bc-button--loading',
        theme.buttonRaisedShadowEnabled === false && 'bc-button--flat'
      ),
      style: themed(theme),
      onClick: inert ? undefined : onTap
    },
    h('span', { className: 'bc-button__shadow', 'aria-hidden': true }),
    h(
      'span',
      { className: 'bc-button__face' },
      busy ? h('span', { className: 'bc-button__spinner', 'aria-hidden': true }) : null,
      label
    )
  );
}
