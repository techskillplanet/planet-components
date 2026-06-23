import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspListItem – List row with title, description and trailing element.
 *
 * Supports selected and disabled states.
 *
 * @param {Object} props
 * @param {string} props.title - Primary title text.
 * @param {string} [props.message] - Secondary description text.
 * @param {string|React.ReactNode} [props.trailing] - Trailing element (e.g. '›').
 * @param {boolean} [props.selected=false] - Selected highlight state.
 * @param {boolean} [props.disabled=false] - Disable interaction.
 * @param {Object} [props.theme] - Theme object.
 * @param {Function} [props.onTap] - Click handler.
 * @returns {React.ReactElement} A <button> element styled as a list row.
 */

export function TspListItem({ title, message, trailing, selected = false, disabled = false, theme = starPlanetTheme, onTap }) {
  return h(
    'button',
    { type: 'button', disabled, className: cx('bc-list-item', selected && 'bc-selected', disabled && 'bc-disabled'), style: themed(theme), onClick: disabled ? undefined : onTap },
    h('span', { className: 'bc-list-item__body' }, h('strong', null, title), message && h('span', null, message)),
    trailing && h('span', { className: 'bc-list-item__trailing' }, trailing)
  );
}
