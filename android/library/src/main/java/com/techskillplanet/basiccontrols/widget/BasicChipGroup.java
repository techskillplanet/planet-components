package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 水平 Chip 容器，自动为相邻 Chip 添加 token 驱动的间距。
 */
public class BasicChipGroup extends LinearLayout {
    public BasicChipGroup(Context context) {
        this(context, null);
    }

    public BasicChipGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicChipGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setClipToPadding(false);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        LayoutParams lp = ensureLayoutParams(params);
        if (getChildCount() > 0 && index < 0) {
            lp.leftMargin = chipGapPx();
        } else if (index > 0) {
            lp.leftMargin = chipGapPx();
        }
        super.addView(child, index, lp);
    }

    private LayoutParams ensureLayoutParams(ViewGroup.LayoutParams params) {
        if (params instanceof LayoutParams) {
            return (LayoutParams) params;
        }
        return new LayoutParams(params);
    }

    private int chipGapPx() {
        BasicStyle style = BasicThemeManager.style();
        if (style == null) {
            return dp(8);
        }
        return Math.round(style.chipGap);
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }
}
