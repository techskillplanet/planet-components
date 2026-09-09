package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.R;
import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

import java.util.Locale;

/**
 * 连续打卡摘要卡片。
 */
public class BasicCheckInStreakCardView extends LinearLayout {
    /** 打开详情回调。 */
    public interface OnOpenListener {
        void onOpen();
    }

    private int streakDays;
    private int totalDays;
    private float weekProgress;
    private boolean basicDisabled;
    private OnOpenListener listener;

    public BasicCheckInStreakCardView(Context context) {
        this(context, null);
    }

    public BasicCheckInStreakCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicCheckInStreakCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        setClickable(true);
        setFocusable(true);
        readAttrs(attrs);
        setOnClickListener(v -> {
            if (!basicDisabled && isEnabled() && listener != null) {
                listener.onOpen();
            }
        });
        refreshTheme();
    }

    public void setStreakDays(int streakDays) {
        this.streakDays = streakDays;
        refreshTheme();
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
        refreshTheme();
    }

    /** weekProgress 为 0~1 的比例。 */
    public void setWeekProgress(float weekProgress) {
        this.weekProgress = weekProgress;
        refreshTheme();
    }

    public void setOnOpenListener(OnOpenListener listener) {
        this.listener = listener;
    }

    public void setVariant(String variant) {
        refreshTheme();
    }

    public void setBasicText(CharSequence text) {
        setContentDescription(text);
    }

    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        setClickable(!disabled);
        refreshTheme();
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int pad = Math.round(style.spaceMd + 2);
        setPadding(pad, pad, pad, pad);
        setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderDefault,
                style.borderHairline,
                style.radiusCardOrganic
        ));
        setAlpha(basicDisabled || !isEnabled() ? 0.45f : 1f);
        removeAllViews();

        TextView title = new TextView(getContext());
        title.setText("连续打卡");
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(colors.textPrimary);
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        addView(title);

        int pct = Math.max(0, Math.min(100, Math.round(weekProgress * 100f)));
        LinearLayout stats = new LinearLayout(getContext());
        stats.setOrientation(HORIZONTAL);
        LayoutParams statsLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        statsLp.topMargin = Math.round(style.spaceSm);

        stats.addView(statText(String.format(Locale.CHINA, "连续 %d 天", streakDays), colors, style));
        TextView mid = statText(String.format(Locale.CHINA, "累计 %d 天", totalDays), colors, style);
        LinearLayout.LayoutParams midLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        midLp.leftMargin = dp(16);
        stats.addView(mid, midLp);
        TextView week = statText(String.format(Locale.CHINA, "本周 %d%%", pct), colors, style);
        LinearLayout.LayoutParams weekLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        weekLp.leftMargin = dp(16);
        stats.addView(week, weekLp);
        addView(stats, statsLp);
    }

    private TextView statText(String text, BasicColors colors, BasicStyle style) {
        TextView view = new TextView(getContext());
        view.setText(text);
        view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        view.setTextColor(colors.textSecondary);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        return view;
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }

    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setEnabled(!basicDisabled);
            setClickable(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
