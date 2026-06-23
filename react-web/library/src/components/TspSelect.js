import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';
import { TspOptionSheet } from './TspOptionSheet.js';

const { useState } = React;

/**
 * TspSelect – dropdown selector that opens TspOptionSheet.
 * @param {Object} props
 * @param {Array} props.options - List of options (strings or {title/text/label/value} objects).
 * @param {number} [props.selectedIndex=0] - Currently selected option index.
 * @param {boolean} [props.disabled=false] - Disable interaction.
 * @param {Object} [props.theme] - Theme object for CSS variable injection.
 * @param {Function} [props.onSelect] - Callback (index, option) => void.
 */
export function TspSelect({ options = [], selectedIndex = 0, disabled = false, theme = starPlanetTheme, onSelect }) {
  const [open, setOpen] = useState(false);
  const selected = options[selectedIndex];
  return h(
    React.Fragment,
    null,
    h(
      'button',
      {
        type: 'button',
        className: cx('bc-select', disabled && 'bc-disabled'),
        style: themed(theme),
        disabled,
        onClick: disabled ? undefined : () => setOpen(true)
      },
      h('span', null, optionText(selected) ?? ''),
      h('span', { className: 'bc-select__chevron' }, '⌄')
    ),
    h(TspOptionSheet, {
      title: '请选择',
      options,
      selectedIndex,
      visible: open,
      theme,
      onCancel: () => setOpen(false),
      onSelect: (index, option) => {
        setOpen(false);
        onSelect?.(index, option);
      }
    })
  );
}
