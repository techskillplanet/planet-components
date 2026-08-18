package com.techskillplanet.planetcomponents.samples;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.widget.BasicAlertView;
import com.techskillplanet.planetcomponents.widget.BasicButton;
import com.techskillplanet.planetcomponents.widget.BasicCardView;
import com.techskillplanet.planetcomponents.widget.BasicChipView;
import com.techskillplanet.planetcomponents.widget.BasicIconButtonView;
import com.techskillplanet.planetcomponents.widget.BasicListItemView;
import com.techskillplanet.planetcomponents.widget.BasicRadioView;
import com.techskillplanet.planetcomponents.widget.BasicBadgeView;

/**
 * Sample 页面渲染上下文（Host 接口）。
 * <p>
 * Page 类（{@link HomeSamplePage}、{@link SettingsSamplePage} 等）只依赖本接口，
 * 不直接引用 {@link MainActivity}，便于保持「一页一文件」且导航集中。
 * <p>
 * 约定：
 * <ul>
 *   <li>Page 只往 {@link #content()} 里 {@code addView}，不重建 Shell。</li>
 *   <li>导航通过 {@link #navigateSettings()} 等方法，内部由 Shell 更新 Router 并重绘。</li>
 *   <li>Demo 控件工厂方法统一主题与间距，减少各 Page 重复样板代码。</li>
 * </ul>
 */
public interface SamplePageHost {
    Activity getActivity();

    BasicColors colors();

    BasicStyle style();

    /** Shell 内 ScrollView 下的 content 容器；换页时会 {@code removeAllViews} 后由 Page 重新填充。 */
    LinearLayout content();

    String currentLanguage();

    String currentThemeLabel();

    String t(String key);

    String t(String key, Object... args);

    void navigateHome();

    void navigateSettings();

    void navigateComponent(String componentId);

    void navigateBack();

    /** 主题或语言切换后，刷新 Shell 主题并重建当前 Page（不整页 {@code setContentView}）。 */
    void reloadThemeAndPage();

    void addSectionTitle(String title);

    TextView text(String value, float sizePx, int color, boolean bold);

    LinearLayout.LayoutParams fullWidth();

    LinearLayout.LayoutParams withTopMargin(int topDp);

    LinearLayout.LayoutParams fullBleedWidth();

    BasicButton button(String label, String variant, boolean disabled);

    BasicButton themeButton(SettingsSamplePage.ThemePreset preset);

    BasicButton languageButton(String label, String language);

    BasicListItemView listItem(String title, String message, String trailing, boolean selected, boolean disabled);

    BasicCardView card(String title, String body);

    BasicAlertView alert(String title, String message, String variant);

    BasicBadgeView badge(String text, String variant, boolean disabled);

    BasicChipView chip(String text, String variant, boolean selected);

    BasicRadioView radio(String text, boolean selected);

    BasicIconButtonView iconButton(String text, String variant, boolean disabled);

    LinearLayout horizontalWrap();

    BasicButton loadingDialogButton();
}
