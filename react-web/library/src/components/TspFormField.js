import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/**
 * Form field shell: label + control slot + helper/error.
 * @param {Object} props
 * @param {string} [props.label]
 * @param {boolean} [props.required]
 * @param {string} [props.helper]
 * @param {string} [props.error]
 * @param {Object} [props.theme]
 * @param {React.ReactNode} props.children
 */
export function TspFormField({
  label = '',
  required = false,
  helper = '',
  error = '',
  theme = starPlanetTheme,
  children,
}) {
  const showError = Boolean(error && String(error).trim());
  const hint = showError ? error : helper;
  return h(
    'div',
    {
      className: cx('bc-form-field', showError && 'bc-form-field--error'),
      style: themed(theme),
    },
    label
      ? h(
          'div',
          { className: 'bc-form-field__label' },
          h('span', null, label),
          required ? h('span', { className: 'bc-form-field__required', 'aria-hidden': true }, '*') : null
        )
      : null,
    h('div', { className: 'bc-form-field__control' }, children),
    hint
      ? h(
          'div',
          {
            className: cx('bc-form-field__hint', showError && 'bc-form-field__hint--error'),
            role: showError ? 'alert' : undefined,
          },
          hint
        )
      : null
  );
}
