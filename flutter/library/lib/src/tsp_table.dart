import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Lightweight data table with [columns] / [rows].
class TspTable extends StatelessWidget {
  const TspTable({
    super.key,
    this.columns = const [],
    this.rows = const [],
    this.variant = 'default',
    this.emptyText = '暂无数据',
    this.theme = StarPlanetTheme.sky,
  });

  final List<dynamic> columns;
  final List<dynamic> rows;
  final String variant;
  final String emptyText;
  final StarPlanetTheme theme;

  String _header(dynamic col) {
    if (col is String) return col;
    if (col is Map) return '${col['title'] ?? col['label'] ?? col['key'] ?? ''}';
    return '$col';
  }

  String? _colKey(dynamic col, int index) {
    if (col is Map) return '${col['key'] ?? col['id'] ?? index}';
    return null;
  }

  String _cell(dynamic row, dynamic col, int index) {
    if (row == null) return '';
    if (row is String || row is num) return '$row';
    if (row is List) return index < row.length ? '${row[index]}' : '';
    if (row is Map) {
      final key = _colKey(col, index) ?? '$index';
      return '${row[key] ?? ''}';
    }
    return '$row';
  }

  @override
  Widget build(BuildContext context) {
    final cols = columns;
    final data = rows;
    final striped = variant == 'striped';
    final headers = cols.map(_header).toList();

    return Container(
      decoration: BoxDecoration(
        color: theme.surfaceRaised,
        borderRadius: BorderRadius.circular(16),
        border: Border.all(color: theme.borderDefault, width: 1.5),
      ),
      clipBehavior: Clip.antiAlias,
      child: SingleChildScrollView(
        scrollDirection: Axis.horizontal,
        child: IntrinsicWidth(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              _row(
                cells: headers.isEmpty ? [''] : headers,
                header: true,
                striped: false,
              ),
              if (data.isEmpty)
                _row(
                  cells: [emptyText],
                  header: false,
                  striped: false,
                  spanHint: headers.isEmpty ? 1 : headers.length,
                )
              else
                ...data.asMap().entries.map((e) {
                  final cells = cols.isEmpty
                      ? ['${e.value}']
                      : List.generate(cols.length, (ci) => _cell(e.value, cols[ci], ci));
                  return _row(cells: cells, header: false, striped: striped && e.key.isOdd);
                }),
            ],
          ),
        ),
      ),
    );
  }

  Widget _row({
    required List<String> cells,
    required bool header,
    required bool striped,
    int spanHint = 1,
  }) {
    final bg = header
        ? theme.surfaceSubtle
        : (striped ? theme.brandSubtle.withValues(alpha: 0.45) : theme.surfaceRaised);
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 10),
      decoration: BoxDecoration(
        color: bg,
        border: Border(bottom: BorderSide(color: theme.borderDefault.withValues(alpha: 0.7))),
      ),
      child: Row(
        children: [
          for (var i = 0; i < cells.length; i++)
            SizedBox(
              width: cells.length == 1 && spanHint > 1 ? 120.0 * spanHint : 120,
              child: Text(
                cells[i],
                style: TextStyle(
                  color: header ? theme.textSecondary : theme.textPrimary,
                  fontWeight: header ? FontWeight.w900 : FontWeight.w600,
                  fontSize: 13,
                ),
              ),
            ),
        ],
      ),
    );
  }
}
