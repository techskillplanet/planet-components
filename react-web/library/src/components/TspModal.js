import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';
import { TspButton } from './TspButton.js';

export function TspModal({ title, message, confirmText = 'OK', cancelText = 'Cancel', theme = starPlanetTheme, onConfirm, onCancel }) {
  return h(
    'div',
    { className: 'bc-modal', style: themed(theme), role: 'dialog', 'aria-modal': 'true' },
    h('section', { className: 'bc-modal__panel' },
      title && h('h3', null, title),
      message && h('p', null, message),
      h('div', { className: 'bc-modal__actions' },
        h(TspButton, { text: cancelText, variant: 'default', theme, onTap: onCancel }),
        h(TspButton, { text: confirmText, variant: 'primary', theme, onTap: onConfirm })
      )
    )
  );
}
