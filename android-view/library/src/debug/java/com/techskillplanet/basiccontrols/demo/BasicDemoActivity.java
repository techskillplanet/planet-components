package com.techskillplanet.basiccontrols.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;
import com.techskillplanet.basiccontrols.widget.BasicAlertView;
import com.techskillplanet.basiccontrols.widget.BasicBadgeView;
import com.techskillplanet.basiccontrols.widget.BasicButton;
import com.techskillplanet.basiccontrols.widget.BasicCardView;
import com.techskillplanet.basiccontrols.widget.BasicInputView;
import com.techskillplanet.basiccontrols.widget.BasicTabsView;
import com.techskillplanet.basiccontrols.widget.BasicToast;

import java.util.Arrays;

/**
 * 核心组件人工预览页面。
 *
 * <p>该 Activity 放在 debug 源集中，只用于本地 assembleDebug 或接入宿主 App 后
 * 进行人工验收，不会进入 release AAR 的 main 源集。</p>
 */
public class BasicDemoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BasicThemeManager.init(this);
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();

        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(colors.backgroundPage);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(Math.round(style.spaceLg), Math.round(style.spaceLg),
                Math.round(style.spaceLg), Math.round(style.spaceLg));
        scrollView.addView(root, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        addTitle(root, "Basic Controls / Android View");
        addButtons(root);
        addInputs(root);
        addCards(root);
        addAlerts(root);
        addBadges(root);
        addTabs(root);
        setContentView(scrollView);
    }

    /** 添加页面标题。 */
    private void addTitle(LinearLayout root, String title) {
        BasicStyle style = BasicThemeManager.style();
        TextView titleView = new TextView(this);
        titleView.setText(title);
        titleView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, style.textTitle);
        titleView.setTextColor(BasicThemeManager.colors().textPrimary);
        root.addView(titleView, matchWrap());
    }

    /** 添加按钮状态预览。 */
    private void addButtons(LinearLayout root) {
        BasicButton primary = button("Primary", BasicButton.VARIANT_PRIMARY);
        primary.setOnClickListener(view -> BasicToast.show(this, "已点击 Primary", "success", android.widget.Toast.LENGTH_SHORT));
        root.addView(primary, blockParams());
        root.addView(button("Default", BasicButton.VARIANT_DEFAULT), blockParams());
        root.addView(button("Danger", BasicButton.VARIANT_DANGER), blockParams());
        BasicButton disabled = button("Disabled", BasicButton.VARIANT_PRIMARY);
        disabled.setBasicDisabled(true);
        root.addView(disabled, blockParams());
    }

    /** 添加输入框状态预览。 */
    private void addInputs(LinearLayout root) {
        BasicInputView input = new BasicInputView(this);
        input.getEditText().setHint("请输入内容");
        root.addView(input, blockParams());
        BasicInputView error = new BasicInputView(this);
        error.setVariant(BasicInputView.VARIANT_ERROR);
        error.getEditText().setHint("错误状态");
        root.addView(error, blockParams());
    }

    /** 添加卡片预览。 */
    private void addCards(LinearLayout root) {
        BasicCardView card = new BasicCardView(this);
        TextView text = new TextView(this);
        text.setText("云朵 surface 卡片，适合承载表单块、列表项和轻量面板。");
        text.setTextColor(BasicThemeManager.colors().textSecondary);
        text.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, BasicThemeManager.style().textMd);
        card.addView(text);
        root.addView(card, blockParams());
    }

    /** 添加四类 Alert 预览。 */
    private void addAlerts(LinearLayout root) {
        root.addView(alert("Info", "蓝色星球信息提示", BasicAlertView.VARIANT_INFO), blockParams());
        root.addView(alert("Success", "操作完成，可以继续下一步", BasicAlertView.VARIANT_SUCCESS), blockParams());
        root.addView(alert("Warning", "请检查输入内容", BasicAlertView.VARIANT_WARNING), blockParams());
        root.addView(alert("Error", "提交失败，请稍后重试", BasicAlertView.VARIANT_ERROR), blockParams());
    }

    /** 添加徽标预览。 */
    private void addBadges(LinearLayout root) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.addView(badge("默认", "default"));
        row.addView(badge("成功", "success"));
        row.addView(badge("警告", "warning"));
        row.addView(badge("危险", "danger"));
        root.addView(row, blockParams());
    }

    /** 添加 Tabs 预览。 */
    private void addTabs(LinearLayout root) {
        BasicTabsView tabs = new BasicTabsView(this);
        tabs.setTabs(Arrays.asList("基础", "表单", "反馈", "导航"));
        root.addView(tabs, blockParams());
    }

    /** 创建一个按钮实例。 */
    private BasicButton button(String text, String variant) {
        BasicButton button = new BasicButton(this);
        button.setBasicText(text);
        button.setVariant(variant);
        return button;
    }

    /** 创建一个 Alert 实例。 */
    private BasicAlertView alert(String title, String message, String variant) {
        BasicAlertView alert = new BasicAlertView(this);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setVariant(variant);
        return alert;
    }

    /** 创建一个徽标实例。 */
    private BasicBadgeView badge(String text, String variant) {
        BasicBadgeView badge = new BasicBadgeView(this);
        badge.setBasicText(text);
        badge.setVariant(variant);
        return badge;
    }

    /** 通用块级布局参数。 */
    private LinearLayout.LayoutParams blockParams() {
        BasicStyle style = BasicThemeManager.style();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, Math.round(style.spaceMd), 0, 0);
        return params;
    }

    /** 标题类布局参数。 */
    private LinearLayout.LayoutParams matchWrap() {
        return new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }
}
