import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspTopBar – Top navigation bar.
 *
 * Shows title with optional back button, trailing action, and custom background.
 *
 * @param {Object} props
 * @param {string} props.title - Navigation bar title.
 * @param {boolean} [props.showBack=false] - Show the back arrow button.
 * @param {string|React.ReactNode} [props.trailing] - Right-side action (e.g. 完成).
 * @param {string} [props.backgroundColor] - Override background color.
 * @param {Object} [props.theme] - Theme object.
 * @param {Function} [props.onBack] - Back button click handler.
 * @returns {React.ReactElement} A <header> element.
 */

export function TspTopBar({ title, showBack = false, trailing, backgroundColor, theme = starPlanetTheme, onBack }) {
  return h(
    'header',
    { className: 'bc-top-bar', style: themed(theme, backgroundColor ? { backgroundColor } : undefined) },
    showBack && h('button', { type: 'button', className: 'bc-top-bar__back', onClick: onBack, 'aria-label': 'Back' }, '‹'),
    h('div', { className: 'bc-top-bar__title' }, title),
    trailing != null && trailing !== false && h('div', { className: 'bc-top-bar__trailing' }, trailing)
  );
}
