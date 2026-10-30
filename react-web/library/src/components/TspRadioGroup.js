import { React, h, themed, starPlanetTheme } from './_shared.js';
import { TspRadio } from './TspRadio.js';

/** Radio group by option id. */
export function TspRadioGroup({
  options = [],
  selectedId = null,
  theme = starPlanetTheme,
  onChange,
}) {
  return h(
    'div',
    { className: 'bc-radio-group', style: themed(theme), role: 'radiogroup' },
    options.map((opt) =>
      h(TspRadio, {
        key: opt.id,
        text: opt.label,
        checked: opt.id === selectedId,
        disabled: opt.enabled === false,
        theme,
        onChange: (on) => {
          if (on) onChange?.(opt.id);
        },
      })
    )
  );
}
