import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';
import { TspButton } from './TspButton.js';

/**
 * TspEmpty – Empty state placeholder.
 *
 * Shows an illustration mark, title, message, and optional action button.
 *
 * @param {Object} props
 * @param {string} [props.title] - Empty state title.
 * @param {string} [props.message] - Descriptive message.
 * @param {string} [props.actionText] - Action button text (shows button when set).
 * @param {Object} [props.theme] - Theme object.
 * @param {Function} [props.onAction] - Action button click handler.
 * @returns {React.ReactElement} A <section> element.
 */

export function TspEmpty({ title, message, actionText, theme = starPlanetTheme, onAction }) {
  return h(
    'section',
    { className: 'bc-empty', style: themed(theme) },
    h('div', { className: 'bc-empty__mark' }, '○'),
    title && h('strong', null, title),
    message && h('span', null, message),
    actionText && h(TspButton, { text: actionText, variant: 'primary', fullWidth: false, theme, onTap: onAction })
  );
}
