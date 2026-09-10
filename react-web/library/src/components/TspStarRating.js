import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** Interactive star rating. */
export function TspStarRating({
  value = 0,
  max = 5,
  disabled = false,
  variant = 'default',
  theme = starPlanetTheme,
  onChange,
}) {
  const count = Math.max(1, Math.min(10, Number(max) || 5));
  const readonly = variant === 'readonly' || disabled;
  const stars = [];
  for (let i = 1; i <= count; i += 1) {
    const filled = i <= value;
    stars.push(
      h(
        'button',
        {
          key: i,
          type: 'button',
          className: cx('bc-star-rating__star', filled && 'bc-filled'),
          disabled: readonly,
          'aria-label': `${i}`,
          onClick: readonly ? undefined : () => onChange?.(i),
        },
        '★'
      )
    );
  }
  return h(
    'div',
    {
      className: cx(
        'bc-star-rating',
        `bc-star-rating--${variant}`,
        disabled && 'bc-disabled'
      ),
      style: themed(theme),
      role: 'slider',
      'aria-valuemin': 0,
      'aria-valuemax': count,
      'aria-valuenow': value,
    },
    stars
  );
}
