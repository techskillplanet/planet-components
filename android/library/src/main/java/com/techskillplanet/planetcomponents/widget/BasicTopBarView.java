package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.system.BasicEdgeToEdgeHelper;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 蓝天星球风格顶部导航栏。
 *
 * <p>固定承载页面标题和返回入口，适合单 Activity 的轻量页面流。默认启用沉浸式顶栏：
 * 背景延伸到状态栏区域，内容自动下移 {@code statusBarInset}。</p>
 */
public class BasicTopBarView extends LinearLayout {
    private final ImageView backView;
    private final TextView titleView;
    private final ImageView secondaryActionView;
    private final ImageView actionView;
    private final View bottomBorder;
    private Integer barBackgroundColor;
    private Integer titleTextColor;
    private Integer backTextColor;
    private boolean immersiveStatusBar = true;
    private int statusBarInset;

    public BasicTopBarView(Context context) {
        this(context, null);
    }

    public BasicTopBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicTopBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        backView = new ImageView(context);
        backView.setImageResource(com.techskillplanet.planetcomponents.R.drawable.ic_basic_back);
        backView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        backView.setContentDescription("Back");
        backView.setClickable(true);
        backView.setFocusable(true);
        addView(backView);

        titleView = new TextView(context);
        titleView.setGravity(Gravity.CENTER);
        titleView.setTypeface(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD);
        titleView.setIncludeFontPadding(false);
        addView(titleView);

        secondaryActionView = new ImageView(context);
        secondaryActionView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        secondaryActionView.setVisibility(INVISIBLE);
        secondaryActionView.setClickable(true);
        secondaryActionView.setFocusable(true);
        addView(secondaryActionView);

