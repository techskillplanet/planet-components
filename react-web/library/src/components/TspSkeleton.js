import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** Skeleton placeholder with optional shimmer. */
export function TspSkeleton({
  rows = 3,
  animated = true,
  avatar = false,
  variant = 'default',
  theme = starPlanetTheme,
}) {
  const count = Math.max(1, Math.min(12, Number(rows) || 3));
  const lines = [];
  for (let i = 0; i < count; i += 1) {
    lines.push(
      h('div', {
        key: i,
        className: cx('bc-skeleton__line', i === count - 1 && 'bc-skeleton__line--short'),
      })
    );
  }
  return h(
    'div',
    {
      className: cx(
        'bc-skeleton',
        `bc-skeleton--${variant}`,
        animated && 'bc-skeleton--animated'
      ),
      style: themed(theme),
      'aria-hidden': true,
    },
    avatar ? h('div', { className: 'bc-skeleton__avatar' }) : null,
    h('div', { className: 'bc-skeleton__body' }, lines)
  );
}
