package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
    private int iconResId;
    private Drawable iconDrawable;

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
        iconResId = 0;
        iconDrawable = null;
        setCompoundDrawablesRelative(null, null, null, null);
        setText(text);
    }

    public void setIconResource(int resId) {
        iconResId = resId;
        setText("");
        refreshTheme();
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int fill = "primary".equals(variant) ? colors.brandPrimarySubtle : colors.backgroundSurfaceRaised;
        int text = "primary".equals(variant) ? colors.brandPrimary : colors.textPrimary;
        setTextColor(text);
        if (iconResId != 0) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, 0);
            applyIconDrawable(text);
        } else {
            setCompoundDrawablesRelative(null, null, null, null);
            setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textTitle);
        }
        setBackground(BasicDrawableFactory.roundedFill(fill, style.radiusPill));
        int padding = Math.round(style.spaceSm);
        setPadding(padding, padding, padding, padding);
    }

    private void applyIconDrawable(int tintColor) {
        Drawable drawable = loadDrawable(iconResId);
        if (drawable == null) {
            iconDrawable = null;
            setCompoundDrawablesRelative(null, null, null, null);
            return;
        }
        drawable = drawable.mutate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable.setTint(tintColor);
        }
        iconDrawable = drawable;
        setCompoundDrawablesRelative(null, null, null, null);
        setGravity(Gravity.CENTER);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (iconDrawable != null && iconResId != 0) {
            int size = Math.round(TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    22f,
                    getResources().getDisplayMetrics()
            ));
            int left = (getWidth() - size) / 2;
            int top = (getHeight() - size) / 2;
            iconDrawable.setBounds(left, top, left + size, top + size);
            iconDrawable.draw(canvas);
            return;
        }
        super.onDraw(canvas);
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

    private Drawable loadDrawable(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getContext().getDrawable(resId);
        }
        return getResources().getDrawable(resId);
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
