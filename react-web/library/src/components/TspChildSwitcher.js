import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/**
 * TspChildSwitcher – Single-select child chips/tabs.
 * Item label resolves from label | name | text.
 * Prefer iconSrc (image) over emoji for production UI.
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
      const selected = String(id) === String(selectedId);
      const leading = item?.leading
        ? item.leading
        : item?.iconSrc
          ? h('img', {
              className: 'bc-child-switcher__avatar',
              src: item.iconSrc,
              alt: '',
              width: 22,
              height: 22,
              draggable: false
            })
          : item?.emoji
            ? h('span', { className: 'bc-child-switcher__emoji', 'aria-hidden': true }, item.emoji)
            : null;
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
        leading,
        h('span', { className: 'bc-child-switcher__label' }, label)
      );
    })
  );
}
