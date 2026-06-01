import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';
import { TspButton } from './TspButton.js';

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
