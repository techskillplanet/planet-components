package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.techskillplanet.basiccontrols.R;
import com.techskillplanet.basiccontrols.i18n.BasicI18nManager;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 支持 JSON 国际化 key 的基础文本组件。
 */
public class BasicTextView extends android.widget.TextView {
    private String textKey;
    private String variant = "body";

    public BasicTextView(Context context) {
        this(context, null);
    }

    public BasicTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(attrs);
        refreshTheme();
        refreshText();
    }

    public void setTextKey(String key) {
        textKey = key;
        refreshText();
    }

    public String getTextKey() {
        return textKey;
    }

    public void setVariant(String variant) {
        this.variant = variant == null ? "body" : variant;
        refreshTheme();
    }

    public void refreshText() {
        if (textKey != null && textKey.length() > 0) {
            setText(BasicI18nManager.text(textKey, getText().toString()));
        }
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        if ("title".equals(variant)) {
            setTextColor(colors.textPrimary);
            setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textTitle);
            setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        } else if ("caption".equals(variant)) {
            setTextColor(colors.textTertiary);
            setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
            setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        } else {
            setTextColor(colors.textSecondary);
            setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
            setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        }
    }

    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String xmlText = array.getString(R.styleable.BasicView_basicText);
            String xmlKey = array.getString(R.styleable.BasicView_basicTextKey);
            String xmlVariant = array.getString(R.styleable.BasicView_basicVariant);
            if (xmlText != null) {
                setText(xmlText);
            }
            textKey = xmlKey;
            variant = xmlVariant == null ? "body" : xmlVariant;
        } finally {
            array.recycle();
        }
    }
}