        actionView = new ImageView(context);
        actionView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        actionView.setVisibility(INVISIBLE);
        actionView.setClickable(true);
        actionView.setFocusable(true);
        addView(actionView);
        bottomBorder = new View(context);
        addView(bottomBorder);
        refreshTheme();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!immersiveStatusBar) {
            return;
        }
        requestApplyInsets();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            setOnApplyWindowInsetsListener(this::onApplyWindowInsetsToTopBar);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            setOnApplyWindowInsetsListener(null);
        }
        super.onDetachedFromWindow();
    }

    /**
     * 是否把 TopBar 背景延伸到状态栏并自动留出安全间距。
     *
     * @param enabled true 为沉浸式 edge-to-edge；false 为固定 56dp 内容高度。
     */
    public void setImmersiveStatusBar(boolean enabled) {
        immersiveStatusBar = enabled;
        if (!enabled) {
            statusBarInset = 0;
        } else if (isAttachedToWindow()) {
            requestApplyInsets();
        }
        requestLayout();
    }

    public void setTitle(CharSequence title) {
        titleView.setText(title);
    }

    public void setBackVisible(boolean visible) {
        backView.setVisibility(visible ? VISIBLE : INVISIBLE);
    }

    /** 设置导航栏背景色。未设置时使用当前主题 token。 */
    public void setBarBackgroundColor(int color) {
        barBackgroundColor = color;
        refreshTheme();
    }

    /** 设置标题和返回按钮颜色，适合深色导航栏。 */
    public void setBarTextColor(int titleColor, int backColor) {
        titleTextColor = titleColor;
        backTextColor = backColor;
        refreshTheme();
    }

    /** 恢复使用主题 token 作为导航栏文字颜色。 */
    public void clearBarTextColor() {
        titleTextColor = null;
        backTextColor = null;
        refreshTheme();
    }

    /** 恢复使用当前主题 token 作为导航栏背景。 */
    public void clearBarBackgroundColor() {
        barBackgroundColor = null;
        refreshTheme();
    }

    public void setOnBackClickListener(OnClickListener listener) {
        backView.setOnClickListener(listener);
    }

    public void setActionVisible(boolean visible) {
        actionView.setVisibility(visible ? VISIBLE : INVISIBLE);
    }

    public void setSecondaryActionVisible(boolean visible) {
        secondaryActionView.setVisibility(visible ? VISIBLE : INVISIBLE);
    }

    public void setActionIconResource(int resId) {
        actionView.setImageResource(resId);
        refreshTheme();
    }

    public void setActionContentDescription(CharSequence description) {
        actionView.setContentDescription(description);
    }

    public void setSecondaryActionIconResource(int resId) {
        secondaryActionView.setImageResource(resId);
        refreshTheme();
    }

    public void setSecondaryActionContentDescription(CharSequence description) {
        secondaryActionView.setContentDescription(description);
    }

    public void setOnActionClickListener(OnClickListener listener) {
        actionView.setOnClickListener(listener);
    }

    public void setOnSecondaryActionClickListener(OnClickListener listener) {
        secondaryActionView.setOnClickListener(listener);
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        setBackgroundColor(barBackgroundColor == null ? colors.backgroundSurfaceRaised : barBackgroundColor);
        setPadding(0, 0, 0, 0);

        backView.setColorFilter(backTextColor == null ? colors.brandPrimary : backTextColor);
        backView.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        int backPadding = dp(16);
        backView.setPadding(backPadding, backPadding, backPadding, backPadding);

        titleView.setTextColor(titleTextColor == null ? colors.textPrimary : titleTextColor);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        titleView.setSingleLine(true);

        if (actionView.getDrawable() != null) {
            actionView.setColorFilter(colors.brandPrimary);
        }
        if (secondaryActionView.getDrawable() != null) {
            secondaryActionView.setColorFilter(colors.brandPrimary);
        }
        // A 56dp touch target with 16dp insets keeps the visual glyph at the Material 24dp size.
        int actionPadding = dp(16);
        actionView.setPadding(actionPadding, actionPadding, actionPadding, actionPadding);
        secondaryActionView.setPadding(actionPadding, actionPadding, actionPadding, actionPadding);

        bottomBorder.setBackgroundColor(colors.borderDivider);
    }

    private android.view.WindowInsets onApplyWindowInsetsToTopBar(View view, android.view.WindowInsets insets) {
        int inset = immersiveStatusBar ? BasicEdgeToEdgeHelper.getStatusBarInset(view, insets) : 0;
        if (inset != statusBarInset) {
            statusBarInset = inset;
            requestLayout();
        }
        return insets;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        BasicStyle style = BasicThemeManager.style();
        int side = dp(56);
        int border = Math.max(1, Math.round(style.borderHairline));
        int barContentHeight = dp(56);
        int totalHeight = statusBarInset + barContentHeight + border;
        int height = resolveSize(totalHeight, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int exactSide = MeasureSpec.makeMeasureSpec(side, MeasureSpec.EXACTLY);
        int exactContentHeight = MeasureSpec.makeMeasureSpec(barContentHeight, MeasureSpec.EXACTLY);
        backView.measure(exactSide, exactContentHeight);
        secondaryActionView.measure(exactSide, exactContentHeight);
        actionView.measure(exactSide, exactContentHeight);
        int rightSlots = 0;
        if (secondaryActionView.getVisibility() == VISIBLE) {
            rightSlots++;
        }
        if (actionView.getVisibility() == VISIBLE) {
            rightSlots++;
        }
        int reservedSideWidth = side * Math.max(1, rightSlots);
        int titleWidth = Math.max(0, width - reservedSideWidth * 2);
        titleView.measure(
                MeasureSpec.makeMeasureSpec(titleWidth, MeasureSpec.EXACTLY),
                exactContentHeight
        );
        bottomBorder.measure(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(border, MeasureSpec.EXACTLY)
        );
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = right - left;
        int height = bottom - top;
        int border = bottomBorder.getMeasuredHeight();
        int barContentHeight = backView.getMeasuredHeight();
        int contentTop = statusBarInset;
        int childTop = contentTop + Math.max(0, (barContentHeight - backView.getMeasuredHeight()) / 2);
        int side = backView.getMeasuredWidth();
        backView.layout(0, childTop, side, childTop + backView.getMeasuredHeight());
        int rightEdge = width;
        if (actionView.getVisibility() == VISIBLE) {
            actionView.layout(rightEdge - side, childTop, rightEdge, childTop + actionView.getMeasuredHeight());
            rightEdge -= side;
        }
        if (secondaryActionView.getVisibility() == VISIBLE) {
            secondaryActionView.layout(rightEdge - side, childTop, rightEdge,
                    childTop + secondaryActionView.getMeasuredHeight());
            rightEdge -= side;
        }
        int titleLeft = (width - titleView.getMeasuredWidth()) / 2;
        titleView.layout(
                titleLeft,
                childTop,
                titleLeft + titleView.getMeasuredWidth(),
                childTop + titleView.getMeasuredHeight()
        );
        bottomBorder.layout(0, height - border, width, height);
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }
}
