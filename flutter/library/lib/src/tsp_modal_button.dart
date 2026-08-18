import 'package:flutter/material.dart';
import 'star_planet_theme.dart';
import 'tsp_button.dart';
import 'tsp_modal.dart';

class TspModalButton extends StatelessWidget {
  const TspModalButton({super.key, required this.theme, this.onResult});
  final StarPlanetTheme theme;
  final ValueChanged<bool>? onResult;

  @override
  Widget build(BuildContext context) => TspButton(
        text: 'Open Modal',
        theme: theme,
        onTap: () => showDialog<void>(
          context: context,
          barrierColor: Colors.transparent,
          builder: (dialogContext) => TspModal(
            title: '确认',
            message: '组件弹窗完整显示。',
            confirmText: '确定',
            cancelText: '取消',
            theme: theme,
            onCancel: () => Navigator.pop(dialogContext),
            onConfirm: () {
              Navigator.pop(dialogContext);
              onResult?.call(true);
            },
          ),
        ),
      );
}
