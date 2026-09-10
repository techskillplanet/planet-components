import React, { useMemo, useState } from 'react';
import { Modal, Pressable, Text, View, StyleSheet, ScrollView } from 'react-native';
import { styles as shared, withTheme } from '../utils/shared';

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

/** Multi-level cascader — trigger + Modal multi-column panel. */
export function TspCascader({
  options = [],
  value = [],
  placeholder = '请选择',
  disabled = false,
  theme,
  onChange,
}) {
  const t = withTheme(theme);
  const opts = Array.isArray(options) ? options : [];
  const path = Array.isArray(value) ? value.map(String) : [];
  const [open, setOpen] = useState(false);
  const [draft, setDraft] = useState(path);

  const display = useMemo(() => {
    const labels = findPathLabels(opts, path);
    return labels.length ? labels.join(' / ') : '';
  }, [opts, path]);

  const columns = [];
  let level = opts;
  const walk = open ? draft : path;
  for (let i = 0; i <= walk.length; i += 1) {
    if (!Array.isArray(level) || level.length === 0) break;
    columns.push(level);
    const cur = walk[i];
    if (cur == null || cur === '') break;
    const hit = level.find((o, idx) => optId(o, idx) === String(cur));
    level = hit?.children || [];
  }

  const pick = (colIndex, opt, idx) => {
    const id = optId(opt, idx);
    const next = [...draft.slice(0, colIndex), id];
    setDraft(next);
    if (!opt.children?.length) {
      onChange?.(next, findPathLabels(opts, next));
      setOpen(false);
    }
  };

  return (
    <View style={[local.wrap, disabled && shared.disabled]}>
      <Pressable
        accessibilityRole="button"
        accessibilityLabel={display || placeholder}
        accessibilityState={{ disabled, expanded: open }}
        disabled={disabled}
        onPress={() => {
          if (disabled) return;
          setDraft(path);
          setOpen(true);
        }}
        style={[
          local.trigger,
          {
            backgroundColor: t.surfaceRaised,
            borderColor: t.borderDefault,
          },
        ]}
      >
        <Text
          style={[
            local.value,
            { color: display ? t.textPrimary : t.textTertiary },
            !display && { fontWeight: '600' },
          ]}
          numberOfLines={1}
        >
          {display || placeholder}
        </Text>
        <Text style={{ color: t.textTertiary }}>▾</Text>
      </Pressable>

      <Modal transparent animationType="fade" visible={open} onRequestClose={() => setOpen(false)}>
        <View style={local.maskRoot}>
          <Pressable
            accessibilityLabel="Close"
            accessibilityRole="button"
            style={local.maskFill}
            onPress={() => setOpen(false)}
          />
          <View
            accessibilityRole="list"
            accessibilityLabel="Cascader"
            style={[
              local.panel,
              { backgroundColor: t.surfaceRaised, borderColor: t.borderDefault },
            ]}
          >
            <View style={local.panelHeader}>
              <Text style={[shared.strong, { color: t.textPrimary, flex: 1 }]}>请选择</Text>
              <Pressable
                accessibilityRole="button"
                accessibilityLabel="Close"
                onPress={() => setOpen(false)}
                hitSlop={8}
                style={local.closeBtn}
              >
                <Text style={{ color: t.textSecondary, fontSize: 22, fontWeight: '700' }}>×</Text>
              </Pressable>
            </View>
            <ScrollView horizontal showsHorizontalScrollIndicator={false}>
              <View style={local.cols}>
                {columns.map((col, ci) => (
                  <ScrollView
                    key={ci}
                    style={[local.col, { borderRightColor: t.borderDefault }]}
                    showsVerticalScrollIndicator={false}
                  >
                    {col.map((opt, oi) => {
                      const id = optId(opt, oi);
                      const active = draft[ci] === id;
                      return (
                        <Pressable
                          key={id}
                          accessibilityRole="button"
                          accessibilityState={{ selected: active }}
                          onPress={() => pick(ci, opt, oi)}
                          style={[
                            local.option,
                            active && { backgroundColor: t.brandSubtle },
                          ]}
                        >
                          <Text
                            style={{
                              color: active ? t.brandPrimary : t.textPrimary,
                              fontWeight: '700',
                            }}
                            numberOfLines={1}
                          >
                            {optLabel(opt)}
                          </Text>
                        </Pressable>
                      );
                    })}
                  </ScrollView>
                ))}
              </View>
            </ScrollView>
          </View>
        </View>
      </Modal>
    </View>
  );
}

const local = StyleSheet.create({
  wrap: { width: '100%' },
  trigger: {
    minHeight: 48,
    borderWidth: 1,
    borderRadius: 16,
    paddingHorizontal: 14,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    gap: 8,
  },
  value: { flex: 1, fontSize: 15, fontWeight: '700' },
  maskRoot: { flex: 1, backgroundColor: 'rgba(23,58,98,0.32)', justifyContent: 'flex-end' },
  maskFill: { ...StyleSheet.absoluteFillObject },
  panel: {
    borderTopLeftRadius: 24,
    borderTopRightRadius: 24,
    borderWidth: 1,
    maxHeight: '62%',
    zIndex: 1,
  },
  panelHeader: {
    minHeight: 52,
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 16,
    gap: 8,
  },
  closeBtn: { width: 36, height: 36, alignItems: 'center', justifyContent: 'center' },
  cols: { flexDirection: 'row', minHeight: 240, paddingBottom: 12 },
  col: {
    width: 140,
    borderRightWidth: StyleSheet.hairlineWidth,
    maxHeight: 280,
  },
  option: {
    minHeight: 44,
    paddingHorizontal: 12,
    justifyContent: 'center',
    marginHorizontal: 6,
    borderRadius: 12,
  },
});
