package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;

import android.widget.TextView;

import com.techskillplanet.basiccontrols.R;
import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * RN IconButton 的轻量 Android 实现，图标可直接传入 glyph 或短字符。
 */
public class BasicIconButtonView extends TextView {
    private String variant = "default";

    public BasicIconButtonView(Context context) {
        this(context, null);
    }

    public BasicIconButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicIconButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
        setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        setClickable(true);
        setFocusable(true);
        readAttrs(attrs);
        refreshTheme();
    }

    public void setVariant(String variant) {
        this.variant = variant == null ? "default" : variant;
        refreshTheme();
    }

    public void setIconText(CharSequence text) {
        setText(text);
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int fill = "primary".equals(variant) ? colors.brandPrimarySubtle : colors.backgroundSurfaceRaised;
        int text = "primary".equals(variant) ? colors.brandPrimary : colors.textPrimary;
        setTextColor(text);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textTitle);
        setBackground(BasicDrawableFactory.roundedFillStroke(fill, colors.borderDefault, style.borderHairline, style.radiusPill));
        int padding = Math.round(style.spaceSm);
        setPadding(padding, padding, padding, padding);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return super.onTouchEvent(event);
        }
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            setScaleX(0.94f);
            setScaleY(0.94f);
        } else if (event.getActionMasked() == MotionEvent.ACTION_UP || event.getActionMasked() == MotionEvent.ACTION_CANCEL) {
            setScaleX(1f);
            setScaleY(1f);
        }
        return super.onTouchEvent(event);
    }

    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String xmlVariant = array.getString(R.styleable.BasicView_basicVariant);
            String xmlText = array.getString(R.styleable.BasicView_basicText);
            variant = xmlVariant == null ? "default" : xmlVariant;
            if (xmlText != null) {
                setText(xmlText);
            }
        } finally {
            array.recycle();
        }
    }
}
