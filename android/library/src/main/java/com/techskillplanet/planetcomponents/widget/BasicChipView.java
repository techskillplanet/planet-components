package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import com.techskillplanet.planetcomponents.R;
import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 技趣星球主题胶囊标签。
 *
 * <p>Chip 常用于筛选条件、兴趣标签、步骤状态和轻量分类。选中态对齐 RN starPlanet：
 * selectedFill 底 + success 边 + textPrimary，而非品牌实心白字。</p>
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

    /** 设置选中态，选中时使用 selectedFill + success 边框。 */
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
        int fill = colors.backgroundSurfaceRaised;
        int stroke = colors.borderDefault;
        int text = colors.textPrimary;
        if (isSelected()) {
            fill = colors.selectedFill;
            stroke = colors.statusSuccess;
            text = colors.textPrimary;
        } else if ("primary".equals(variant)) {
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
        int padH = Math.round(style.chipPaddingHorizontal);
        setPadding(padH, 0, padH, 0);
        setIncludeFontPadding(false);
        setBackground(BasicDrawableFactory.roundedFillStroke(fill, stroke, style.borderHairline, style.radiusPill));
        setAlpha(basicDisabled || !isEnabled() ? 0.45f : 1f);
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
