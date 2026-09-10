import { computed, defineComponent, onBeforeUnmount, onMounted, ref, watch } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

function optId(opt, i) {
  return String(opt?.value ?? opt?.id ?? opt?.key ?? i);
}

function optLabel(opt) {
  return opt?.label ?? opt?.title ?? opt?.text ?? String(opt?.value ?? opt?.id ?? '');
}

function findPathLabels(options, valuePath) {
  const labels = [];
  let level = options;
  for (const v of valuePath) {
    const hit = (level || []).find((o, i) => optId(o, i) === String(v));
    if (!hit) break;
    labels.push(optLabel(hit));
    level = hit.children || [];
  }
  return labels;
}

function buildColumns(options, walk) {
  const columns = [];
  let level = options;
  for (let i = 0; i <= walk.length; i += 1) {
    if (!Array.isArray(level) || level.length === 0) break;
    columns.push(level);
    const cur = walk[i];
    if (cur == null || cur === '') break;
    const hit = level.find((o, idx) => optId(o, idx) === String(cur));
    level = hit?.children || [];
  }
  return columns;
}

/** Multi-level cascader picker. */
export const TspCascader = defineComponent({
  name: 'TspCascader',
  props: {
    options: { type: Array, default: () => [] },
    value: { type: Array, default: () => [] },
    placeholder: { type: String, default: '请选择' },
    disabled: { type: Boolean, default: false },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change'],
  setup(props, { emit }) {
    const open = ref(false);
    const draft = ref([]);
    const rootRef = ref(null);

    const path = computed(() =>
      Array.isArray(props.value) ? props.value.map(String) : []
    );

    watch(
      path,
      (v) => {
        if (!open.value) draft.value = [...v];
      },
      { immediate: true }
    );

    const onDoc = (event) => {
      if (!open.value) return;
      if (!rootRef.value?.contains(event.target)) open.value = false;
    };

    onMounted(() => document.addEventListener('mousedown', onDoc));
    onBeforeUnmount(() => document.removeEventListener('mousedown', onDoc));

    return () => {
      const opts = Array.isArray(props.options) ? props.options : [];
      const labels = findPathLabels(opts, path.value);
      const display = labels.length ? labels.join(' / ') : '';
      const columns = buildColumns(opts, open.value ? draft.value : path.value);

      const pick = (colIndex, opt, idx) => {
        const id = optId(opt, idx);
        const next = [...draft.value.slice(0, colIndex), id];
        draft.value = next;
        if (!opt.children?.length) {
          emit('change', next, findPathLabels(opts, next));
          open.value = false;
        }
      };

      return h(
        'div',
        {
          ref: rootRef,
          class: cx('bc-cascader', open.value && 'bc-open', props.disabled && 'bc-disabled'),
          style: themed(props.theme)
        },
        [
          h(
            'button',
            {
              type: 'button',
              class: 'bc-cascader__trigger',
              disabled: props.disabled,
              'aria-expanded': open.value,
              'aria-haspopup': 'listbox',
              onClick: () => {
                if (props.disabled) return;
                draft.value = [...path.value];
                open.value = !open.value;
              }
            },
            [
              h(
                'span',
                { class: cx('bc-cascader__value', !display && 'bc-placeholder') },
                display || props.placeholder
              ),
              h('span', { class: 'bc-cascader__caret', 'aria-hidden': true }, open.value ? '▴' : '▾')
            ]
          ),
          open.value
            ? h(
                'div',
                { class: 'bc-cascader__panel', role: 'listbox' },
                columns.map((col, ci) =>
                  h(
                    'div',
                    { key: `col-${ci}`, class: 'bc-cascader__col' },
                    col.map((opt, oi) => {
                      const id = optId(opt, oi);
                      const active = draft.value[ci] === id;
                      const hasChildren = Array.isArray(opt.children) && opt.children.length > 0;
                      return h(
                        'button',
                        {
                          type: 'button',
                          key: id,
                          class: cx(
                            'bc-cascader__option',
                            active && 'bc-cascader__option--active',
                            hasChildren && 'bc-cascader__option--branch'
                          ),
                          'aria-selected': active,
                          onClick: () => pick(ci, opt, oi)
                        },
                        [
                          h('span', { class: 'bc-cascader__option-label' }, optLabel(opt)),
                          hasChildren
                            ? h('span', { class: 'bc-cascader__option-arrow', 'aria-hidden': true }, '›')
                            : null
                        ]
                      );
                    })
                  )
                )
              )
            : null
        ]
      );
    };
  }
});
