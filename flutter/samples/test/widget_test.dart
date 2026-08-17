import 'package:flutter_test/flutter_test.dart';
import 'package:tech_skill_planet_components_example/main.dart';

void main() {
  testWidgets('TC-SAMPLE-01 renders home catalog and tabs', (tester) async {
    await tester.pumpWidget(const BasicControlsExampleApp());

    expect(find.text('基础组件'), findsOneWidget);
    expect(find.text('TspButton'), findsOneWidget);
    expect(find.text('TspChip'), findsOneWidget);
    expect(find.text('学习'), findsOneWidget);
    expect(find.text('设置'), findsOneWidget);
  });

  testWidgets('TC-SAMPLE-02 opens component detail and returns', (tester) async {
    await tester.pumpWidget(const BasicControlsExampleApp());

    await tester.tap(find.text('TspButton'));
    await tester.pumpAndSettle();
    expect(find.text('TspButton'), findsWidgets);
    expect(find.text('使用案例'), findsOneWidget);

    await tester.tap(find.text('‹'));
    await tester.pumpAndSettle();
    expect(find.text('基础组件'), findsOneWidget);
    expect(find.text('TspChip'), findsOneWidget);
  });

  testWidgets('TC-SAMPLE-03 switches to settings page', (tester) async {
    await tester.pumpWidget(const BasicControlsExampleApp());

    await tester.tap(find.text('设置'));
    await tester.pumpAndSettle();
    expect(find.text('Theme Switch'), findsOneWidget);
    expect(find.text('Language Switch'), findsOneWidget);
  });
}
