package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.R;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * RN Amount 的 Android 星球主题实现。
 */
public class BasicAmountView extends LinearLayout {
    private final TextView symbolView;
    private final TextView valueView;
    private final TextView cycleView;
    private String symbol = "¥";
    private String value = "0";
    private String cycle = "";
    private boolean symbolAfter;
    private boolean strikeThrough;

    public BasicAmountView(Context context) {
        this(context, null);
    }

    public BasicAmountView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicAmountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        symbolView = new TextView(context);
        valueView = new TextView(context);
        cycleView = new TextView(context);
        readAttrs(attrs);
        refreshTheme();
        refreshText();
    }

    public void setAmount(String currencySymbol, String currencyValue, String currencyCycle) {
        symbol = currencySymbol == null ? "" : currencySymbol;
        value = currencyValue == null ? "" : currencyValue;
        cycle = currencyCycle == null ? "" : currencyCycle;
        refreshText();
    }

    public void setSymbolAfter(boolean after) {
        symbolAfter = after;
        refreshText();
    }

    public void setStrikeThrough(boolean enabled) {
        strikeThrough = enabled;
        int flags = enabled ? Paint.STRIKE_THRU_TEXT_FLAG : 0;
        symbolView.setPaintFlags((symbolView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG) | flags);
        valueView.setPaintFlags((valueView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG) | flags);
        cycleView.setPaintFlags((cycleView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG) | flags);
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        symbolView.setTextColor(colors.textPrimary);
        valueView.setTextColor(colors.textPrimary);
        cycleView.setTextColor(colors.textSecondary);
        symbolView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textLg);
        valueView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textDialogTitle);
        cycleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        symbolView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        valueView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        cycleView.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        int gap = Math.round(style.spaceSm);
        symbolView.setPadding(0, 0, gap, 0);
        valueView.setPadding(0, 0, gap, 0);
        setStrikeThrough(strikeThrough);
    }

    private void refreshText() {
        removeAllViews();
        symbolView.setText(symbol);
        valueView.setText(value);
        cycleView.setText(cycle.length() == 0 ? "" : "/" + cycle);
        if (symbolAfter) {
            addView(valueView);
            addView(symbolView);
        } else {
            addView(symbolView);
            addView(valueView);
        }
        addView(cycleView);
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
            String variant = array.getString(R.styleable.BasicView_basicVariant);
            symbolAfter = "after".equals(variant);
        } finally {
            array.recycle();
        }
    }
}
