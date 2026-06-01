import 'package:tech_skill_planet_basic_controls/tech_skill_planet_basic_controls.dart';
import 'package:flutter/material.dart';

void main() => runApp(const BasicControlsExampleApp());

class BasicControlsExampleApp extends StatefulWidget {
  const BasicControlsExampleApp({super.key});

  @override
  State<BasicControlsExampleApp> createState() => _BasicControlsExampleAppState();
}

class ComponentDoc {
  const ComponentDoc(this.name, this.category, this.description);
  final String name;
  final String category;
  final String description;
}

class _BasicControlsExampleAppState extends State<BasicControlsExampleApp> {
  static const docs = [
    ComponentDoc('Button', 'Actions', '主要操作按钮，支持主按钮、默认、危险、文本和链接样式。'),
    ComponentDoc('Chip', 'Actions', '可点击标签，用于筛选、选择和轻量操作。'),
    ComponentDoc('IconButton', 'Actions', '图标按钮，用于工具栏、快捷动作和选中态。'),
    ComponentDoc('TextLink', 'Actions', '文本链接按钮，适合弱操作和辅助跳转。'),
    ComponentDoc('Card', 'Surfaces', '内容容器，用于承载分组内容、选中态和弱化背景。'),
    ComponentDoc('ListItem', 'Surfaces', '列表项，支持描述、尾部内容、选中和禁用。'),
    ComponentDoc('Empty', 'Surfaces', '空状态展示，支持说明文案和操作按钮。'),
    ComponentDoc('Alert', 'Feedback', '页面内提示，适合成功、警告、错误和普通信息。'),
    ComponentDoc('Badge', 'Feedback', '短文本状态徽标，用于标记数量、状态或风险等级。'),
    ComponentDoc('Progress', 'Feedback', '进度条，支持主色、成功、警告和危险色。'),
    ComponentDoc('Notification', 'Feedback', '通知卡片，用于强调当前任务或状态提醒。'),
    ComponentDoc('Toast', 'Feedback', '轻提示，用于短时反馈。'),
    ComponentDoc('Modal', 'Feedback', '确认弹窗，支持确认和取消按钮。'),
    ComponentDoc('Input', 'Inputs', '单行输入框，支持错误态、禁用态和受控输入。'),
    ComponentDoc('Select', 'Inputs', '选择入口，移动端默认打开底部 OptionSheet。'),
    ComponentDoc('OptionSheet', 'Inputs', '移动端底部选择弹窗，和 Select 共用选项渲染逻辑。'),
    ComponentDoc('Switch', 'Inputs', '二元开关，支持加载、禁用和开关文案。'),
    ComponentDoc('PinInput', 'Inputs', '验证码或密码输入，支持 4 到 6 位和安全显示。'),
    ComponentDoc('TopBar', 'Navigation', '顶部导航栏，支持标题、返回按钮和背景色覆盖。'),
    ComponentDoc('BottomTab', 'Navigation', '一级页面底部 Tab，通常承载 3 到 5 个入口。'),
    ComponentDoc('Tabs', 'Navigation', '内容区分段切换，适合页面内筛选和分类。'),
    ComponentDoc('StickyFooter', 'Navigation', '固定底部操作区，用于主操作按钮。'),
    ComponentDoc('Amount', 'Data', '金额或数值展示，支持币种前后置、周期和删除线。'),
    ComponentDoc('KeyValueLabel', 'Data', '键值对展示，用于摘要信息和表单确认。'),
    ComponentDoc('Stepper', 'Data', '步骤进度，支持 3 到 5 步。'),
  ];
  static const themes = [('Sky', StarPlanetTheme.sky), ('Night', StarPlanetTheme.night), ('Mint', StarPlanetTheme.mint)];
  static const languages = [('zh-CN', '简体中文', '基础组件'), ('en', 'English', 'Basic Controls'), ('ja', '日本語', '基本コンポーネント')];
  final inputController = TextEditingController();
  int themeIndex = 0;
  int languageIndex = 0;
  int selectedIndex = 1;
  int selectedTab = 0;
  String tab = 'learn';
  bool checked = true;
  bool showToast = false;
  ComponentDoc? selectedDoc;

