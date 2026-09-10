package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 数字步进输入：− / 值 / +。
 *
 * <p>对齐合约 InputNumber（value / min / max / step / disabled / onChange）。</p>
 */
public class BasicInputNumberView extends LinearLayout {
    public interface OnValueChangeListener {
        void onValueChanged(BasicInputNumberView view, float value);
    }

    private final TextView minusView;
    private final TextView valueView;
    private final TextView plusView;
    private float value;
    private float min = Float.NEGATIVE_INFINITY;
    private float max = Float.POSITIVE_INFINITY;
    private float step = 1f;
    private boolean basicDisabled;
    private OnValueChangeListener valueChangeListener;

    public BasicInputNumberView(Context context) {
        this(context, null);
    }

    public BasicInputNumberView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicInputNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        minusView = createButton(context, "−");
        valueView = new TextView(context);
        valueView.setGravity(Gravity.CENTER);
        valueView.setTypeface(Typeface.DEFAULT_BOLD);
        plusView = createButton(context, "+");

        minusView.setOnClickListener(v -> adjust(-1));
        plusView.setOnClickListener(v -> adjust(1));

        addView(minusView);
        addView(valueView, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
        addView(plusView);
        setValue(0f, false);
        refreshTheme();
    }

    public void setMin(float min) {
        this.min = min;
        setValue(value, false);
    }

    public void setMax(float max) {
        this.max = max;
        setValue(value, false);
    }

    public void setStep(float step) {
        this.step = step == 0f ? 1f : step;
    }

    public void setValue(float value) {
        setValue(value, true);
    }

    public void setValue(float value, boolean notify) {
        float next = clamp(value, min, max);
        boolean changed = Float.compare(this.value, next) != 0;
        this.value = next;
        valueView.setText(format(next));
        refreshTheme();
        if (notify && changed && valueChangeListener != null) {
            valueChangeListener.onValueChanged(this, this.value);
        }
    }

    public float getValue() {
        return value;
    }

    public void setOnValueChangeListener(OnValueChangeListener listener) {
        valueChangeListener = listener;
    }

    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        refreshTheme();
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        if (colors == null || style == null) {
            return;
        }
        boolean disabled = basicDisabled || !isEnabled();
        int height = Math.round(style.controlHeightMd);
        int btnSize = height;
        setBackground(BasicDrawableFactory.roundedFillStroke(
                disabled ? colors.backgroundSurfaceDisabled : colors.backgroundSurface,
                disabled ? colors.borderLight : colors.borderControl,
                style.borderHairline,
                style.radiusMd
        ));
        setAlpha(disabled ? 0.45f : 1f);

        LayoutParams minusParams = new LayoutParams(btnSize, height);
        LayoutParams plusParams = new LayoutParams(btnSize, height);
        LayoutParams valueParams = new LayoutParams(0, height, 1f);
        minusView.setLayoutParams(minusParams);
        plusView.setLayoutParams(plusParams);
        valueView.setLayoutParams(valueParams);

        int text = disabled ? colors.textDisabled : colors.textPrimary;
        styleButton(minusView, colors, style, disabled || value <= min);
        styleButton(plusView, colors, style, disabled || value >= max);
        valueView.setTextColor(text);
        valueView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        setContentDescription("InputNumber " + format(value));
        setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
    }

    private void adjust(int direction) {
        if (basicDisabled || !isEnabled()) {
            return;
        }
        setValue(value + direction * step, true);
    }

    private TextView createButton(Context context, String label) {
        TextView view = new TextView(context);
        view.setText(label);
        view.setGravity(Gravity.CENTER);
        view.setTypeface(Typeface.DEFAULT_BOLD);
        view.setClickable(true);
        return view;
    }

    private void styleButton(TextView view, BasicColors colors, BasicStyle style, boolean softDisabled) {
        view.setTextColor(softDisabled ? colors.textDisabled : colors.textPrimary);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textLg);
        view.setEnabled(!softDisabled && !basicDisabled);
        view.setAlpha(softDisabled ? 0.4f : 1f);
    }

    private static float clamp(float v, float lo, float hi) {
        return Math.max(lo, Math.min(hi, v));
    }

    private static String format(float value) {
        if (Math.abs(value - Math.round(value)) < 0.0001f) {
            return String.valueOf(Math.round(value));
        }
        return String.valueOf(value);
    }
}
