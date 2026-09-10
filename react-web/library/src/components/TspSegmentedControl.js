import { React, h, cx, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * Segmented control.
 * @param {Array<string|{label:string,value?:string}>} [props.options]
 */
export function TspSegmentedControl({
  options = [],
  selectedIndex = 0,
  disabled = false,
  variant = 'default',
  theme = starPlanetTheme,
  onSelect,
}) {
  const items = options.map((opt, index) => {
    if (typeof opt === 'string') return { label: opt, value: opt, index };
    return {
      label: optionText(opt) || String(opt?.label ?? ''),
      value: opt?.value ?? String(index),
      index,
    };
  });
  return h(
    'div',
    {
      className: cx('bc-segmented', `bc-segmented--${variant}`, disabled && 'bc-disabled'),
      style: themed(theme),
      role: 'tablist',
    },
    items.map((item, index) =>
      h(
        'button',
        {
          key: `${item.value}-${index}`,
          type: 'button',
          role: 'tab',
          'aria-selected': index === selectedIndex,
          disabled,
          className: cx('bc-segmented__item', index === selectedIndex && 'bc-selected'),
          onClick: disabled
            ? undefined
            : () => onSelect?.(index, item.label, item.value),
        },
        item.label
      )
    )
  );
}
