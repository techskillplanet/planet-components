import React from 'react';
import { Pressable, Text, View, StyleSheet } from 'react-native';
import { styles as shared, withTheme } from '../utils/shared';

function normalizeFiles(files) {
  if (!Array.isArray(files)) return [];
  return files.map((f, i) =>
    typeof f === 'string' ? { id: String(i), name: f } : { id: String(f.id ?? i), name: f.name || 'file' }
  );
}

/** Lightweight file list + add button (mock filenames when no real filesystem). */
export function TspUpload({
  files = [],
  multiple = true,
  disabled = false,
  accept: _accept,
  theme,
  onChange,
  onRemove,
}) {
  const t = withTheme(theme);
  const list = normalizeFiles(files);
  const emit = (next) => onChange?.(next);

  const onAdd = () => {
    if (disabled) return;
    const mock = {
      id: `${Date.now()}`,
      name: `file-${list.length + 1}.png`,
    };
    emit(multiple ? [...list, mock] : [mock]);
  };

  return (
    <View
      style={[local.root, disabled && shared.disabled]}
      accessibilityLabel="Upload"
    >
      <Pressable
        accessibilityRole="button"
        accessibilityLabel="Add file"
        accessibilityState={{ disabled }}
        disabled={disabled}
        onPress={onAdd}
        style={[
          local.trigger,
          {
            backgroundColor: t.brandSubtle,
            borderColor: t.brandPrimary,
          },
        ]}
      >
        <Text style={{ color: t.brandPrimary, fontWeight: '800' }}>添加</Text>
      </Pressable>

      {list.length ? (
        <View style={local.list}>
          {list.map((file) => (
            <View
              key={file.id}
              style={[
                local.item,
                { backgroundColor: t.surfaceRaised, borderColor: t.borderDefault },
              ]}
            >
              <Text style={[local.name, { color: t.textPrimary }]} numberOfLines={1}>
                {file.name}
              </Text>
              <Pressable
                accessibilityRole="button"
                accessibilityLabel={`Remove ${file.name}`}
                disabled={disabled}
                hitSlop={8}
                onPress={() => {
                  onRemove?.(file);
                  emit(list.filter((x) => x.id !== file.id));
                }}
                style={local.remove}
              >
                <Text style={{ color: t.textSecondary, fontSize: 18, fontWeight: '700' }}>×</Text>
              </Pressable>
            </View>
          ))}
        </View>
      ) : (
        <Text style={{ color: t.textTertiary, fontSize: 13, fontWeight: '600' }}>尚未选择文件</Text>
      )}
    </View>
  );
}

const local = StyleSheet.create({
  root: { width: '100%', gap: 10 },
  trigger: {
    alignSelf: 'flex-start',
    minHeight: 40,
    paddingHorizontal: 16,
    borderRadius: 999,
    borderWidth: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  list: { gap: 8 },
  item: {
    flexDirection: 'row',
    alignItems: 'center',
    minHeight: 44,
    borderWidth: 1,
    borderRadius: 14,
    paddingHorizontal: 12,
    gap: 8,
  },
  name: { flex: 1, fontWeight: '700', fontSize: 14 },
  remove: { width: 32, height: 32, alignItems: 'center', justifyContent: 'center' },
});
