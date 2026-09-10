import { defineComponent, ref } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

function nodeId(node, index) {
  return String(node?.id ?? node?.key ?? index);
}

function nodeLabel(node) {
  return node?.label ?? node?.title ?? node?.text ?? String(node?.id ?? '');
}

function renderNode(node, depth, selectedId, expanded, toggle, onSelect, index) {
  const id = nodeId(node, index);
  const kids = Array.isArray(node?.children) ? node.children : [];
  const open = expanded.has(id);
  return h('div', { class: 'bc-tree__node', key: id }, [
    h(
      'div',
      {
        class: cx('bc-tree__row', selectedId === id && 'bc-selected'),
        style: { paddingLeft: `${8 + depth * 16}px` }
      },
      [
        kids.length
          ? h(
              'button',
              {
                type: 'button',
                class: cx('bc-tree__twist', open && 'bc-open'),
                'aria-label': open ? 'Collapse' : 'Expand',
                onClick: () => toggle(id)
              },
              open ? '▾' : '▸'
            )
          : h('span', { class: 'bc-tree__twist bc-tree__twist--leaf' }),
        h(
          'button',
          {
            type: 'button',
            class: 'bc-tree__label',
            onClick: () => onSelect?.(id, node)
          },
          nodeLabel(node)
        )
      ]
    ),
    open
      ? kids.map((child, i) =>
          renderNode(child, depth + 1, selectedId, expanded, toggle, onSelect, i)
        )
      : null
  ]);
}

/** Nested tree selector. */
export const TspTree = defineComponent({
  name: 'TspTree',
  props: {
    items: { type: Array, default: () => [] },
    selectedId: { type: [String, Number], default: undefined },
    expandedIds: { type: Array, default: undefined },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['select', 'expand'],
  setup(props, { emit }) {
    const localExpanded = ref(new Set(props.expandedIds || []));

    return () => {
      const list = Array.isArray(props.items) ? props.items : [];
      const expanded =
        props.expandedIds != null ? new Set(props.expandedIds) : localExpanded.value;

      const toggle = (id) => {
        const next = new Set(expanded);
        if (next.has(id)) next.delete(id);
        else next.add(id);
        if (props.expandedIds == null) localExpanded.value = next;
        emit('expand', Array.from(next));
      };

      return h(
        'div',
        { class: 'bc-tree', style: themed(props.theme), role: 'tree' },
        list.map((node, i) =>
          renderNode(
            node,
            0,
            props.selectedId != null ? String(props.selectedId) : props.selectedId,
            expanded,
            toggle,
            (id, n) => emit('select', id, n),
            i
          )
        )
      );
    };
  }
});
