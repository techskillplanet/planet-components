import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspAlert – Inline alert banner for page-level messages.
 *
 * Displays an info/success/warning/error status message with optional title.
 *
 * @param {Object} props
 * @param {string} [props.title] - Bold alert title.
 * @param {string} [props.message] - Alert body text.
 * @param {'info'|'success'|'warning'|'error'} [props.variant='info'] - Semantic color variant.
 * @param {Object} [props.theme] - Theme object.
 * @returns {React.ReactElement} A <section role="status"> element.
 */

export function TspAlert({ title, message, variant = 'info', theme = starPlanetTheme }) {
  return h(
    'section',
    { className: cx('bc-alert', `bc-alert--${variant}`), style: themed(theme), role: 'status' },
    title && h('div', { className: 'bc-alert__title' }, title),
    message && h('div', { className: 'bc-alert__message' }, message)
  );
}
