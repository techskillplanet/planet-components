import 'dart:async';

import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Simple PageView carousel with dots.
class TspSwiper extends StatefulWidget {
  const TspSwiper({
    super.key,
    this.items = const [],
    this.index = 0,
    this.autoplay = false,
    this.height = 160,
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
  });

  /// Slide labels (string content). For custom widgets, wrap labels as needed.
  final List<String> items;
  final int index;
  final bool autoplay;
  final double height;
  final StarPlanetTheme theme;
  final ValueChanged<int>? onChanged;

  @override
  State<TspSwiper> createState() => _TspSwiperState();
}

class _TspSwiperState extends State<TspSwiper> {
  late PageController _controller;
  late int _index;
  Timer? _timer;

  @override
  void initState() {
    super.initState();
    _index = widget.items.isEmpty ? 0 : widget.index.clamp(0, widget.items.length - 1);
    _controller = PageController(initialPage: _index);
    _syncAutoplay();
  }

  @override
  void didUpdateWidget(covariant TspSwiper oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (widget.index != oldWidget.index && widget.items.isNotEmpty) {
      final next = widget.index.clamp(0, widget.items.length - 1);
      if (next != _index) {
        _index = next;
        _controller.animateToPage(next, duration: const Duration(milliseconds: 280), curve: Curves.easeOut);
      }
    }
    if (widget.autoplay != oldWidget.autoplay || widget.items.length != oldWidget.items.length) {
      _syncAutoplay();
    }
  }

  void _syncAutoplay() {
    _timer?.cancel();
    if (!widget.autoplay || widget.items.length < 2) return;
    final reduceMotion = WidgetsBinding.instance.platformDispatcher.accessibilityFeatures.disableAnimations;
    if (reduceMotion) return;
    _timer = Timer.periodic(const Duration(milliseconds: 3200), (_) {
      if (!mounted || widget.items.isEmpty) return;
      final next = (_index + 1) % widget.items.length;
      _controller.animateToPage(next, duration: const Duration(milliseconds: 320), curve: Curves.easeOut);
    });
  }

  @override
  void dispose() {
    _timer?.cancel();
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final theme = widget.theme;
    final list = widget.items;

    return Semantics(
      label: 'Carousel',
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          SizedBox(
            height: widget.height,
            child: list.isEmpty
                ? Container(
                    alignment: Alignment.center,
                    decoration: BoxDecoration(
                      color: theme.surfaceSubtle,
                      borderRadius: BorderRadius.circular(18),
                      border: Border.all(color: theme.borderDefault),
                    ),
                    child: Text('—', style: TextStyle(color: theme.textTertiary)),
                  )
                : PageView.builder(
                    controller: _controller,
                    itemCount: list.length,
                    onPageChanged: (i) {
                      setState(() => _index = i);
                      widget.onChanged?.call(i);
                    },
                    itemBuilder: (context, i) {
                      return Container(
                        margin: const EdgeInsets.symmetric(horizontal: 2),
                        alignment: Alignment.center,
                        decoration: BoxDecoration(
                          color: theme.brandSubtle,
                          borderRadius: BorderRadius.circular(18),
                          border: Border.all(color: theme.borderDefault),
                        ),
                        child: Text(
                          list[i],
                          textAlign: TextAlign.center,
                          style: TextStyle(color: theme.textPrimary, fontSize: 16, fontWeight: FontWeight.w800),
                        ),
                      );
                    },
                  ),
          ),
          if (list.length > 1) ...[
            const SizedBox(height: 10),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                for (var i = 0; i < list.length; i++)
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 3),
                    child: GestureDetector(
                      onTap: () {
                        _controller.animateToPage(i, duration: const Duration(milliseconds: 240), curve: Curves.easeOut);
                      },
                      child: AnimatedContainer(
                        duration: const Duration(milliseconds: 180),
                        width: i == _index ? 18 : 8,
                        height: 8,
                        decoration: BoxDecoration(
                          color: i == _index ? theme.brandPrimary : theme.borderDefault,
                          borderRadius: BorderRadius.circular(99),
                        ),
                      ),
                    ),
                  ),
              ],
            ),
          ],
        ],
      ),
    );
  }
}
