import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspStickyFooter – Fixed bottom action area.
 *
 * Sticks to the viewport bottom for primary page actions (e.g., submit button).
 *
 * @param {Object} props
 * @param {React.ReactNode} [props.content] - Footer content (alternative to children).
 * @param {Object} [props.theme] - Theme object.
 * @param {React.ReactNode} [props.children] - Footer children (preferred over content).
 * @returns {React.ReactElement} A <footer> element.
 */

export function TspStickyFooter({ content, theme = starPlanetTheme, children }) {
  return h('footer', { className: 'bc-sticky-footer', style: themed(theme) }, children ?? content);
}
