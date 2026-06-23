import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspNotification – Notification card.
 *
 * Highlights important status messages or task reminders.
 *
 * @param {Object} props
 * @param {string} [props.title] - Notification title.
 * @param {string} [props.message] - Notification message.
 * @param {'info'|'alert'} [props.variant='info'] - Visual variant.
 * @param {Object} [props.theme] - Theme object.
 * @returns {React.ReactElement} A <section> element.
 */

export function TspNotification({ title, message, variant = 'info', theme = starPlanetTheme }) {
  return h(
    'section',
    { className: cx('bc-notification', `bc-notification--${variant}`), style: themed(theme) },
    title && h('strong', null, title),
    message && h('span', null, message)
  );
}
