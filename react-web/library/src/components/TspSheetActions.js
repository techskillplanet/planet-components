import { React, h, themed, starPlanetTheme } from './_shared.js';
import { TspButton } from './TspButton.js';

/** Dual footer: cancel | confirm. */
export function TspSheetActions({
  cancelText = '取消',
  confirmText = '确定',
  confirmEnabled = true,
  confirmLoading = false,
  theme = starPlanetTheme,
  onCancel,
  onConfirm,
}) {
  return h(
    'div',
    { className: 'bc-sheet-actions', style: themed(theme) },
    h(TspButton, {
      text: cancelText,
      variant: 'default',
      fullWidth: true,
      disabled: confirmLoading,
      theme,
      onTap: onCancel,
    }),
    h(TspButton, {
      text: confirmText,
      variant: 'primary',
      fullWidth: true,
      disabled: !confirmEnabled,
      loading: confirmLoading,
      theme,
      onTap: onConfirm,
    })
  );
}
