import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspTopBar extends StatelessWidget {
  const TspTopBar({super.key, required this.title, this.showBack = false, this.backgroundColor, this.theme = StarPlanetTheme.sky, this.onBack});
  final String title;
  final bool showBack;
  final Color? backgroundColor;
  final StarPlanetTheme theme;
  final VoidCallback? onBack;

  @override
  Widget build(BuildContext context) {
    final topInset = MediaQuery.of(context).padding.top;
    return Container(
      width: double.infinity,
      padding: EdgeInsets.only(top: topInset),
      decoration: BoxDecoration(
        color: backgroundColor ?? theme.surfaceRaised,
        border: Border(bottom: BorderSide(color: theme.borderDefault)),
      ),
      child: SizedBox(
        height: 56,
        child: Stack(
          alignment: Alignment.center,
          children: [
            if (showBack)
              Positioned(
                left: 0,
                top: 0,
                bottom: 0,
                width: 44,
                child: GestureDetector(
                  behavior: HitTestBehavior.opaque,
                  onTap: onBack ?? () => Navigator.maybePop(context),
                  child: Center(child: Text('‹', style: TextStyle(color: theme.brandPrimary, fontSize: 28, height: 1, fontWeight: FontWeight.w400))),
                ),
              ),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 48),
              child: Text(
                title,
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
                textAlign: TextAlign.center,
                style: TextStyle(color: theme.textPrimary, fontSize: 20, fontWeight: FontWeight.w900),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
