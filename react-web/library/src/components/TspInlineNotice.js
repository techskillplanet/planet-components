import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** Inline notice bar (warn / error / info). */
export function TspInlineNotice({
  message = '',
  tone = 'warning',
  theme = starPlanetTheme,
  onClick,
}) {
  const text = String(message || '').trim();
  if (!text) return null;
  const Tag = onClick ? 'button' : 'div';
  return h(
    Tag,
    {
      type: onClick ? 'button' : undefined,
      className: cx('bc-inline-notice', `bc-inline-notice--${tone}`),
      style: themed(theme),
      onClick: onClick || undefined,
    },
    h('span', { className: 'bc-inline-notice__text' }, text)
  );
}
