package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
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
 * RN KeyValueLabel 的 Android 实现。
 */
public class BasicKeyValueLabelView extends LinearLayout {
    private final TextView keyView;
    private final TextView valueView;

    public BasicKeyValueLabelView(Context context) {
        this(context, null);
    }

    public BasicKeyValueLabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicKeyValueLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        keyView = new TextView(context);
        valueView = new TextView(context);
        LayoutParams keyParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
        addView(keyView, keyParams);
        addView(valueView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        readAttrs(attrs);
        refreshTheme();
    }

    public void setPair(CharSequence key, CharSequence value) {
        keyView.setText(key);
        valueView.setText(value);
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        keyView.setTextColor(colors.textSecondary);
        valueView.setTextColor(colors.textPrimary);
        keyView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        valueView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        valueView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        int vertical = Math.round(style.spaceSm);
        setPadding(0, vertical, 0, vertical);
    }

    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            keyView.setText(array.getString(R.styleable.BasicView_basicTitle));
            valueView.setText(array.getString(R.styleable.BasicView_basicText));
        } finally {
            array.recycle();
        }
    }
}
