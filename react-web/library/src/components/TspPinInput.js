import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspPinInput – PIN/verification code input.
 *
 * Renders individual cells for 4-6 digit codes with optional secure masking.
 *
 * @param {Object} props
 * @param {string} [props.value=''] - Current input value.
 * @param {number} [props.cellCount=4] - Number of cells (clamped 4-6).
 * @param {boolean} [props.secure=false] - Mask digits with bullets.
 * @param {Object} [props.theme] - Theme object.
 * @param {Function} [props.onComplete] - Called when all cells are filled.
 * @param {Function} [props.onChange] - Called on each input change.
 * @returns {React.ReactElement} A <label> wrapping hidden input and visual cells.
 */

export function TspPinInput({ value = '', cellCount = 4, secure = false, theme = starPlanetTheme, onComplete, onChange }) {
  const count = clamp(cellCount, 4, 6);
  const chars = value.slice(0, count).split('');
  return h(
    'label',
    { className: 'bc-pin-input', style: themed(theme) },
    h('input', {
      value,
      maxLength: count,
      inputMode: 'numeric',
      onInput: (event) => {
        const next = event.target.value.slice(0, count);
        onChange?.(next);
        if (next.length === count) onComplete?.(next);
      }
    }),
    Array.from({ length: count }, (_, index) => h('span', { key: index, className: 'bc-pin-input__cell' }, secure && chars[index] ? '•' : chars[index] ?? ''))
  );
}
