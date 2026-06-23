package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import com.techskillplanet.basiccontrols.R;
import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 基础徽标组件。
 *
 * <p>适合状态标签、数量徽标和轻量提示。组件继承 TextView，因此可以直接使用
 * TextView 的 setText、setMaxLines 等能力。</p>
 */
public class BasicBadgeView extends android.widget.TextView {
    /** 当前状态变体，例如 default/primary/success/warning/danger。 */
    private String variant = "default";
    /** 禁用态用于弱化不可用状态。 */
    private boolean basicDisabled;

    public BasicBadgeView(Context context) {
        this(context, null);
    }

    public BasicBadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicBadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
        setSingleLine(true);
        setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        readAttrs(attrs);
        refreshTheme();
    }

    /** 设置徽标变体。 */
    public void setVariant(String variant) {
        this.variant = variant == null ? "default" : variant;
        refreshTheme();
    }

    /** 设置徽标文字。 */
    public void setBasicText(CharSequence text) {
        setText(text);
    }

    /** 设置选中态，选中后按 primary 视觉刷新。 */
    public void setSelectedState(boolean selected) {
        setSelected(selected);
        refreshTheme();
    }

    /** 设置禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        refreshTheme();
    }

    /** 根据当前变体刷新背景和文字颜色。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int fill = colors.backgroundSurfaceSubtle;
        int text = colors.textSecondary;
        if (isSelected() || "primary".equals(variant)) {
            fill = colors.brandPrimary;
            text = colors.textInverse;
        } else if ("success".equals(variant)) {
            fill = colors.statusSuccess;
            text = colors.textInverse;
        } else if ("warning".equals(variant)) {
            fill = colors.statusWarning;
            text = colors.textPrimary;
        } else if ("danger".equals(variant) || "error".equals(variant)) {
            fill = colors.statusDanger;
            text = colors.textInverse;
        }
        if (basicDisabled || !isEnabled()) {
            fill = colors.backgroundSurfaceDisabled;
            text = colors.textDisabled;
        }
        setTextColor(text);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        setMinHeight(Math.round(style.badgeHeight));
        setMinWidth(Math.round(style.badgeHeight));
        setPadding(Math.round(style.spaceSm), 0, Math.round(style.spaceSm), 0);
        setBackground(BasicDrawableFactory.roundedFill(fill, style.radiusPill));
    }

    /** 从 XML 读取通用 BasicView 属性。 */
    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String xmlVariant = array.getString(R.styleable.BasicView_basicVariant);
            String xmlText = array.getString(R.styleable.BasicView_basicText);
            variant = xmlVariant == null ? "default" : xmlVariant;
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setSelected(array.getBoolean(R.styleable.BasicView_basicSelected, false));
            if (xmlText != null) {
                setText(xmlText);
            }
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
