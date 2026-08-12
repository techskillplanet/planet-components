import React, { useCallback, useRef } from 'react';
import { ActivityIndicator, RefreshControl, ScrollView, Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspRefreshLayout({
  children,
  refreshing = false,
  loadingMore = false,
  disabled = false,
  onRefresh,
  onLoadMore,
  theme,
  style,
  contentContainerStyle,
}) {
  const t = withTheme(theme);
  const loadingMoreLock = useRef(false);

  const handleScroll = useCallback(
    event => {
      if (disabled || loadingMore || refreshing || !onLoadMore || loadingMoreLock.current) {
        return;
      }
      const { layoutMeasurement, contentOffset, contentSize } = event.nativeEvent;
      const distanceFromEnd = contentSize.height - (layoutMeasurement.height + contentOffset.y);
      if (distanceFromEnd < 48) {
        loadingMoreLock.current = true;
        onLoadMore();
      }
    },
    [disabled, loadingMore, refreshing, onLoadMore]
  );

  if (!loadingMore) {
    loadingMoreLock.current = false;
  }

  return (
    <ScrollView
      style={[{ flex: 1 }, style]}
      contentContainerStyle={contentContainerStyle}
      scrollEnabled={!disabled}
      nestedScrollEnabled
      onScroll={handleScroll}
      scrollEventThrottle={16}
      refreshControl={
        <RefreshControl
          refreshing={!!refreshing}
          enabled={!disabled}
          onRefresh={disabled ? undefined : onRefresh}
          tintColor={t.brandPrimary}
          colors={[t.brandPrimary]}
        />
      }
    >
      {children}
      {loadingMore ? (
        <View style={[styles.refreshFooter, { borderColor: t.borderDefault }]}>
          <ActivityIndicator color={t.brandPrimary} />
          <Text style={[styles.secondary, { color: t.textSecondary, marginTop: 0 }]}>加载更多…</Text>
        </View>
      ) : null}
    </ScrollView>
  );
}
