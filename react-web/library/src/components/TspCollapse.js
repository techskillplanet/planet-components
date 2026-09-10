import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** Expandable collapse / accordion panel. */
export function TspCollapse({
  title = '',
  message = '',
  expanded = false,
  disabled = false,
  variant = 'default',
  theme = starPlanetTheme,
  onChange,
}) {
  return h(
    'div',
    {
      className: cx(
        'bc-collapse',
        `bc-collapse--${variant}`,
        expanded && 'bc-expanded',
        disabled && 'bc-disabled'
      ),
      style: themed(theme),
    },
    h(
      'button',
      {
        type: 'button',
        className: 'bc-collapse__header',
        disabled,
        'aria-expanded': expanded,
        onClick: disabled ? undefined : () => onChange?.(!expanded),
      },
      h('span', { className: 'bc-collapse__title' }, title),
      h('span', { className: 'bc-collapse__mark', 'aria-hidden': true }, expanded ? '−' : '+')
    ),
    expanded
      ? h('div', { className: 'bc-collapse__body' }, message)
      : null
  );
}
