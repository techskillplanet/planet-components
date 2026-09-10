import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspProgress – Horizontal progress bar.
 *
 * Progress value is clamped between 0 and 100.
 *
 * @param {Object} props
 * @param {number} [props.progress=0] - Progress value (0-100).
 * @param {'primary'|'success'|'warning'|'danger'} [props.variant='primary'] - Color variant.
 * @param {Object} [props.theme] - Theme object.
 * @returns {React.ReactElement} A progressbar element.
 */

export function TspProgress({ progress = 0, variant = 'primary', theme = starPlanetTheme, label = 'Progress' }) {
  const percent = clamp(progress, 0, 100);
  return h('div', {
    className: cx('bc-progress', `bc-progress--${variant}`),
    style: themed(theme),
    role: 'progressbar',
    'aria-label': label,
    'aria-valuenow': percent,
    'aria-valuemin': 0,
    'aria-valuemax': 100
  }, h('span', { style: { width: `${percent}%` } }));
}
