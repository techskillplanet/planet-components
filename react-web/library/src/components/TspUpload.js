import { React, h, cx, themed, starPlanetTheme } from './_shared.js';
import { useRef } from 'react';

function normalizeFiles(files) {
  if (!Array.isArray(files)) return [];
  return files.map((f, i) =>
    typeof f === 'string' ? { id: String(i), name: f } : { id: String(f.id ?? i), name: f.name || 'file' }
  );
}

/** Lightweight file list + picker (no real upload backend). */
export function TspUpload({
  files = [],
  multiple = true,
  disabled = false,
  accept,
  theme = starPlanetTheme,
  onChange,
  onRemove,
}) {
  const inputRef = useRef(null);
  const list = normalizeFiles(files);
  const emit = (next) => onChange?.(next);

  const onPick = (e) => {
    const picked = Array.from(e.target.files || []).map((file, i) => ({
      id: `${Date.now()}-${i}`,
      name: file.name,
    }));
    emit(multiple ? [...list, ...picked] : picked.slice(0, 1));
    e.target.value = '';
  };

  return h(
    'div',
    {
      className: cx('bc-upload', disabled && 'bc-disabled'),
      style: themed(theme),
    },
    h(
      'button',
      {
        type: 'button',
        className: 'bc-upload__trigger',
        disabled,
        onClick: () => inputRef.current?.click(),
      },
      '选择文件'
    ),
    h('input', {
      ref: inputRef,
      type: 'file',
      className: 'bc-upload__input',
      multiple,
      accept,
      disabled,
      onChange: onPick,
      tabIndex: -1,
      'aria-hidden': true,
    }),
    list.length
      ? h(
          'ul',
          { className: 'bc-upload__list' },
          list.map((file) =>
            h(
              'li',
              { key: file.id, className: 'bc-upload__item' },
              h('span', { className: 'bc-upload__name' }, file.name),
              h(
                'button',
                {
                  type: 'button',
                  className: 'bc-upload__remove',
                  disabled,
                  'aria-label': `Remove ${file.name}`,
                  onClick: () => {
                    onRemove?.(file);
                    emit(list.filter((x) => x.id !== file.id));
                  },
                },
                '×'
              )
            )
          )
        )
      : h('div', { className: 'bc-upload__empty' }, '尚未选择文件')
  );
}
