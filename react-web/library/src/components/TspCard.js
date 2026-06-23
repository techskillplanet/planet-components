import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspCard – Content container card with rounded-island surface.
 *
 * Provides a raised white surface with optional selection state highlight.
 *
 * @param {Object} props
 * @param {'default'} [props.variant='default'] - Card variant.
 * @param {boolean} [props.selected=false] - Apply selected highlight background.
 * @param {boolean} [props.disabled=false] - Dim the card appearance.
 * @param {React.ReactNode} [props.content] - Card content (alternative to children).
 * @param {Object} [props.theme] - Theme object.
 * @param {React.ReactNode} [props.children] - Card children (preferred over content).
 * @returns {React.ReactElement} A styled <section> element.
 */

export function TspCard({ variant = 'default', selected = false, disabled = false, content, theme = starPlanetTheme, children }) {
  return h(
    'section',
    {
      className: cx('bc-card', `bc-card--${variant}`, selected && 'bc-selected', disabled && 'bc-disabled'),
      style: themed(theme)
    },
    children ?? content
  );
}
