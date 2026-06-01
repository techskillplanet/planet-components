package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 基础代码块组件。
 *
 * <p>参考 animal-island-ui CodeBlock 的标题栏和圆角代码容器，用于展示提示词、
 * Java/XML 片段或配置示例。</p>
 */
public class BasicCodeBlockView extends LinearLayout {
    private final TextView titleView;
    private final TextView codeView;

    public BasicCodeBlockView(Context context) {
        this(context, null);
    }

    public BasicCodeBlockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicCodeBlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        titleView = new TextView(context);
        titleView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        HorizontalScrollView scroll = new HorizontalScrollView(context);
        codeView = new TextView(context);
        codeView.setTypeface(Typeface.MONOSPACE);
        scroll.addView(codeView);
        addView(titleView);
        addView(scroll);
        refreshTheme();
    }

    /** 设置语言/标题。 */
    public void setTitle(CharSequence title) {
        titleView.setText(title);
    }

    /** 设置代码内容。 */
    public void setCode(CharSequence code) {
        codeView.setText(code);
    }

    /** 统一协议：设置代码内容。 */
    public void setBasicText(CharSequence text) {
        setCode(text);
    }

    /** 当前不使用变体。 */
    public void setVariant(String variant) {
        refreshTheme();
    }

    /** 当前不使用选中态。 */
    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    /** 设置禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        setEnabled(!disabled);
        refreshTheme();
    }

    /** 刷新代码块主题。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderLight,
                style.borderHairline,
                style.radiusControlIsland
        ));
        titleView.setTextColor(colors.textPrimary);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        titleView.setGravity(Gravity.CENTER_VERTICAL);
        titleView.setPadding(Math.round(style.spaceMd), 0, Math.round(style.spaceMd), 0);
        titleView.setMinHeight(Math.round(style.controlHeightSm));
        codeView.setTextColor(colors.textPrimary);
        codeView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        codeView.setPadding(Math.round(style.spaceMd), Math.round(style.spaceMd),
                Math.round(style.spaceMd), Math.round(style.spaceMd));
        codeView.setBackground(BasicDrawableFactory.roundedFill(colors.backgroundSurfaceSubtle, style.radiusMd));
        ((ViewGroup.MarginLayoutParams) codeView.getLayoutParams()).setMargins(0, 0, 0, 0);
    }
}
