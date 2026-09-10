package com.techskillplanet.planetcomponents.samples;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.techskillplanet.planetcomponents.widget.BasicAlertView;
import com.techskillplanet.planetcomponents.widget.BasicAmountView;
import com.techskillplanet.planetcomponents.widget.BasicAvatarView;
import com.techskillplanet.planetcomponents.widget.BasicBalanceHeroView;
import com.techskillplanet.planetcomponents.widget.BasicButton;
import com.techskillplanet.planetcomponents.widget.BasicCalendarHeatmapView;
import com.techskillplanet.planetcomponents.widget.BasicCardView;
import com.techskillplanet.planetcomponents.widget.BasicCascaderView;
import com.techskillplanet.planetcomponents.widget.BasicCheckInStreakCardView;
import com.techskillplanet.planetcomponents.widget.BasicCheckboxView;
import com.techskillplanet.planetcomponents.widget.BasicChildSwitcherView;
import com.techskillplanet.planetcomponents.widget.BasicChipView;
import com.techskillplanet.planetcomponents.widget.BasicCodeBlockView;
import com.techskillplanet.planetcomponents.widget.BasicCollapseView;
import com.techskillplanet.planetcomponents.widget.BasicDatePickerView;
import com.techskillplanet.planetcomponents.widget.BasicDividerView;
import com.techskillplanet.planetcomponents.widget.BasicDrawerView;
import com.techskillplanet.planetcomponents.widget.BasicEmptyView;
import com.techskillplanet.planetcomponents.widget.BasicFabView;
import com.techskillplanet.planetcomponents.widget.BasicIconButtonView;
import com.techskillplanet.planetcomponents.widget.BasicInputNumberView;
import com.techskillplanet.planetcomponents.widget.BasicInputView;
import com.techskillplanet.planetcomponents.widget.BasicKeyValueLabelView;
import com.techskillplanet.planetcomponents.widget.BasicListItemView;
import com.techskillplanet.planetcomponents.widget.BasicLoadingDialog;
import com.techskillplanet.planetcomponents.widget.BasicLoadingView;
import com.techskillplanet.planetcomponents.widget.BasicModalDialog;
import com.techskillplanet.planetcomponents.widget.BasicNotificationView;
import com.techskillplanet.planetcomponents.widget.BasicPinInputView;
import com.techskillplanet.planetcomponents.widget.BasicPrintSheetView;
import com.techskillplanet.planetcomponents.widget.BasicProgressView;
import com.techskillplanet.planetcomponents.widget.BasicRadioView;
import com.techskillplanet.planetcomponents.widget.BasicRedeemCardGridView;
import com.techskillplanet.planetcomponents.widget.BasicRefreshLayout;
import com.techskillplanet.planetcomponents.widget.BasicScoreRuleGridView;
import com.techskillplanet.planetcomponents.widget.BasicSearchBarView;
import com.techskillplanet.planetcomponents.widget.BasicSegmentedControl;
import com.techskillplanet.planetcomponents.widget.BasicSelectView;
import com.techskillplanet.planetcomponents.widget.BasicSkeletonView;
import com.techskillplanet.planetcomponents.widget.BasicSliderView;
import com.techskillplanet.planetcomponents.widget.BasicStarRatingView;
import com.techskillplanet.planetcomponents.widget.BasicStepperView;
import com.techskillplanet.planetcomponents.widget.BasicStickyFooterView;
import com.techskillplanet.planetcomponents.widget.BasicSwitchView;
import com.techskillplanet.planetcomponents.widget.BasicSwiperView;
import com.techskillplanet.planetcomponents.widget.BasicTableView;
import com.techskillplanet.planetcomponents.widget.BasicTabsView;
import com.techskillplanet.planetcomponents.widget.BasicTagView;
import com.techskillplanet.planetcomponents.widget.BasicTextAreaView;
import com.techskillplanet.planetcomponents.widget.BasicTextLinkView;
import com.techskillplanet.planetcomponents.widget.BasicTimePickerView;
import com.techskillplanet.planetcomponents.widget.BasicToast;
import com.techskillplanet.planetcomponents.widget.BasicTooltipView;
import com.techskillplanet.planetcomponents.widget.BasicTopBarView;
import com.techskillplanet.planetcomponents.widget.BasicTreeView;
import com.techskillplanet.planetcomponents.widget.BasicTypewriterView;
import com.techskillplanet.planetcomponents.widget.BasicUploadView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 组件详情 Demo Page。
 * <p>
 * 每个 {@code componentId}（如 {@code "Button"}）对应一组 variant / 状态示例。
 * 新增组件 Demo：在此增加 {@code addXxxSamples} 并在 {@link #render} 的 switch 注册，
 * 同时在 {@link HomeSamplePage} 列表里加入 id。
 */
public final class ComponentDetailSamplePage {
    private ComponentDetailSamplePage() {
    }

    /** 入口：route=detail 时由 Shell 传入 {@link com.techskillplanet.planetcomponents.samples.navigation.SampleRoute#componentId()}。 */
    public static void render(SamplePageHost host, String name) {
        switch (name) {
            case "Button":
                addButtonSamples(host);
                break;
            case "Chip":
                addChipSamples(host);
                break;
            case "IconButton":
            case "TextLink":
            case "Notification":
            case "PinInput":
            case "StickyFooter":
            case "Amount":
            case "KeyValueLabel":
            case "Stepper":
                addRnParitySamples(host);
                break;
            case "Card":
                addCardSamples(host);
                break;
            case "ListItem":
                addListItemSamples(host);
                break;
            case "Empty":
                addEmptySamples(host);
                break;
            case "Alert":
                addAlertSamples(host);
                break;
            case "Badge":
                addBadgeSamples(host);
                break;
            case "Progress":
                addProgressSamples(host);
                break;
            case "Toast":
                addToastSamples(host);
                break;
            case "Modal":
                addModalSamples(host);
                break;
            case "Input":
                addInputSamples(host);
                break;
            case "Select":
            case "OptionSheet":
                addSelectSamples(host);
                break;
            case "Switch":
                addSwitchSamples(host);
                break;
            case "Checkbox":
            case "Radio":
                addSelectionSamples(host);
                break;
            case "Collapse":
                addCollapseSamples(host);
                break;
            case "Divider":
                addDividerSamples(host);
                break;
            case "SearchBar":
                addSearchBarSamples(host);
                break;
            case "SegmentedControl":
                addSegmentedControlSamples(host);
                break;
            case "StarRating":
                addStarRatingSamples(host);
                break;
            case "LoadingDialog":
                addLoadingDialogSamples(host);
                break;
            case "RefreshLayout":
                addRefreshLayoutSamples(host);
                break;
            case "TopBar":
                host.addSectionTitle(host.t("sample/section/topbar_preview"));
                BasicTopBarView preview = new BasicTopBarView(host.getActivity());
                preview.setImmersiveStatusBar(false);
                preview.setTitle(host.t("sample/app/title"));
                preview.setBackVisible(true);
                host.content().addView(preview, host.fullBleedWidth());
                break;
            case "BottomTab":
            case "Tabs":
                addTabsSamples(host);
                break;
            case "DatePicker":
                addDatePickerSamples(host);
                break;
            case "ChildSwitcher":
                addChildSwitcherSamples(host);
                break;
            case "ScoreRuleGrid":
                addScoreRuleGridSamples(host);
                break;
            case "RedeemCardGrid":
                addRedeemCardGridSamples(host);
                break;
            case "CalendarHeatmap":
                addCalendarHeatmapSamples(host);
                break;
            case "PrintSheet":
                addPrintSheetSamples(host);
                break;
            case "BalanceHero":
                addBalanceHeroSamples(host);
                break;
            case "CheckInStreakCard":
                addCheckInStreakCardSamples(host);
                break;
            case "Avatar":
                addAvatarSamples(host);
                break;
            case "Skeleton":
                addSkeletonSamples(host);
                break;
            case "Tooltip":
                addTooltipSamples(host);
                break;
            case "Slider":
                addSliderSamples(host);
                break;
            case "TextArea":
                addTextAreaSamples(host);
                break;
            case "Drawer":
                addDrawerSamples(host);
                break;
            case "InputNumber":
                addInputNumberSamples(host);
                break;
            case "Swiper":
                addSwiperSamples(host);
                break;
            case "Tag":
                addTagSamples(host);
                break;
            case "Fab":
                addFabSamples(host);
                break;
            case "TimePicker":
                addTimePickerSamples(host);
                break;
            case "Upload":
                addUploadSamples(host);
                break;
            case "Table":
                addTableContractSamples(host);
                break;
            case "Tree":
                addTreeSamples(host);
                break;
            case "Cascader":
                addCascaderSamples(host);
                break;
            default:
                addEmptySamples(host);
        }
        host.addSectionTitle(host.t("sample/section/stack_sync"));
        LinearLayout row = host.horizontalWrap();
        row.addView(host.chip(host.t("sample/demo/platform/react"), "primary", true));
        row.addView(host.chip(host.t("sample/demo/platform/vue"), "primary", true));
        row.addView(host.chip(host.t("sample/demo/platform/android"), "primary", true));
        row.addView(host.chip(host.t("sample/demo/platform/ios"), "primary", true));
        row.addView(host.chip(host.t("sample/demo/platform/kuikly"), "primary", true));
        row.addView(host.chip(host.t("sample/demo/platform/rn"), "primary", true));
        host.content().addView(row, host.withTopMargin(10));
    }

    private static void addButtonSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/buttons"));
        host.content().addView(host.button(host.t("sample/demo/button/primary"), BasicButton.VARIANT_PRIMARY, false), host.withTopMargin(10));
        host.content().addView(host.button(host.t("sample/demo/button/default"), BasicButton.VARIANT_DEFAULT, false), host.withTopMargin(10));
        host.content().addView(host.button(host.t("sample/demo/button/danger"), BasicButton.VARIANT_DANGER, false), host.withTopMargin(10));
        host.content().addView(host.button(host.t("sample/demo/button/text"), BasicButton.VARIANT_TEXT, false), host.withTopMargin(10));
        host.content().addView(host.button(host.t("sample/demo/button/link"), BasicButton.VARIANT_LINK, false), host.withTopMargin(10));
        host.content().addView(host.button(host.t("sample/demo/button/disabled_primary"), BasicButton.VARIANT_PRIMARY, true), host.withTopMargin(10));

        BasicButton loadingBtn = host.button(host.t("sample/demo/button/loading"), BasicButton.VARIANT_PRIMARY, false);
        loadingBtn.setLoading(true);
        host.content().addView(loadingBtn, host.withTopMargin(10));

        BasicButton busyTap = host.button(host.t("sample/demo/button/busy_tap"), BasicButton.VARIANT_DEFAULT, false);
        busyTap.setOnClickListener(v -> {
            busyTap.setLoading(true);
            busyTap.postDelayed(() -> busyTap.setLoading(false), 1200);
        });
        host.content().addView(busyTap, host.withTopMargin(10));
    }

    private static void addDividerSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/divider"));
        host.content().addView(new BasicDividerView(host.getActivity()), host.withTopMargin(10));
    }

    private static void addInputSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/inputs"));
        BasicInputView normal = new BasicInputView(host.getActivity());
        normal.getEditText().setHint(host.t("sample/demo/input/hint_default"));
        host.content().addView(normal, host.withTopMargin(10));

        BasicInputView filled = new BasicInputView(host.getActivity());
        filled.setBasicText(host.t("sample/demo/input/value_filled"));
        filled.getEditText().setHint(host.t("sample/demo/input/hint_filled"));
        host.content().addView(filled, host.withTopMargin(10));

        BasicInputView error = new BasicInputView(host.getActivity());
        error.setVariant(BasicInputView.VARIANT_ERROR);
        error.getEditText().setHint(host.t("sample/demo/input/hint_error"));
        host.content().addView(error, host.withTopMargin(10));

        BasicInputView disabled = new BasicInputView(host.getActivity());
        disabled.setBasicText(host.t("sample/demo/input/value_disabled"));
        disabled.setBasicDisabled(true);
        host.content().addView(disabled, host.withTopMargin(10));
    }

    private static void addSelectSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/select"));
        BasicSelectView select = new BasicSelectView(host.getActivity());
        select.setOptions(Arrays.asList(
                host.t("sample/demo/select/option_all"),
                host.t("sample/demo/select/option_ai"),
                host.t("sample/demo/select/option_android"),
                host.t("sample/demo/select/option_web")
        ));
        select.setOnOptionSelectedListener((index, option) ->
                BasicToast.show(host.getActivity(), host.t("sample/toast/select", option), "info", Toast.LENGTH_SHORT));
        host.content().addView(select, host.withTopMargin(10));

        BasicSelectView disabled = new BasicSelectView(host.getActivity());
        disabled.setOptions(Arrays.asList(
                host.t("sample/demo/select/disabled_1"),
                host.t("sample/demo/select/disabled_2"),
                host.t("sample/demo/select/disabled_3")
        ));
        disabled.setBasicDisabled(true);
        host.content().addView(disabled, host.withTopMargin(10));
    }

    private static void addCardSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/cards"));
        BasicCardView card = host.card(host.t("sample/demo/card/default_title"), host.t("sample/demo/card/default_body"));
        host.content().addView(card, host.withTopMargin(10));

        BasicCardView subtle = host.card(host.t("sample/demo/card/subtle_title"), host.t("sample/demo/card/subtle_body"));
        subtle.setVariant("subtle");
        host.content().addView(subtle, host.withTopMargin(10));

        BasicCardView selected = host.card(host.t("sample/demo/card/selected_title"), host.t("sample/demo/card/selected_body"));
        selected.setSelectedState(true);
        host.content().addView(selected, host.withTopMargin(10));
    }

    private static void addCollapseSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/collapse"));
        BasicCollapseView first = new BasicCollapseView(host.getActivity());
        first.setTitle(host.t("sample/demo/collapse/q1_title"));
        first.setMessage(host.t("sample/demo/collapse/q1_body"));
        first.setSelectedState(true);
        host.content().addView(first, host.withTopMargin(10));

        BasicCollapseView second = new BasicCollapseView(host.getActivity());
        second.setTitle(host.t("sample/demo/collapse/q2_title"));
        second.setMessage(host.t("sample/demo/collapse/q2_body"));
        host.content().addView(second, host.withTopMargin(10));
    }

    private static void addAlertSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/alerts"));
        host.content().addView(host.alert(
                host.t("sample/demo/alert/info_title"),
                host.t("sample/demo/alert/info_body"),
                BasicAlertView.VARIANT_INFO
        ), host.withTopMargin(10));
        host.content().addView(host.alert(
                host.t("sample/demo/alert/success_title"),
                host.t("sample/demo/alert/success_body"),
                BasicAlertView.VARIANT_SUCCESS
        ), host.withTopMargin(10));
        host.content().addView(host.alert(
                host.t("sample/demo/alert/warning_title"),
                host.t("sample/demo/alert/warning_body"),
                BasicAlertView.VARIANT_WARNING
        ), host.withTopMargin(10));
        host.content().addView(host.alert(
                host.t("sample/demo/alert/error_title"),
                host.t("sample/demo/alert/error_body"),
                BasicAlertView.VARIANT_ERROR
        ), host.withTopMargin(10));
    }

    private static void addBadgeSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/badges"));
        LinearLayout row = host.horizontalWrap();
        row.addView(host.badge(host.t("sample/demo/badge/default"), "default", false));
        row.addView(host.badge(host.t("sample/demo/badge/primary"), "primary", false));
        row.addView(host.badge(host.t("sample/demo/badge/success"), "success", false));
        row.addView(host.badge(host.t("sample/demo/badge/warning"), "warning", false));
        row.addView(host.badge(host.t("sample/demo/badge/danger"), "danger", false));
        row.addView(host.badge(host.t("sample/demo/badge/disabled"), "default", true));
        host.content().addView(row, host.withTopMargin(10));
    }

    private static void addChipSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/chips"));
        LinearLayout row = host.horizontalWrap();
        row.addView(host.chip(host.t("sample/demo/chip/ai"), "primary", true));
        row.addView(host.chip(host.t("sample/demo/chip/android"), "default", false));
        row.addView(host.chip(host.t("sample/demo/chip/published"), "success", false));
        row.addView(host.chip(host.t("sample/demo/chip/review"), "warning", false));
        row.addView(host.chip(host.t("sample/demo/chip/fix"), "danger", false));
        host.content().addView(row, host.withTopMargin(10));
    }

    private static void addSelectionSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/selection"));
        BasicCheckboxView agree = new BasicCheckboxView(host.getActivity());
        agree.setBasicText(host.t("sample/demo/checkbox/subscribe"));
        agree.setSelectedState(true);
        host.content().addView(agree, host.withTopMargin(10));

        BasicCheckboxView disabled = new BasicCheckboxView(host.getActivity());
        disabled.setBasicText(host.t("sample/demo/checkbox/disabled"));
        disabled.setBasicDisabled(true);
        host.content().addView(disabled, host.withTopMargin(10));

        LinearLayout group = new LinearLayout(host.getActivity());
        group.setOrientation(LinearLayout.VERTICAL);
        BasicRadioView beginner = host.radio(host.t("sample/demo/radio/beginner"), true);
        BasicRadioView android = host.radio(host.t("sample/demo/radio/android"), false);
        BasicRadioView aiTool = host.radio(host.t("sample/demo/radio/ai"), false);
        BasicRadioView[] radios = new BasicRadioView[]{beginner, android, aiTool};
        for (BasicRadioView radio : radios) {
            radio.setOnClickListener(view -> {
                for (BasicRadioView item : radios) {
                    item.setSelectedState(item == view);
                }
            });
            group.addView(radio, host.withTopMargin(8));
        }
        host.content().addView(group, host.fullWidth());
    }

    private static void addSwitchSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/switches"));
        BasicSwitchView publish = new BasicSwitchView(host.getActivity());
        publish.setBasicText(host.t("sample/demo/switch/publish"));
        publish.setCheckedText(host.t("sample/demo/switch/on"));
        publish.setUncheckedText(host.t("sample/demo/switch/off"));
        publish.setSelectedState(true);
        host.content().addView(publish, host.withTopMargin(10));

        BasicSwitchView draft = new BasicSwitchView(host.getActivity());
        draft.setBasicText(host.t("sample/demo/switch/draft"));
        draft.setCheckedText(host.t("sample/demo/switch/on_upper"));
        draft.setUncheckedText(host.t("sample/demo/switch/off_upper"));
        host.content().addView(draft, host.withTopMargin(10));

        BasicSwitchView small = new BasicSwitchView(host.getActivity());
        small.setBasicText(host.t("sample/demo/switch/small"));
        small.setVariant(BasicSwitchView.SIZE_SMALL);
        small.setCheckedText(host.t("sample/demo/switch/y"));
        small.setUncheckedText(host.t("sample/demo/switch/n"));
        small.setSelectedState(true);
        host.content().addView(small, host.withTopMargin(10));

        BasicSwitchView loading = new BasicSwitchView(host.getActivity());
        loading.setBasicText(host.t("sample/demo/switch/loading"));
        loading.setSelectedState(true);
        loading.setLoading(true);
        host.content().addView(loading, host.withTopMargin(10));

        BasicSwitchView disabled = new BasicSwitchView(host.getActivity());
        disabled.setBasicText(host.t("sample/demo/switch/disabled"));
        disabled.setBasicDisabled(true);
        host.content().addView(disabled, host.withTopMargin(10));
    }

    private static void addProgressSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/progress_loading"));
        BasicProgressView article = new BasicProgressView(host.getActivity());
        article.setProgress(0.68f);
        host.content().addView(article, host.withTopMargin(12));

        BasicProgressView warning = new BasicProgressView(host.getActivity());
        warning.setVariant("warning");
        warning.setProgress(0.36f);
        host.content().addView(warning, host.withTopMargin(12));

        BasicLoadingView loading = new BasicLoadingView(host.getActivity());
        loading.setBasicText(host.t("sample/demo/loading/text"));
        host.content().addView(loading, host.withTopMargin(12));

        host.content().addView(host.loadingDialogButton(), host.withTopMargin(12));
    }

    private static void addDataDisplaySamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/data_display"));
        BasicTableView table = new BasicTableView(host.getActivity());
        List<List<String>> rows = new ArrayList<>();
        rows.add(Arrays.asList(
                host.t("sample/demo/table/row_switch"),
                host.t("sample/demo/table/status_done"),
                host.t("sample/demo/table/priority_core")
        ));
        rows.add(Arrays.asList(
                host.t("sample/demo/table/row_select"),
                host.t("sample/demo/table/status_new"),
                host.t("sample/demo/table/priority_common")
        ));
        rows.add(Arrays.asList(
                host.t("sample/demo/table/row_collapse"),
                host.t("sample/demo/table/status_new"),
                host.t("sample/demo/table/priority_common")
        ));
        table.setData(Arrays.asList(
                host.t("sample/demo/table/header_component"),
                host.t("sample/demo/table/header_status"),
                host.t("sample/demo/table/header_priority")
        ), rows);
        host.content().addView(table, host.withTopMargin(10));

        BasicCodeBlockView code = new BasicCodeBlockView(host.getActivity());
        code.setTitle(host.t("sample/demo/code/title"));
        code.setCode(host.t("sample/demo/code/body"));
        host.content().addView(code, host.withTopMargin(10));

        BasicTypewriterView typewriter = new BasicTypewriterView(host.getActivity());
        typewriter.setBasicText(host.t("sample/demo/typewriter"));
        host.content().addView(typewriter, host.withTopMargin(10));
    }

    private static void addRnParitySamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/rn_parity"));

        BasicAmountView amount = new BasicAmountView(host.getActivity());
        amount.setAmount("$", "128.80", host.t("sample/demo/amount/cycle"));
        host.content().addView(amount, host.withTopMargin(10));

        BasicAmountView amountAfter = new BasicAmountView(host.getActivity());
        amountAfter.setAmount("USD", "99", "");
        amountAfter.setSymbolAfter(true);
        amountAfter.setStrikeThrough(true);
        host.content().addView(amountAfter, host.withTopMargin(8));

        LinearLayout iconRow = host.horizontalWrap();
        iconRow.addView(host.iconButton("‹", "default", false));
        iconRow.addView(host.iconButton("✓", "primary", false));
        iconRow.addView(host.iconButton("×", "default", true));
        host.content().addView(iconRow, host.withTopMargin(10));

        BasicKeyValueLabelView kv = new BasicKeyValueLabelView(host.getActivity());
        kv.setPair(host.t("sample/demo/kv/label"), host.t("sample/demo/kv/value"));
        host.content().addView(kv, host.withTopMargin(10));

        BasicNotificationView notification = new BasicNotificationView(host.getActivity());
        notification.setContent(host.t("sample/demo/notification/title"), host.t("sample/demo/notification/body"));
        host.content().addView(notification, host.withTopMargin(10));

        BasicNotificationView alert = new BasicNotificationView(host.getActivity());
        alert.setVariant("alert");
        alert.setContent(host.t("sample/demo/notification/alert_title"), host.t("sample/demo/notification/alert_body"));
        host.content().addView(alert, host.withTopMargin(10));

        BasicTextLinkView link = new BasicTextLinkView(host.getActivity());
        link.setText(host.t("sample/demo/link/contract"));
        link.setOnClickListener(view -> BasicToast.show(host.getActivity(), host.t("sample/toast/contract"), "info", Toast.LENGTH_SHORT));
        host.content().addView(link, host.withTopMargin(10));

        BasicStepperView stepper3 = new BasicStepperView(host.getActivity());
        stepper3.setSteps(3, 2);
        host.content().addView(stepper3, host.withTopMargin(12));

        BasicStepperView stepper5 = new BasicStepperView(host.getActivity());
        stepper5.setSteps(5, 4);
        host.content().addView(stepper5, host.withTopMargin(12));

        BasicPinInputView pin = new BasicPinInputView(host.getActivity());
        pin.setCellCount(6);
        pin.setOnPinCompleteListener(value -> BasicToast.show(host.getActivity(), host.t("sample/toast/pin", value), "success", Toast.LENGTH_SHORT));
        host.content().addView(pin, host.withTopMargin(10));

        BasicStickyFooterView footer = new BasicStickyFooterView(host.getActivity());
        footer.addView(host.button(host.t("sample/demo/footer/action"), BasicButton.VARIANT_PRIMARY, false), host.fullWidth());
        host.content().addView(footer, host.withTopMargin(10));
    }

    private static void addListItemSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/list_items"));
        BasicListItemView app = host.listItem(
                host.t("sample/demo/list/app_title"),
                host.t("sample/demo/list/app_desc"),
                host.t("sample/demo/list/rating"),
                false,
                false
        );
        host.content().addView(app, host.withTopMargin(10));

        BasicListItemView selected = host.listItem(
                host.t("sample/demo/list/plan_title"),
                host.t("sample/demo/list/plan_desc"),
                host.t("sample/demo/list/plan_trailing"),
                true,
                false
        );
        host.content().addView(selected, host.withTopMargin(10));

        BasicListItemView disabled = host.listItem(
                host.t("sample/demo/list/video_title"),
                host.t("sample/demo/list/video_desc"),
                host.t("sample/demo/list/video_trailing"),
                false,
                true
        );
        host.content().addView(disabled, host.withTopMargin(10));
    }

    private static void addEmptySamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/empty_state"));
        BasicEmptyView empty = new BasicEmptyView(host.getActivity());
        empty.setTitle(host.t("sample/demo/empty/title"));
        empty.setMessage(host.t("sample/demo/empty/body"));
        empty.setActionText(host.t("sample/demo/empty/action"));
        empty.getActionButton().setOnClickListener(view ->
                BasicToast.show(host.getActivity(), host.t("sample/toast/create_tool"), "success", Toast.LENGTH_SHORT));
        host.content().addView(empty, host.withTopMargin(10));
    }

    private static void addTabsSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/tabs"));
        BasicTabsView tabs = new BasicTabsView(host.getActivity());
        tabs.setTabs(Arrays.asList(
                host.t("sample/demo/tabs/basic"),
                host.t("sample/demo/tabs/form"),
                host.t("sample/demo/tabs/feedback"),
                host.t("sample/demo/tabs/navigation"),
                host.t("sample/demo/tabs/data")
        ));
        tabs.setOnTabSelectedListener((index, title) ->
                BasicToast.show(host.getActivity(), host.t("sample/toast/tab_selected", title), "info", Toast.LENGTH_SHORT));
        host.content().addView(tabs, host.withTopMargin(10));
    }

    private static void addModalSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/modal"));
        BasicButton modal = host.button(host.t("sample/demo/modal/button"), BasicButton.VARIANT_PRIMARY, false);
        modal.setOnClickListener(view -> {
            BasicModalDialog dialog = BasicModalDialog.show(
                    host.getActivity(),
                    host.t("sample/demo/modal/title"),
                    host.t("sample/demo/modal/body")
            );
            dialog.setOnConfirmClickListener(confirm ->
                    BasicToast.show(host.getActivity(), host.t("sample/toast/sync_confirmed"), "success", Toast.LENGTH_SHORT));
        });
        host.content().addView(modal, host.withTopMargin(10));
    }

    private static void addToastSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/toasts"));
        BasicButton info = host.button(host.t("sample/demo/toast/show_info"), BasicButton.VARIANT_PRIMARY, false);
        info.setOnClickListener(view -> BasicToast.show(host.getActivity(), host.t("sample/toast/info"), "info", Toast.LENGTH_SHORT));
        host.content().addView(info, host.withTopMargin(10));

        BasicButton success = host.button(host.t("sample/demo/toast/show_success"), BasicButton.VARIANT_DEFAULT, false);
        success.setOnClickListener(view -> BasicToast.show(host.getActivity(), host.t("sample/toast/success"), "success", Toast.LENGTH_SHORT));
        host.content().addView(success, host.withTopMargin(10));

        BasicButton warning = host.button(host.t("sample/demo/toast/show_warning"), BasicButton.VARIANT_DEFAULT, false);
        warning.setOnClickListener(view -> BasicToast.show(host.getActivity(), host.t("sample/toast/warning"), "warning", Toast.LENGTH_SHORT));
        host.content().addView(warning, host.withTopMargin(10));

        BasicButton error = host.button(host.t("sample/demo/toast/show_error"), BasicButton.VARIANT_DANGER, false);
        error.setOnClickListener(view -> BasicToast.show(host.getActivity(), host.t("sample/toast/error"), "error", Toast.LENGTH_SHORT));
        host.content().addView(error, host.withTopMargin(10));
    }

    private static void addDatePickerSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/domain"));
        BasicDatePickerView picker = new BasicDatePickerView(host.getActivity());
        picker.setValue("2026-08-26");
        picker.setPlaceholder(host.t("sample/demo/datepicker/placeholder"));
        picker.setOnDateChangeListener(value ->
                BasicToast.show(host.getActivity(), host.t("sample/toast/datepicker", value), "info", Toast.LENGTH_SHORT));
        host.content().addView(picker, host.withTopMargin(10));
    }

    private static void addChildSwitcherSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/domain"));
        BasicChildSwitcherView chips = new BasicChildSwitcherView(host.getActivity());
        chips.setItems(Arrays.asList(
                new BasicChildSwitcherView.Item("1", host.t("sample/demo/child/yue"), "👧"),
                new BasicChildSwitcherView.Item("2", host.t("sample/demo/child/you"), "👦")
        ));
        chips.setSelectedId("1");
        chips.setOnChildChangeListener(id ->
                BasicToast.show(host.getActivity(), host.t("sample/toast/child", id), "info", Toast.LENGTH_SHORT));
        host.content().addView(chips, host.withTopMargin(10));

        BasicChildSwitcherView tabs = new BasicChildSwitcherView(host.getActivity());
        tabs.setVariant("tabs");
        tabs.setItems(Arrays.asList(
                new BasicChildSwitcherView.Item("1", host.t("sample/demo/child/yue")),
                new BasicChildSwitcherView.Item("2", host.t("sample/demo/child/you"))
        ));
        tabs.setSelectedId("1");
        host.content().addView(tabs, host.withTopMargin(10));
    }

    private static void addScoreRuleGridSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/domain"));
        BasicScoreRuleGridView grid = new BasicScoreRuleGridView(host.getActivity());
        grid.setColumns("auto");
        grid.setRules(Arrays.asList(
                new BasicScoreRuleGridView.Rule(
                        "1",
                        host.t("sample/demo/score/homework"),
                        "📝",
                        5,
                        1,
                        1
                ),
                new BasicScoreRuleGridView.Rule(
                        "2",
                        host.t("sample/demo/score/messy"),
                        "✏️",
                        -2,
                        0,
                        2
                )
        ));
        grid.setOnIncrementListener(rule ->
                BasicToast.show(host.getActivity(), host.t("sample/toast/score_plus"), "success", Toast.LENGTH_SHORT));
        host.content().addView(grid, host.withTopMargin(10));
    }

    private static void addRedeemCardGridSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/domain"));
        BasicRedeemCardGridView grid = new BasicRedeemCardGridView(host.getActivity());
        grid.setAvailablePoints(20);
        grid.setItems(Arrays.asList(
                new BasicRedeemCardGridView.Item("1", host.t("sample/demo/redeem/snack"), "🍬", 15),
                new BasicRedeemCardGridView.Item("2", host.t("sample/demo/redeem/game"), "🎮", 30)
        ));
        grid.setOnRedeemListener(item ->
                BasicToast.show(host.getActivity(), host.t("sample/toast/redeem"), "success", Toast.LENGTH_SHORT));
        host.content().addView(grid, host.withTopMargin(10));
    }

    private static void addCalendarHeatmapSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/domain"));
        BasicCalendarHeatmapView heatmap = new BasicCalendarHeatmapView(host.getActivity());
        heatmap.setYearMonth("2026-08");
        heatmap.setCells(Arrays.asList(
                new BasicCalendarHeatmapView.Cell("2026-08-01", "full"),
                new BasicCalendarHeatmapView.Cell("2026-08-02", "partial"),
                new BasicCalendarHeatmapView.Cell("2026-08-03", "exempt")
        ));
        host.content().addView(heatmap, host.withTopMargin(10));
    }

    private static void addPrintSheetSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/domain"));
        BasicPrintSheetView sheet = new BasicPrintSheetView(host.getActivity());
        sheet.setTitle(host.t("sample/demo/print/title"));
        sheet.setVariant("pinyin");
        sheet.setColumns(5);
        sheet.setItems(Arrays.asList("dǐng", "lù", "yàn", "xīn", "wǎn"));
        host.content().addView(sheet, host.withTopMargin(10));
    }

    private static void addBalanceHeroSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/domain"));
        BasicBalanceHeroView hero = new BasicBalanceHeroView(host.getActivity());
        hero.setTotal(41);
        Map<String, Integer> breakdown = new LinkedHashMap<>();
        breakdown.put("balance", 40);
        breakdown.put("ruleScore", 11);
        breakdown.put("streakBonus", 5);
        breakdown.put("redeemTotal", 15);
        hero.setBreakdown(breakdown);
        host.content().addView(hero, host.withTopMargin(10));
    }

    private static void addCheckInStreakCardSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/domain"));
        BasicCheckInStreakCardView card = new BasicCheckInStreakCardView(host.getActivity());
        card.setStreakDays(7);
        card.setTotalDays(45);
        card.setWeekProgress(0.85f);
        card.setOnOpenListener(() ->
                BasicToast.show(host.getActivity(), host.t("sample/toast/checkin"), "info", Toast.LENGTH_SHORT));
        host.content().addView(card, host.withTopMargin(10));
    }

    private static void addAvatarSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        LinearLayout row = host.horizontalWrap();
        BasicAvatarView a1 = new BasicAvatarView(host.getActivity());
        a1.setBasicText("技趣");
        BasicAvatarView a2 = new BasicAvatarView(host.getActivity());
        a2.setBasicText("SP");
        a2.setVariant("primary");
        a2.setSizeKey("lg");
        BasicAvatarView a3 = new BasicAvatarView(host.getActivity());
        a3.setBasicText("A");
        a3.setVariant("subtle");
        a3.setSizeKey("sm");
        row.addView(a1);
        row.addView(a2);
        row.addView(a3);
        host.content().addView(row, host.withTopMargin(10));
    }

    private static void addSkeletonSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicSkeletonView skeleton = new BasicSkeletonView(host.getActivity());
        skeleton.setRows(3);
        skeleton.setShowAvatar(true);
        skeleton.setAnimated(true);
        host.content().addView(skeleton, host.withTopMargin(10));
    }

    private static void addTooltipSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicTooltipView tooltip = new BasicTooltipView(host.getActivity());
        tooltip.setTipText("Sky Planet tip");
        tooltip.setPlacement("top");
        BasicButton trigger = host.button(host.t("sample/demo/button/default"), BasicButton.VARIANT_DEFAULT, false);
        tooltip.addView(trigger, new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.WRAP_CONTENT,
                android.widget.FrameLayout.LayoutParams.WRAP_CONTENT));
        host.content().addView(tooltip, host.withTopMargin(10));
    }

    private static void addSliderSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicSliderView slider = new BasicSliderView(host.getActivity());
        slider.setMin(0f);
        slider.setMax(100f);
        slider.setStep(1f);
        slider.setValue(40f);
        host.content().addView(slider, host.withTopMargin(10));
    }

    private static void addTextAreaSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicTextAreaView area = new BasicTextAreaView(host.getActivity());
        area.setPlaceholder("Write a note…");
        area.setRows(3);
        host.content().addView(area, host.withTopMargin(10));
    }

    private static void addDrawerSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicDrawerView drawer = new BasicDrawerView(host.getActivity());
        drawer.setTitleText("Drawer");
        drawer.setPlacement("bottom");
        drawer.setBody(host.text("Sky Planet drawer body.", host.style().textMd, host.colors().textPrimary, false));
        drawer.setVisibleState(false);
        BasicButton open = host.button("Open Drawer", BasicButton.VARIANT_PRIMARY, false);
        open.setOnClickListener(v -> drawer.setVisibleState(true));
        drawer.setOnCloseListener(view -> drawer.setVisibleState(false));
        host.content().addView(open, host.withTopMargin(10));
        host.content().addView(drawer, host.withTopMargin(10));
    }

    private static void addInputNumberSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicInputNumberView inputNumber = new BasicInputNumberView(host.getActivity());
        inputNumber.setMin(0f);
        inputNumber.setMax(10f);
        inputNumber.setStep(1f);
        inputNumber.setValue(3f);
        host.content().addView(inputNumber, host.withTopMargin(10));
    }

    private static void addSwiperSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicSwiperView swiper = new BasicSwiperView(host.getActivity());
        swiper.setItems(Arrays.asList("Slide A", "Slide B", "Slide C"));
        swiper.setIndex(0);
        host.content().addView(swiper, host.withTopMargin(10));
    }

    private static void addTagSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        LinearLayout row = host.horizontalWrap();
        BasicTagView def = new BasicTagView(host.getActivity());
        def.setBasicText("default");
        BasicTagView primary = new BasicTagView(host.getActivity());
        primary.setBasicText("primary");
        primary.setVariant("primary");
        BasicTagView closable = new BasicTagView(host.getActivity());
        closable.setBasicText("closable");
        closable.setClosable(true);
        row.addView(def);
        row.addView(primary);
        row.addView(closable);
        host.content().addView(row, host.withTopMargin(10));
    }

    private static void addFabSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        LinearLayout row = host.horizontalWrap();
        BasicFabView icon = new BasicFabView(host.getActivity());
        icon.setIcon("+");
        BasicFabView extended = new BasicFabView(host.getActivity());
        extended.setIcon("+");
        extended.setBasicText("新建");
        BasicFabView flat = new BasicFabView(host.getActivity());
        flat.setIcon("✎");
        flat.setBasicText("默认");
        flat.setVariant("default");
        row.addView(icon);
        row.addView(extended);
        row.addView(flat);
        host.content().addView(row, host.withTopMargin(10));
    }

    private static void addTimePickerSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicTimePickerView picker = new BasicTimePickerView(host.getActivity());
        picker.setValue("09:30");
        picker.setPlaceholder("HH:mm");
        host.content().addView(picker, host.withTopMargin(10));
    }

    private static void addUploadSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicUploadView upload = new BasicUploadView(host.getActivity());
        List<BasicUploadView.FileItem> files = new ArrayList<>();
        files.add(new BasicUploadView.FileItem("1", "readme.md"));
        upload.setFiles(files);
        upload.setOnPickRequestListener(view -> {
            List<BasicUploadView.FileItem> next = new ArrayList<>(view.getFiles());
            next.add(new BasicUploadView.FileItem(String.valueOf(System.currentTimeMillis()), "mock.txt"));
            view.setFiles(next);
        });
        host.content().addView(upload, host.withTopMargin(10));
    }

    private static void addTableContractSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicTableView table = new BasicTableView(host.getActivity());
        List<List<String>> rows = new ArrayList<>();
        rows.add(Arrays.asList("Avatar", "就绪"));
        rows.add(Arrays.asList("Tag", "新增"));
        table.setData(Arrays.asList("名称", "状态"), rows);
        table.setVariant("striped");
        host.content().addView(table, host.withTopMargin(10));
    }

    private static void addTreeSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicTreeView tree = new BasicTreeView(host.getActivity());
        List<BasicTreeView.Node> items = new ArrayList<>();
        items.add(new BasicTreeView.Node("a", "星球", Arrays.asList(
                new BasicTreeView.Node("a1", "天空", null),
                new BasicTreeView.Node("a2", "岛屿", null)
        )));
        items.add(new BasicTreeView.Node("b", "玩法", Arrays.asList(
                new BasicTreeView.Node("b1", "闯关", null)
        )));
        tree.setItems(items);
        tree.setExpandedIds(Arrays.asList("a"));
        tree.setSelectedId("a1");
        host.content().addView(tree, host.withTopMargin(10));
    }

    private static void addCascaderSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicCascaderView cascader = new BasicCascaderView(host.getActivity());
        List<BasicCascaderView.Option> options = new ArrayList<>();
        options.add(new BasicCascaderView.Option("asia", "亚洲", Arrays.asList(
                new BasicCascaderView.Option("cn", "中国", null),
                new BasicCascaderView.Option("jp", "日本", null)
        )));
        options.add(new BasicCascaderView.Option("eu", "欧洲", Arrays.asList(
                new BasicCascaderView.Option("fr", "法国", null)
        )));
        cascader.setOptions(options);
        cascader.setPlaceholder("请选择地区");
        host.content().addView(cascader, host.withTopMargin(10));
    }

    private static void addSearchBarSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicSearchBarView search = new BasicSearchBarView(host.getActivity());
        search.setHint("Search…");
        host.content().addView(search, host.withTopMargin(10));
        BasicSearchBarView disabled = new BasicSearchBarView(host.getActivity());
        disabled.setHint("Disabled");
        disabled.setBasicDisabled(true);
        host.content().addView(disabled, host.withTopMargin(10));
    }

    private static void addSegmentedControlSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicSegmentedControl segmented = new BasicSegmentedControl(host.getActivity());
        segmented.setOptions(Arrays.asList("Day", "Week", "Month"), Arrays.asList("day", "week", "month"));
        segmented.setSelectedIndex(0);
        host.content().addView(segmented, host.withTopMargin(10));
    }

    private static void addStarRatingSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicStarRatingView rating = new BasicStarRatingView(host.getActivity());
        rating.setStarCount(5);
        rating.setSelectedStars(3);
        host.content().addView(rating, host.withTopMargin(10));
        BasicStarRatingView disabled = new BasicStarRatingView(host.getActivity());
        disabled.setStarCount(5);
        disabled.setSelectedStars(4);
        disabled.setBasicDisabled(true);
        host.content().addView(disabled, host.withTopMargin(10));
    }

    private static void addLoadingDialogSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicButton open = host.button("Show Loading", BasicButton.VARIANT_PRIMARY, false);
        open.setOnClickListener(v -> {
            BasicLoadingDialog dialog = BasicLoadingDialog.show(host.getActivity(), "同步主题中...");
            open.postDelayed(dialog::dismiss, 1400);
        });
        host.content().addView(open, host.withTopMargin(10));
    }

    private static void addRefreshLayoutSamples(SamplePageHost host) {
        host.addSectionTitle(host.t("sample/section/usage"));
        BasicRefreshLayout refresh = new BasicRefreshLayout(host.getActivity());
        LinearLayout content = new LinearLayout(host.getActivity());
        content.setOrientation(LinearLayout.VERTICAL);
        content.addView(host.text("下拉刷新 / 上拉加载", host.style().textMd, host.colors().textPrimary, true), host.fullWidth());
        content.addView(host.listItem("列表项 1", "示例内容", "›", false, false), host.withTopMargin(8));
        content.addView(host.listItem("列表项 2", "示例内容", "›", false, false), host.withTopMargin(8));
        refresh.setContentView(content);
        refresh.setOnRefreshLoadListener(new BasicRefreshLayout.OnRefreshLoadListener() {
            @Override
            public void onRefresh() {
                refresh.postDelayed(() -> {
                    refresh.finishRefresh();
                    Toast.makeText(host.getActivity(), host.t("sample/toast/refreshed"), Toast.LENGTH_SHORT).show();
                }, 900);
            }

            @Override
            public void onLoadMore() {
                refresh.postDelayed(() -> {
                    refresh.finishLoadMore();
                    Toast.makeText(host.getActivity(), host.t("sample/toast/load_more"), Toast.LENGTH_SHORT).show();
                }, 900);
            }
        });
        LinearLayout.LayoutParams lp = host.fullWidth();
        lp.height = (int) (280 * host.getActivity().getResources().getDisplayMetrics().density);
        host.content().addView(refresh, lp);
    }

}
