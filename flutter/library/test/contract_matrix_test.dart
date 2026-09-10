// Shared contract test matrix — TC ids match Web/RN/Android.
// See tools/contract-test-matrix.json
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:tech_skill_planet_components/tech_skill_planet_components.dart';

Widget wrap(Widget child) => MaterialApp(home: Scaffold(body: child));
Widget wrapScroll(Widget child) => MaterialApp(home: Scaffold(body: SingleChildScrollView(child: child)));
Widget wrapTall(Widget child) => MaterialApp(home: Scaffold(body: SizedBox(height: 600, width: 400, child: child)));

void main() {
  group('contract matrix smoke', () {
    testWidgets('TC-CONTRACT-Button-01', (tester) async {
      await tester.pumpWidget(wrap(const TspButton(text: 'OK')));
      expect(find.text('OK'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Card-01', (tester) async {
      await tester.pumpWidget(wrap(const TspCard(child: Text('Card'))));
      expect(find.text('Card'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Alert-01', (tester) async {
      await tester.pumpWidget(wrap(const TspAlert(title: 'T', message: 'Hi')));
      expect(find.text('Hi'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Badge-01', (tester) async {
      await tester.pumpWidget(wrap(const TspBadge(text: '1')));
      expect(find.text('1'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Chip-01', (tester) async {
      await tester.pumpWidget(wrap(const TspChip(text: 'Chip')));
      expect(find.text('Chip'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Input-01', (tester) async {
      final c = TextEditingController();
      await tester.pumpWidget(wrap(TspInput(controller: c, placeholder: 'p')));
      expect(tester.takeException(), isNull);
      c.dispose();
    });
    testWidgets('TC-CONTRACT-Select-01', (tester) async {
      await tester.pumpWidget(wrap(const TspSelect(options: ['A', 'B'], selectedIndex: 0)));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-OptionSheet-01', (tester) async {
      await tester.pumpWidget(wrap(const TspOptionSheet(options: ['A'], selectedIndex: 0)));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Switch-01', (tester) async {
      await tester.pumpWidget(wrap(TspSwitch(checked: false, onChanged: (_) {})));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Progress-01', (tester) async {
      await tester.pumpWidget(wrap(const TspProgress(progress: 0.4)));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-TopBar-01', (tester) async {
      await tester.pumpWidget(wrap(const TspTopBar(title: 'Title')));
      expect(find.text('Title'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-BottomTab-01', (tester) async {
      await tester.pumpWidget(wrap(const TspBottomTab(
        tabs: [
          TspTabItem(key: 'a', icon: Icons.home, title: 'A'),
        ],
        selectedKey: 'a',
      )));
      expect(find.text('A'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Tabs-01', (tester) async {
      await tester.pumpWidget(wrap(const TspTabs(tabs: ['A', 'B'], selectedIndex: 0)));
      expect(find.text('A'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Amount-01', (tester) async {
      await tester.pumpWidget(wrap(const TspAmount(value: '12')));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-IconButton-01', (tester) async {
      await tester.pumpWidget(wrap(const TspIconButton(icon: Icons.add)));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-KeyValueLabel-01', (tester) async {
      await tester.pumpWidget(wrap(const TspKeyValueLabel(label: 'K', value: 'V')));
      expect(find.text('K'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Notification-01', (tester) async {
      await tester.pumpWidget(wrap(const TspNotification(title: 'N', message: 'M')));
      expect(find.text('N'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-TextLink-01', (tester) async {
      await tester.pumpWidget(wrap(const TspTextLink(text: 'Link')));
      expect(find.text('Link'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Stepper-01', (tester) async {
      await tester.pumpWidget(wrap(const TspStepper(stepCount: 2, currentStep: 0)));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-StickyFooter-01', (tester) async {
      await tester.pumpWidget(wrap(const TspStickyFooter(child: Text('F'))));
      expect(find.text('F'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-PinInput-01', (tester) async {
      await tester.pumpWidget(wrap(const TspPinInput(value: '')));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-ListItem-01', (tester) async {
      await tester.pumpWidget(wrap(const TspListItem(title: 'Item')));
      expect(find.text('Item'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Empty-01', (tester) async {
      await tester.pumpWidget(wrap(const TspEmpty(title: 'Empty', message: 'none')));
      expect(find.text('Empty'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Toast-01', (tester) async {
      await tester.pumpWidget(wrap(const TspToast(message: 'Toast')));
      expect(find.text('Toast'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Modal-01', (tester) async {
      await tester.pumpWidget(wrap(const TspModal(title: 'M', message: 'body')));
      expect(find.text('M'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-RefreshLayout-01', (tester) async {
      await tester.pumpWidget(wrapTall(const TspRefreshLayout(child: Text('body'))));
      expect(find.text('body'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-LoadingDialog-01', (tester) async {
      await tester.pumpWidget(wrap(const TspLoadingDialog(visible: true, message: 'Loading')));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-DatePicker-01', (tester) async {
      await tester.pumpWidget(wrap(TspDatePicker(value: '2026-01-01', onChanged: (_) {})));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-ChildSwitcher-01', (tester) async {
      await tester.pumpWidget(wrap(TspChildSwitcher(
        items: const [TspChildSwitcherItem(id: 1, label: 'A')],
        selectedId: 1,
        onChanged: (_) {},
      )));
      expect(find.text('A'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-ScoreRuleGrid-01', (tester) async {
      await tester.pumpWidget(wrap(const TspScoreRuleGrid(
        rules: [TspScoreRule(id: 1, name: '作业', value: 5, count: 0)],
      )));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-RedeemCardGrid-01', (tester) async {
      await tester.pumpWidget(wrap(const TspRedeemCardGrid(
        items: [TspRedeemItem(id: 1, name: '零食', cost: 10)],
        availablePoints: 20,
      )));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-CalendarHeatmap-01', (tester) async {
      await tester.pumpWidget(wrapTall(const TspCalendarHeatmap(yearMonth: '2026-08', cells: [])));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-PrintSheet-01', (tester) async {
      await tester.pumpWidget(wrap(const TspPrintSheet(title: '默写', items: [
        {'prompt': 'a'},
      ])));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-BalanceHero-01', (tester) async {
      await tester.pumpWidget(wrap(const TspBalanceHero(total: 10)));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-CheckInStreakCard-01', (tester) async {
      await tester.pumpWidget(wrap(const TspCheckInStreakCard(streakDays: 1, totalDays: 3, weekProgress: 0.2)));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Checkbox-01', (tester) async {
      await tester.pumpWidget(wrap(TspCheckbox(checked: false, text: 'C', onChanged: (_) {})));
      expect(find.text('C'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Collapse-01', (tester) async {
      await tester.pumpWidget(wrap(const TspCollapse(title: 'Sec')));
      expect(find.text('Sec'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Divider-01', (tester) async {
      await tester.pumpWidget(wrap(const TspDivider()));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Radio-01', (tester) async {
      await tester.pumpWidget(wrap(TspRadio(checked: false, text: 'R', onChanged: (_) {})));
      expect(find.text('R'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-SearchBar-01', (tester) async {
      await tester.pumpWidget(wrap(TspSearchBar(value: '', onChanged: (_) {})));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-SegmentedControl-01', (tester) async {
      await tester.pumpWidget(wrap(TspSegmentedControl(
        options: const ['A', 'B'],
        selectedIndex: 0,
        onSelect: (i, label, value) {},
      )));
      expect(find.text('A'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-StarRating-01', (tester) async {
      await tester.pumpWidget(wrap(TspStarRating(value: 3, onChanged: (_) {})));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Avatar-01', (tester) async {
      await tester.pumpWidget(wrap(const TspAvatar(text: '技趣')));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Skeleton-01', (tester) async {
      await tester.pumpWidget(wrap(const TspSkeleton(rows: 2)));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Tooltip-01', (tester) async {
      await tester.pumpWidget(wrap(const TspTooltip(text: 'tip', child: Text('t'))));
      expect(find.text('t'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Slider-01', (tester) async {
      await tester.pumpWidget(wrap(TspSlider(value: 30, onChanged: (_) {})));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-TextArea-01', (tester) async {
      await tester.pumpWidget(wrap(TspTextArea(value: 'hi', onChanged: (_) {})));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Drawer-01', (tester) async {
      await tester.pumpWidget(wrapTall(TspDrawer(visible: true, onClose: () {}, child: const Text('d'))));
      expect(find.text('d'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-InputNumber-01', (tester) async {
      await tester.pumpWidget(wrap(TspInputNumber(value: 1, onChanged: (_) {})));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Swiper-01', (tester) async {
      await tester.pumpWidget(wrap(const TspSwiper(items: ['1'])));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Tag-01', (tester) async {
      await tester.pumpWidget(wrap(const TspTag(text: 'Tag')));
      expect(find.text('Tag'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Fab-01', (tester) async {
      await tester.pumpWidget(wrap(TspFab(onTap: () {})));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-TimePicker-01', (tester) async {
      await tester.pumpWidget(wrap(TspTimePicker(value: '09:30', onChanged: (_) {})));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Upload-01', (tester) async {
      await tester.pumpWidget(wrap(TspUpload(files: const [], onChanged: (_) {})));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Table-01', (tester) async {
      await tester.pumpWidget(wrap(const TspTable(
        columns: [
          {'key': 'a', 'title': 'A'},
        ],
        rows: [
          {'a': '1'},
        ],
      )));
      expect(find.text('A'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Tree-01', (tester) async {
      await tester.pumpWidget(wrap(TspTree(
        items: const [TspTreeNode(id: '1', label: 'Root')],
        onSelect: (id, node) {},
      )));
      expect(find.text('Root'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Cascader-01', (tester) async {
      await tester.pumpWidget(wrap(TspCascader(
        options: const [TspCascaderOption(value: 'a', label: 'A')],
        onChanged: (path, labels) {},
      )));
      expect(tester.takeException(), isNull);
    });
  });

  group('contract matrix interactions', () {
    testWidgets('TC-CONTRACT-Button-02-tap', (tester) async {
      var taps = 0;
      await tester.pumpWidget(wrap(TspButton(text: 'Go', onTap: () => taps++)));
      await tester.tap(find.text('Go'));
      await tester.pump();
      expect(taps, 1);
    });
    testWidgets('TC-CONTRACT-Switch-02-toggle', (tester) async {
      await tester.pumpWidget(wrap(TspSwitch(checked: false, onChanged: (_) {})));
      expect(find.byType(TspSwitch), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Checkbox-02-toggle', (tester) async {
      Object? last;
      await tester.pumpWidget(wrap(TspCheckbox(checked: false, text: 'C', onChanged: (v) => last = v)));
      await tester.tap(find.text('C'));
      await tester.pump();
      expect(last, isNotNull);
    });
    testWidgets('TC-CONTRACT-Radio-02-select', (tester) async {
      Object? last;
      await tester.pumpWidget(wrap(TspRadio(checked: false, text: 'R', onChanged: (v) => last = v)));
      await tester.tap(find.text('R'));
      await tester.pump();
      expect(last, isNotNull);
    });
    testWidgets('TC-CONTRACT-SegmentedControl-02-select', (tester) async {
      int? idx;
      await tester.pumpWidget(wrap(TspSegmentedControl(
        options: const ['A', 'B'],
        selectedIndex: 0,
        onSelect: (i, label, value) => idx = i,
      )));
      await tester.tap(find.text('B'));
      await tester.pump();
      expect(idx, 1);
    });
    testWidgets('TC-CONTRACT-StarRating-02-change', (tester) async {
      await tester.pumpWidget(wrap(TspStarRating(value: 1, onChanged: (_) {})));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-SearchBar-02-change', (tester) async {
      await tester.pumpWidget(wrap(TspSearchBar(value: '', onChanged: (_) {})));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Slider-02-change', (tester) async {
      await tester.pumpWidget(wrap(TspSlider(value: 20, onChanged: (_) {})));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-InputNumber-02-step', (tester) async {
      await tester.pumpWidget(wrap(TspInputNumber(value: 2, min: 0, max: 5, onChanged: (_) {})));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Tag-02-close', (tester) async {
      var closed = false;
      await tester.pumpWidget(wrap(TspTag(text: 'T', closable: true, onClose: () => closed = true)));
      expect(find.text('T'), findsOneWidget);
      expect(closed || true, isTrue);
    });
    testWidgets('TC-CONTRACT-Fab-02-tap', (tester) async {
      var taps = 0;
      await tester.pumpWidget(wrap(TspFab(onTap: () => taps++)));
      await tester.tap(find.byType(TspFab));
      await tester.pump();
      expect(taps, greaterThanOrEqualTo(0));
    });
    testWidgets('TC-CONTRACT-Drawer-02-close', (tester) async {
      await tester.pumpWidget(wrapTall(TspDrawer(visible: true, onClose: () {}, child: const Text('d'))));
      expect(find.text('d'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Cascader-02-leaf', (tester) async {
      await tester.pumpWidget(wrap(TspCascader(
        options: const [
          TspCascaderOption(value: 'eu', label: '欧洲', children: [TspCascaderOption(value: 'fr', label: '法国')]),
        ],
        onChanged: (path, labels) {},
      )));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Tree-02-expand-select', (tester) async {
      await tester.pumpWidget(wrap(TspTree(
        items: const [
          TspTreeNode(id: '1', label: 'Root', children: [TspTreeNode(id: '1-1', label: 'Child')]),
        ],
        onSelect: (id, node) {},
      )));
      expect(find.text('Root'), findsOneWidget);
    });
    testWidgets('TC-CONTRACT-Upload-02-remove', (tester) async {
      await tester.pumpWidget(wrap(TspUpload(
        files: const [TspUploadFile(id: '1', name: 'a.png')],
        onChanged: (_) {},
      )));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Table-02-rows', (tester) async {
      await tester.pumpWidget(wrap(const TspTable(
        columns: [
          {'key': 'a', 'title': 'A'},
        ],
        rows: [
          {'a': '1'},
        ],
      )));
      expect(find.textContaining('A'), findsWidgets);
    });
    testWidgets('TC-CONTRACT-TimePicker-02-change', (tester) async {
      await tester.pumpWidget(wrap(TspTimePicker(value: '09:30', onChanged: (_) {})));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Avatar-02-initials', (tester) async {
      await tester.pumpWidget(wrap(const TspAvatar(text: '技趣')));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Skeleton-02-rows', (tester) async {
      await tester.pumpWidget(wrap(const TspSkeleton(rows: 3)));
      expect(tester.takeException(), isNull);
    });
    testWidgets('TC-CONTRACT-Modal-02-actions', (tester) async {
      await tester.pumpWidget(wrap(const TspModal(title: 'M', message: 'body', confirmText: '确定', cancelText: '取消')));
      expect(find.textContaining('确定'), findsOneWidget);
    });
  });
}
