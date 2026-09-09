import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:tech_skill_planet_components/tech_skill_planet_components.dart';

void main() {
  testWidgets('TC-FLUTTER-BUTTON-01 renders primary and default labels', (tester) async {
    await tester.pumpWidget(
      const MaterialApp(
        home: Scaffold(
          body: Column(
            children: [
              TspButton(text: '确定', variant: TspButtonVariant.primary),
              TspButton(text: '取消'),
            ],
          ),
        ),
      ),
    );
    expect(find.text('确定'), findsOneWidget);
    expect(find.text('取消'), findsOneWidget);
  });

  testWidgets('TC-FLUTTER-BADGE-01 renders variant labels', (tester) async {
    await tester.pumpWidget(
      const MaterialApp(
        home: Scaffold(
          body: Wrap(
            children: [
              TspBadge(text: 'default'),
              TspBadge(text: 'primary', variant: 'primary'),
              TspBadge(text: 'danger', variant: 'danger'),
            ],
          ),
        ),
      ),
    );
    expect(find.text('default'), findsOneWidget);
    expect(find.text('primary'), findsOneWidget);
    expect(find.text('danger'), findsOneWidget);
  });

  testWidgets('TC-FLUTTER-TAB-01 keeps top padding on bottom tab content', (tester) async {
    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: TspBottomTab(
            tabs: const [
              TspTabItem(key: 'learn', icon: Icons.home_outlined, title: '学习'),
              TspTabItem(key: 'settings', icon: Icons.settings_outlined, title: '设置'),
            ],
            selectedKey: 'learn',
          ),
        ),
      ),
    );
    expect(find.text('学习'), findsOneWidget);
    expect(find.text('设置'), findsOneWidget);
    final padding = tester.widgetList<Padding>(find.byType(Padding)).firstWhere(
      (widget) => widget.padding == const EdgeInsets.fromLTRB(12, 8, 12, 8),
    );
    expect(padding.padding, const EdgeInsets.fromLTRB(12, 8, 12, 8));
  });

  testWidgets('TC-FLUTTER-TOPBAR-01 shows title and optional back', (tester) async {
    var backCount = 0;
    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: TspTopBar(
            title: '详情',
            showBack: true,
            onBack: () => backCount += 1,
          ),
        ),
      ),
    );
    expect(find.text('详情'), findsOneWidget);
    await tester.tap(find.text('‹'));
    await tester.pump();
    expect(backCount, 1);
  });

  testWidgets('TC-FLUTTER-CHIP-BADGE-01 matches RN selected and solid fills', (tester) async {
    await tester.pumpWidget(
      const MaterialApp(
        home: Scaffold(
          body: Column(
            children: [
              TspChip(text: 'Selected', selected: true),
              TspBadge(text: 'primary', variant: 'primary'),
              TspBadge(text: 'default'),
            ],
          ),
        ),
      ),
    );
    final chip = tester.widget<Container>(
      find.descendant(of: find.widgetWithText(TspChip, 'Selected'), matching: find.byType(Container)).first,
    );
    final chipDeco = chip.decoration! as ShapeDecoration;
    expect(chipDeco.color, StarPlanetTheme.sky.selectedFill);
    final badge = tester.widget<Container>(
      find.descendant(of: find.widgetWithText(TspBadge, 'primary'), matching: find.byType(Container)).first,
    );
    expect((badge.decoration! as ShapeDecoration).color, StarPlanetTheme.sky.brandPrimary);
  });

  testWidgets('TC-FLUTTER-TABS-01 uses segmented capsule not chips', (tester) async {
    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: TspTabs(tabs: const ['全部', '已学', '未学'], selectedIndex: 1, onSelect: (_) {}),
        ),
      ),
    );
    expect(find.byType(TspChip), findsNothing);
    expect(find.text('已学'), findsOneWidget);
  });

  testWidgets('TC-FLUTTER-MODAL-01 shows tokenized panel and dual actions', (tester) async {
    var confirmed = false;
    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: TspModal(
            title: '确认',
            message: '组件弹窗完整显示。',
            confirmText: '确定',
            cancelText: '取消',
            onCancel: () {},
            onConfirm: () => confirmed = true,
          ),
        ),
      ),
    );
    expect(find.text('确认'), findsOneWidget);
    expect(find.text('组件弹窗完整显示。'), findsOneWidget);
    expect(find.text('取消'), findsOneWidget);
    expect(find.text('确定'), findsOneWidget);
    expect(find.byType(AlertDialog), findsNothing);
    await tester.tap(find.text('确定'));
    await tester.pump();
    expect(confirmed, isTrue);
  });

  testWidgets('TC-FLUTTER-SWITCH-01 flat track without inner ON text or system Switch', (tester) async {
    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: TspSwitch(
            text: 'Publish',
            checked: true,
            onChanged: (_) {},
          ),
        ),
      ),
    );
    expect(find.byType(Switch), findsNothing);
    expect(find.text('ON'), findsNothing);
    expect(find.text('Publish'), findsOneWidget);
  });

  testWidgets('TC-FLUTTER-TOAST-01 uses solid variant fill and auto-dismisses', (tester) async {
    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: Builder(
            builder: (context) => ElevatedButton(
              onPressed: () => TspToast.show(context, message: '已保存', variant: 'success'),
              child: const Text('Show Toast'),
            ),
          ),
        ),
      ),
    );
    await tester.tap(find.text('Show Toast'));
    await tester.pump();
    expect(find.text('已保存'), findsOneWidget);
    final toast = tester.widget<TspToast>(find.byType(TspToast));
    expect(toast.variant, 'success');
    final container = tester.widget<Container>(
      find.descendant(of: find.byType(TspToast), matching: find.byType(Container)).first,
    );
    final decoration = container.decoration! as ShapeDecoration;
    expect(decoration.color, StarPlanetTheme.sky.success);
    await tester.pump(TspToast.defaultDuration);
    await tester.pump();
    expect(find.text('已保存'), findsNothing);
  });

  testWidgets('TC-FLUTTER-LOADING-01 shows loading message', (tester) async {
    await tester.pumpWidget(
      const MaterialApp(
        home: Scaffold(
          body: TspLoadingDialog(message: '加载中...'),
        ),
      ),
    );
    expect(find.text('加载中...'), findsOneWidget);
    expect(find.byType(CircularProgressIndicator), findsOneWidget);
  });

  testWidgets('TC-FLUTTER-DOMAIN-01 renders DatePicker and Domain-7 shells', (tester) async {
    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: SingleChildScrollView(
            child: Column(
              children: [
                TspDatePicker(value: '2026-08-26', onChanged: (_) {}),
                TspChildSwitcher(
                  items: const [
                    TspChildSwitcherItem(id: '1', label: 'A'),
                    TspChildSwitcherItem(id: '2', label: 'B'),
                  ],
                  selectedId: '1',
                  onChanged: (_) {},
                ),
                TspScoreRuleGrid(
                  rules: const [
                    TspScoreRule(id: '1', name: '作业', value: 5, count: 0),
                  ],
                ),
                TspRedeemCardGrid(
                  items: const [TspRedeemItem(id: '1', name: '零食', cost: 10)],
                  availablePoints: 20,
                ),
                const TspCalendarHeatmap(yearMonth: '2026-08', cells: []),
                const TspPrintSheet(title: '默写', items: ['a']),
                const TspBalanceHero(total: 41),
                TspCheckInStreakCard(streakDays: 3, totalDays: 10, weekProgress: 0.5, onOpen: () {}),
                const TspButton(text: 'Loading', loading: true),
              ],
            ),
          ),
        ),
      ),
    );
    expect(find.text('可用积分'), findsOneWidget);
    expect(find.text('41'), findsOneWidget);
    expect(find.text('连续打卡'), findsOneWidget);
  });
}
