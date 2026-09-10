import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** Side / bottom drawer overlay. */
export function TspDrawer({
  visible = false,
  title = '',
  placement = 'bottom',
  theme = starPlanetTheme,
  onClose,
  children,
}) {
  if (!visible) return null;
  return h(
    'div',
    {
      className: cx('bc-drawer', `bc-drawer--${placement}`),
      style: themed(theme),
      role: 'dialog',
      'aria-modal': true,
      'aria-label': title || 'Drawer',
    },
    h('button', {
      type: 'button',
      className: 'bc-drawer__mask',
      'aria-label': 'Close',
      onClick: () => onClose?.(),
    }),
    h(
      'div',
      { className: 'bc-drawer__panel' },
      title
        ? h(
            'div',
            { className: 'bc-drawer__header' },
            h('div', { className: 'bc-drawer__title' }, title),
            h(
              'button',
              {
                type: 'button',
                className: 'bc-drawer__close',
                'aria-label': 'Close',
                onClick: () => onClose?.(),
              },
              '×'
            )
          )
        : null,
      h('div', { className: 'bc-drawer__body' }, children)
    )
  );
}
