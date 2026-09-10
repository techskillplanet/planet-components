import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Lightweight file list + add trigger (no real upload backend; add via [onAdd] mock).
class TspUploadFile {
  const TspUploadFile({required this.id, required this.name});
  final String id;
  final String name;

  factory TspUploadFile.fromDynamic(dynamic raw, int index) {
    if (raw is TspUploadFile) return raw;
    if (raw is String) return TspUploadFile(id: '$index', name: raw);
    if (raw is Map) {
      return TspUploadFile(
        id: '${raw['id'] ?? index}',
        name: '${raw['name'] ?? 'file'}',
      );
    }
    return TspUploadFile(id: '$index', name: '$raw');
  }
}

class TspUpload extends StatelessWidget {
  const TspUpload({
    super.key,
    this.files = const [],
    this.multiple = true,
    this.disabled = false,
    this.accept,
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
    this.onRemove,
    this.onAdd,
  });

  final List<dynamic> files;
  final bool multiple;
  final bool disabled;
  final String? accept;
  final StarPlanetTheme theme;
  final ValueChanged<List<TspUploadFile>>? onChanged;
  final ValueChanged<TspUploadFile>? onRemove;

  /// Optional mock picker. When null, taps add a placeholder file.
  final Future<List<TspUploadFile>> Function()? onAdd;

  List<TspUploadFile> get _list =>
      files.asMap().entries.map((e) => TspUploadFile.fromDynamic(e.value, e.key)).toList();

  Future<void> _handleAdd() async {
    if (disabled) return;
    List<TspUploadFile> picked;
    if (onAdd != null) {
      picked = await onAdd!();
    } else {
      final stamp = DateTime.now().millisecondsSinceEpoch;
      picked = [TspUploadFile(id: '$stamp', name: accept?.isNotEmpty == true ? 'file.$accept' : 'mock-file.txt')];
    }
    if (picked.isEmpty) return;
    final next = multiple ? [..._list, ...picked] : picked.take(1).toList();
    onChanged?.call(next);
  }

  void _handleRemove(TspUploadFile file) {
    if (disabled) return;
    onRemove?.call(file);
    onChanged?.call(_list.where((f) => f.id != file.id).toList());
  }

  @override
  Widget build(BuildContext context) {
    final list = _list;
    return Opacity(
      opacity: disabled ? .45 : 1,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          GestureDetector(
            onTap: disabled ? null : _handleAdd,
            child: Container(
              padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
              decoration: BoxDecoration(
                color: theme.brandSubtle,
                borderRadius: BorderRadius.circular(16),
                border: Border.all(color: theme.borderDefault, width: 2),
              ),
              child: Text(
                '选择文件',
                textAlign: TextAlign.center,
                style: TextStyle(color: theme.brandPrimary, fontWeight: FontWeight.w800, fontSize: 14),
              ),
            ),
          ),
          const SizedBox(height: 10),
          if (list.isEmpty)
            Text(
              '尚未选择文件',
              style: TextStyle(color: theme.textTertiary, fontWeight: FontWeight.w600, fontSize: 13),
            )
          else
            ...list.map(
              (file) => Padding(
                padding: const EdgeInsets.only(bottom: 8),
                child: Container(
                  padding: const EdgeInsets.fromLTRB(12, 10, 6, 10),
                  decoration: BoxDecoration(
                    color: theme.surfaceRaised,
                    borderRadius: BorderRadius.circular(14),
                    border: Border.all(color: theme.borderDefault),
                  ),
                  child: Row(
                    children: [
                      Expanded(
                        child: Text(
                          file.name,
                          style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w700, fontSize: 14),
                          overflow: TextOverflow.ellipsis,
                        ),
                      ),
                      GestureDetector(
                        onTap: disabled ? null : () => _handleRemove(file),
                        behavior: HitTestBehavior.opaque,
                        child: Padding(
                          padding: const EdgeInsets.all(8),
                          child: Text(
                            '×',
                            style: TextStyle(color: theme.textSecondary, fontSize: 18, fontWeight: FontWeight.w700),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
        ],
      ),
    );
  }
}
