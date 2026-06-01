package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.techskillplanet.basiccontrols.R;
import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 云朵 surface 卡片容器。
 *
 * <p>卡片只负责视觉外壳，不假设内部内容结构；业务可以像使用 FrameLayout 一样
 * 继续 addView。圆角、边框和内边距都来自 style token。</p>
 */
public class BasicCardView extends FrameLayout {
    /** 统一组件协议中的变体字段，第一版保留给后续扩展。 */
    private String variant = "default";
    /** 禁用态会降低视觉层级，适合不可操作的信息卡。 */
    private boolean basicDisabled;

    public BasicCardView(Context context) {
        this(context, null);
    }

    public BasicCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(attrs);
        refreshTheme();
    }

    /** 设置卡片变体，当前版本保留参数并刷新主题。 */
    public void setVariant(String variant) {
        this.variant = variant == null ? "default" : variant;
        refreshTheme();
    }

    /** 卡片没有内置文字，这里保留统一接口，避免 XML/Java 协议不一致。 */
    public void setBasicText(CharSequence text) {
        setContentDescription(text);
    }

    /** 设置卡片选中态，选中时使用品牌色边框强调。 */
    public void setSelectedState(boolean selected) {
        setSelected(selected);
        refreshTheme();
    }

    /** 设置卡片禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        refreshTheme();
    }

    /** 按当前主题刷新卡片背景、边框和内边距。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int fill = basicDisabled ? colors.backgroundSurfaceDisabled : colors.backgroundSurfaceRaised;
        int stroke = isSelected() ? colors.brandPrimary : colors.borderDefault;
        if ("subtle".equals(variant)) {
            fill = colors.backgroundSurfaceSubtle;
        }
        setBackground(BasicDrawableFactory.roundedFillStroke(
                fill,
                stroke,
                style.borderHairline,
                style.radiusCardOrganic
        ));
        setPadding(
                Math.round(style.spaceLg),
                Math.round(style.spaceLg),
                Math.round(style.spaceLg),
                Math.round(style.spaceLg)
        );
    }

    /** 从 XML 中读取通用属性。 */
    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String xmlVariant = array.getString(R.styleable.BasicView_basicVariant);
            variant = xmlVariant == null ? "default" : xmlVariant;
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setSelected(array.getBoolean(R.styleable.BasicView_basicSelected, false));
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
