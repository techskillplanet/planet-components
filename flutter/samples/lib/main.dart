import 'package:tech_skill_planet_components/tech_skill_planet_components.dart';
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
    ComponentDoc('LoadingDialog', 'Feedback', '阻塞式加载弹窗，适合提交与同步等待。'),
    ComponentDoc('Input', 'Inputs', '单行输入框，支持错误态、禁用态和受控输入。'),
    ComponentDoc('Select', 'Inputs', '选择入口，移动端默认打开底部 OptionSheet。'),
    ComponentDoc('OptionSheet', 'Inputs', '移动端底部选择弹窗，和 Select 共用选项渲染逻辑。'),
    ComponentDoc('Switch', 'Inputs', '二元开关，支持加载、禁用和开关文案。'),
    ComponentDoc('Checkbox', 'Inputs', '复选框，支持选中、禁用与变体。'),
    ComponentDoc('Radio', 'Inputs', '单选选项，分组互斥由业务层管理。'),
    ComponentDoc('SearchBar', 'Inputs', '搜索输入条，支持占位符与禁用。'),
    ComponentDoc('SegmentedControl', 'Inputs', '分段选择器，支持字符串或 label/value 选项。'),
    ComponentDoc('StarRating', 'Inputs', '星级评分，支持只读变体。'),
    ComponentDoc('PinInput', 'Inputs', '验证码或密码输入，支持 4 到 6 位和安全显示。'),
    ComponentDoc('Collapse', 'Surfaces', '可展开折叠面板，支持标题与内容。'),
    ComponentDoc('Divider', 'Surfaces', '水平分割线，text 仅用于无障碍标签。'),
    ComponentDoc('RefreshLayout', 'Surfaces', '下拉刷新与上拉加载容器，适合列表与滚动内容。'),
    ComponentDoc('TopBar', 'Navigation', '顶部导航栏，支持标题、返回按钮和背景色覆盖。'),
    ComponentDoc('BottomTab', 'Navigation', '一级页面底部 Tab，通常承载 3 到 5 个入口。'),
    ComponentDoc('Tabs', 'Navigation', '内容区分段切换，适合页面内筛选和分类。'),
    ComponentDoc('StickyFooter', 'Navigation', '固定底部操作区，用于主操作按钮。'),
    ComponentDoc('Amount', 'Data', '金额或数值展示，支持币种前后置、周期和删除线。'),
    ComponentDoc('KeyValueLabel', 'Data', '键值对展示，用于摘要信息和表单确认。'),
    ComponentDoc('Stepper', 'Data', '步骤进度，支持 3 到 5 步。'),
    ComponentDoc('DatePicker', 'Domain', '主题化 YYYY-MM-DD 日期选择。'),
    ComponentDoc('ChildSwitcher', 'Domain', '单选儿童/对象切换器，支持 chip 与 tabs。'),
    ComponentDoc('ScoreRuleGrid', 'Domain', '积分规则卡片网格，支持 +1 与列布局。'),
    ComponentDoc('RedeemCardGrid', 'Domain', '兑换商品卡片，支持冻结与积分不足态。'),
    ComponentDoc('CalendarHeatmap', 'Domain', '月度打卡热力图，支持全勤/部分/未打/豁免。'),
    ComponentDoc('PrintSheet', 'Domain', 'A4 听写/填空打印版面。'),
    ComponentDoc('BalanceHero', 'Domain', '可用积分英雄区，可展示拆分明细。'),
    ComponentDoc('CheckInStreakCard', 'Domain', '连续打卡摘要卡片，可打开详情。'),
    ComponentDoc('Avatar', 'Surfaces', '圆形头像，支持首字母或图片。'),
    ComponentDoc('Skeleton', 'Feedback', '加载骨架占位，可选头像与闪烁动画。'),
    ComponentDoc('Tooltip', 'Feedback', '悬停/长按气泡提示。'),
    ComponentDoc('Slider', 'Inputs', '连续数值滑块，支持最小/最大/步进。'),
    ComponentDoc('TextArea', 'Inputs', '多行文本输入，支持错误与禁用态。'),
    ComponentDoc('Drawer', 'Surfaces', '侧边或底部抽屉面板。'),
    ComponentDoc('InputNumber', 'Inputs', '数字步进器，加减按钮调节数值。'),
    ComponentDoc('Swiper', 'Navigation', '简易轮播，支持指示点与自动播放。'),
    ComponentDoc('Tag', 'Actions', '可关闭或可选中的状态标签（区别于 Chip 筛选标签）。'),
    ComponentDoc('Fab', 'Actions', '悬浮操作按钮，支持图标或扩展文字。'),
    ComponentDoc('TimePicker', 'Inputs', 'HH:mm 时间选择，与 DatePicker 配套。'),
    ComponentDoc('Upload', 'Inputs', '轻量文件列表与选择触发（不含上传后端）。'),
    ComponentDoc('Table', 'Data', '轻量数据表格，支持斑马纹。'),
    ComponentDoc('Tree', 'Data', '嵌套树选择，支持展开与选中。'),
    ComponentDoc('Cascader', 'Data', '多级级联选择器。'),
  ];
  static const themes = [
    ('Sky', StarPlanetTheme.sky),
    ('Night', StarPlanetTheme.night),
    ('Mint', StarPlanetTheme.mint),
    ('Sunrise', StarPlanetTheme.sunrise),
  ];
  static const languages = [('zh-CN', '简体中文', '基础组件'), ('en', 'English', 'Basic Controls'), ('ja', '日本語', '基本コンポーネント')];
  final inputController = TextEditingController();
  int themeIndex = 0;
  int languageIndex = 0;
  int selectedIndex = 1;
  int selectedTab = 0;
  String tab = 'learn';
  bool checked = true;
  bool expanded = false;
  String radioValue = 'a';
  String searchValue = '';
  int segmentIndex = 0;
  int starValue = 3;
  String dateValue = '2026-08-26';
  Object childId = 1;
  bool showDrawer = false;
  double sliderValue = 40;
  String textAreaValue = '';
  num inputNumberValue = 3;
  int swiperIndex = 0;
  String timeValue = '09:30';
  List<TspUploadFile> uploadFiles = [const TspUploadFile(id: '1', name: 'readme.md')];
  String treeSelectedId = 'a1';
  List<String> treeExpandedIds = ['a'];
  List<String> cascaderValue = [];
  Object? showLoading;
  bool refreshing = false;
  bool loadingMore = false;
  List<int> refreshItems = [1, 2, 3, 4, 5, 6];
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
      theme: ThemeData(
        useMaterial3: true,
        colorScheme: ColorScheme.fromSeed(
          seedColor: theme.brandPrimary,
          primary: theme.brandPrimary,
          surface: theme.surfaceRaised,
        ),
        fontFamily: 'Roboto',
      ),
      home: Scaffold(
        backgroundColor: theme.pageStart,
        body: Column(children: [
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
            SafeArea(
              top: false,
              child: TspBottomTab(
                tabs: const [TspTabItem(key: 'learn', icon: Icons.home_outlined, title: '学习'), TspTabItem(key: 'settings', icon: Icons.settings_outlined, title: '设置')],
                selectedKey: tab,
                theme: theme,
                onSelect: (value) => setState(() => tab = value),
              ),
            ),
        ]),
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
        return Builder(
          builder: (context) => TspButton(
            text: 'Open OptionSheet',
            variant: TspButtonVariant.primary,
            theme: theme,
            onTap: () => TspOptionSheet.show(
              context,
              options: const ['A', 'B', 'C'],
              selectedIndex: selectedIndex,
              theme: theme,
              onSelect: (value) => setState(() => selectedIndex = value),
            ),
          ),
        );
      case 'Switch':
        return Column(crossAxisAlignment: CrossAxisAlignment.stretch, children: [
          TspSwitch(text: 'Publish', checked: checked, theme: theme, onChanged: (value) => setState(() => checked = value)),
          const SizedBox(height: 12),
          TspSwitch(text: 'Small', checked: checked, variant: 'sm', theme: theme, onChanged: (value) => setState(() => checked = value)),
          const SizedBox(height: 12),
          TspSwitch(text: 'Loading', checked: true, loading: true, theme: theme),
          const SizedBox(height: 12),
          const TspSwitch(text: 'Disabled', checked: false, disabled: true),
        ]);
      case 'Checkbox':
        return Column(crossAxisAlignment: CrossAxisAlignment.stretch, children: [
          TspCheckbox(text: 'Agree', checked: checked, theme: theme, onChanged: (v) => setState(() => checked = v)),
          const SizedBox(height: 12),
          const TspCheckbox(text: 'Disabled', checked: true, disabled: true),
        ]);
      case 'Radio':
        return Column(crossAxisAlignment: CrossAxisAlignment.stretch, children: [
          TspRadio(text: 'Option A', checked: radioValue == 'a', theme: theme, onChanged: (_) => setState(() => radioValue = 'a')),
          const SizedBox(height: 12),
          TspRadio(text: 'Option B', checked: radioValue == 'b', theme: theme, onChanged: (_) => setState(() => radioValue = 'b')),
        ]);
      case 'SearchBar':
        return TspSearchBar(
          value: searchValue,
          placeholder: 'Search…',
          theme: theme,
          onChanged: (v) => setState(() => searchValue = v),
        );
      case 'SegmentedControl':
        return TspSegmentedControl(
          options: const ['日', '周', '月'],
          selectedIndex: segmentIndex,
          theme: theme,
          onSelect: (index, _, __) => setState(() => segmentIndex = index),
        );
      case 'StarRating':
        return Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          TspStarRating(value: starValue, theme: theme, onChanged: (v) => setState(() => starValue = v)),
          const SizedBox(height: 12),
          const TspStarRating(value: 4, variant: 'readonly'),
        ]);
      case 'Collapse':
        return TspCollapse(
          title: 'Details',
          message: 'Expanded body uses theme tokens.',
          expanded: expanded,
          theme: theme,
          onChanged: (v) => setState(() => expanded = v),
        );
      case 'Divider':
        return Column(crossAxisAlignment: CrossAxisAlignment.stretch, children: [
          Text('Above', style: TextStyle(color: theme.textSecondary)),
          TspDivider(text: 'Section', theme: theme),
          Text('Below', style: TextStyle(color: theme.textSecondary)),
        ]);
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
        return Builder(
          builder: (context) => TspButton(
            text: 'Show Toast',
            variant: TspButtonVariant.primary,
            theme: theme,
            onTap: () => TspToast.show(context, message: '已保存', variant: 'success', theme: theme),
          ),
        );
      case 'Modal':
        return TspModalButton(theme: theme);
      case 'LoadingDialog':
        return Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            TspButton(
              text: 'Show Loading',
              variant: TspButtonVariant.primary,
              theme: theme,
              onTap: () {
                setState(() => showLoading = true);
                Future.delayed(const Duration(milliseconds: 1400), () {
                  if (mounted) setState(() => showLoading = false);
                });
              },
            ),
            const SizedBox(height: 10),
            TspButton(
              text: 'Compact Loading',
              theme: theme,
              onTap: () {
                setState(() => showLoading = 'compact');
                Future.delayed(const Duration(milliseconds: 1400), () {
                  if (mounted) setState(() => showLoading = false);
                });
              },
            ),
            if (showLoading != null && showLoading != false)
              Padding(
                padding: const EdgeInsets.only(top: 12),
                child: SizedBox(
                  height: 160,
                  child: TspLoadingDialog(
                    visible: true,
                    variant: showLoading == 'compact' ? 'compact' : 'default',
                    message: '同步主题中...',
                    theme: theme,
                  ),
                ),
              ),
          ],
        );
      case 'RefreshLayout':
        return SizedBox(
          height: 280,
          child: TspRefreshLayout(
            theme: theme,
            refreshing: refreshing,
            loadingMore: loadingMore,
            onRefresh: () async {
              setState(() => refreshing = true);
              await Future.delayed(const Duration(milliseconds: 900));
              if (!mounted) return;
              setState(() {
                refreshItems = [1, 2, 3, 4, 5, 6];
                refreshing = false;
              });
            },
            onLoadMore: () {
              if (loadingMore || refreshing) return;
              setState(() => loadingMore = true);
              Future.delayed(const Duration(milliseconds: 900), () {
                if (!mounted) return;
                setState(() {
                  refreshItems = [...refreshItems, refreshItems.length + 1, refreshItems.length + 2];
                  loadingMore = false;
                });
              });
            },
            child: Column(
              children: refreshItems
                  .map((item) => TspListItem(
                        title: '列表项 $item',
                        message: '下拉刷新，上拉加载更多',
                        trailing: '›',
                        theme: theme,
                      ))
                  .toList(),
            ),
          ),
        );
      case 'DatePicker':
        return TspDatePicker(
          value: dateValue,
          placeholder: '选择日期',
          theme: theme,
          onChanged: (value) => setState(() => dateValue = value),
        );
      case 'ChildSwitcher':
        return Column(
          children: [
            TspChildSwitcher(
              items: const [
                TspChildSwitcherItem(id: 1, label: '悦悦', emoji: '👧'),
                TspChildSwitcherItem(id: 2, label: '佑佑', emoji: '👦'),
              ],
              selectedId: childId,
              theme: theme,
              onChanged: (id) => setState(() => childId = id),
            ),
            const SizedBox(height: 10),
            TspChildSwitcher(
              variant: 'tabs',
              items: const [
                TspChildSwitcherItem(id: 1, label: '悦悦'),
                TspChildSwitcherItem(id: 2, label: '佑佑'),
              ],
              selectedId: childId,
              theme: theme,
              onChanged: (id) => setState(() => childId = id),
            ),
          ],
        );
      case 'ScoreRuleGrid':
        return TspScoreRuleGrid(
          rules: const [
            TspScoreRule(id: 1, name: '按时认真完成作业', icon: '📝', value: 5, count: 1, dailyLimit: 1),
            TspScoreRule(id: 2, name: '作业潦草', icon: '✏️', value: -2, count: 0, dailyLimit: 2),
          ],
          columns: 'auto',
          theme: theme,
          onIncrement: (_) {},
        );
      case 'RedeemCardGrid':
        return TspRedeemCardGrid(
          items: const [
            TspRedeemItem(id: 1, name: '零食', icon: '🍬', cost: 15),
            TspRedeemItem(id: 2, name: '游戏', icon: '🎮', cost: 30),
          ],
          availablePoints: 20,
          theme: theme,
          onRedeem: (_) {},
        );
      case 'CalendarHeatmap':
        return TspCalendarHeatmap(
          yearMonth: '2026-08',
          cells: const [
            TspCalendarCell(date: '2026-08-01', level: 'full'),
            TspCalendarCell(date: '2026-08-02', level: 'partial'),
            TspCalendarCell(date: '2026-08-03', level: 'exempt'),
          ],
          theme: theme,
        );
      case 'PrintSheet':
        return TspPrintSheet(
          title: '错字默写',
          variant: 'pinyin',
          items: const [
            {'prompt': 'dǐng'},
            {'prompt': 'lù'},
            {'prompt': 'yàn'},
            {'prompt': 'xīn'},
            {'prompt': 'wǎn'},
          ],
          columns: 5,
          theme: theme,
        );
      case 'BalanceHero':
        return TspBalanceHero(
          total: 41,
          breakdown: const {
            'balance': 40,
            'ruleScore': 11,
            'streakBonus': 5,
            'redeemTotal': 15,
          },
          theme: theme,
        );
      case 'CheckInStreakCard':
        return TspCheckInStreakCard(
          streakDays: 7,
          totalDays: 45,
          weekProgress: 0.85,
          theme: theme,
          onOpen: () {},
        );
      case 'Avatar':
        return Row(
          children: [
            TspAvatar(text: '技趣', theme: theme),
            const SizedBox(width: 12),
            TspAvatar(text: 'SP', variant: 'primary', size: 'lg', theme: theme),
            const SizedBox(width: 12),
            TspAvatar(text: 'A', variant: 'subtle', size: 'sm', theme: theme),
          ],
        );
      case 'Skeleton':
        return TspSkeleton(rows: 3, animated: true, avatar: true, theme: theme);
      case 'Tooltip':
        return TspTooltip(
          text: 'Sky Planet tip',
          placement: 'top',
          theme: theme,
          child: TspButton(text: 'Hover me', variant: TspButtonVariant.standard, theme: theme),
        );
      case 'Slider':
        return TspSlider(
          value: sliderValue,
          min: 0,
          max: 100,
          theme: theme,
          onChanged: (v) => setState(() => sliderValue = v),
        );
      case 'TextArea':
        return TspTextArea(
          value: textAreaValue,
          placeholder: 'Write a note…',
          rows: 3,
          theme: theme,
          onChanged: (v) => setState(() => textAreaValue = v),
        );
      case 'Drawer':
        return Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            TspButton(
              text: 'Open Drawer',
              variant: TspButtonVariant.primary,
              theme: theme,
              onTap: () => setState(() => showDrawer = true),
            ),
            TspDrawer(
              visible: showDrawer,
              title: 'Drawer',
              placement: 'bottom',
              theme: theme,
              onClose: () => setState(() => showDrawer = false),
              child: Text('Sky Planet drawer body.', style: TextStyle(color: theme.textPrimary)),
            ),
          ],
        );
      case 'InputNumber':
        return TspInputNumber(
          value: inputNumberValue,
          min: 0,
          max: 10,
          theme: theme,
          onChanged: (v) => setState(() => inputNumberValue = v),
        );
      case 'Swiper':
        return TspSwiper(
          items: const ['Slide A', 'Slide B', 'Slide C'],
          index: swiperIndex,
          theme: theme,
          onChanged: (i) => setState(() => swiperIndex = i),
        );
      case 'Tag':
        return Wrap(
          spacing: 8,
          children: [
            TspTag(text: 'default', theme: theme),
            TspTag(text: 'primary', variant: 'primary', theme: theme),
            TspTag(
              text: 'closable',
              closable: true,
              theme: theme,
              onClose: () {},
            ),
          ],
        );
      case 'Fab':
        return Row(
          children: [
            TspFab(icon: '+', theme: theme),
            const SizedBox(width: 12),
            TspFab(icon: '+', text: '新建', theme: theme),
            const SizedBox(width: 12),
            TspFab(icon: '✎', text: '默认', variant: 'default', theme: theme),
          ],
        );
      case 'TimePicker':
        return TspTimePicker(
          value: timeValue,
          placeholder: 'HH:mm',
          theme: theme,
          onChanged: (v) => setState(() => timeValue = v),
        );
      case 'Upload':
        return TspUpload(
          files: uploadFiles,
          theme: theme,
          onChanged: (files) => setState(() => uploadFiles = files),
        );
      case 'Table':
        return TspTable(
          columns: const [
            {'key': 'name', 'title': '名称'},
            {'key': 'status', 'title': '状态'},
          ],
          rows: const [
            {'name': 'Avatar', 'status': '就绪'},
            {'name': 'Tag', 'status': '新增'},
          ],
          variant: 'striped',
          theme: theme,
        );
      case 'Tree':
        return TspTree(
          items: const [
            {
              'id': 'a',
              'label': '星球',
              'children': [
                {'id': 'a1', 'label': '天空'},
                {'id': 'a2', 'label': '岛屿'},
              ],
            },
            {
              'id': 'b',
              'label': '玩法',
              'children': [
                {'id': 'b1', 'label': '闯关'},
              ],
            },
          ],
          selectedId: treeSelectedId,
          expandedIds: treeExpandedIds,
          theme: theme,
          onSelect: (id, _) => setState(() => treeSelectedId = id),
          onExpand: (ids) => setState(() => treeExpandedIds = ids),
        );
      case 'Cascader':
        return TspCascader(
          options: const [
            {
              'value': 'asia',
              'label': '亚洲',
              'children': [
                {'value': 'cn', 'label': '中国'},
                {'value': 'jp', 'label': '日本'},
              ],
            },
            {
              'value': 'eu',
              'label': '欧洲',
              'children': [
                {'value': 'fr', 'label': '法国'},
              ],
            },
          ],
          value: cascaderValue,
          placeholder: '请选择地区',
          theme: theme,
          onChanged: (v, _) => setState(() => cascaderValue = v),
        );
      default:
        return const SizedBox.shrink();
    }
  }
}
