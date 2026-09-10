import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Side / bottom drawer overlay. Returns empty when not [visible].
class TspDrawer extends StatelessWidget {
  const TspDrawer({
    super.key,
    this.visible = false,
    this.title = '',
    this.placement = 'bottom',
    this.theme = StarPlanetTheme.sky,
    this.onClose,
    this.child,
  });

  final bool visible;
  final String title;
  final String placement;
  final StarPlanetTheme theme;
  final VoidCallback? onClose;
  final Widget? child;

  bool get _isSide => placement == 'left' || placement == 'right';

  @override
  Widget build(BuildContext context) {
    if (!visible) return const SizedBox.shrink();

    final panel = Material(
      color: theme.surfaceRaised,
      borderRadius: _panelRadius,
      child: Container(
        decoration: BoxDecoration(
          borderRadius: _panelRadius,
          border: Border.all(color: theme.borderDefault),
        ),
        child: SafeArea(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            mainAxisSize: _isSide ? MainAxisSize.max : MainAxisSize.min,
            children: [
              if (title.isNotEmpty)
                Padding(
                  padding: const EdgeInsets.fromLTRB(16, 14, 8, 8),
                  child: Row(
                    children: [
                      Expanded(
                        child: Text(
                          title,
                          style: TextStyle(color: theme.textPrimary, fontSize: 17, fontWeight: FontWeight.w900),
                        ),
                      ),
                      IconButton(
                        onPressed: onClose,
                        icon: Text('×', style: TextStyle(color: theme.textSecondary, fontSize: 22, fontWeight: FontWeight.w700)),
                        tooltip: 'Close',
                      ),
                    ],
                  ),
                ),
              if (_isSide)
                Expanded(
                  child: Padding(
                    padding: const EdgeInsets.fromLTRB(16, 4, 16, 20),
                    child: child ?? const SizedBox.shrink(),
                  ),
                )
              else
                Padding(
                  padding: const EdgeInsets.fromLTRB(16, 4, 16, 20),
                  child: child ?? const SizedBox.shrink(),
                ),
            ],
          ),
        ),
      ),
    );

    return Material(
      type: MaterialType.transparency,
      child: Stack(
        fit: StackFit.expand,
        children: [
          GestureDetector(
            onTap: onClose,
            child: const ColoredBox(color: Color(0x47173A62)),
          ),
          if (placement == 'left')
            Align(alignment: Alignment.centerLeft, child: SizedBox(width: 300, child: panel))
          else if (placement == 'right')
            Align(alignment: Alignment.centerRight, child: SizedBox(width: 300, child: panel))
          else
            Align(
              alignment: Alignment.bottomCenter,
              child: ConstrainedBox(
                constraints: BoxConstraints(maxHeight: MediaQuery.sizeOf(context).height * 0.72),
                child: panel,
              ),
            ),
        ],
      ),
    );
  }

  BorderRadius get _panelRadius {
    switch (placement) {
      case 'left':
        return const BorderRadius.horizontal(right: Radius.circular(24));
      case 'right':
        return const BorderRadius.horizontal(left: Radius.circular(24));
      default:
        return const BorderRadius.vertical(top: Radius.circular(24));
    }
  }
}
