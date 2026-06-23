package com.techskillplanet.basiccontrols.samples;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.techskillplanet.basiccontrols.i18n.BasicI18nManager;
import com.techskillplanet.basiccontrols.system.BasicEdgeToEdgeHelper;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;
import com.techskillplanet.basiccontrols.widget.BasicAlertView;
import com.techskillplanet.basiccontrols.widget.BasicBadgeView;
import com.techskillplanet.basiccontrols.widget.BasicBottomTabView;
import com.techskillplanet.basiccontrols.widget.BasicButton;
import com.techskillplanet.basiccontrols.widget.BasicCardView;
import com.techskillplanet.basiccontrols.widget.BasicChipView;
import com.techskillplanet.basiccontrols.widget.BasicIconButtonView;
import com.techskillplanet.basiccontrols.widget.BasicListItemView;
import com.techskillplanet.basiccontrols.widget.BasicLoadingDialog;
import com.techskillplanet.basiccontrols.widget.BasicRadioView;
import com.techskillplanet.basiccontrols.widget.BasicRefreshLayout;
import com.techskillplanet.basiccontrols.widget.BasicToast;
import com.techskillplanet.basiccontrols.widget.BasicTopBarView;
import com.techskillplanet.basiccontrols.samples.navigation.SampleRoute;
import com.techskillplanet.basiccontrols.samples.navigation.SampleRouter;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Basic Controls Sample 壳 Activity（Shell）。
 * <p>
 * 架构：Shell + Router + Page，与 RN {@code AppRouter.js} 对齐，见 {@code docs/PLATFORM_STRUCTURE.md}。
 * <pre>
 * Router（SampleRouter）     → 路由状态 home / settings / detail
 * Shell（本类 buildShell）    → TopBar + RefreshLayout + BottomTab，生命周期内只建一次
 * Page（*SamplePage.render）  → 往 content 容器拼 UI，通过 SamplePageHost 访问主题与导航
 * </pre>
 * 换页本质：根据 {@link #router} 清空 {@link #content} 后 dispatch 到对应 Page；
 * 不是 Intent 多 Activity，也不是 Fragment back stack。
 */
public class MainActivity extends Activity implements SamplePageHost {
    private static final String SAMPLE_I18N_ASSET = "i18n/sample_strings.json";

    private final SampleRouter router = new SampleRouter();
    private final Handler handler = new Handler(Looper.getMainLooper());

    /** 当前运行时主题 Token，与 {@link BasicThemeManager} 同步。 */
    private BasicColors colors;
    private BasicStyle style;
    /** Page 渲染目标：换页时 {@link #renderCurrentPage()} 会 removeAllViews 后由 Page 重新 addView。 */
    private LinearLayout content;
    private BasicTopBarView topBar;
    private BasicRefreshLayout refreshLayout;
    /** 学习 / 设置 Tab；组件详情页隐藏（对齐 RN {@code TspBottomTab}）。 */
    private BasicBottomTabView bottomTab;
    private String currentColorTheme = "sky_planet_day";
    private String currentStyleProfile = "island_raised";
    private String currentLanguage = "zh-CN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BasicI18nManager.init(this, SAMPLE_I18N_ASSET, currentLanguage);
        BasicThemeManager.init(this, currentColorTheme, currentStyleProfile);
        colors = BasicThemeManager.colors();
        style = BasicThemeManager.style();
        router.restoreInstanceState(savedInstanceState);
        setContentView(buildShell());
        renderCurrentPage();
        applyEdgeToEdgeWindow();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        router.saveInstanceState(outState);
    }

    /** 固定壳层：TopBar + RefreshLayout + ScrollView content。路由切换时不调用 setContentView。 */
    private LinearLayout buildShell() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(colors.backgroundPage);

        topBar = new BasicTopBarView(this);
        topBar.setOnBackClickListener(view -> navigateBack());
        root.addView(topBar, fullWidth());

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(20), dp(18), dp(20), dp(32));

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.addView(content, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        refreshLayout = new BasicRefreshLayout(this);
        refreshLayout.setContentView(scrollView);
        refreshLayout.setOnRefreshLoadListener(new BasicRefreshLayout.OnRefreshLoadListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(() -> {
                    BasicToast.show(MainActivity.this, t("sample/toast/refreshed"), "success", Toast.LENGTH_SHORT);
                    refreshLayout.finishRefresh();
                }, 1200L);
            }

            @Override
            public void onLoadMore() {
                handler.postDelayed(() -> {
                    BasicToast.show(MainActivity.this, t("sample/toast/load_more"), "info", Toast.LENGTH_SHORT);
                    refreshLayout.finishLoadMore();
                }, 1200L);
            }
        });
        root.addView(refreshLayout, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
        ));

        bottomTab = buildBottomTab();
        root.addView(bottomTab, fullWidth());
        return root;
    }

    /** 底部 Tab：学习 + 设置，与 RN Sample tabs 一致。 */
    private BasicBottomTabView buildBottomTab() {
        BasicBottomTabView tabBar = new BasicBottomTabView(this);
        ArrayList<BasicBottomTabView.Tab> tabs = new ArrayList<>();
        tabs.add(new BasicBottomTabView.Tab("home", "⌂", "sample/tab/home", "Learn"));
        tabs.add(new BasicBottomTabView.Tab("settings", "⚙", "sample/tab/settings", "Settings"));
        tabBar.setTabs(tabs);
        tabBar.setOnTabSelectedListener((view, index, key) -> {
            if ("settings".equals(key)) {
                if (!router.route().isSettings()) {
                    router.openSettings();
                    renderCurrentPage();
                }
            } else if ("home".equals(key)) {
                if (!router.route().isHome()) {
                    router.openHome();
                    renderCurrentPage();
                }
            }
        });
        return tabBar;
    }

    private void syncBottomTab() {
        if (bottomTab == null) {
            return;
        }
        SampleRoute route = router.route();
        bottomTab.setVisibility(route.isDetail() ? View.GONE : View.VISIBLE);
        bottomTab.setSelectedIndex(route.isSettings() ? 1 : 0);
        bottomTab.refreshTheme();
    }

    /**
     * 状态驱动渲染：Router 决定 Page，Page 只负责 content 区域。
     * Shell 控件在此同步标题、返回键与主题，然后清空 content 并 dispatch。
     */
    private void renderCurrentPage() {
        topBar.setTitle(router.topBarTitle(this));
        topBar.setBackVisible(router.showBack());
        topBar.refreshTheme();
        refreshLayout.refreshTheme();
        content.removeAllViews(); // 换页实现：清空 Page 容器，由下方 Page 类重新 addView

        SampleRoute route = router.route();
        if (route.isHome()) {
            HomeSamplePage.render(this);
        } else if (route.isSettings()) {
            SettingsSamplePage.render(this);
        } else if (route.isDetail()) {
            ComponentDetailSamplePage.render(this, route.componentId());
        }
        syncBottomTab();
    }

    private void applyEdgeToEdgeWindow() {
        BasicEdgeToEdgeHelper.applyWindow(this, BasicEdgeToEdgeHelper.shouldUseLightStatusBarIcons(colors));
    }

    /** 主题/语言变更后刷新 Shell 与当前 Page，避免整页 setContentView。 */
    private void refreshThemeRuntime() {
        colors = BasicThemeManager.colors();
        style = BasicThemeManager.style();
        getWindow().getDecorView().setBackgroundColor(colors.backgroundPage);
        topBar.refreshTheme();
        refreshLayout.refreshTheme();
        if (bottomTab != null) {
            bottomTab.refreshText();
            bottomTab.refreshTheme();
        }
        applyEdgeToEdgeWindow();
        renderCurrentPage();
    }

    @Override
    public String t(String key) {
        return BasicI18nManager.text(key);
    }

    @Override
    public String t(String key, Object... args) {
        return String.format(Locale.getDefault(), BasicI18nManager.text(key), args);
    }

    // --- SamplePageHost：导航（更新 Router → renderCurrentPage）---

    @Override
    public void navigateHome() {
        router.openHome();
        renderCurrentPage();
    }

    @Override
    public void navigateSettings() {
        router.openSettings();
        renderCurrentPage();
    }

    @Override
    public void navigateComponent(String componentId) {
        router.openDetail(componentId);
        renderCurrentPage();
    }

    @Override
    public void navigateBack() {
        router.back();
        renderCurrentPage();
    }

    @Override
    public void reloadThemeAndPage() {
        refreshThemeRuntime();
    }

    // --- SamplePageHost：Demo 控件工厂（统一主题 Token 与间距）---

    @Override
    public void addSectionTitle(String title) {
        TextView view = text(title, style.textLg, colors.textPrimary, true);
        content.addView(view, withTopMargin(24));
    }

    @Override
    public BasicButton button(String label, String variant, boolean disabled) {
        BasicButton button = new BasicButton(this);
        button.setBasicText(label);
        button.setVariant(variant);
        button.setBasicDisabled(disabled);
        if (!disabled) {
            button.setOnClickListener(view -> BasicToast.show(this, label, "info", Toast.LENGTH_SHORT));
        }
        return button;
    }

    @Override
    public BasicButton themeButton(SettingsSamplePage.ThemePreset preset) {
        String label = preset.label(this);
        boolean selected = preset.colorTheme.equals(currentColorTheme)
                && preset.styleProfile.equals(currentStyleProfile);
        BasicButton button = button(label, selected
                ? BasicButton.VARIANT_PRIMARY
                : BasicButton.VARIANT_DEFAULT, false);
        button.setOnClickListener(view -> {
            currentColorTheme = preset.colorTheme;
            currentStyleProfile = preset.styleProfile;
            BasicThemeManager.init(this, currentColorTheme, currentStyleProfile);
            reloadThemeAndPage();
            BasicToast.show(this, t("sample/toast/theme_changed", label), "success", Toast.LENGTH_SHORT);
        });
        return button;
    }

    @Override
    public String currentThemeLabel() {
        for (SettingsSamplePage.ThemePreset preset : SettingsSamplePage.THEME_PRESETS) {
            if (preset.colorTheme.equals(currentColorTheme)
                    && preset.styleProfile.equals(currentStyleProfile)) {
                return preset.label(this);
            }
        }
        return currentColorTheme + " / " + currentStyleProfile;
    }

    @Override
    public BasicButton languageButton(String label, String language) {
        BasicButton button = button(label, language.equals(currentLanguage)
                ? BasicButton.VARIANT_PRIMARY
                : BasicButton.VARIANT_DEFAULT, false);
        button.setOnClickListener(view -> {
            currentLanguage = language;
            BasicI18nManager.setLanguage(language);
            reloadThemeAndPage();
            BasicToast.show(this, t("sample/toast/language_changed", label), "success", Toast.LENGTH_SHORT);
        });
        return button;
    }

    @Override
    public BasicCardView card(String title, String body) {
        BasicCardView card = new BasicCardView(this);
        LinearLayout inner = new LinearLayout(this);
        inner.setOrientation(LinearLayout.VERTICAL);
        inner.addView(text(title, style.textMd, colors.textPrimary, true), fullWidth());
        inner.addView(text(body, style.textSm, colors.textSecondary, false), withTopMargin(4));
        card.addView(inner, fullWidth());
        return card;
    }

    @Override
    public BasicAlertView alert(String title, String message, String variant) {
        BasicAlertView alert = new BasicAlertView(this);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setVariant(variant);
        return alert;
    }

    @Override
    public BasicBadgeView badge(String text, String variant, boolean disabled) {
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

    @Override
    public BasicChipView chip(String text, String variant, boolean selected) {
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

    @Override
    public BasicRadioView radio(String text, boolean selected) {
        BasicRadioView radio = new BasicRadioView(this);
        radio.setBasicText(text);
        radio.setSelectedState(selected);
        return radio;
    }

    @Override
    public BasicListItemView listItem(String title, String message, String trailing, boolean selected, boolean disabled) {
        BasicListItemView item = new BasicListItemView(this);
        item.setTitle(title);
        item.setMessage(message);
        item.setTrailingText(trailing);
        item.setSelectedState(selected);
        item.setBasicDisabled(disabled);
        return item;
    }

    @Override
    public BasicIconButtonView iconButton(String text, String variant, boolean disabled) {
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

    @Override
    public LinearLayout horizontalWrap() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        return row;
    }

    @Override
    public BasicButton loadingDialogButton() {
        BasicButton button = button(t("sample/demo/loading/dialog_button"), BasicButton.VARIANT_PRIMARY, false);
        button.setOnClickListener(view -> {
            BasicLoadingDialog dialog = BasicLoadingDialog.show(this, t("sample/demo/loading/dialog_message"));
            handler.postDelayed(dialog::dismiss, 1800L);
        });
        return button;
    }

    @Override
    public TextView text(String value, float sizePx, int color, boolean bold) {
        TextView textView = new TextView(this);
        textView.setText(value);
        textView.setTextColor(color);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizePx);
        textView.setTypeface(android.graphics.Typeface.DEFAULT, bold
                ? android.graphics.Typeface.BOLD
                : android.graphics.Typeface.NORMAL);
        return textView;
    }

    @Override
    public LinearLayout.LayoutParams fullWidth() {
        return new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public LinearLayout.LayoutParams withTopMargin(int topDp) {
        LinearLayout.LayoutParams params = fullWidth();
        params.setMargins(0, dp(topDp), 0, 0);
        return params;
    }

    @Override
    public LinearLayout.LayoutParams fullBleedWidth() {
        LinearLayout.LayoutParams params = fullWidth();
        int inset = dp(20);
        params.setMargins(-inset, 0, -inset, 0);
        return params;
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public BasicColors colors() {
        return colors;
    }

    @Override
    public BasicStyle style() {
        return style;
    }

    @Override
    public LinearLayout content() {
        return content;
    }

    @Override
    public String currentLanguage() {
        return currentLanguage;
    }
}
