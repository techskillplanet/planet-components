package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 连续数值滑块，基于平台 SeekBar。
 *
 * <p>对齐合约 Slider（value / min / max / step / disabled / onChange）。</p>
 */
public class BasicSliderView extends FrameLayout {
    public interface OnValueChangeListener {
        void onValueChanged(BasicSliderView view, float value);
    }

    private final SeekBar seekBar;
    private float min = 0f;
    private float max = 100f;
    private float step = 1f;
    private boolean basicDisabled;
    private boolean internalUpdate;
    private OnValueChangeListener valueChangeListener;

    public BasicSliderView(Context context) {
        this(context, null);
    }

    public BasicSliderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicSliderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        seekBar = new SeekBar(context);
        seekBar.setMax(1000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (internalUpdate || !fromUser) {
                    return;
                }
                float value = progressToValue(progress);
                float snapped = snap(value);
                if (Math.abs(snapped - value) > 0.0001f) {
                    setValue(snapped, false);
                }
                if (valueChangeListener != null) {
                    valueChangeListener.onValueChanged(BasicSliderView.this, snapped);
                }
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        addView(seekBar, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
        refreshTheme();
    }

    public void setMin(float min) {
        this.min = min;
        if (this.max < this.min) {
            this.max = this.min;
        }
        setValue(getValue(), false);
    }

    public void setMax(float max) {
        this.max = max;
        if (this.max < this.min) {
            this.min = this.max;
        }
        setValue(getValue(), false);
    }

    public void setStep(float step) {
        this.step = step <= 0f ? 1f : step;
        setValue(getValue(), false);
    }

    public void setValue(float value) {
        setValue(value, true);
    }

    public void setValue(float value, boolean notify) {
        float clamped = clamp(snap(value), min, max);
        internalUpdate = true;
        seekBar.setProgress(valueToProgress(clamped));
        internalUpdate = false;
        if (notify && valueChangeListener != null) {
            valueChangeListener.onValueChanged(this, clamped);
        }
    }

    public float getValue() {
        return progressToValue(seekBar.getProgress());
    }

    public void setOnValueChangeListener(OnValueChangeListener listener) {
        valueChangeListener = listener;
    }

    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        seekBar.setEnabled(!disabled);
        refreshTheme();
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        if (colors == null) {
            return;
        }
        boolean disabled = basicDisabled || !isEnabled();
        setAlpha(disabled ? 0.45f : 1f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            seekBar.setProgressTintList(ColorStateList.valueOf(colors.brandPrimary));
            seekBar.setThumbTintList(ColorStateList.valueOf(colors.brandPrimary));
            seekBar.setProgressBackgroundTintList(ColorStateList.valueOf(colors.borderLight));
        }
        setContentDescription("Slider " + getValue());
    }

    private float progressToValue(int progress) {
        float pct = progress / 1000f;
        return min + (max - min) * pct;
    }

    private int valueToProgress(float value) {
        if (max == min) {
            return 0;
        }
        float pct = (value - min) / (max - min);
        return Math.round(clamp(pct, 0f, 1f) * 1000f);
    }

    private float snap(float value) {
        if (step <= 0f) {
            return value;
        }
        float offset = value - min;
        float snapped = Math.round(offset / step) * step + min;
        return clamp(snapped, min, max);
    }

    private static float clamp(float v, float lo, float hi) {
        return Math.max(lo, Math.min(hi, v));
    }
}
