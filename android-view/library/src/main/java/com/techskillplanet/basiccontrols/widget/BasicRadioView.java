package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.R;
import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 基础单选项组件。
 *
 * <p>组件不管理同组互斥逻辑，业务层可以在点击某一项后手动把同组其他项取消。
 * 这样它既能用于 RadioGroup，也能用于卡片式单选列表。</p>
 */
public class BasicRadioView extends LinearLayout {
    private final View indicatorView;
    private final TextView labelView;
    private boolean basicDisabled;

    public BasicRadioView(Context context) {
        this(context, null);
    }

    public BasicRadioView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicRadioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setClickable(true);
        indicatorView = new View(context);
        labelView = new TextView(context);
        addView(indicatorView);
        addView(labelView);
        readAttrs(attrs);
        setOnClickListener(view -> {
            if (isEnabled()) {
                setSelectedState(true);
            }
        });
        refreshTheme();
    }

    /** Radio 当前不使用 variant，保留统一组件协议。 */
    public void setVariant(String variant) {
        refreshTheme();
    }

    /** 设置右侧说明文字。 */
    public void setBasicText(CharSequence text) {
        labelView.setText(text);
    }

    /** 设置是否选中。 */
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

    /** 刷新圆形指示器和文字。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int stroke = isSelected() ? colors.brandPrimary : colors.borderControl;
        int dot = isSelected() ? colors.radioCheckedDot : colors.backgroundSurface;
        int text = colors.textPrimary;
        if (basicDisabled || !isEnabled()) {
            stroke = colors.borderLight;
            dot = colors.backgroundSurfaceDisabled;
            text = colors.textDisabled;
        }

        android.graphics.drawable.Drawable outer = BasicDrawableFactory.ovalFillStroke(
                colors.backgroundSurface,
                stroke,
                style.borderDefault
        );
        android.graphics.drawable.Drawable inner = BasicDrawableFactory.ovalFillStroke(dot, dot, 0f);
        LayerDrawable layerDrawable = new LayerDrawable(new android.graphics.drawable.Drawable[]{outer, inner});
        int inset = Math.round(style.spaceSm);
        // 内层圆点通过 inset 缩小，避免手写 Canvas 时出现不同密度下的偏移误差。
        layerDrawable.setLayerInset(1, inset, inset, inset, inset);
        indicatorView.setBackground(layerDrawable);
        labelView.setTextColor(text);
        labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);

        int size = Math.round(style.controlHeightSm);
        LayoutParams indicatorParams = new LayoutParams(size, size);
        indicatorParams.setMargins(0, 0, Math.round(style.spaceSm), 0);
        indicatorView.setLayoutParams(indicatorParams);
        labelView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
    }

    /** 同步子 View enabled 状态。 */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).setEnabled(enabled);
        }
    }

    /** 从 XML 读取文字、选中态和禁用态。 */
    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String text = array.getString(R.styleable.BasicView_basicText);
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setSelected(array.getBoolean(R.styleable.BasicView_basicSelected, false));
            labelView.setText(text == null ? "" : text);
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
