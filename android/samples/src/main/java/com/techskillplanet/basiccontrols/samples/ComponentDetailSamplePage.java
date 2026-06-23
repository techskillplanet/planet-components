package com.techskillplanet.basiccontrols.samples;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.techskillplanet.basiccontrols.widget.BasicAlertView;
import com.techskillplanet.basiccontrols.widget.BasicAmountView;
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
import com.techskillplanet.basiccontrols.widget.BasicLoadingView;
import com.techskillplanet.basiccontrols.widget.BasicModalDialog;
import com.techskillplanet.basiccontrols.widget.BasicNotificationView;
import com.techskillplanet.basiccontrols.widget.BasicPinInputView;
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
 * 组件详情 Demo Page。
 * <p>
 * 每个 {@code componentId}（如 {@code "Button"}）对应一组 variant / 状态示例。
 * 新增组件 Demo：在此增加 {@code addXxxSamples} 并在 {@link #render} 的 switch 注册，
 * 同时在 {@link HomeSamplePage} 列表里加入 id。
 */
public final class ComponentDetailSamplePage {
    private ComponentDetailSamplePage() {
    }

    /** 入口：route=detail 时由 Shell 传入 {@link com.techskillplanet.basiccontrols.samples.navigation.SampleRoute#componentId()}。 */
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


}
