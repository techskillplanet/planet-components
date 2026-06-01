import 'package:flutter_test/flutter_test.dart';
import 'package:tech_skill_planet_basic_controls_example/main.dart';

void main() {
  testWidgets('基础组件示例工程可以渲染组件列表', (WidgetTester tester) async {
    await tester.pumpWidget(const BasicControlsExampleApp());

    expect(find.text('基础组件'), findsOneWidget);
    expect(find.text('TspButton'), findsOneWidget);
    expect(find.text('TspChip'), findsOneWidget);
    expect(find.text('学习'), findsOneWidget);
    expect(find.text('设置'), findsOneWidget);
  });
}
