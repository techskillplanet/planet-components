import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspKeyValueLabel – Key-value pair inline label.
 *
 * Displays a label and bold value side by side.
 *
 * @param {Object} props
 * @param {string} props.label - Left-side label text.
 * @param {string} props.value - Right-side bold value text.
 * @param {Object} [props.theme] - Theme object.
 * @returns {React.ReactElement} A <div> with label and value spans.
 */

export function TspKeyValueLabel({ label, value, theme = starPlanetTheme }) {
  return h('div', { className: 'bc-key-value', style: themed(theme) }, h('span', null, label), h('strong', null, value));
}
