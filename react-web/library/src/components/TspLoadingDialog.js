import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/**
 * TspLoadingDialog – loading overlay aligned with RN.
 */
export function TspLoadingDialog({
  visible = false,
  message = '加载中...',
  variant = 'default',
  dismissible = false,
  theme = starPlanetTheme,
  onDismiss,
}) {
  if (!visible) return null;
  const compact = variant === 'compact';
  return h(
    'div',
    {
      className: cx('bc-loading-dialog', compact && 'bc-loading-dialog--compact'),
      style: themed(theme),
      role: 'dialog',
      'aria-modal': 'true',
      onClick: dismissible ? onDismiss : undefined,
    },
    h(
      'section',
      {
        className: 'bc-loading-dialog__panel',
        onClick: (event) => event.stopPropagation(),
      },
      h('div', { className: 'bc-loading-dialog__spinner', 'aria-hidden': 'true' }),
      message ? h('p', { className: 'bc-loading-dialog__message' }, message) : null
    )
  );
}
