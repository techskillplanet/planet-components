package com.techskillplanet.planetcomponents.widget;

import android.app.DatePickerDialog;
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
 * 技趣星球主题日期选择器（YYYY-MM-DD）。
 *
 * <p>点击后弹出系统 {@link DatePickerDialog}，选中值通过回调以字符串形式返回。</p>
 */
public class BasicDatePickerView extends TextView {
    /** 日期变更回调。 */
    public interface OnDateChangeListener {
        /** @param value YYYY-MM-DD 格式日期。 */
        void onDateChange(String value);
    }

    private String value = "";
    private String min;
    private String max;
    private String placeholder = "YYYY-MM-DD";
    private boolean basicDisabled;
    private OnDateChangeListener listener;

    public BasicDatePickerView(Context context) {
        this(context, null);
    }

    public BasicDatePickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicDatePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    /** 设置当前日期值（YYYY-MM-DD）。 */
    public void setValue(String value) {
        this.value = value == null ? "" : value.trim();
        refreshTheme();
    }

    /** 返回当前日期值。 */
    public String getValue() {
        return value;
    }

    /** 设置最早可选日期（YYYY-MM-DD）。 */
    public void setMin(String min) {
        this.min = emptyToNull(min);
    }

    /** 设置最晚可选日期（YYYY-MM-DD）。 */
    public void setMax(String max) {
        this.max = emptyToNull(max);
    }

    /** 设置占位文案。 */
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder == null || placeholder.length() == 0 ? "YYYY-MM-DD" : placeholder;
        refreshTheme();
    }

    /** 设置日期变更监听。 */
    public void setOnDateChangeListener(OnDateChangeListener listener) {
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
    }

    private void openPicker() {
        if (basicDisabled || !isEnabled()) {
            return;
        }
        Calendar initial = parseOrNow(value);
        Calendar minCal = parseCalendar(min);
        Calendar maxCal = parseCalendar(max);
        DatePickerDialog dialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    String next = format(year, month + 1, dayOfMonth);
                    setValue(next);
                    if (listener != null) {
                        listener.onDateChange(next);
                    }
                },
                initial.get(Calendar.YEAR),
                initial.get(Calendar.MONTH),
                initial.get(Calendar.DAY_OF_MONTH)
        );
        if (minCal != null) {
            dialog.getDatePicker().setMinDate(minCal.getTimeInMillis());
        }
        if (maxCal != null) {
            dialog.getDatePicker().setMaxDate(maxCal.getTimeInMillis());
        }
        dialog.show();
    }

    private static String format(int year, int month, int day) {
        return String.format(Locale.US, "%04d-%02d-%02d", year, month, day);
    }

    private static Calendar parseOrNow(String raw) {
        Calendar parsed = parseCalendar(raw);
        if (parsed != null) {
            return parsed;
        }
        return Calendar.getInstance();
    }

    private static Calendar parseCalendar(String raw) {
        if (raw == null || raw.length() == 0) {
            return null;
        }
        String[] parts = raw.split("-");
        if (parts.length != 3) {
            return null;
        }
        try {
            int y = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            int d = Integer.parseInt(parts[2]);
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(y, m - 1, d);
            return cal;
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private static String emptyToNull(String raw) {
        if (raw == null) {
            return null;
        }
        String trimmed = raw.trim();
        return trimmed.length() == 0 ? null : trimmed;
    }

    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String xmlText = array.getString(R.styleable.BasicView_basicText);
            if (xmlText != null) {
                value = xmlText;
            }
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setEnabled(!basicDisabled);
            setClickable(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
