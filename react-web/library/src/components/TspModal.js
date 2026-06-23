import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';
import { TspButton } from './TspButton.js';

/**
 * TspModal – Confirmation dialog.
 *
 * Full-screen overlay with centered panel containing title, message,
 * and confirm/cancel action buttons.
 *
 * @param {Object} props
 * @param {string} [props.title] - Dialog title.
 * @param {string} [props.message] - Dialog body message.
 * @param {string} [props.confirmText='OK'] - Confirm button text.
 * @param {string} [props.cancelText='Cancel'] - Cancel button text.
 * @param {Object} [props.theme] - Theme object.
 * @param {Function} [props.onConfirm] - Confirm button handler.
 * @param {Function} [props.onCancel] - Cancel button handler.
 * @returns {React.ReactElement} A dialog overlay element.
 */

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
