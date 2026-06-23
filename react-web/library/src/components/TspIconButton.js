import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspIconButton – Icon-only action button.
 *
 * Compact button for toolbar actions and quick selections.
 *
 * @param {Object} props
 * @param {string|React.ReactNode} props.icon - Icon content (emoji, text or element).
 * @param {boolean} [props.selected=false] - Selected highlight state.
 * @param {boolean} [props.disabled=false] - Disable interaction.
 * @param {'default'|'primary'} [props.variant='default'] - Button variant.
 * @param {Object} [props.theme] - Theme object.
 * @param {Function} [props.onTap] - Click handler.
 * @returns {React.ReactElement} A <button> element.
 */

export function TspIconButton({ icon, selected = false, disabled = false, variant = 'default', theme = starPlanetTheme, onTap }) {
  return h(
    'button',
    {
      type: 'button',
      disabled,
      className: cx('bc-icon-button', `bc-icon-button--${variant}`, selected && 'bc-selected', disabled && 'bc-disabled'),
      style: themed(theme),
      onClick: disabled ? undefined : onTap
    },
    icon
  );
}