  @override
  void dispose() {
    inputController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final theme = themes[themeIndex].$2;
    final title = selectedDoc == null ? languages[languageIndex].$3 : 'Tsp${selectedDoc!.name}';
    return MaterialApp(
      home: Scaffold(
        backgroundColor: theme.pageStart,
        body: SafeArea(
          child: Column(children: [
            TspTopBar(title: title, showBack: selectedDoc != null, theme: theme, onBack: () => setState(() => selectedDoc = null)),
            Expanded(
              child: ListView(
                padding: EdgeInsets.fromLTRB(18, 18, 18, selectedDoc == null ? 96 : 18),
                children: selectedDoc == null
                    ? (tab == 'settings' ? _settings(theme) : _componentList(theme))
                    : _componentDetail(selectedDoc!, theme),
              ),
            ),
            if (selectedDoc == null)
              TspBottomTab(
                tabs: const [TspTabItem(key: 'learn', icon: Icons.home_outlined, title: '学习'), TspTabItem(key: 'settings', icon: Icons.settings_outlined, title: '设置')],
                selectedKey: tab,
                theme: theme,
                onSelect: (value) => setState(() => tab = value),
              ),
          ]),
        ),
      ),
    );
  }

  List<Widget> _settings(StarPlanetTheme theme) => [
        TspCard(
          theme: theme,
          child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            Text('Theme Switch', style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800)),
            const SizedBox(height: 10),
            for (var i = 0; i < themes.length; i++) ...[
              TspButton(text: themes[i].$1, variant: i == themeIndex ? TspButtonVariant.primary : TspButtonVariant.standard, theme: theme, onTap: () => setState(() => themeIndex = i)),
              if (i < themes.length - 1) const SizedBox(height: 8),
            ],
          ]),
        ),
        const SizedBox(height: 14),
        TspCard(
          theme: theme,
          child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            Text('Language Switch', style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800)),
            const SizedBox(height: 10),
            for (var i = 0; i < languages.length; i++) ...[
              TspButton(text: languages[i].$2, variant: i == languageIndex ? TspButtonVariant.primary : TspButtonVariant.standard, theme: theme, onTap: () => setState(() => languageIndex = i)),
              if (i < languages.length - 1) const SizedBox(height: 8),
            ],
          ]),
        ),
      ];

  List<Widget> _componentList(StarPlanetTheme theme) {
    final widgets = <Widget>[];
    // Keep the Flutter sample structure aligned with Web/RN/Android/iOS/Kuikly.
    for (final category in docs.map((doc) => doc.category).toSet()) {
      widgets.add(Text(category, style: TextStyle(color: theme.textSecondary, fontWeight: FontWeight.w900)));
      widgets.add(const SizedBox(height: 8));
      for (final doc in docs.where((item) => item.category == category)) {
        widgets.add(TspListItem(title: 'Tsp${doc.name}', message: doc.description, trailing: '›', theme: theme, onTap: () => setState(() => selectedDoc = doc)));
        widgets.add(const SizedBox(height: 10));
      }
      widgets.add(const SizedBox(height: 8));
    }
    return widgets;
  }

  List<Widget> _componentDetail(ComponentDoc doc, StarPlanetTheme theme) => [
        TspCard(
          theme: theme,
          child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            Text(doc.category, style: TextStyle(color: theme.brandPrimary, fontWeight: FontWeight.w900)),
            const SizedBox(height: 8),
            Text('Tsp${doc.name}', style: TextStyle(color: theme.textPrimary, fontSize: 26, fontWeight: FontWeight.w900)),
            const SizedBox(height: 8),
            Text(doc.description, style: TextStyle(color: theme.textSecondary)),
          ]),
        ),
        const SizedBox(height: 14),
        TspCard(theme: theme, child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [Text('使用案例', style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w900)), const SizedBox(height: 12), _preview(doc.name, theme)])),
        const SizedBox(height: 14),
        TspCard(
          theme: theme,
          child: Wrap(spacing: 8, runSpacing: 8, children: ['React', 'Vue', 'Android', 'iOS', 'Flutter', 'Kuikly', 'RN'].map((item) => TspBadge(text: item, theme: theme)).toList()),
        ),
      ];

  Widget _preview(String name, StarPlanetTheme theme) {
    switch (name) {
      case 'Button':
        return Column(children: [TspButton(text: 'primary', variant: TspButtonVariant.primary, theme: theme), const SizedBox(height: 10), TspButton(text: 'default', theme: theme), const SizedBox(height: 10), TspButton(text: 'danger', variant: TspButtonVariant.danger, theme: theme)]);
      case 'Card':
        return TspCard(selected: true, theme: theme, child: Text('Selected card', style: TextStyle(color: theme.textPrimary)));
      case 'Alert':
        return Column(children: [TspAlert(title: 'success', message: 'Theme is applied.', variant: TspAlertVariant.success, theme: theme), const SizedBox(height: 10), TspAlert(title: 'warning', message: 'Check required fields.', variant: TspAlertVariant.warning, theme: theme)]);
      case 'Badge':
        return Wrap(spacing: 8, runSpacing: 8, children: ['default', 'primary', 'success', 'warning', 'danger'].map((item) => TspBadge(text: item, variant: item, theme: theme)).toList());
      case 'Chip':
        return Wrap(spacing: 8, children: [TspChip(text: 'Default', theme: theme), TspChip(text: 'Selected', selected: true, theme: theme), TspChip(text: 'Disabled', disabled: true, theme: theme)]);
      case 'Input':
        return TspInput(controller: inputController, placeholder: 'Input', theme: theme);
      case 'Select':
        return TspSelect(options: const ['A', 'B', 'C'], selectedIndex: selectedIndex, theme: theme, onSelect: (value) => setState(() => selectedIndex = value));
      case 'OptionSheet':
        return TspOptionSheet(options: const ['A', 'B', 'C'], selectedIndex: selectedIndex, theme: theme, onSelect: (value) => setState(() => selectedIndex = value));
      case 'Switch':
        return TspSwitch(text: 'Switch', checked: checked, theme: theme, onChanged: (value) => setState(() => checked = value));
      case 'Progress':
        return Column(children: [TspProgress(progress: 38, theme: theme), const SizedBox(height: 10), TspProgress(progress: 68, variant: 'success', theme: theme), const SizedBox(height: 10), TspProgress(progress: 82, variant: 'danger', theme: theme)]);
      case 'TopBar':
        return TspTopBar(title: '基础组件', showBack: true, theme: theme);
      case 'BottomTab':
        return TspBottomTab(tabs: const [TspTabItem(key: 'learn', icon: Icons.home_outlined, title: '学习'), TspTabItem(key: 'settings', icon: Icons.settings_outlined, title: '设置')], selectedKey: 'learn', theme: theme);
      case 'Tabs':
        return TspTabs(tabs: const ['全部', '已学', '未学'], selectedIndex: selectedTab, theme: theme, onSelect: (value) => setState(() => selectedTab = value));
      case 'Amount':
        return Column(children: [TspAmount(symbol: r'$', value: '128.80', cycle: 'month', theme: theme), const SizedBox(height: 10), TspAmount(symbol: r'$', value: '199.00', strikeThrough: true, theme: theme)]);
      case 'IconButton':
        return Row(children: [TspIconButton(icon: Icons.music_note, selected: true, theme: theme), TspIconButton(icon: Icons.check, theme: theme), TspIconButton(icon: Icons.close, disabled: true, theme: theme)]);
      case 'KeyValueLabel':
        return TspKeyValueLabel(label: 'Progress', value: '12/48', theme: theme);
      case 'Notification':
        return TspNotification(title: '通知', message: '继续学习。', variant: 'alert', theme: theme);
      case 'TextLink':
        return TspTextLink(text: 'Text Link', theme: theme);
      case 'Stepper':
        return TspStepper(stepCount: 5, currentStep: 3, theme: theme);
      case 'StickyFooter':
        return TspStickyFooter(theme: theme, child: TspButton(text: 'Sticky Footer', variant: TspButtonVariant.primary, theme: theme));
      case 'PinInput':
        return TspPinInput(value: '2468', secure: true, theme: theme);
      case 'ListItem':
        return TspListItem(title: '列表项', message: '选中状态', trailing: '›', selected: true, theme: theme);
      case 'Empty':
        return TspEmpty(title: '空状态', message: '暂无记录。', actionText: '操作', theme: theme);
      case 'Toast':
        return Stack(children: [TspButton(text: 'Show Toast', variant: TspButtonVariant.primary, theme: theme, onTap: () => setState(() => showToast = !showToast)), if (showToast) Positioned.fill(child: TspToast(message: '已保存', variant: 'success', theme: theme))]);
      case 'Modal':
        return TspModalButton(theme: theme);
      default:
        return const SizedBox.shrink();
    }
  }
}
