import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** Closable / selectable tag (Chip remains for filter chips). */
export function TspTag({
  text = '',
  closable = false,
  selected = false,
  disabled = false,
  variant = 'default',
  theme = starPlanetTheme,
  onClose,
  onTap,
}) {
  return h(
    'span',
    {
      className: cx(
        'bc-tag',
        `bc-tag--${variant}`,
        selected && 'bc-selected',
        disabled && 'bc-disabled',
        closable && 'bc-tag--closable'
      ),
      style: themed(theme),
      role: onTap ? 'button' : undefined,
      tabIndex: onTap && !disabled ? 0 : undefined,
      onClick: disabled ? undefined : onTap,
      onKeyDown:
        onTap && !disabled
          ? (e) => {
              if (e.key === 'Enter' || e.key === ' ') {
                e.preventDefault();
                onTap();
              }
            }
          : undefined,
    },
    h('span', { className: 'bc-tag__text' }, text),
    closable
      ? h(
          'button',
          {
            type: 'button',
            className: 'bc-tag__close',
            disabled,
            'aria-label': 'Remove',
            onClick: (e) => {
              e.stopPropagation();
              if (!disabled) onClose?.();
            },
          },
          '×'
        )
      : null
  );
}
