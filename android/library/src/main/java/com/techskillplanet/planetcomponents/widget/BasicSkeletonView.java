package com.techskillplanet.planetcomponents.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 骨架屏占位：可选圆形头像 + 若干灰色行，支持 pulse 透明度动画。
 *
 * <p>对齐合约 Skeleton（rows / animated / avatar / variant）。</p>
 */
public class BasicSkeletonView extends LinearLayout {
    private int rows = 3;
    private boolean animated = true;
    private boolean showAvatar;
    private String variant = "default";
    private ValueAnimator pulseAnimator;
    private View avatarView;
    private final LinearLayout bodyLayout;

    public BasicSkeletonView(Context context) {
        this(context, null);
    }

    public BasicSkeletonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicSkeletonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.TOP);
        setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);
        bodyLayout = new LinearLayout(context);
        bodyLayout.setOrientation(VERTICAL);
        addView(bodyLayout, new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
        rebuild();
    }

    public void setRows(int rows) {
        this.rows = Math.max(1, Math.min(12, rows));
        rebuild();
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
        updatePulse();
    }

    public void setShowAvatar(boolean showAvatar) {
        this.showAvatar = showAvatar;
        rebuild();
    }

    public void setVariant(String variant) {
        this.variant = variant == null ? "default" : variant;
        if ("pulse".equals(this.variant)) {
            this.animated = true;
        }
        refreshTheme();
        updatePulse();
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        if (colors == null || style == null) {
            return;
        }
        int fill = colors.backgroundSurfaceSubtle;
        if (avatarView != null) {
            int size = Math.round(style.controlHeightLg);
            LayoutParams avatarParams = (LayoutParams) avatarView.getLayoutParams();
            if (avatarParams != null) {
                avatarParams.width = size;
                avatarParams.height = size;
                avatarParams.setMargins(0, 0, Math.round(style.spaceMd), 0);
                avatarView.setLayoutParams(avatarParams);
            }
            avatarView.setBackground(BasicDrawableFactory.roundedFill(fill, size / 2f));
        }
        int count = bodyLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View line = bodyLayout.getChildAt(i);
            float radius = style.radiusSm;
            line.setBackground(BasicDrawableFactory.roundedFill(fill, radius));
            LayoutParams lp = (LayoutParams) line.getLayoutParams();
            if (lp != null) {
                lp.height = Math.round(style.textMd);
                lp.width = i == count - 1 ? LayoutParams.MATCH_PARENT : LayoutParams.MATCH_PARENT;
                lp.weight = 0;
                lp.setMargins(0, 0, 0, Math.round(style.spaceSm));
                if (i == count - 1) {
                    line.setLayoutParams(lp);
                    // 最后一行缩短：用 paddingRight 模拟短线宽度感
                    line.setPadding(0, 0, Math.round(style.spaceXl * 2), 0);
                } else {
                    line.setPadding(0, 0, 0, 0);
                    line.setLayoutParams(lp);
                }
            }
        }
    }

    private void rebuild() {
        removeAllViews();
        avatarView = null;
        bodyLayout.removeAllViews();
        Context context = getContext();
        BasicStyle style = BasicThemeManager.style();
        float space = style == null ? 12f : style.spaceMd;
        if (showAvatar) {
            avatarView = new View(context);
            int size = style == null ? 48 : Math.round(style.controlHeightLg);
            LayoutParams avatarParams = new LayoutParams(size, size);
            avatarParams.setMargins(0, 0, Math.round(space), 0);
            addView(avatarView, avatarParams);
        }
        addView(bodyLayout, new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
        for (int i = 0; i < rows; i++) {
            View line = new View(context);
            bodyLayout.addView(line, new LayoutParams(LayoutParams.MATCH_PARENT, 14));
        }
        refreshTheme();
        updatePulse();
    }

    private void updatePulse() {
        stopPulse();
        boolean shouldAnimate = animated || "pulse".equals(variant);
        if (!shouldAnimate) {
            setAlpha(1f);
            return;
        }
        pulseAnimator = ValueAnimator.ofFloat(0.45f, 1f);
        pulseAnimator.setDuration(900);
        pulseAnimator.setRepeatMode(ValueAnimator.REVERSE);
        pulseAnimator.setRepeatCount(ValueAnimator.INFINITE);
        pulseAnimator.setInterpolator(new LinearInterpolator());
        pulseAnimator.addUpdateListener(animation -> setAlpha((Float) animation.getAnimatedValue()));
        pulseAnimator.start();
    }

    private void stopPulse() {
        if (pulseAnimator != null) {
            pulseAnimator.cancel();
            pulseAnimator = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        stopPulse();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        updatePulse();
    }
}
