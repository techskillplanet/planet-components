import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** Pill search field. */
export function TspSearchBar({
  value = '',
  placeholder = 'Search…',
  disabled = false,
  variant = 'default',
  theme = starPlanetTheme,
  onChange,
}) {
  return h(
    'label',
    {
      className: cx('bc-search-bar', `bc-search-bar--${variant}`, disabled && 'bc-disabled'),
      style: themed(theme),
    },
    h('span', { className: 'bc-search-bar__icon', 'aria-hidden': true }, '⌕'),
    h('input', {
      className: 'bc-search-bar__input',
      type: 'search',
      value,
      placeholder,
      disabled,
      onChange: (event) => onChange?.(event.target.value),
    })
  );
}
