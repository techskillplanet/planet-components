import React, { useState } from 'react';
import { Pressable, Text, View, StyleSheet } from 'react-native';
import { withTheme } from '../utils/shared';

function nodeId(node, index) {
  return String(node?.id ?? node?.key ?? index);
}

function nodeLabel(node) {
  return node?.label ?? node?.title ?? node?.text ?? String(node?.id ?? '');
}

function TreeNode({ node, depth, selectedId, expanded, toggle, onSelect, index, t }) {
  const id = nodeId(node, index);
  const kids = Array.isArray(node?.children) ? node.children : [];
  const open = expanded.has(id);
  const selected = selectedId === id;

  return (
    <View>
      <View style={[local.row, { paddingLeft: 8 + depth * 16 }]}>
        {kids.length ? (
          <Pressable
            accessibilityRole="button"
            accessibilityLabel={open ? 'Collapse' : 'Expand'}
            onPress={() => toggle(id)}
            hitSlop={6}
            style={local.twist}
          >
            <Text style={{ color: t.textSecondary, fontWeight: '800' }}>{open ? '▾' : '▸'}</Text>
          </Pressable>
        ) : (
          <View style={local.twistLeaf} />
        )}
        <Pressable
          accessibilityRole="button"
          accessibilityState={{ selected }}
          accessibilityLabel={nodeLabel(node)}
          onPress={() => onSelect?.(id, node)}
          style={[
            local.labelBtn,
            selected && { backgroundColor: t.brandSubtle },
          ]}
        >
          <Text
            style={{
              color: selected ? t.brandPrimary : t.textPrimary,
              fontWeight: '700',
            }}
            numberOfLines={1}
          >
            {nodeLabel(node)}
          </Text>
        </Pressable>
      </View>
      {open
        ? kids.map((child, i) => (
            <TreeNode
              key={nodeId(child, i)}
              node={child}
              depth={depth + 1}
              selectedId={selectedId}
              expanded={expanded}
              toggle={toggle}
              onSelect={onSelect}
              index={i}
              t={t}
            />
          ))
        : null}
    </View>
  );
}

/** Nested tree selector with expand / collapse. */
export function TspTree({
  items = [],
  selectedId,
  expandedIds,
  theme,
  onSelect,
  onExpand,
}) {
  const t = withTheme(theme);
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

  return (
    <View
      style={[local.root, { borderColor: t.borderDefault, backgroundColor: t.surfaceRaised }]}
      accessibilityRole="none"
      accessibilityLabel="Tree"
    >
      {list.map((node, i) => (
        <TreeNode
          key={nodeId(node, i)}
          node={node}
          depth={0}
          selectedId={selectedId}
          expanded={expanded}
          toggle={toggle}
          onSelect={onSelect}
          index={i}
          t={t}
        />
      ))}
    </View>
  );
}

const local = StyleSheet.create({
  root: {
    width: '100%',
    borderWidth: 1,
    borderRadius: 16,
    paddingVertical: 6,
    overflow: 'hidden',
  },
  row: {
    flexDirection: 'row',
    alignItems: 'center',
    minHeight: 40,
    paddingRight: 8,
    gap: 2,
  },
  twist: {
    width: 28,
    height: 28,
    alignItems: 'center',
    justifyContent: 'center',
  },
  twistLeaf: { width: 28 },
  labelBtn: {
    flex: 1,
    minHeight: 32,
    borderRadius: 10,
    paddingHorizontal: 8,
    justifyContent: 'center',
  },
});
