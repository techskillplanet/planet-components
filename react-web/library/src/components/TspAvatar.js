import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** Avatar — initials or image. */
export function TspAvatar({
  text = '',
  src,
  size = 'md',
  variant = 'default',
  theme = starPlanetTheme,
}) {
  const dim = size === 'sm' ? 32 : size === 'lg' ? 56 : 40;
  const initial = String(text || '?').trim().slice(0, 2).toUpperCase();
  return h(
    'span',
    {
      className: cx('bc-avatar', `bc-avatar--${size}`, `bc-avatar--${variant}`),
      style: { ...themed(theme), width: dim, height: dim, fontSize: dim * 0.38 },
      role: 'img',
      'aria-label': text || 'Avatar',
    },
    src
      ? h('img', { className: 'bc-avatar__img', src, alt: text || '' })
      : h('span', { className: 'bc-avatar__text' }, initial)
  );
}
