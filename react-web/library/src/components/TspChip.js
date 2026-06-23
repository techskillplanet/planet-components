import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspChip – Selectable chip/tag for filtering and lightweight selection.
 *
 * @param {Object} props
 * @param {string} props.text - Chip label text.
 * @param {'default'} [props.variant='default'] - Visual variant.
 * @param {boolean} [props.selected=false] - Selected highlight state.
 * @param {boolean} [props.disabled=false] - Disable interaction.
 * @param {Object} [props.theme] - Theme object.
 * @param {Function} [props.onTap] - Click handler.
 * @returns {React.ReactElement} A styled <button> element.
 */

export function TspChip({ text, variant = 'default', selected = false, disabled = false, theme = starPlanetTheme, onTap }) {
  return h(
    'button',
    {
      type: 'button',
      disabled,
      className: cx('bc-chip', `bc-chip--${variant}`, selected && 'bc-selected', disabled && 'bc-disabled'),
      style: themed(theme),
      onClick: disabled ? undefined : onTap
    },
    text
  );
}
