package com.techskillplanet.basiccontrols.samples;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;
import com.techskillplanet.basiccontrols.widget.BasicAlertView;
import com.techskillplanet.basiccontrols.widget.BasicAmountView;
import com.techskillplanet.basiccontrols.widget.BasicBadgeView;
import com.techskillplanet.basiccontrols.widget.BasicButton;
import com.techskillplanet.basiccontrols.widget.BasicCardView;
import com.techskillplanet.basiccontrols.widget.BasicCheckboxView;
import com.techskillplanet.basiccontrols.widget.BasicChipView;
import com.techskillplanet.basiccontrols.widget.BasicCodeBlockView;
import com.techskillplanet.basiccontrols.widget.BasicCollapseView;
import com.techskillplanet.basiccontrols.widget.BasicDividerView;
import com.techskillplanet.basiccontrols.widget.BasicEmptyView;
import com.techskillplanet.basiccontrols.widget.BasicIconButtonView;
import com.techskillplanet.basiccontrols.widget.BasicInputView;
import com.techskillplanet.basiccontrols.widget.BasicKeyValueLabelView;
import com.techskillplanet.basiccontrols.widget.BasicListItemView;
import com.techskillplanet.basiccontrols.widget.BasicLoadingDialog;
import com.techskillplanet.basiccontrols.widget.BasicLoadingView;
import com.techskillplanet.basiccontrols.widget.BasicModalDialog;
import com.techskillplanet.basiccontrols.widget.BasicNotificationView;
import com.techskillplanet.basiccontrols.widget.BasicPinInputView;
import com.techskillplanet.basiccontrols.widget.BasicRefreshLayout;
import com.techskillplanet.basiccontrols.widget.BasicProgressView;
import com.techskillplanet.basiccontrols.widget.BasicRadioView;
import com.techskillplanet.basiccontrols.widget.BasicSelectView;
import com.techskillplanet.basiccontrols.widget.BasicStepperView;
import com.techskillplanet.basiccontrols.widget.BasicStickyFooterView;
import com.techskillplanet.basiccontrols.widget.BasicSwitchView;
import com.techskillplanet.basiccontrols.widget.BasicTableView;
import com.techskillplanet.basiccontrols.widget.BasicTabsView;
import com.techskillplanet.basiccontrols.widget.BasicTextLinkView;
import com.techskillplanet.basiccontrols.widget.BasicToast;
import com.techskillplanet.basiccontrols.widget.BasicTopBarView;
import com.techskillplanet.basiccontrols.widget.BasicTypewriterView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Basic Controls 可运行样例页面。
 *
 * <p>这个 Sample App 不依赖 Compose、Kotlin、AppCompat 或 Material，所有界面都用
 * 传统 Android View + Java 代码创建，便于验证 library 在老 View 体系中的真实效果。</p>
 */
