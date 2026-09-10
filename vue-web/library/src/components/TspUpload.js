import { defineComponent, ref } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

function normalizeFiles(files) {
  if (!Array.isArray(files)) return [];
  return files.map((f, i) =>
    typeof f === 'string' ? { id: String(i), name: f } : { id: String(f.id ?? i), name: f.name || 'file' }
  );
}

/** Lightweight file list + picker (no real upload backend). */
export const TspUpload = defineComponent({
  name: 'TspUpload',
  props: {
    files: { type: Array, default: () => [] },
    multiple: { type: Boolean, default: true },
    disabled: { type: Boolean, default: false },
    accept: { type: String, default: undefined },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change', 'remove'],
  setup(props, { emit }) {
    const inputRef = ref(null);

    return () => {
      const list = normalizeFiles(props.files);
      const emitChange = (next) => emit('change', next);

      return h(
        'div',
        {
          class: cx('bc-upload', props.disabled && 'bc-disabled'),
          style: themed(props.theme)
        },
        [
          h(
            'button',
            {
              type: 'button',
              class: 'bc-upload__trigger',
              disabled: props.disabled,
              onClick: () => inputRef.value?.click()
            },
            '选择文件'
          ),
          h('input', {
            ref: inputRef,
            type: 'file',
            class: 'bc-upload__input',
            multiple: props.multiple,
            accept: props.accept,
            disabled: props.disabled,
            tabindex: -1,
            'aria-hidden': true,
            onChange: (e) => {
              const picked = Array.from(e.target.files || []).map((file, i) => ({
                id: `${Date.now()}-${i}`,
                name: file.name
              }));
              emitChange(props.multiple ? [...list, ...picked] : picked.slice(0, 1));
              e.target.value = '';
            }
          }),
          list.length
            ? h(
                'ul',
                { class: 'bc-upload__list' },
                list.map((file) =>
                  h('li', { key: file.id, class: 'bc-upload__item' }, [
                    h('span', { class: 'bc-upload__name' }, file.name),
                    h(
                      'button',
                      {
                        type: 'button',
                        class: 'bc-upload__remove',
                        disabled: props.disabled,
                        'aria-label': `Remove ${file.name}`,
                        onClick: () => {
                          emit('remove', file);
                          emitChange(list.filter((x) => x.id !== file.id));
                        }
                      },
                      '×'
                    )
                  ])
                )
              )
            : h('div', { class: 'bc-upload__empty' }, '尚未选择文件')
        ]
      );
    };
  }
});
