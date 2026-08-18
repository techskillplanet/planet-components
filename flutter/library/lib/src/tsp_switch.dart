import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspSwitch extends StatefulWidget {
  const TspSwitch({
    super.key,
    required this.checked,
    this.text = '',
    this.checkedText = 'ON',
    this.uncheckedText = 'OFF',
    this.loading = false,
    this.disabled = false,
    this.variant = 'md',
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
  });

  final bool checked;
  final String text;
  final String checkedText;
  final String uncheckedText;
  final bool loading;
  final bool disabled;
  final String variant;
  final StarPlanetTheme theme;
  final ValueChanged<bool>? onChanged;

  @override
  State<TspSwitch> createState() => _TspSwitchState();
}

class _TspSwitchState extends State<TspSwitch> with TickerProviderStateMixin {
  late final AnimationController _thumb;
  late final AnimationController _spin;

  bool get _isSm => widget.variant == 'sm' || widget.variant == 'small';
  double get _width => _isSm ? 40 : 52;
  double get _height => _isSm ? 22 : 28;
  double get _handle => _isSm ? 18 : 24;
  double get _inset => 2;
  double get _travel => _width - _handle - _inset * 2;

  @override
  void initState() {
    super.initState();
    _thumb = AnimationController(vsync: this, duration: const Duration(milliseconds: 180), value: widget.checked ? 1 : 0);
    _spin = AnimationController(vsync: this, duration: const Duration(milliseconds: 700));
    if (widget.loading) _spin.repeat();
  }

  @override
  void didUpdateWidget(covariant TspSwitch oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (oldWidget.checked != widget.checked) {
      _thumb.animateTo(widget.checked ? 1 : 0, curve: Curves.easeOut);
    }
    if (oldWidget.loading != widget.loading) {
      if (widget.loading) {
        _spin.repeat();
      } else {
        _spin.stop();
        _spin.value = 0;
      }
    }
  }

  @override
  void dispose() {
    _thumb.dispose();
    _spin.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final theme = widget.theme;
    final blocked = widget.disabled || widget.loading;
    final trackBg = widget.checked ? theme.brandPrimary : theme.borderDefault;
    final spinnerColor = widget.checked ? Colors.white : theme.brandDark;
    final controlOpacity = widget.disabled ? 0.5 : (widget.loading ? 0.7 : 1.0);

    return GestureDetector(
      onTap: blocked ? null : () => widget.onChanged?.call(!widget.checked),
      child: Row(
        children: [
          if (widget.text.isNotEmpty)
            Expanded(
              child: Text(
                widget.text,
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
                style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800, fontSize: 14),
              ),
            ),
          if (widget.text.isNotEmpty) const SizedBox(width: 12),
          Opacity(
            opacity: controlOpacity,
            child: AnimatedBuilder(
              animation: Listenable.merge([_thumb, _spin]),
              builder: (context, _) {
                final dx = _travel * _thumb.value;
                return SizedBox(
                  width: _width,
                  height: _height,
                  child: DecoratedBox(
                    decoration: BoxDecoration(
                      color: trackBg,
                      borderRadius: BorderRadius.circular(999),
                    ),
                    child: Stack(
                      children: [
                        Positioned(
                          left: _inset + dx,
                          top: _inset,
                          width: _handle,
                          height: _handle,
                          child: DecoratedBox(
                            decoration: const BoxDecoration(
                              color: Colors.white,
                              shape: BoxShape.circle,
                            ),
                            child: widget.loading
                                ? Center(
                                    child: SizedBox(
                                      width: _handle * 0.5,
                                      height: _handle * 0.5,
                                      child: CircularProgressIndicator(
                                        strokeWidth: 2,
                                        color: spinnerColor,
                                      ),
                                    ),
                                  )
                                : null,
                          ),
                        ),
                      ],
                    ),
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
