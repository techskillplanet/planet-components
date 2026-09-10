import React, { useState } from 'react';
import { Modal, Pressable, Text, TextInput, View, StyleSheet, ScrollView } from 'react-native';
import { styles as shared, withTheme } from '../utils/shared';

function pad2(n) {
  return String(n).padStart(2, '0');
}

function parseHhMm(value) {
  const m = String(value || '').match(/^(\d{1,2}):(\d{2})$/);
  if (!m) return null;
  const h = Number(m[1]);
  const min = Number(m[2]);
  if (h < 0 || h > 23 || min < 0 || min > 59) return null;
  return { h, min };
}

function formatHhMm(h, min) {
  return `${pad2(h)}:${pad2(min)}`;
}

const HOURS = Array.from({ length: 24 }, (_, i) => i);
const MINS = Array.from({ length: 12 }, (_, i) => i * 5);

/** HH:mm time field — text input plus optional simple picker sheet. */
export function TspTimePicker({
  value = '',
  placeholder = 'HH:mm',
  disabled = false,
  theme,
  onChange,
}) {
  const t = withTheme(theme);
  const [open, setOpen] = useState(false);
  const parsed = parseHhMm(value) || { h: 9, min: 0 };
  const [draftH, setDraftH] = useState(parsed.h);
  const [draftM, setDraftM] = useState(parsed.min);

  const openSheet = () => {
    if (disabled) return;
    const cur = parseHhMm(value) || { h: 9, min: 0 };
    setDraftH(cur.h);
    setDraftM((Math.round(cur.min / 5) * 5) % 60);
    setOpen(true);
  };

  return (
    <View style={local.wrap}>
      <View
        style={[
          local.fieldRow,
          {
            backgroundColor: t.surfaceRaised,
            borderColor: t.borderDefault,
          },
          disabled && shared.disabled,
        ]}
      >
        <TextInput
          editable={!disabled}
          value={value || ''}
          placeholder={placeholder}
          placeholderTextColor={t.textTertiary}
          keyboardType="numbers-and-punctuation"
          autoCapitalize="none"
          autoCorrect={false}
          onChangeText={(next) => onChange?.(next)}
          style={[local.input, { color: t.textPrimary }]}
          accessibilityLabel={placeholder || 'Time'}
        />
        <Pressable
          accessibilityRole="button"
          accessibilityLabel="Open time picker"
          disabled={disabled}
          onPress={openSheet}
          hitSlop={8}
          style={local.clockBtn}
        >
          <Text style={{ color: t.brandPrimary, fontSize: 16, fontWeight: '800' }}>◷</Text>
        </Pressable>
      </View>

      <Modal transparent animationType="fade" visible={open} onRequestClose={() => setOpen(false)}>
        <View style={local.maskRoot}>
          <Pressable
            accessibilityLabel="Close"
            accessibilityRole="button"
            style={local.maskFill}
            onPress={() => setOpen(false)}
          />
          <View
            accessibilityRole="dialog"
            accessibilityLabel="Time picker"
            style={[
              local.sheet,
              { backgroundColor: t.surfaceRaised, borderColor: t.borderDefault },
            ]}
          >
            <View style={local.sheetHeader}>
              <Text style={[shared.strong, { color: t.textPrimary, flex: 1 }]}>选择时间</Text>
              <Pressable
                accessibilityRole="button"
                accessibilityLabel="Confirm"
                onPress={() => {
                  onChange?.(formatHhMm(draftH, draftM));
                  setOpen(false);
                }}
                hitSlop={8}
              >
                <Text style={{ color: t.brandPrimary, fontWeight: '800' }}>确定</Text>
              </Pressable>
            </View>
            <View style={local.columns}>
              <ScrollView style={local.col} showsVerticalScrollIndicator={false}>
                {HOURS.map((h) => (
                  <Pressable
                    key={h}
                    accessibilityRole="button"
                    accessibilityState={{ selected: draftH === h }}
                    onPress={() => setDraftH(h)}
                    style={[
                      local.option,
                      draftH === h && { backgroundColor: t.brandSubtle },
                    ]}
                  >
                    <Text
                      style={{
                        color: draftH === h ? t.brandPrimary : t.textPrimary,
                        fontWeight: '700',
                      }}
                    >
                      {pad2(h)}
                    </Text>
                  </Pressable>
                ))}
              </ScrollView>
              <ScrollView style={local.col} showsVerticalScrollIndicator={false}>
                {MINS.map((m) => (
                  <Pressable
                    key={m}
                    accessibilityRole="button"
                    accessibilityState={{ selected: draftM === m }}
                    onPress={() => setDraftM(m)}
                    style={[
                      local.option,
                      draftM === m && { backgroundColor: t.brandSubtle },
                    ]}
                  >
                    <Text
                      style={{
                        color: draftM === m ? t.brandPrimary : t.textPrimary,
                        fontWeight: '700',
                      }}
                    >
                      {pad2(m)}
                    </Text>
                  </Pressable>
                ))}
              </ScrollView>
            </View>
          </View>
        </View>
      </Modal>
    </View>
  );
}

const local = StyleSheet.create({
  wrap: { width: '100%' },
  fieldRow: {
    minHeight: 48,
    borderWidth: 1,
    borderRadius: 16,
    paddingLeft: 14,
    paddingRight: 6,
    flexDirection: 'row',
    alignItems: 'center',
  },
  input: { flex: 1, fontSize: 15, fontWeight: '700', paddingVertical: 10 },
  clockBtn: { width: 40, height: 40, alignItems: 'center', justifyContent: 'center' },
  maskRoot: { flex: 1, backgroundColor: 'rgba(23,58,98,0.32)', justifyContent: 'flex-end' },
  maskFill: { ...StyleSheet.absoluteFillObject },
  sheet: {
    borderTopLeftRadius: 24,
    borderTopRightRadius: 24,
    borderWidth: 1,
    maxHeight: '55%',
    zIndex: 1,
  },
  sheetHeader: {
    minHeight: 52,
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 16,
    gap: 8,
  },
  columns: { flexDirection: 'row', height: 220, paddingBottom: 12 },
  col: { flex: 1 },
  option: {
    minHeight: 44,
    alignItems: 'center',
    justifyContent: 'center',
    marginHorizontal: 10,
    borderRadius: 12,
  },
});
