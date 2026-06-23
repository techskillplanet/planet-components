package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;

import android.widget.TextView;

import com.techskillplanet.basiccontrols.R;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * RN TextLink 的 Android 实现。
 */
public class BasicTextLinkView extends TextView {
    private String variant = "default";

    public BasicTextLinkView(Context context) {
        this(context, null);
    }

    public BasicTextLinkView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicTextLinkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        setFocusable(true);
        readAttrs(attrs);
        refreshTheme();
    }

    public void setVariant(String variant) {
        this.variant = variant == null ? "default" : variant;
        refreshTheme();
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        setTextColor("inverse".equals(variant) ? colors.textInverse : colors.brandPrimary);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        setTypeface(Typeface.DEFAULT, Typeface.BOLD);
    }

    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String xmlText = array.getString(R.styleable.BasicView_basicText);
            String xmlVariant = array.getString(R.styleable.BasicView_basicVariant);
            if (xmlText != null) {
                setText(xmlText);
            }
            variant = xmlVariant == null ? "default" : xmlVariant;
        } finally {
            array.recycle();
        }
    }
}