public class MainActivity extends Activity {
    private BasicColors colors;
    private BasicStyle style;
    private LinearLayout content;
    private String currentThemeName = "sky_planet_day";
    private String currentLanguage = "zh-CN";
    private String currentComponentName = null;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BasicThemeManager.init(this, currentThemeName);
        colors = BasicThemeManager.colors();
        style = BasicThemeManager.style();
        setContentView(createContentView());
    }

    /** 创建整页可滚动样例内容。 */
    private View createContentView() {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(colors.backgroundPage);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(20), dp(18), dp(20), dp(32));
        scrollView.addView(content, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        addHeader();
        if (currentComponentName == null) {
            addThemeSamples();
            addLanguageSamples();
            addComponentList();
        } else {
            addComponentDetail(currentComponentName);
        }
        BasicRefreshLayout refreshLayout = new BasicRefreshLayout(this);
        refreshLayout.setContentView(scrollView);
        refreshLayout.setOnRefreshLoadListener(new BasicRefreshLayout.OnRefreshLoadListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(() -> {
                    BasicToast.show(MainActivity.this, "技趣星球已刷新", "success", Toast.LENGTH_SHORT);
                    refreshLayout.finishRefresh();
                }, 1200L);
            }

            @Override
            public void onLoadMore() {
                handler.postDelayed(() -> {
                    BasicToast.show(MainActivity.this, "已经加载更多组件", "info", Toast.LENGTH_SHORT);
                    refreshLayout.finishLoadMore();
                }, 1200L);
            }
        });
        return refreshLayout;
    }

    /** 顶部标题区域，用于说明当前样例运行的是 Java + Android View 组件库。 */
    private void addHeader() {
        BasicTopBarView topBar = new BasicTopBarView(this);
        topBar.setTitle(currentComponentName == null ? localizedTitle() : "Tsp" + currentComponentName);
        topBar.setBackVisible(currentComponentName != null);
        topBar.setOnBackClickListener(view -> {
            currentComponentName = null;
            setContentView(createContentView());
        });
        content.addView(topBar, fullWidth());

        TextView title = text(localizedTitle(), style.textTitle, colors.textPrimary, true);
        content.addView(title, fullWidth());
        TextView subtitle = text("用技术创造乐趣 · Java + classic Android View / no Compose", style.textMd, colors.textSecondary, false);
        content.addView(subtitle, withTopMargin(6));
    }

    /** 学习 Tab 使用分组列表承载全部组件入口。 */
    private void addComponentList() {
        addComponentGroup("Actions", new String[][]{
                {"Button", "主要操作按钮，支持主按钮、默认、危险、文本和链接样式。"},
                {"Chip", "可点击标签，用于筛选、选择和轻量操作。"},
                {"IconButton", "图标按钮，用于工具栏、快捷动作和选中态。"},
                {"TextLink", "文本链接按钮，适合弱操作和辅助跳转。"}
        });
        addComponentGroup("Surfaces", new String[][]{
                {"Card", "内容容器，用于承载分组内容、选中态和弱化背景。"},
                {"ListItem", "列表项，支持描述、尾部内容、选中和禁用。"},
                {"Empty", "空状态展示，支持说明文案和操作按钮。"}
        });
        addComponentGroup("Feedback", new String[][]{
                {"Alert", "页面内提示，适合成功、警告、错误和普通信息。"},
                {"Badge", "短文本状态徽标，用于标记数量、状态或风险等级。"},
                {"Progress", "进度条，支持主色、成功、警告和危险色。"},
                {"Notification", "通知卡片，用于强调当前任务或状态提醒。"},
                {"Toast", "轻提示，用于短时反馈。"},
                {"Modal", "确认弹窗，支持确认和取消按钮。"}
        });
        addComponentGroup("Inputs", new String[][]{
                {"Input", "单行输入框，支持错误态、禁用态和受控输入。"},
                {"Select", "选择入口，移动端默认打开底部 OptionSheet。"},
                {"OptionSheet", "移动端底部选择弹窗，和 Select 共用选项渲染逻辑。"},
                {"Switch", "二元开关，支持加载、禁用和开关文案。"},
                {"PinInput", "验证码或密码输入，支持 4 到 6 位和安全显示。"}
        });
        addComponentGroup("Navigation", new String[][]{
                {"TopBar", "顶部导航栏，支持标题、返回按钮和背景色覆盖。"},
                {"BottomTab", "一级页面底部 Tab，通常承载 3 到 5 个入口。"},
                {"Tabs", "内容区分段切换，适合页面内筛选和分类。"},
                {"StickyFooter", "固定底部操作区，用于主操作按钮。"}
        });
        addComponentGroup("Data", new String[][]{
                {"Amount", "金额或数值展示，支持币种前后置、周期和删除线。"},
                {"KeyValueLabel", "键值对展示，用于摘要信息和表单确认。"},
                {"Stepper", "步骤进度，支持 3 到 5 步。"}
        });
    }

    private void addComponentGroup(String title, String[][] rows) {
        addSectionTitle(title);
        for (String[] row : rows) {
            BasicListItemView item = listItem("Tsp" + row[0], row[1], "›", false, false);
            item.setOnClickListener(view -> {
                currentComponentName = row[0];
                setContentView(createContentView());
            });
            content.addView(item, withTopMargin(10));
        }
    }

    /** 每个组件独立详情页只展示可直接看的案例。 */
    private void addComponentDetail(String name) {
        switch (name) {
            case "Button":
                addButtonSamples();
                break;
            case "Chip":
                addChipSamples();
                break;
            case "IconButton":
                addRnParitySamples();
                break;
            case "TextLink":
                addRnParitySamples();
                break;
            case "Card":
                addCardSamples();
                break;
            case "ListItem":
                addListItemSamples();
                break;
            case "Empty":
                addEmptySamples();
                break;
            case "Alert":
                addAlertSamples();
                break;
            case "Badge":
                addBadgeSamples();
                break;
            case "Progress":
                addProgressSamples();
                break;
            case "Notification":
                addRnParitySamples();
                break;
            case "Toast":
                addToastSamples();
                break;
            case "Modal":
                addModalSamples();
                break;
            case "Input":
                addInputSamples();
                break;
            case "Select":
            case "OptionSheet":
                addSelectSamples();
                break;
            case "Switch":
                addSwitchSamples();
                break;
            case "PinInput":
                addRnParitySamples();
                break;
            case "TopBar":
                addHeader();
                break;
            case "BottomTab":
            case "Tabs":
                addTabsSamples();
                break;
            case "StickyFooter":
            case "Amount":
            case "KeyValueLabel":
            case "Stepper":
                addRnParitySamples();
                break;
            default:
                addEmptySamples();
        }
        addSectionTitle("技术栈同步");
        LinearLayout row = horizontalWrap();
        row.addView(chip("React", "primary", true));
        row.addView(chip("Vue", "primary", true));
        row.addView(chip("Android", "primary", true));
        row.addView(chip("iOS", "primary", true));
        row.addView(chip("Kuikly", "primary", true));
        row.addView(chip("RN", "primary", true));
        content.addView(row, withTopMargin(10));
    }

    /** 覆盖语言切换场景，验证样例文案能跟随内置字典切换。 */
    private void addLanguageSamples() {
        addSectionTitle("Language Switch");
        BasicCardView card = new BasicCardView(this);
        LinearLayout inner = new LinearLayout(this);
        inner.setOrientation(LinearLayout.VERTICAL);
        inner.addView(text("当前语言：" + currentLanguage, style.textMd, colors.textPrimary, true), fullWidth());
        inner.addView(text("点击切换后重建 sample 页面，验证文案读取内置语言表。", style.textSm, colors.textSecondary, false), withTopMargin(4));
        inner.addView(languageButton("简体中文", "zh-CN"), withTopMargin(10));
        inner.addView(languageButton("English", "en"), withTopMargin(10));
        inner.addView(languageButton("日本語", "ja"), withTopMargin(10));
        card.addView(inner, fullWidth());
        content.addView(card, withTopMargin(10));
    }

    /** 覆盖主题切换场景，验证 Android 原生组件读取同一套 token 后可重绘。 */
    private void addThemeSamples() {
        addSectionTitle("Theme Switch");
        BasicCardView card = new BasicCardView(this);
        LinearLayout inner = new LinearLayout(this);
        inner.setOrientation(LinearLayout.VERTICAL);
        inner.addView(text("当前主题：" + currentThemeName, style.textMd, colors.textPrimary, true), fullWidth());
        inner.addView(text("点击切换后整页组件重新读取 BasicThemeManager token。", style.textSm, colors.textSecondary, false), withTopMargin(4));

        LinearLayout actions = new LinearLayout(this);
        actions.setOrientation(LinearLayout.VERTICAL);
        actions.addView(themeButton("Sky Planet", "sky_planet_day"), withTopMargin(10));
        actions.addView(themeButton("Star Planet Night", "star_planet_night"), withTopMargin(10));
        inner.addView(actions, fullWidth());
        card.addView(inner, fullWidth());
        content.addView(card, withTopMargin(10));
    }

    /** 覆盖 BasicButton 的 primary/default/danger/text/link/disabled/pressed 场景。 */
    private void addButtonSamples() {
        addSectionTitle("Buttons");
        content.addView(button("Primary island button", BasicButton.VARIANT_PRIMARY, false), withTopMargin(10));
        content.addView(button("Default button", BasicButton.VARIANT_DEFAULT, false), withTopMargin(10));
        content.addView(button("Danger button", BasicButton.VARIANT_DANGER, false), withTopMargin(10));
        content.addView(button("Text button", BasicButton.VARIANT_TEXT, false), withTopMargin(10));
        content.addView(button("Link button", BasicButton.VARIANT_LINK, false), withTopMargin(10));
        content.addView(button("Disabled primary", BasicButton.VARIANT_PRIMARY, true), withTopMargin(10));
    }

    /** 覆盖 Divider 分隔场景。 */
    private void addDividerSamples() {
        addSectionTitle("Divider");
        content.addView(new BasicDividerView(this), withTopMargin(10));
    }

    /** 覆盖 BasicInputView 的默认、焦点、错误、禁用场景。 */
    private void addInputSamples() {
        addSectionTitle("Inputs");
        BasicInputView normal = new BasicInputView(this);
        normal.getEditText().setHint("Default input");
        content.addView(normal, withTopMargin(10));

        BasicInputView filled = new BasicInputView(this);
        filled.setBasicText("已输入内容");
        filled.getEditText().setHint("Filled input");
        content.addView(filled, withTopMargin(10));

        BasicInputView error = new BasicInputView(this);
        error.setVariant(BasicInputView.VARIANT_ERROR);
        error.getEditText().setHint("Error input");
        content.addView(error, withTopMargin(10));

        BasicInputView disabled = new BasicInputView(this);
        disabled.setBasicText("Disabled input");
        disabled.setBasicDisabled(true);
        content.addView(disabled, withTopMargin(10));
    }

    /** 覆盖 Select 下拉选择场景。 */
    private void addSelectSamples() {
        addSectionTitle("Select");
        BasicSelectView select = new BasicSelectView(this);
        select.setOptions(Arrays.asList("全部主题", "AI 工具", "Android", "网页小工具"));
        select.setOnOptionSelectedListener((index, option) ->
                BasicToast.show(this, "选择：" + option, "info", Toast.LENGTH_SHORT));
        content.addView(select, withTopMargin(10));

        BasicSelectView disabled = new BasicSelectView(this);
        disabled.setOptions(Arrays.asList("不可选择", "草稿", "已发布"));
        disabled.setBasicDisabled(true);
        content.addView(disabled, withTopMargin(10));
    }

    /** 覆盖 BasicCardView 的默认、subtle、selected 场景。 */
    private void addCardSamples() {
        addSectionTitle("Cards");
        BasicCardView card = card("默认云朵卡片", "用于表单块、列表项和信息面板。");
        content.addView(card, withTopMargin(10));

        BasicCardView subtle = card("Subtle card", "更轻的 surface，用于页面里的弱强调区域。");
        subtle.setVariant("subtle");
        content.addView(subtle, withTopMargin(10));

        BasicCardView selected = card("Selected card", "选中态会使用品牌色边框，便于选择类场景。");
        selected.setSelectedState(true);
        content.addView(selected, withTopMargin(10));
    }

    /** 覆盖 Collapse 折叠问答场景。 */
    private void addCollapseSamples() {
        addSectionTitle("Collapse");
        BasicCollapseView first = new BasicCollapseView(this);
        first.setTitle("为什么坚持 Java + Android View？");
        first.setMessage("为了兼容老项目、降低接入成本，并且不引入 Compose 迁移成本。");
        first.setSelectedState(true);
        content.addView(first, withTopMargin(10));

        BasicCollapseView second = new BasicCollapseView(this);
        second.setTitle("换肤需要改组件代码吗？");
        second.setMessage("不需要。稳定维护 color_token.json 和 style_token.json，即可驱动 Android 与 Figma 的一致换肤。");
        content.addView(second, withTopMargin(10));
    }

    /** 覆盖 BasicAlertView 的 info/success/warning/error 场景。 */
    private void addAlertSamples() {
        addSectionTitle("Alerts");
        content.addView(alert("Info", "蓝色星球信息提示。", BasicAlertView.VARIANT_INFO), withTopMargin(10));
        content.addView(alert("Success", "操作已经完成，可以继续下一步。", BasicAlertView.VARIANT_SUCCESS), withTopMargin(10));
        content.addView(alert("Warning", "请检查当前输入内容。", BasicAlertView.VARIANT_WARNING), withTopMargin(10));
        content.addView(alert("Error", "提交失败，请稍后重试。", BasicAlertView.VARIANT_ERROR), withTopMargin(10));
    }

    /** 覆盖 BasicBadgeView 的 default/primary/success/warning/danger/disabled 场景。 */
    private void addBadgeSamples() {
        addSectionTitle("Badges");
        LinearLayout row = horizontalWrap();
        row.addView(badge("Default", "default", false));
        row.addView(badge("Primary", "primary", false));
        row.addView(badge("Success", "success", false));
        row.addView(badge("Warning", "warning", false));
        row.addView(badge("Danger", "danger", false));
        row.addView(badge("Disabled", "default", true));
        content.addView(row, withTopMargin(10));
    }

    /** 覆盖 BasicChipView 的筛选、状态和选中场景。 */
    private void addChipSamples() {
        addSectionTitle("Chips");
        LinearLayout row = horizontalWrap();
        row.addView(chip("AI 工具", "primary", true));
        row.addView(chip("Android", "default", false));
        row.addView(chip("已发布", "success", false));
        row.addView(chip("待检查", "warning", false));
        row.addView(chip("需修复", "danger", false));
        content.addView(row, withTopMargin(10));
    }

    /** 覆盖 Checkbox 和 Radio 的常见选择场景。 */
    private void addSelectionSamples() {
        addSectionTitle("Selection");
        BasicCheckboxView agree = new BasicCheckboxView(this);
        agree.setBasicText("订阅技趣星球更新");
        agree.setSelectedState(true);
        content.addView(agree, withTopMargin(10));

        BasicCheckboxView disabled = new BasicCheckboxView(this);
        disabled.setBasicText("禁用的复选项");
        disabled.setBasicDisabled(true);
        content.addView(disabled, withTopMargin(10));

        LinearLayout group = new LinearLayout(this);
        group.setOrientation(LinearLayout.VERTICAL);
        BasicRadioView beginner = radio("零基础教程", true);
        BasicRadioView android = radio("Android 组件库", false);
        BasicRadioView aiTool = radio("AI 小工具", false);
        BasicRadioView[] radios = new BasicRadioView[]{beginner, android, aiTool};
        for (BasicRadioView radio : radios) {
            radio.setOnClickListener(view -> {
                for (BasicRadioView item : radios) {
                    item.setSelectedState(item == view);
                }
            });
            group.addView(radio, withTopMargin(8));
        }
        content.addView(group, fullWidth());
    }

    /** 覆盖 BasicSwitchView 的开启、关闭和禁用场景。 */
    private void addSwitchSamples() {
        addSectionTitle("Switches");
        BasicSwitchView publish = new BasicSwitchView(this);
        publish.setBasicText("开启发布提醒");
        publish.setCheckedText("开");
        publish.setUncheckedText("关");
        publish.setSelectedState(true);
        content.addView(publish, withTopMargin(10));

        BasicSwitchView draft = new BasicSwitchView(this);
        draft.setBasicText("自动保存草稿");
        draft.setCheckedText("ON");
        draft.setUncheckedText("OFF");
        content.addView(draft, withTopMargin(10));

        BasicSwitchView small = new BasicSwitchView(this);
        small.setBasicText("小号开关");
        small.setVariant(BasicSwitchView.SIZE_SMALL);
        small.setCheckedText("Y");
        small.setUncheckedText("N");
        small.setSelectedState(true);
        content.addView(small, withTopMargin(10));

        BasicSwitchView loading = new BasicSwitchView(this);
        loading.setBasicText("同步主题中");
        loading.setSelectedState(true);
        loading.setLoading(true);
        content.addView(loading, withTopMargin(10));

        BasicSwitchView disabled = new BasicSwitchView(this);
        disabled.setBasicText("禁用的实验功能");
        disabled.setBasicDisabled(true);
        content.addView(disabled, withTopMargin(10));
    }

    /** 覆盖确定性进度条和不确定加载条。 */
    private void addProgressSamples() {
        addSectionTitle("Progress & Loading");
        BasicProgressView article = new BasicProgressView(this);
        article.setProgress(0.68f);
        content.addView(article, withTopMargin(12));

        BasicProgressView warning = new BasicProgressView(this);
        warning.setVariant("warning");
        warning.setProgress(0.36f);
        content.addView(warning, withTopMargin(12));

        BasicLoadingView loading = new BasicLoadingView(this);
        loading.setBasicText("技趣星球加载中");
        content.addView(loading, withTopMargin(12));

        content.addView(loadingDialogButton(), withTopMargin(12));
    }

    /** 覆盖 Table、CodeBlock、Typewriter 数据展示场景。 */
    private void addDataDisplaySamples() {
        addSectionTitle("Data Display");
        BasicTableView table = new BasicTableView(this);
        List<List<String>> rows = new ArrayList<>();
        rows.add(Arrays.asList("Switch", "已补齐", "核心"));
        rows.add(Arrays.asList("Select", "新增", "常用"));
        rows.add(Arrays.asList("Collapse", "新增", "常用"));
        table.setData(Arrays.asList("组件", "状态", "优先级"), rows);
        content.addView(table, withTopMargin(10));

        BasicCodeBlockView code = new BasicCodeBlockView(this);
        code.setTitle("Java");
        code.setCode("BasicThemeManager.init(this);\\nBasicButton button = new BasicButton(this);\\nbutton.setVariant(BasicButton.VARIANT_PRIMARY);");
        content.addView(code, withTopMargin(10));

        BasicTypewriterView typewriter = new BasicTypewriterView(this);
        typewriter.setBasicText("技趣星球：用技术创造乐趣，把组件、主题和代码生成流程都做成可复用能力。");
        content.addView(typewriter, withTopMargin(10));
    }

    /** 覆盖从 RN Base Widgets 对齐过来的高频组件 API。 */
    private void addRnParitySamples() {
        addSectionTitle("RN Parity Components");

        BasicAmountView amount = new BasicAmountView(this);
        amount.setAmount("$", "128.80", "month");
        content.addView(amount, withTopMargin(10));

        BasicAmountView amountAfter = new BasicAmountView(this);
        amountAfter.setAmount("USD", "99", "");
        amountAfter.setSymbolAfter(true);
        amountAfter.setStrikeThrough(true);
        content.addView(amountAfter, withTopMargin(8));

        LinearLayout iconRow = horizontalWrap();
        iconRow.addView(iconButton("‹", "default", false));
        iconRow.addView(iconButton("✓", "primary", false));
        iconRow.addView(iconButton("×", "default", true));
        content.addView(iconRow, withTopMargin(10));

        BasicKeyValueLabelView kv = new BasicKeyValueLabelView(this);
        kv.setPair("当前主题", "Sky Planet");
        content.addView(kv, withTopMargin(10));

        BasicNotificationView notification = new BasicNotificationView(this);
        notification.setContent("组件 API 已对齐", "Android、Kuikly、小程序、Flutter、iOS、RN 使用同一套语义。");
        content.addView(notification, withTopMargin(10));

        BasicNotificationView alert = new BasicNotificationView(this);
        alert.setVariant("alert");
        alert.setContent("注意", "复杂平台能力组件需要按平台继续补真实交互。");
        content.addView(alert, withTopMargin(10));

        BasicTextLinkView link = new BasicTextLinkView(this);
        link.setText("查看组件契约");
        link.setOnClickListener(view -> BasicToast.show(this, "component_contract.json", "info", Toast.LENGTH_SHORT));
        content.addView(link, withTopMargin(10));

        BasicStepperView stepper3 = new BasicStepperView(this);
        stepper3.setSteps(3, 2);
        content.addView(stepper3, withTopMargin(12));

        BasicStepperView stepper5 = new BasicStepperView(this);
        stepper5.setSteps(5, 4);
        content.addView(stepper5, withTopMargin(12));

        BasicPinInputView pin = new BasicPinInputView(this);
        pin.setCellCount(6);
        pin.setOnPinCompleteListener(value -> BasicToast.show(this, "PIN: " + value, "success", Toast.LENGTH_SHORT));
        content.addView(pin, withTopMargin(10));

        BasicStickyFooterView footer = new BasicStickyFooterView(this);
        footer.addView(button("Sticky footer action", BasicButton.VARIANT_PRIMARY, false), fullWidth());
        content.addView(footer, withTopMargin(10));
    }

    /** 覆盖 BasicListItemView 的工具入口、选中和禁用场景。 */
    private void addListItemSamples() {
        addSectionTitle("List Items");
        BasicListItemView app = listItem("AI 帮你做 Android App", "从需求到可运行 Demo", "⭐⭐⭐", false, false);
        content.addView(app, withTopMargin(10));

        BasicListItemView selected = listItem("组件库搭建计划", "Token、Theme、View 组件", "进行中", true, false);
        content.addView(selected, withTopMargin(10));

        BasicListItemView disabled = listItem("视频特效工作流", "后续阶段开放", "未开始", false, true);
        content.addView(disabled, withTopMargin(10));
    }

    /** 覆盖 BasicEmptyView 的空内容场景。 */
    private void addEmptySamples() {
        addSectionTitle("Empty State");
        BasicEmptyView empty = new BasicEmptyView(this);
        empty.setTitle("还没有生成工具卡片");
        empty.setMessage("选择一个技趣星球主题，先做一个能跑起来的小工具。");
        empty.setActionText("开始创建");
        empty.getActionButton().setOnClickListener(view ->
                BasicToast.show(this, "准备创建新工具", "success", Toast.LENGTH_SHORT));
        content.addView(empty, withTopMargin(10));
    }

    /** 覆盖 BasicTabsView 的默认选中和点击切换场景。 */
    private void addTabsSamples() {
        addSectionTitle("Tabs");
        BasicTabsView tabs = new BasicTabsView(this);
        tabs.setTabs(Arrays.asList("基础", "表单", "反馈", "导航", "数据"));
        tabs.setOnTabSelectedListener((index, title) ->
                BasicToast.show(this, "已选择：" + title, "info", Toast.LENGTH_SHORT));
        content.addView(tabs, withTopMargin(10));
    }

    /** 覆盖 Modal 模态弹窗场景。 */
    private void addModalSamples() {
        addSectionTitle("Modal");
        BasicButton modal = button("Show modal dialog", BasicButton.VARIANT_PRIMARY, false);
        modal.setOnClickListener(view -> {
            BasicModalDialog dialog = BasicModalDialog.show(this, "确认同步主题", "将当前 token 应用到 Android View 组件和 Figma 设计稿。");
            dialog.setOnConfirmClickListener(confirm ->
                    BasicToast.show(this, "已确认同步", "success", Toast.LENGTH_SHORT));
        });
        content.addView(modal, withTopMargin(10));
    }

    /** 覆盖 BasicToast 的 info/success/warning/error 场景。 */
    private void addToastSamples() {
        addSectionTitle("Toasts");
        BasicButton info = button("Show info toast", BasicButton.VARIANT_PRIMARY, false);
        info.setOnClickListener(view -> BasicToast.show(this, "Info toast", "info", Toast.LENGTH_SHORT));
        content.addView(info, withTopMargin(10));

        BasicButton success = button("Show success toast", BasicButton.VARIANT_DEFAULT, false);
        success.setOnClickListener(view -> BasicToast.show(this, "Success toast", "success", Toast.LENGTH_SHORT));
        content.addView(success, withTopMargin(10));

        BasicButton warning = button("Show warning toast", BasicButton.VARIANT_DEFAULT, false);
        warning.setOnClickListener(view -> BasicToast.show(this, "Warning toast", "warning", Toast.LENGTH_SHORT));
        content.addView(warning, withTopMargin(10));

        BasicButton error = button("Show error toast", BasicButton.VARIANT_DANGER, false);
        error.setOnClickListener(view -> BasicToast.show(this, "Error toast", "error", Toast.LENGTH_SHORT));
        content.addView(error, withTopMargin(10));
    }

    /** 添加一个分组标题。 */
    private void addSectionTitle(String title) {
        TextView view = text(title, style.textLg, colors.textPrimary, true);
        content.addView(view, withTopMargin(24));
    }

    /** 创建按钮样例。 */
    private BasicButton button(String label, String variant, boolean disabled) {
        BasicButton button = new BasicButton(this);
        button.setBasicText(label);
        button.setVariant(variant);
        button.setBasicDisabled(disabled);
        if (!disabled) {
            button.setOnClickListener(view -> BasicToast.show(this, label, "info", Toast.LENGTH_SHORT));
        }
        return button;
    }

    /** 创建半透明 Loading 弹窗演示按钮。 */
    private BasicButton loadingDialogButton() {
        BasicButton button = button("Show planet loading dialog", BasicButton.VARIANT_PRIMARY, false);
        button.setOnClickListener(view -> {
            BasicLoadingDialog dialog = BasicLoadingDialog.show(this, "技趣星球同步中...");
            handler.postDelayed(dialog::dismiss, 1800L);
        });
        return button;
    }

    /** 创建主题切换按钮。 */
    private BasicButton themeButton(String label, String themeName) {
        BasicButton button = button(label, themeName.equals(currentThemeName)
                ? BasicButton.VARIANT_PRIMARY
                : BasicButton.VARIANT_DEFAULT, false);
        button.setOnClickListener(view -> {
            currentThemeName = themeName;
            BasicThemeManager.init(this, currentThemeName);
            colors = BasicThemeManager.colors();
            style = BasicThemeManager.style();
            setContentView(createContentView());
            BasicToast.show(this, "已切换主题：" + label, "success", Toast.LENGTH_SHORT);
        });
        return button;
    }

    /** 创建语言切换按钮。 */
    private BasicButton languageButton(String label, String language) {
        BasicButton button = button(label, language.equals(currentLanguage)
                ? BasicButton.VARIANT_PRIMARY
                : BasicButton.VARIANT_DEFAULT, false);
        button.setOnClickListener(view -> {
            currentLanguage = language;
            setContentView(createContentView());
            BasicToast.show(this, "Language: " + label, "success", Toast.LENGTH_SHORT);
        });
        return button;
    }

    /** 返回当前语言标题。 */
    private String localizedTitle() {
        if ("en".equals(currentLanguage)) {
            return "Basic Controls";
        }
        if ("ja".equals(currentLanguage)) {
            return "基本コンポーネント";
        }
        return "基础组件";
    }

    /** 创建卡片样例。 */
    private BasicCardView card(String title, String body) {
        BasicCardView card = new BasicCardView(this);
        LinearLayout inner = new LinearLayout(this);
        inner.setOrientation(LinearLayout.VERTICAL);
        inner.addView(text(title, style.textMd, colors.textPrimary, true), fullWidth());
        inner.addView(text(body, style.textSm, colors.textSecondary, false), withTopMargin(4));
        card.addView(inner, fullWidth());
        return card;
    }

    /** 创建提示条样例。 */
    private BasicAlertView alert(String title, String message, String variant) {
        BasicAlertView alert = new BasicAlertView(this);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setVariant(variant);
        return alert;
    }

    /** 创建徽标样例。 */
    private BasicBadgeView badge(String text, String variant, boolean disabled) {
        BasicBadgeView badge = new BasicBadgeView(this);
        badge.setBasicText(text);
        badge.setVariant(variant);
        badge.setBasicDisabled(disabled);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, dp(8), dp(8));
        badge.setLayoutParams(params);
        return badge;
    }

    /** 创建标签样例。 */
    private BasicChipView chip(String text, String variant, boolean selected) {
        BasicChipView chip = new BasicChipView(this);
        chip.setBasicText(text);
        chip.setVariant(variant);
        chip.setSelectedState(selected);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, dp(8), dp(8));
        chip.setLayoutParams(params);
        return chip;
    }

    /** 创建单选项样例。 */
    private BasicRadioView radio(String text, boolean selected) {
        BasicRadioView radio = new BasicRadioView(this);
        radio.setBasicText(text);
        radio.setSelectedState(selected);
        return radio;
    }

    /** 创建列表项样例。 */
    private BasicListItemView listItem(String title, String message, String trailing, boolean selected, boolean disabled) {
        BasicListItemView item = new BasicListItemView(this);
        item.setTitle(title);
        item.setMessage(message);
        item.setTrailingText(trailing);
        item.setSelectedState(selected);
        item.setBasicDisabled(disabled);
        return item;
    }

    /** 创建 IconButton 样例。 */
    private BasicIconButtonView iconButton(String text, String variant, boolean disabled) {
        BasicIconButtonView view = new BasicIconButtonView(this);
        view.setIconText(text);
        view.setVariant(variant);
        view.setEnabled(!disabled);
        view.setAlpha(disabled ? 0.45f : 1f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp(44), dp(44));
        params.setMargins(0, 0, dp(8), 0);
        view.setLayoutParams(params);
        return view;
    }

    /** 创建基础文本。 */
    private TextView text(String value, float sizePx, int color, boolean bold) {
        TextView textView = new TextView(this);
        textView.setText(value);
        textView.setTextColor(color);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizePx);
        textView.setTypeface(android.graphics.Typeface.DEFAULT, bold
                ? android.graphics.Typeface.BOLD
                : android.graphics.Typeface.NORMAL);
        return textView;
    }

    /** 横向自动换行的简单容器。当前用 LinearLayout 承载，宽度不足时可横向滚动页面观察。 */
    private LinearLayout horizontalWrap() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        return row;
    }

    /** 全宽自适应高度布局参数。 */
    private LinearLayout.LayoutParams fullWidth() {
        return new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    /** 带顶部间距的全宽布局参数。 */
    private LinearLayout.LayoutParams withTopMargin(int topDp) {
        LinearLayout.LayoutParams params = fullWidth();
        params.setMargins(0, dp(topDp), 0, 0);
        return params;
    }

    /** dp 转 px。 */
    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }
}
