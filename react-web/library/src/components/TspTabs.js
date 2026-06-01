import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspTabs({ tabs = [], selectedIndex = 0, theme = starPlanetTheme, onSelect }) {
  return h(
    'div',
    { className: 'bc-tabs', style: themed(theme), role: 'tablist' },
    tabs.map((tab, index) => h(
      'button',
      {
        key: `${tab}-${index}`,
        type: 'button',
        className: cx('bc-tabs__item', index === selectedIndex && 'bc-selected'),
        role: 'tab',
        'aria-selected': index === selectedIndex,
        onClick: () => onSelect?.(index, tab)
      },
      tab
    ))
  );
}
