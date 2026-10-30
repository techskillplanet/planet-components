import { React, h, cx, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * Segmented control.
 * appearance: 'solid'（默认轨道实心）| 'soft'（等分浅选胶囊，对齐书页 Tab）
 * @param {Array<string|{label:string,value?:string}>} [props.options]
 */
export function TspSegmentedControl({
  options = [],
  selectedIndex = 0,
  disabled = false,
  variant = 'default',
  appearance = 'solid',
  theme = starPlanetTheme,
  onSelect,
}) {
  const soft = appearance === 'soft' || variant === 'soft';
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
      className: cx(
        soft ? 'bc-segmented-soft' : 'bc-segmented',
        `bc-segmented--${variant}`,
        disabled && 'bc-disabled',
      ),
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
          className: cx(
            soft ? 'bc-segmented-soft__item' : 'bc-segmented__item',
            index === selectedIndex && 'bc-selected',
          ),
          onClick: disabled
            ? undefined
            : () => onSelect?.(index, item.label, item.value),
        },
        item.label
      )
    )
  );
}
