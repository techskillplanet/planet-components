import React, { useCallback, useMemo, useRef, useState } from 'react';
import { PanResponder, Pressable, Text, View, StyleSheet } from 'react-native';
import { styles as shared, withTheme } from '../utils/shared';

function clampNum(value, min, max) {
  const n = Number(value);
  const v = Number.isFinite(n) ? n : min;
  return Math.max(min, Math.min(max, v));
}

function snap(value, min, max, step) {
  const s = Number(step) > 0 ? Number(step) : 1;
  const snapped = Math.round((value - min) / s) * s + min;
  return clampNum(Number(snapped.toFixed(6)), min, max);
}

/** Continuous value slider — track + thumb (PanResponder), no extra deps. */
export function TspSlider({
  value = 0,
  min = 0,
  max = 100,
  step = 1,
  disabled = false,
  theme,
  onChange,
}) {
  const t = withTheme(theme);
  const lo = Number(min);
  const hi = Number(max);
  const current = clampNum(value, lo, hi);
  const pct = hi === lo ? 0 : ((current - lo) / (hi - lo)) * 100;
  const trackWidth = useRef(0);
  const [layoutW, setLayoutW] = useState(0);

  const emitFromX = useCallback(
    x => {
      if (disabled || hi === lo) return;
      const w = trackWidth.current || layoutW;
      if (w <= 0) return;
      const ratio = clampNum(x / w, 0, 1);
      const next = snap(lo + ratio * (hi - lo), lo, hi, step);
      onChange?.(next);
    },
    [disabled, hi, lo, layoutW, onChange, step]
  );

  const pan = useMemo(
    () =>
      PanResponder.create({
        onStartShouldSetPanResponder: () => !disabled,
        onMoveShouldSetPanResponder: () => !disabled,
        onPanResponderGrant: evt => emitFromX(evt.nativeEvent.locationX),
        onPanResponderMove: evt => emitFromX(evt.nativeEvent.locationX),
      }),
    [disabled, emitFromX]
  );

  const dec = () => onChange?.(snap(current - (Number(step) || 1), lo, hi, step));
  const inc = () => onChange?.(snap(current + (Number(step) || 1), lo, hi, step));

  return (
    <View
      style={[local.root, disabled && shared.disabled]}
      accessibilityRole="adjustable"
      accessibilityState={{ disabled }}
      accessibilityValue={{ min: lo, max: hi, now: current }}
    >
      <Pressable
        accessibilityLabel="Decrease"
        disabled={disabled || current <= lo}
        onPress={dec}
        style={[local.stepBtn, { borderColor: t.borderDefault, backgroundColor: t.surfaceRaised }]}
      >
        <Text style={{ color: t.brandPrimary, fontWeight: '900' }}>−</Text>
      </Pressable>
      <View
        style={local.trackWrap}
        onLayout={e => {
          const w = e.nativeEvent.layout.width;
          trackWidth.current = w;
          setLayoutW(w);
        }}
        {...pan.panHandlers}
      >
        <View style={[local.track, { backgroundColor: t.borderDefault }]}>
          <View
            style={[
              local.fill,
              { width: `${pct}%`, backgroundColor: t.brandPrimary },
            ]}
          />
        </View>
        <View
          pointerEvents="none"
          style={[
            local.thumb,
            {
              left: `${pct}%`,
              marginLeft: -10,
              backgroundColor: t.surfaceRaised,
              borderColor: t.brandPrimary,
            },
          ]}
        />
      </View>
      <Pressable
        accessibilityLabel="Increase"
        disabled={disabled || current >= hi}
        onPress={inc}
        style={[local.stepBtn, { borderColor: t.borderDefault, backgroundColor: t.surfaceRaised }]}
      >
        <Text style={{ color: t.brandPrimary, fontWeight: '900' }}>+</Text>
      </Pressable>
    </View>
  );
}

const local = StyleSheet.create({
  root: { flexDirection: 'row', alignItems: 'center', gap: 10, width: '100%', minHeight: 44 },
  trackWrap: { flex: 1, height: 44, justifyContent: 'center', position: 'relative' },
  track: { height: 10, borderRadius: 999, overflow: 'hidden' },
  fill: { height: 10, borderRadius: 999 },
  thumb: {
    position: 'absolute',
    top: 12,
    width: 20,
    height: 20,
    borderRadius: 10,
    borderWidth: 2,
  },
  stepBtn: {
    width: 36,
    height: 36,
    borderRadius: 12,
    borderWidth: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
