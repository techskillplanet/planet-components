import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/**
 * TspChildSwitcher – Single-select child chips/tabs.
 * Item label resolves from label | name | text.
 */
export function TspChildSwitcher({
  items = [],
  selectedId,
  variant = 'chip',
  disabled = false,
  theme = starPlanetTheme,
  onChange
}) {
  const useTabs = variant === 'tabs';
  return h(
    'div',
    {
      className: cx('bc-child-switcher', useTabs && 'bc-child-switcher--tabs'),
      style: themed(theme),
      role: 'tablist'
    },
    items.map((item) => {
      const id = item?.id;
      const label = item?.label ?? item?.name ?? item?.text ?? '';
      const emoji = item?.emoji ? `${item.emoji} ` : '';
      const selected = String(id) === String(selectedId);
      return h(
        'button',
        {
          key: String(id),
          type: 'button',
          disabled,
          role: 'tab',
          'aria-selected': selected,
          className: cx(
            useTabs ? 'bc-child-switcher__tab' : 'bc-child-switcher__chip',
            selected && 'bc-selected'
          ),
          onClick: disabled ? undefined : () => onChange?.(id)
        },
        `${emoji}${label}`
      );
    })
  );
}
