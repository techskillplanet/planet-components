import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/**
 * iOS-style action sheet (options + separate cancel).
 * @param {{ id: string, label: string, enabled?: boolean, destructive?: boolean }[]} items
 */
export function TspActionSheet({
  title = '',
  items = [],
  cancelText = '取消',
  visible = false,
  theme = starPlanetTheme,
  onDismiss,
  onSelect,
}) {
  if (!visible || !items.length) return null;
  return h(
    'div',
    {
      className: 'bc-action-sheet',
      style: themed(theme),
      role: 'dialog',
      'aria-modal': true,
    },
    h('div', { className: 'bc-action-sheet__scrim', onClick: onDismiss }),
    h(
      'div',
      { className: 'bc-action-sheet__panel' },
      h(
        'div',
        { className: 'bc-action-sheet__card' },
        title
          ? h('div', { className: 'bc-action-sheet__title' }, title)
          : null,
        items.map((item) =>
          h(
            'button',
            {
              key: item.id,
              type: 'button',
              disabled: item.enabled === false,
              className: cx(
                'bc-action-sheet__item',
                item.destructive && 'bc-action-sheet__item--danger'
              ),
              onClick: () => onSelect?.(item.id),
            },
            item.label
          )
        )
      ),
      h(
        'button',
        {
          type: 'button',
          className: 'bc-action-sheet__cancel',
          onClick: onDismiss,
        },
        cancelText
      )
    )
  );
}
