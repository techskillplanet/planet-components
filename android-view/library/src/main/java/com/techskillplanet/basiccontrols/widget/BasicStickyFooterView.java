package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.techskillplanet.basiccontrols.R;
import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * RN StickyFooter 的 Android 容器，固定底部操作区可直接放按钮或价格信息。
 */
public class BasicStickyFooterView extends FrameLayout {
    private boolean subtle;

    public BasicStickyFooterView(Context context) {
        this(context, null);
    }

    public BasicStickyFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicStickyFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(attrs);
        refreshTheme();
    }

    public void setSubtle(boolean subtle) {
        this.subtle = subtle;
        refreshTheme();
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int fill = subtle ? colors.backgroundSurfaceSubtle : colors.backgroundSurfaceRaised;
        setBackground(BasicDrawableFactory.roundedFillStroke(fill, colors.borderLight, style.borderHairline, 0f));
        int h = Math.round(style.spaceLg);
        int v = Math.round(style.spaceMd);
        setPadding(h, v, h, v);
    }

    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            subtle = "subtle".equals(array.getString(R.styleable.BasicView_basicVariant));
        } finally {
            array.recycle();
        }
    }
}
