import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspBadge – Short text status badge.
 *
 * Used for counts, risk levels, or state labels.
 *
 * @param {Object} props
 * @param {string} props.text - Badge text content.
 * @param {'default'|'primary'|'success'|'warning'|'danger'} [props.variant='default'] - Color variant.
 * @param {boolean} [props.disabled=false] - Dim appearance.
 * @param {Object} [props.theme] - Theme object.
 * @returns {React.ReactElement} A <span> badge element.
 */

export function TspBadge({ text, variant = 'default', disabled = false, theme = starPlanetTheme }) {
  return h('span', { className: cx('bc-badge', `bc-badge--${variant}`, disabled && 'bc-disabled'), style: themed(theme) }, text);
}
