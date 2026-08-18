import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspRefreshLayout extends StatefulWidget {
  const TspRefreshLayout({
    super.key,
    required this.child,
    this.refreshing = false,
    this.loadingMore = false,
    this.disabled = false,
    this.theme = StarPlanetTheme.sky,
    this.onRefresh,
    this.onLoadMore,
  });

  final Widget child;
  final bool refreshing;
  final bool loadingMore;
  final bool disabled;
  final StarPlanetTheme theme;
  final Future<void> Function()? onRefresh;
  final VoidCallback? onLoadMore;

  @override
  State<TspRefreshLayout> createState() => _TspRefreshLayoutState();
}

class _TspRefreshLayoutState extends State<TspRefreshLayout> {
  bool _loadMoreLock = false;

  @override
  Widget build(BuildContext context) {
    if (!widget.loadingMore) {
      _loadMoreLock = false;
    }
    return NotificationListener<ScrollNotification>(
      onNotification: (notification) {
        if (widget.disabled ||
            widget.loadingMore ||
            widget.refreshing ||
            widget.onLoadMore == null ||
            _loadMoreLock) {
          return false;
        }
        if (notification.metrics.extentAfter < 48) {
          _loadMoreLock = true;
          widget.onLoadMore!();
        }
        return false;
      },
      child: RefreshIndicator(
        color: widget.theme.brandPrimary,
        onRefresh: widget.disabled
            ? () async {}
            : () async {
                await widget.onRefresh?.call();
              },
        notificationPredicate: (_) => !widget.disabled,
        child: ListView(
          physics: widget.disabled
              ? const NeverScrollableScrollPhysics()
              : const AlwaysScrollableScrollPhysics(),
          children: [
            widget.child,
            if (widget.loadingMore)
              Padding(
                padding: const EdgeInsets.symmetric(vertical: 14),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    SizedBox(
                      width: 18,
                      height: 18,
                      child: CircularProgressIndicator(strokeWidth: 2, color: widget.theme.brandPrimary),
                    ),
                    const SizedBox(width: 8),
                    Text('加载更多…', style: TextStyle(color: widget.theme.textSecondary)),
                  ],
                ),
              ),
          ],
        ),
      ),
    );
  }
}
