package com.techskillplanet.planetcomponents.widget;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.R;
import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

import java.util.Calendar;
import java.util.Locale;

/**
 * 技趣星球主题时间选择器（HH:mm）。
 *
 * <p>点击后弹出系统 {@link TimePickerDialog}，选中值通过回调以字符串形式返回。</p>
 */
public class BasicTimePickerView extends TextView {
    /** 时间变更回调。 */
    public interface OnTimeChangeListener {
        /** @param value HH:mm 格式时间。 */
        void onTimeChange(String value);
    }

    private String value = "";
    private String placeholder = "HH:mm";
    private boolean basicDisabled;
    private OnTimeChangeListener listener;

    public BasicTimePickerView(Context context) {
        this(context, null);
    }

    public BasicTimePickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicTimePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER_VERTICAL);
        setSingleLine(true);
        setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        setClickable(true);
        setFocusable(true);
        readAttrs(attrs);
        setOnClickListener(v -> openPicker());
        refreshTheme();
    }

    /** 设置当前时间值（HH:mm）。 */
    public void setValue(String value) {
        this.value = value == null ? "" : value.trim();
        refreshTheme();
    }

    /** 返回当前时间值。 */
    public String getValue() {
        return value;
    }

    /** 设置占位文案。 */
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder == null || placeholder.length() == 0 ? "HH:mm" : placeholder;
        refreshTheme();
    }

    /** 设置时间变更监听。 */
    public void setOnTimeChangeListener(OnTimeChangeListener listener) {
        this.listener = listener;
    }

    /** 保留统一组件协议。 */
    public void setVariant(String variant) {
        refreshTheme();
    }

    /** 与 setValue 同义。 */
    public void setBasicText(CharSequence text) {
        setValue(text == null ? "" : text.toString());
    }

    /** 选中态不改变样式。 */
    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    /** 设置禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        setClickable(!disabled);
        refreshTheme();
    }

    /** 按 token 刷新外观与文案。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        boolean empty = value == null || value.length() == 0;
        setText(empty ? placeholder : value);
        setTextColor(empty ? colors.textTertiary : colors.textPrimary);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        int padH = Math.round(style.spaceMd + 2);
        int padV = Math.round(style.spaceMd);
        setMinHeight(Math.round(style.controlHeightButtonMedium));
        setPadding(padH, padV, padH, padV);
        setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderDefault,
                style.borderDefault,
                style.radiusLg
        ));
        setAlpha(basicDisabled || !isEnabled() ? 0.45f : 1f);
        setContentDescription(empty ? placeholder : value);
    }

    private void openPicker() {
        if (basicDisabled || !isEnabled()) {
            return;
        }
        int[] hm = parseOrNow(value);
        TimePickerDialog dialog = new TimePickerDialog(
                getContext(),
                (view, hourOfDay, minute) -> {
                    String next = format(hourOfDay, minute);
                    setValue(next);
                    if (listener != null) {
                        listener.onTimeChange(next);
                    }
                },
                hm[0],
                hm[1],
                true
        );
        dialog.show();
    }

    private static int[] parseOrNow(String raw) {
        int[] parsed = parse(raw);
        if (parsed != null) {
            return parsed;
        }
        Calendar now = Calendar.getInstance();
        return new int[]{now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE)};
    }

    private static int[] parse(String raw) {
        if (raw == null || raw.length() == 0) {
            return null;
        }
        String[] parts = raw.trim().split(":");
        if (parts.length < 2) {
            return null;
        }
        try {
            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            if (h < 0 || h > 23 || m < 0 || m > 59) {
                return null;
            }
            return new int[]{h, m};
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private static String format(int hour, int minute) {
        return String.format(Locale.US, "%02d:%02d", hour, minute);
    }

    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String xmlText = array.getString(R.styleable.BasicView_basicText);
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            if (xmlText != null) {
                value = xmlText;
            }
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
