package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 内部评分星级选择器，适用于 App 内评分弹窗（低分反馈 / 高分引流市场）。
 */
public class BasicStarRatingView extends LinearLayout {
    public interface OnRatingSelectedListener {
        void onRatingSelected(BasicStarRatingView view, int stars);
    }

    private static final int MAX_STARS = 5;

    private int starCount = MAX_STARS;
    private int selectedStars;
    private OnRatingSelectedListener listener;

    public BasicStarRatingView(Context context) {
        this(context, null);
    }

    public BasicStarRatingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicStarRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        renderStars();
    }

    public void setStarCount(int count) {
        starCount = Math.max(1, Math.min(count, 10));
        renderStars();
    }

    public int getSelectedStars() {
        return selectedStars;
    }

    public void setSelectedStars(int stars) {
        selectedStars = Math.max(0, Math.min(stars, starCount));
        refreshSelection();
    }

    public void setOnRatingSelectedListener(OnRatingSelectedListener listener) {
        this.listener = listener;
    }

    public void refreshTheme() {
        refreshSelection();
    }

    private void renderStars() {
        removeAllViews();
        BasicStyle style = BasicThemeManager.style();
        int size = style == null ? dp(40) : Math.round(style.spaceXl + style.spaceMd);
        for (int i = 1; i <= starCount; i++) {
            final int starIndex = i;
            TextView star = new TextView(getContext());
            star.setGravity(Gravity.CENTER);
            star.setText("★");
            star.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28f);
            star.setTypeface(Typeface.DEFAULT_BOLD);
            star.setClickable(true);
            star.setFocusable(true);
            star.setOnClickListener(v -> {
                selectedStars = starIndex;
                refreshSelection();
                if (listener != null) {
                    listener.onRatingSelected(this, selectedStars);
                }
            });
            LayoutParams params = new LayoutParams(size, size);
            params.setMargins(dp(4), 0, dp(4), 0);
            addView(star, params);
        }
        refreshSelection();
    }

    private void refreshSelection() {
        BasicColors colors = BasicThemeManager.colors();
        if (colors == null) {
            return;
        }
        for (int i = 0; i < getChildCount(); i++) {
            TextView star = (TextView) getChildAt(i);
            boolean filled = i < selectedStars;
            star.setTextColor(filled ? colors.brandPrimary : colors.borderControl);
        }
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }
}
