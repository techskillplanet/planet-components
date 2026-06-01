import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspBottomTab({ tabs = [], selectedKey, theme = starPlanetTheme, onSelect }) {
  return h(
    'nav',
    { className: 'bc-bottom-tab', style: themed(theme) },
    tabs.map((tab) => h(
      'button',
      {
        key: tab.key,
        type: 'button',
        className: cx('bc-bottom-tab__item', tab.key === selectedKey && 'bc-selected'),
        onClick: () => onSelect?.(tab.key, tab)
      },
      tab.icon && h('span', { className: 'bc-bottom-tab__icon' }, tab.icon),
      h('span', null, tab.title ?? tab.text ?? tab.key)
    ))
  );
}
