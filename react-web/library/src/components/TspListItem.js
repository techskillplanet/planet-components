import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspListItem({ title, message, trailing, selected = false, disabled = false, theme = starPlanetTheme, onTap }) {
  return h(
    'button',
    { type: 'button', disabled, className: cx('bc-list-item', selected && 'bc-selected', disabled && 'bc-disabled'), style: themed(theme), onClick: disabled ? undefined : onTap },
    h('span', { className: 'bc-list-item__body' }, h('strong', null, title), message && h('span', null, message)),
    trailing && h('span', { className: 'bc-list-item__trailing' }, trailing)
  );
}
