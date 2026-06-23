import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspOptionSheet – Bottom sheet option picker.
 *
 * Displays a modal list of options for mobile-style selection.
 * Hidden when `visible` is false (returns null).
 *
 * @param {Object} props
 * @param {string} [props.title='请选择'] - Header title text.
 * @param {Array} [props.options=[]] - Array of option strings or objects.
 * @param {number} [props.selectedIndex=0] - Index of selected option.
 * @param {boolean} [props.visible=false] - Controls sheet visibility.
 * @param {Object} [props.theme] - Theme object.
 * @param {Function} [props.onSelect] - Selection callback (index, option).
 * @param {Function} [props.onCancel] - Dismiss callback.
 * @returns {React.ReactElement|null} Dialog element or null if hidden.
 */

export function TspOptionSheet({ title = '请选择', options = [], selectedIndex = 0, visible = false, theme = starPlanetTheme, onSelect, onCancel }) {
  if (!visible) return null;
  return h(
    'div',
    { className: 'bc-option-sheet', style: themed(theme), role: 'dialog', 'aria-modal': 'true' },
    h('button', { type: 'button', className: 'bc-option-sheet__mask', 'aria-label': 'Close', onClick: onCancel }),
    h(
      'section',
      { className: 'bc-option-sheet__panel' },
      h('div', { className: 'bc-option-sheet__header' },
        h('button', { type: 'button', onClick: onCancel }, '取消'),
        h('strong', null, title),
        h('span', null)
      ),
      h('div', { className: 'bc-option-sheet__options' },
        options.map((option, index) => h(
          'button',
          {
            key: `${optionText(option)}-${index}`,
            type: 'button',
            className: cx('bc-option-sheet__option', index === selectedIndex && 'bc-selected'),
            onClick: () => onSelect?.(index, option)
          },
          h('span', { className: 'bc-option-sheet__check' }, index === selectedIndex ? '✓' : ''),
          h('span', null, optionText(option))
        ))
      )
    )
  );
}
