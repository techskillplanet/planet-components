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
 * 技趣星球主题胶囊标签。
 *
 * <p>Chip 常用于筛选条件、兴趣标签、步骤状态和轻量分类。组件继承 TextView，
 * 保留原生文本能力，同时把选中态、禁用态和 variant 统一交给 token 管理。</p>
 */
public class BasicChipView extends android.widget.TextView {
    private String variant = "default";
    private boolean basicDisabled;

    public BasicChipView(Context context) {
        this(context, null);
    }

    public BasicChipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicChipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
        setSingleLine(true);
        setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        readAttrs(attrs);
        refreshTheme();
    }

    /** 设置标签变体，例如 default/primary/success/warning/danger。 */
    public void setVariant(String variant) {
        this.variant = variant == null ? "default" : variant;
        refreshTheme();
    }

    /** 设置标签文字。 */
    public void setBasicText(CharSequence text) {
        setText(text);
    }

    /** 设置选中态，选中时使用品牌主色背景。 */
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

    /** 根据 token 刷新 Chip 的背景、边框、字号和内边距。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int fill = colors.backgroundSurface;
        int stroke = colors.borderControl;
        int text = colors.textSecondary;
        if (isSelected() || "primary".equals(variant)) {
            fill = colors.brandPrimary;
            stroke = colors.brandPrimary;
            text = colors.textInverse;
        } else if ("success".equals(variant)) {
            fill = colors.backgroundSurfaceRaised;
            stroke = colors.statusSuccess;
            text = colors.statusSuccess;
        } else if ("warning".equals(variant)) {
            fill = colors.backgroundSurfaceRaised;
            stroke = colors.statusWarning;
            text = colors.textPrimary;
        } else if ("danger".equals(variant) || "error".equals(variant)) {
            fill = colors.backgroundSurfaceRaised;
            stroke = colors.statusDanger;
            text = colors.statusDanger;
        }
        if (basicDisabled || !isEnabled()) {
            fill = colors.backgroundSurfaceDisabled;
            stroke = colors.borderLight;
            text = colors.textDisabled;
        }
        setTextColor(text);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        setMinHeight(Math.round(style.chipHeight));
        setPadding(Math.round(style.spaceMd), 0, Math.round(style.spaceMd), 0);
        setBackground(BasicDrawableFactory.roundedFillStroke(fill, stroke, style.borderDefault, style.radiusPill));
    }

    /** 从 XML 读取 BasicView 通用属性。 */
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
