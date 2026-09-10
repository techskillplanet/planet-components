package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 简易轮播：ViewFlipper + 指示点 + 左右切换，可选自动播放。
 *
 * <p>对齐合约 Swiper（items / index / autoplay / onChange）。</p>
 */
public class BasicSwiperView extends FrameLayout {
    public interface OnIndexChangeListener {
        void onIndexChanged(BasicSwiperView view, int index);
    }

    private final ViewFlipper flipper;
    private final LinearLayout dotsRow;
    private final TextView prevView;
    private final TextView nextView;
    private final List<CharSequence> items = new ArrayList<>();
    private int index;
    private boolean autoplay;
    private OnIndexChangeListener indexChangeListener;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable autoplayRunnable = new Runnable() {
        @Override
        public void run() {
            if (!autoplay || items.size() < 2) {
                return;
            }
            goTo(index + 1, true);
            handler.postDelayed(this, 3200);
        }
    };

    public BasicSwiperView(Context context) {
        this(context, null);
    }

    public BasicSwiperView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicSwiperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        flipper = new ViewFlipper(context);
        flipper.setInAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));

        dotsRow = new LinearLayout(context);
        dotsRow.setOrientation(LinearLayout.HORIZONTAL);
        dotsRow.setGravity(Gravity.CENTER);

        prevView = createNav(context, "‹");
        nextView = createNav(context, "›");
        prevView.setOnClickListener(v -> goTo(index - 1, true));
        nextView.setOnClickListener(v -> goTo(index + 1, true));

        addView(flipper, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        LayoutParams dotsParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        dotsParams.gravity = Gravity.BOTTOM;
        addView(dotsRow, dotsParams);

        LayoutParams prevParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        prevParams.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
        addView(prevView, prevParams);

        LayoutParams nextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        nextParams.gravity = Gravity.CENTER_VERTICAL | Gravity.END;
        addView(nextView, nextParams);

        setMinimumHeight(dp(120));
        refreshTheme();
    }

    public void setItems(List<? extends CharSequence> values) {
        items.clear();
        if (values != null) {
            items.addAll(values);
        }
        rebuildSlides();
        goTo(Math.min(index, Math.max(0, items.size() - 1)), false);
    }

    public void setIndex(int index) {
        goTo(index, true);
    }

    public int getIndex() {
        return index;
    }

    public void setAutoplay(boolean autoplay) {
        this.autoplay = autoplay;
        restartAutoplay();
    }

    public void setOnIndexChangeListener(OnIndexChangeListener listener) {
        indexChangeListener = listener;
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        if (colors == null || style == null) {
            return;
        }
        setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurface,
                colors.borderLight,
                style.borderHairline,
                style.radiusMd
        ));
        int pad = Math.round(style.spaceMd);
        setPadding(pad, pad, pad, pad);
        styleNav(prevView, colors, style);
        styleNav(nextView, colors, style);
        int count = flipper.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = flipper.getChildAt(i);
            if (child instanceof TextView) {
                TextView tv = (TextView) child;
                tv.setTextColor(colors.textPrimary);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
                tv.setTypeface(Typeface.DEFAULT_BOLD);
            }
        }
        rebuildDots(colors, style);
        boolean multi = items.size() > 1;
        prevView.setVisibility(multi ? VISIBLE : GONE);
        nextView.setVisibility(multi ? VISIBLE : GONE);
        dotsRow.setVisibility(multi ? VISIBLE : GONE);
        setContentDescription("Swiper");
        setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
    }

    private void rebuildSlides() {
        flipper.removeAllViews();
        Context context = getContext();
        for (CharSequence item : items) {
            TextView slide = new TextView(context);
            slide.setText(item);
            slide.setGravity(Gravity.CENTER);
            flipper.addView(slide, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        refreshTheme();
    }

    private void rebuildDots(BasicColors colors, BasicStyle style) {
        dotsRow.removeAllViews();
        int size = Math.max(8, Math.round(style.spaceSm));
        for (int i = 0; i < items.size(); i++) {
            View dot = new View(getContext());
            boolean active = i == index;
            int fill = active ? colors.brandPrimary : colors.borderLight;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
            lp.setMargins(Math.round(style.spaceSm / 2f), 0, Math.round(style.spaceSm / 2f), 0);
            dot.setBackground(BasicDrawableFactory.roundedFill(fill, size / 2f));
            final int target = i;
            dot.setOnClickListener(v -> goTo(target, true));
            dotsRow.addView(dot, lp);
        }
        dotsRow.setPadding(0, Math.round(style.spaceSm), 0, 0);
    }

    private void goTo(int target, boolean notify) {
        if (items.isEmpty()) {
            index = 0;
            return;
        }
        int size = items.size();
        int next = ((target % size) + size) % size;
        boolean changed = next != index;
        index = next;
        if (flipper.getChildCount() > 0) {
            flipper.setDisplayedChild(index);
        }
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        if (colors != null && style != null) {
            rebuildDots(colors, style);
        }
        if (notify && changed && indexChangeListener != null) {
            indexChangeListener.onIndexChanged(this, index);
        }
        restartAutoplay();
    }

    private void restartAutoplay() {
        handler.removeCallbacks(autoplayRunnable);
        if (autoplay && items.size() > 1 && isAttachedToWindow()) {
            handler.postDelayed(autoplayRunnable, 3200);
        }
    }

    private TextView createNav(Context context, String label) {
        TextView view = new TextView(context);
        view.setText(label);
        view.setGravity(Gravity.CENTER);
        view.setClickable(true);
        return view;
    }

    private void styleNav(TextView view, BasicColors colors, BasicStyle style) {
        view.setTextColor(colors.textPrimary);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textTitle);
        view.setPadding(Math.round(style.spaceSm), Math.round(style.spaceSm),
                Math.round(style.spaceSm), Math.round(style.spaceSm));
        view.setBackground(BasicDrawableFactory.roundedFill(colors.backgroundSurfaceRaised, style.radiusPill));
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        restartAutoplay();
    }

    @Override
    protected void onDetachedFromWindow() {
        handler.removeCallbacks(autoplayRunnable);
        super.onDetachedFromWindow();
    }
}
