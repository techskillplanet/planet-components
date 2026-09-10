import { React, h, cx, themed, starPlanetTheme } from './_shared.js';
import { PLANET_ICONS } from '../icons/planetIcons.js';

/**
 * TspIcon – named stroke icon from the Planet icon catalog.
 *
 * @param {Object} props
 * @param {keyof typeof PLANET_ICONS|string} props.name - Icon name (back|check|close|chevron|warning).
 * @param {number|string} [props.size=20]
 * @param {string} [props.label] - Accessible name; decorative if omitted.
 * @param {Object} [props.theme]
 * @param {string} [props.color] - CSS color; defaults to theme textPrimary.
 */
export function TspIcon({
  name = 'check',
  size = 20,
  label,
  theme = starPlanetTheme,
  color,
  className,
}) {
  const d = PLANET_ICONS[name] || PLANET_ICONS.check;
  const decorative = !label;
  return h(
    'span',
    {
      className: cx('bc-icon', className),
      style: {
        ...themed(theme),
        display: 'inline-flex',
        width: size,
        height: size,
        color: color || theme.textPrimary,
      },
      role: decorative ? undefined : 'img',
      'aria-label': label || undefined,
      'aria-hidden': decorative ? 'true' : undefined,
    },
    h(
      'svg',
      {
        xmlns: 'http://www.w3.org/2000/svg',
        viewBox: '0 0 24 24',
        width: size,
        height: size,
        fill: 'none',
        stroke: 'currentColor',
        strokeWidth: 2,
        strokeLinecap: 'round',
        strokeLinejoin: 'round',
        focusable: 'false',
      },
      // warning uses multiple path segments separated by spaces in catalog — split on " M"
      String(d)
        .split(/(?=M)/)
        .filter(Boolean)
        .map((segment, i) => h('path', { key: i, d: segment.trim() }))
    )
  );
}
