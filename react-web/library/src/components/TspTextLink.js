import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspTextLink – Text link button.
 *
 * Lightweight text-style button for secondary actions and navigation.
 *
 * @param {Object} props
 * @param {string} props.text - Link text.
 * @param {boolean} [props.inverse=false] - Inverse (light) color variant.
 * @param {Object} [props.theme] - Theme object.
 * @param {Function} [props.onTap] - Click handler.
 * @returns {React.ReactElement} A <button> element styled as text link.
 */

export function TspTextLink({ text, inverse = false, theme = starPlanetTheme, onTap }) {
  return h('button', { type: 'button', className: cx('bc-text-link', inverse && 'bc-text-link--inverse'), style: themed(theme), onClick: onTap }, text);
}
