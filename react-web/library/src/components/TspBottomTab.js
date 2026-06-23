import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspBottomTab – Primary page bottom tab bar.
 *
 * Renders 3-5 tab entries with icon and title.
 *
 * @param {Object} props
 * @param {Array<{key:string, title:string, icon?:string}>} [props.tabs=[]] - Tab entries.
 * @param {string} props.selectedKey - Key of the active tab.
 * @param {Object} [props.theme] - Theme object.
 * @param {Function} [props.onSelect] - Callback (key, tab) => void.
 * @returns {React.ReactElement} A <nav> element.
 */

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
