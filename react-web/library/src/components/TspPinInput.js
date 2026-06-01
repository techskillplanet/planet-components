import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

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
