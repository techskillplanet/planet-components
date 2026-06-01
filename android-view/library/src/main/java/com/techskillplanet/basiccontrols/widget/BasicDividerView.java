package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 基础分割线组件。
 *
 * <p>参考 animal-island-ui Divider 的轻量分隔语义，用技趣星球浅蓝边框色绘制。
 * 适合列表、表单块、代码块标题栏等场景。</p>
 */
public class BasicDividerView extends View {
    public BasicDividerView(Context context) {
        this(context, null);
    }

    public BasicDividerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicDividerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        refreshTheme();
    }

    /** Divider 不使用变体，保留统一组件协议。 */
    public void setVariant(String variant) {
        refreshTheme();
    }

    /** Divider 没有可见文字，使用 contentDescription 承载语义。 */
    public void setBasicText(CharSequence text) {
        setContentDescription(text);
    }

    /** Divider 不使用选中态，保留统一组件协议。 */
    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    /** 设置禁用态，禁用时使用更浅的背景。 */
    public void setBasicDisabled(boolean disabled) {
        setEnabled(!disabled);
        refreshTheme();
    }

    /** 刷新分割线颜色。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        setBackgroundColor(isEnabled() ? colors.borderLight : colors.backgroundSurfaceDisabled);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        BasicStyle style = BasicThemeManager.style();
        setMeasuredDimension(
                MeasureSpec.getSize(widthMeasureSpec),
                resolveSize(Math.max(1, Math.round(style.borderHairline)), heightMeasureSpec)
        );
    }
}
