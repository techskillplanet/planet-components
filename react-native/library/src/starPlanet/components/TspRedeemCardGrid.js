import React from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import { withTheme } from '../utils/shared';

/**
 * TspRedeemCardGrid – Redeem item cards.
 */
export function TspRedeemCardGrid({
  items = [],
  availablePoints = 0,
  frozen = false,
  disabled = false,
  theme,
  onRedeem,
}) {
  const t = withTheme(theme);

  return (
    <View style={styles.grid}>
      {frozen ? (
        <View
          style={[
            styles.banner,
            {
              backgroundColor: t.activeFill,
              borderColor: t.warning,
            },
          ]}
        >
          <Text style={[styles.bannerText, { color: t.textPrimary }]}>今日已冻结，暂不可兑换</Text>
        </View>
      ) : null}
      {items.map((item) => {
        const cost = Number(item.cost) || 0;
        const insufficient = availablePoints < cost;
        const blocked = frozen || disabled || insufficient;
        return (
          <Pressable
            key={String(item.id ?? item.name)}
            disabled={blocked}
            onPress={blocked ? undefined : () => onRedeem?.(item)}
            style={[
              styles.card,
              {
                backgroundColor: t.surfaceRaised,
                borderColor: t.borderDefault,
              },
              blocked && styles.blocked,
            ]}
          >
            <Text style={styles.icon}>{item.icon || '🎁'}</Text>
            <Text style={[styles.name, { color: t.textPrimary }]} numberOfLines={2}>
              {item.name || ''}
            </Text>
            <Text style={[styles.cost, { color: t.brandPrimary }]}>{`${cost} 分`}</Text>
            {insufficient && !frozen ? (
              <Text style={[styles.hint, { color: t.danger }]}>积分不足</Text>
            ) : null}
          </Pressable>
        );
      })}
    </View>
  );
}

const styles = StyleSheet.create({
  grid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 12,
    justifyContent: 'space-between',
  },
  banner: {
    width: '100%',
    borderWidth: 1,
    borderRadius: 14,
    paddingHorizontal: 12,
    paddingVertical: 10,
  },
  bannerText: { fontWeight: '800', fontSize: 13 },
  card: {
    width: '48%',
    borderWidth: 1,
    borderRadius: 18,
    padding: 14,
    alignItems: 'flex-start',
  },
  blocked: { opacity: 0.5 },
  icon: { fontSize: 28 },
  name: { fontWeight: '800', fontSize: 14, marginTop: 6 },
  cost: { fontWeight: '900', marginTop: 4 },
  hint: { fontSize: 12, marginTop: 4 },
});
