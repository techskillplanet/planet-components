package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
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
 * 基础复选框组件。
 *
 * <p>使用 LinearLayout 自绘指示器，避免依赖 AppCompat CheckBox。组件通过
 * selected 状态表达是否勾选，适合筛选项、协议确认、多选列表等常见场景。</p>
 */
public class BasicCheckboxView extends LinearLayout {
    /** 勾选方块。 */
    private final TextView indicatorView;
    /** 右侧说明文字。 */
    private final TextView labelView;
    private String variant = "default";
    private boolean basicDisabled;

    public BasicCheckboxView(Context context) {
        this(context, null);
    }

    public BasicCheckboxView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicCheckboxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setClickable(true);
        indicatorView = new TextView(context);
        indicatorView.setGravity(Gravity.CENTER);
        indicatorView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        labelView = new TextView(context);
        addView(indicatorView);
        addView(labelView);
        readAttrs(attrs);
        setOnClickListener(view -> {
            if (isEnabled()) {
                setSelectedState(!isSelected());
            }
        });
        refreshTheme();
    }

    /** 设置变体，当前保留给后续场景扩展。 */
    public void setVariant(String variant) {
        this.variant = variant == null ? "default" : variant;
        refreshTheme();
    }

    /** 设置复选框说明文字。 */
    public void setBasicText(CharSequence text) {
        labelView.setText(text);
    }

    /** 设置是否勾选。 */
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

    /** 按当前 token 和勾选状态刷新方块和文字。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        boolean checked = isSelected();
        int fill = checked ? colors.checkboxCheckedBackground : colors.checkboxBackground;
        int stroke = checked ? colors.checkboxCheckedBackground : colors.borderControl;
        int text = colors.textPrimary;
        if (basicDisabled || !isEnabled()) {
            fill = colors.backgroundSurfaceDisabled;
            stroke = colors.borderLight;
            text = colors.textDisabled;
        }
        indicatorView.setText(checked ? "✓" : "");
        indicatorView.setTextColor(colors.textInverse);
        indicatorView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        indicatorView.setBackground(BasicDrawableFactory.roundedFillStroke(fill, stroke, style.borderDefault, style.radiusSm));
        labelView.setTextColor(text);
        labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);

        int box = Math.round(style.controlHeightSm);
        LayoutParams indicatorParams = new LayoutParams(box, box);
        indicatorParams.setMargins(0, 0, Math.round(style.spaceSm), 0);
        indicatorView.setLayoutParams(indicatorParams);
        labelView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
    }

    /** 同步子 View enabled 状态，避免禁用时仍有点击反馈。 */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.setEnabled(enabled);
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
            String xmlVariant = array.getString(R.styleable.BasicView_basicVariant);
            variant = xmlVariant == null ? "default" : xmlVariant;
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setSelected(array.getBoolean(R.styleable.BasicView_basicSelected, false));
            labelView.setText(text == null ? "" : text);
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
