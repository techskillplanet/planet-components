import { React, h, cx, themed, starPlanetTheme } from './_shared.js';
import { useEffect, useState } from 'react';

/** Simple carousel / swiper. */
export function TspSwiper({
  items = [],
  index = 0,
  autoplay = false,
  theme = starPlanetTheme,
  onChange,
}) {
  const list = Array.isArray(items) ? items : [];
  const [i, setI] = useState(index);
  useEffect(() => setI(index), [index]);
  useEffect(() => {
    if (!autoplay || list.length < 2) return undefined;
    const id = setInterval(() => {
      setI((prev) => {
        const next = (prev + 1) % list.length;
        onChange?.(next);
        return next;
      });
    }, 3200);
    return () => clearInterval(id);
  }, [autoplay, list.length, onChange]);
  const go = (next) => {
    const n = ((next % list.length) + list.length) % list.length;
    setI(n);
    onChange?.(n);
  };
  const current = list[i];
  return h(
    'div',
    {
      className: 'bc-swiper',
      style: themed(theme),
      role: 'region',
      'aria-roledescription': 'carousel',
    },
    h(
      'div',
      { className: 'bc-swiper__viewport' },
      h(
        'div',
        { className: 'bc-swiper__slide', key: i },
        typeof current === 'string' || typeof current === 'number'
          ? current
          : current?.content ?? current?.label ?? null
      )
    ),
    list.length > 1
      ? h(
          'div',
          { className: 'bc-swiper__dots' },
          list.map((_, di) =>
            h('button', {
              key: di,
              type: 'button',
              className: cx('bc-swiper__dot', di === i && 'bc-active'),
              'aria-label': `Slide ${di + 1}`,
              onClick: () => go(di),
            })
          )
        )
      : null,
    list.length > 1
      ? h(
          'div',
          { className: 'bc-swiper__nav' },
          h(
            'button',
            { type: 'button', className: 'bc-swiper__arrow', 'aria-label': 'Previous', onClick: () => go(i - 1) },
            '‹'
          ),
          h(
            'button',
            { type: 'button', className: 'bc-swiper__arrow', 'aria-label': 'Next', onClick: () => go(i + 1) },
            '›'
          )
        )
      : null
  );
}
