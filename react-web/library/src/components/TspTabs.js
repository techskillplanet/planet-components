import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspTabs – In-page segmented tab bar.
 *
 * Used for content filtering and category switching.
 *
 * @param {Object} props
 * @param {Array<string>} [props.tabs=[]] - Tab label strings.
 * @param {number} [props.selectedIndex=0] - Active tab index.
 * @param {Object} [props.theme] - Theme object.
 * @param {Function} [props.onSelect] - Callback (index, tab) => void.
 * @returns {React.ReactElement} A <div role="tablist"> element.
 */

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
