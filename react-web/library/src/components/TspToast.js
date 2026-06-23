import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspToast – Temporary toast notification.
 *
 * Renders a brief feedback message; auto-dismiss is handled by the parent.
 *
 * @param {Object} props
 * @param {string} props.message - Toast message text.
 * @param {'info'|'success'|'warning'|'error'} [props.variant='info'] - Color variant.
 * @param {number} [props.duration=1800] - Display duration hint (ms).
 * @param {Object} [props.theme] - Theme object.
 * @returns {React.ReactElement} A <div role="status"> toast element.
 */

export function TspToast({ message, variant = 'info', duration = 1800, theme = starPlanetTheme }) {
  return h('div', { className: cx('bc-toast', `bc-toast--${variant}`), style: themed(theme), role: 'status', 'data-duration': duration }, message);
}
