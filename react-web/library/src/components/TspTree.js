import { React, h, cx, themed, starPlanetTheme } from './_shared.js';
import { useState } from 'react';

function nodeId(node, index) {
  return String(node?.id ?? node?.key ?? index);
}

function nodeLabel(node) {
  return node?.label ?? node?.title ?? node?.text ?? String(node?.id ?? '');
}

function TreeNode({ node, depth, selectedId, expanded, toggle, onSelect, index }) {
  const id = nodeId(node, index);
  const kids = Array.isArray(node?.children) ? node.children : [];
  const open = expanded.has(id);
  return h(
    'div',
    { className: 'bc-tree__node', key: id },
    h(
      'div',
      {
        className: cx('bc-tree__row', selectedId === id && 'bc-selected'),
        style: { paddingLeft: 8 + depth * 16 },
      },
      kids.length
        ? h(
            'button',
            {
              type: 'button',
              className: cx('bc-tree__twist', open && 'bc-open'),
              'aria-label': open ? 'Collapse' : 'Expand',
              onClick: () => toggle(id),
            },
            open ? '▾' : '▸'
          )
        : h('span', { className: 'bc-tree__twist bc-tree__twist--leaf' }),
      h(
        'button',
        {
          type: 'button',
          className: 'bc-tree__label',
          onClick: () => onSelect?.(id, node),
        },
        nodeLabel(node)
      )
    ),
    open
      ? kids.map((child, i) =>
          h(TreeNode, {
            key: nodeId(child, i),
            node: child,
            depth: depth + 1,
            selectedId,
            expanded,
            toggle,
            onSelect,
            index: i,
          })
        )
      : null
  );
}

/** Nested tree selector. */
export function TspTree({
  items = [],
  selectedId,
  expandedIds,
  theme = starPlanetTheme,
  onSelect,
  onExpand,
}) {
  const list = Array.isArray(items) ? items : [];
  const [localExpanded, setLocalExpanded] = useState(() => new Set(expandedIds || []));
  const expanded = expandedIds != null ? new Set(expandedIds) : localExpanded;

  const toggle = (id) => {
    const next = new Set(expanded);
    if (next.has(id)) next.delete(id);
    else next.add(id);
    if (expandedIds == null) setLocalExpanded(next);
    onExpand?.(Array.from(next));
  };

  return h(
    'div',
    { className: 'bc-tree', style: themed(theme), role: 'tree' },
    list.map((node, i) =>
      h(TreeNode, {
        key: nodeId(node, i),
        node,
        depth: 0,
        selectedId,
        expanded,
        toggle,
        onSelect,
        index: i,
      })
    )
  );
}
