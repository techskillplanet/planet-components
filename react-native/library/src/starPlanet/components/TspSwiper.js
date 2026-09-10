import React, { useEffect, useState } from 'react';
import { Pressable, Text, View, StyleSheet } from 'react-native';
import { withTheme } from '../utils/shared';

function slideLabel(item) {
  if (item == null) return '';
  if (typeof item === 'string' || typeof item === 'number') return String(item);
  return item.content ?? item.label ?? item.title ?? item.text ?? '';
}

/** Horizontal carousel — index + dots + optional autoplay. */
export function TspSwiper({
  items = [],
  index = 0,
  autoplay = false,
  theme,
  onChange,
}) {
  const t = withTheme(theme);
  const list = Array.isArray(items) ? items : [];
  const [i, setI] = useState(clampIndex(index, list.length));

  useEffect(() => {
    setI(clampIndex(index, list.length));
  }, [index, list.length]);

  useEffect(() => {
    if (!autoplay || list.length < 2) return undefined;
    const id = setInterval(() => {
      setI(prev => {
        const next = (prev + 1) % list.length;
        onChange?.(next);
        return next;
      });
    }, 3200);
    return () => clearInterval(id);
  }, [autoplay, list.length, onChange]);

  const go = next => {
    if (list.length === 0) return;
    const n = ((next % list.length) + list.length) % list.length;
    setI(n);
    onChange?.(n);
  };

  const current = list[i];

  return (
    <View
      accessibilityRole="summary"
      accessibilityLabel="Carousel"
      style={[local.root, { borderColor: t.borderDefault, backgroundColor: t.surfaceRaised }]}
    >
      <View style={[local.viewport, { backgroundColor: t.surfaceSubtle }]}>
        <Text style={[local.slideText, { color: t.textPrimary }]} numberOfLines={4}>
          {slideLabel(current)}
        </Text>
        {list.length > 1 ? (
          <View style={local.nav}>
            <Pressable
              accessibilityLabel="Previous"
              accessibilityRole="button"
              onPress={() => go(i - 1)}
              style={[local.arrow, { borderColor: t.borderDefault, backgroundColor: t.surfaceRaised }]}
            >
              <Text style={{ color: t.brandPrimary, fontWeight: '900', fontSize: 18 }}>‹</Text>
            </Pressable>
            <Pressable
              accessibilityLabel="Next"
              accessibilityRole="button"
              onPress={() => go(i + 1)}
              style={[local.arrow, { borderColor: t.borderDefault, backgroundColor: t.surfaceRaised }]}
            >
              <Text style={{ color: t.brandPrimary, fontWeight: '900', fontSize: 18 }}>›</Text>
            </Pressable>
          </View>
        ) : null}
      </View>
      {list.length > 1 ? (
        <View style={local.dots}>
          {list.map((_, di) => (
            <Pressable
              key={di}
              accessibilityLabel={`Slide ${di + 1}`}
              accessibilityRole="button"
              accessibilityState={{ selected: di === i }}
              onPress={() => go(di)}
              style={[
                local.dot,
                {
                  backgroundColor: di === i ? t.brandPrimary : t.borderDefault,
                },
              ]}
            />
          ))}
        </View>
      ) : null}
    </View>
  );
}

function clampIndex(index, length) {
  if (length <= 0) return 0;
  const n = Number(index);
  if (!Number.isFinite(n)) return 0;
  return Math.max(0, Math.min(length - 1, Math.floor(n)));
}

const local = StyleSheet.create({
  root: { width: '100%', borderWidth: 1, borderRadius: 18, overflow: 'hidden' },
  viewport: {
    minHeight: 120,
    padding: 16,
    justifyContent: 'center',
    position: 'relative',
  },
  slideText: { fontSize: 16, fontWeight: '700', textAlign: 'center' },
  nav: {
    position: 'absolute',
    left: 8,
    right: 8,
    top: 0,
    bottom: 0,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  arrow: {
    width: 36,
    height: 36,
    borderRadius: 18,
    borderWidth: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  dots: {
    flexDirection: 'row',
    justifyContent: 'center',
    gap: 8,
    paddingVertical: 12,
  },
  dot: { width: 8, height: 8, borderRadius: 4 },
});
