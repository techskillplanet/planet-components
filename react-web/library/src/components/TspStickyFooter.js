import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspStickyFooter({ content, theme = starPlanetTheme, children }) {
  return h('footer', { className: 'bc-sticky-footer', style: themed(theme) }, children ?? content);
}
