import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** Pill search field（家常默认「搜索」；有字可清，对齐主流 SearchBar）. */
export function TspSearchBar({
  value = '',
  placeholder = '搜索',
  disabled = false,
  variant = 'default',
  showClear = true,
  theme = starPlanetTheme,
  onChange,
  onClear,
}) {
  const canClear = showClear && !!value && !disabled;
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
    }),
    canClear
      ? h(
          'button',
          {
            type: 'button',
            className: 'bc-search-bar__clear',
            'aria-label': '清除',
            onClick: (event) => {
              event.preventDefault();
              onChange?.('');
              onClear?.();
            },
          },
          '×',
        )
      : null,
  );
}
