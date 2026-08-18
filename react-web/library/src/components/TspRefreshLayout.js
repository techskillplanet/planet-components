import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/**
 * TspRefreshLayout – pull/refresh + load-more scroll host aligned with RN.
 */
export function TspRefreshLayout({
  children,
  refreshing = false,
  loadingMore = false,
  disabled = false,
  onRefresh,
  onLoadMore,
  theme = starPlanetTheme,
  className,
  style,
}) {
  const loadMoreLock = React.useRef(false);

  React.useEffect(() => {
    if (!loadingMore) loadMoreLock.current = false;
  }, [loadingMore]);

  const onScroll = (event) => {
    if (disabled || loadingMore || refreshing || !onLoadMore || loadMoreLock.current) return;
    const el = event.currentTarget;
    const distanceFromEnd = el.scrollHeight - (el.clientHeight + el.scrollTop);
    if (distanceFromEnd < 48) {
      loadMoreLock.current = true;
      onLoadMore();
    }
  };

  return h(
    'div',
    {
      className: cx('bc-refresh-layout', disabled && 'bc-disabled', className),
      style: themed(theme, style),
      onScroll,
    },
    refreshing
      ? h('div', { className: 'bc-refresh-layout__banner' }, '刷新中…')
      : null,
    !disabled && onRefresh
      ? h(
          'button',
          {
            type: 'button',
            className: 'bc-refresh-layout__refresh',
            onClick: onRefresh,
          },
          '刷新'
        )
      : null,
    h('div', { className: 'bc-refresh-layout__content' }, children),
    loadingMore
      ? h('div', { className: 'bc-refresh-layout__footer' }, '加载更多…')
      : null
  );
}
