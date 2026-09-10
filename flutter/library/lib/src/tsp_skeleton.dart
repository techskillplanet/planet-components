import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Placeholder skeleton rows with optional avatar + shimmer / pulse.
class TspSkeleton extends StatefulWidget {
  const TspSkeleton({
    super.key,
    this.rows = 3,
    this.animated = true,
    this.avatar = false,
    this.variant = 'default',
    this.theme = StarPlanetTheme.sky,
  });

  final int rows;
  final bool animated;
  final bool avatar;
  final String variant;
  final StarPlanetTheme theme;

  @override
  State<TspSkeleton> createState() => _TspSkeletonState();
}

class _TspSkeletonState extends State<TspSkeleton> with SingleTickerProviderStateMixin {
  AnimationController? _controller;

  @override
  void initState() {
    super.initState();
    _maybeStart();
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    _syncAnimation();
  }

  @override
  void didUpdateWidget(covariant TspSkeleton oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (oldWidget.animated != widget.animated || oldWidget.variant != widget.variant) {
      _syncAnimation();
    }
  }

  void _maybeStart() {
    _controller ??= AnimationController(vsync: this, duration: const Duration(milliseconds: 1200));
  }

  void _syncAnimation() {
    _maybeStart();
    final reduceMotion = MediaQuery.disableAnimationsOf(context);
    final shouldAnimate = widget.animated && !reduceMotion;
    if (shouldAnimate) {
      if (!(_controller?.isAnimating ?? false)) {
        _controller!.repeat(reverse: widget.variant == 'pulse');
      }
    } else {
      _controller?.stop();
      _controller?.value = 0.35;
    }
  }

  @override
  void dispose() {
    _controller?.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final count = widget.rows.clamp(1, 12);
    final base = widget.theme.borderDefault.withValues(alpha: 0.55);
    final highlight = widget.theme.brandSubtle;

    return Semantics(
      excludeSemantics: true,
      child: AnimatedBuilder(
        animation: _controller ?? const AlwaysStoppedAnimation(0.35),
        builder: (context, _) {
          final t = _controller?.value ?? 0.35;
          final fill = Color.lerp(base, highlight, widget.variant == 'pulse' ? t : 0.35 + t * 0.45)!;
          return Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              if (widget.avatar) ...[
                Container(
                  width: 40,
                  height: 40,
                  decoration: BoxDecoration(shape: BoxShape.circle, color: fill),
                ),
                const SizedBox(width: 12),
              ],
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    for (var i = 0; i < count; i++) ...[
                      if (i > 0) const SizedBox(height: 10),
                      FractionallySizedBox(
                        widthFactor: i == count - 1 ? 0.62 : 1,
                        child: Container(
                          height: 12,
                          decoration: BoxDecoration(
                            color: fill,
                            borderRadius: BorderRadius.circular(8),
                          ),
                        ),
                      ),
                    ],
                  ],
                ),
              ),
            ],
          );
        },
      ),
    );
  }
}
