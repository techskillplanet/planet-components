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
 * <p>对齐 RN TspBadge：minHeight 28、水平内边距 10、字号 13sp 加粗；
 * primary/success/danger 实心底白字，default 使用 pageEnd 底 + textPrimary。</p>
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
        setTypeface(Typeface.DEFAULT_BOLD);
        setIncludeFontPadding(false);
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
        int fill = colors.backgroundPageGradientEnd;
        int text = colors.textPrimary;
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
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        setTypeface(Typeface.DEFAULT_BOLD);
        setMinHeight(Math.round(style.badgeHeight));
        int padH = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10f, getResources().getDisplayMetrics()));
        int padV = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5f, getResources().getDisplayMetrics()));
        setPadding(padH, padV, padH, padV);
        setBackground(BasicDrawableFactory.roundedFill(fill, style.radiusPill));
        setAlpha(basicDisabled || !isEnabled() ? 0.45f : 1f);
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
