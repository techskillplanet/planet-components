package com.techskillplanet.planetcomponents.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 支持下拉刷新和上拉加载的基础容器。
 *
 * <p>组件面向传统 Android View 页面，不依赖 SwipeRefreshLayout 或 RecyclerView。
 * 它通过 canScrollVertically 判断内容是否到顶/到底，并在边界拖拽时展示技趣星球
 * 主题动画。业务通过 {@link #setOnRefreshLoadListener(OnRefreshLoadListener)}
 * 接收刷新和加载更多回调。</p>
 */
public class BasicRefreshLayout extends FrameLayout {
    /** 刷新和加载更多回调。 */
    public interface OnRefreshLoadListener {
        /** 下拉超过阈值并松手时触发。 */
        void onRefresh();

        /** 上拉超过阈值并松手时触发。 */
        void onLoadMore();
    }

    private static final int STATE_IDLE = 0;
    private static final int STATE_PULL_DOWN = 1;
    private static final int STATE_PULL_UP = 2;
    private static final int STATE_REFRESHING = 3;
    private static final int STATE_LOADING_MORE = 4;

    private final FrameLayout headerView;
    private final FrameLayout footerView;
    private final View headerFadeView;
    private final View footerFadeView;
    private final BasicPlanetLoadingView headerLoadingView;
    private final BasicPlanetLoadingView footerLoadingView;
    private final TextView headerTextView;
    private final TextView footerTextView;
    private final int touchSlop;
    private View contentView;
    private OnRefreshLoadListener listener;
    private float downY;
    private float pullDistance;
    private int state = STATE_IDLE;
    private int indicatorHeight;
    private int triggerDistance;
    private boolean internalAdd;
    private ValueAnimator offsetAnimator;

    public BasicRefreshLayout(Context context) {
        this(context, null);
    }

    public BasicRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClipChildren(false);
        setClipToPadding(false);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        headerTextView = new TextView(context);
        footerTextView = new TextView(context);
        headerFadeView = new View(context);
        footerFadeView = new View(context);
        headerLoadingView = new BasicPlanetLoadingView(context);
        footerLoadingView = new BasicPlanetLoadingView(context);
        headerView = createIndicatorSlot(context, headerLoadingView, headerTextView, headerFadeView, "下拉刷新");
        footerView = createIndicatorSlot(context, footerLoadingView, footerTextView, footerFadeView, "上拉加载更多");
        internalAdd = true;
        super.addView(headerView);
        super.addView(footerView);
        internalAdd = false;
        refreshTheme();
    }

    /** 设置内容 View。 */
    public void setContentView(View content) {
        if (contentView != null) {
            removeView(contentView);
        }
        contentView = content;
        internalAdd = true;
        super.addView(contentView, 0, new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        internalAdd = false;
        bringChildToFront(headerView);
        bringChildToFront(footerView);
    }

    /** 设置刷新和加载更多监听。 */
    public void setOnRefreshLoadListener(OnRefreshLoadListener listener) {
        this.listener = listener;
    }

    /** 完成刷新，收起下拉头。 */
    public void finishRefresh() {
        if (state != STATE_REFRESHING) {
            return;
        }
        animateOffsetTo(0f, () -> {
            state = STATE_IDLE;
            pullDistance = 0f;
            headerTextView.setText("下拉刷新");
            headerTextView.setTextColor(BasicThemeManager.colors().textSecondary);
            applyPullVisual();
        });
    }

    /** 完成加载更多，收起上拉尾。 */
    public void finishLoadMore() {
        if (state != STATE_LOADING_MORE) {
            return;
        }
        animateOffsetTo(0f, () -> {
            state = STATE_IDLE;
            pullDistance = 0f;
            footerTextView.setText("上拉加载更多");
            footerTextView.setTextColor(BasicThemeManager.colors().textSecondary);
            applyPullVisual();
        });
    }

    /** 重新读取主题并刷新头尾样式。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        setBackgroundColor(colors.backgroundPage);
        indicatorHeight = Math.round(style.spaceXl * 3.6f);
        triggerDistance = Math.round(style.spaceXl * 2.2f);
        styleIndicator(headerView, headerFadeView, headerTextView, headerLoadingView, true);
        styleIndicator(footerView, footerFadeView, footerTextView, footerLoadingView, false);
        applyPullVisual();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (!internalAdd && child != headerView && child != footerView && contentView == null) {
            setContentView(child);
            return;
        }
        super.addView(child, index, params);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (contentView == null || state == STATE_REFRESHING || state == STATE_LOADING_MORE) {
            return false;
        }
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            downY = event.getY();
            return false;
        }
        if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            float dy = event.getY() - downY;
            return Math.abs(dy) > touchSlop
                    && ((dy > 0 && !contentView.canScrollVertically(-1))
                    || (dy < 0 && !contentView.canScrollVertically(1)));
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (contentView == null) {
            return false;
        }
        if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            float dy = event.getY() - downY;
            if (dy > 0 && !contentView.canScrollVertically(-1)) {
                state = STATE_PULL_DOWN;
                pullDistance = Math.min(indicatorHeight * 1.25f, dy * 0.5f);
                headerTextView.setText(pullDistance >= triggerDistance ? "松手刷新" : "下拉刷新");
                applyPullVisual();
                return true;
            }
            if (dy < 0 && !contentView.canScrollVertically(1)) {
                state = STATE_PULL_UP;
                pullDistance = Math.min(indicatorHeight * 1.25f, -dy * 0.5f);
                footerTextView.setText(pullDistance >= triggerDistance ? "松手加载" : "上拉加载更多");
                applyPullVisual();
                return true;
            }
        }
        if (event.getActionMasked() == MotionEvent.ACTION_UP
                || event.getActionMasked() == MotionEvent.ACTION_CANCEL) {
            releasePull();
            return true;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (contentView != null) {
            measureChild(contentView, widthMeasureSpec, heightMeasureSpec);
        }
        int indicatorWidth = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int indicatorHeightSpec = MeasureSpec.makeMeasureSpec(indicatorHeight, MeasureSpec.EXACTLY);
        headerView.measure(indicatorWidth, indicatorHeightSpec);
        footerView.measure(indicatorWidth, indicatorHeightSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = right - left;
        int height = bottom - top;
        if (contentView != null) {
            contentView.layout(0, 0, width, height);
        }
        headerView.layout(0, 0, width, indicatorHeight);
        footerView.layout(0, height - indicatorHeight, width, height);
        applyPullVisual();
    }

    /** 创建带天空渐变和星球动画的头尾提示区。 */
    private FrameLayout createIndicatorSlot(
            Context context,
            BasicPlanetLoadingView loadingView,
            TextView label,
            View fadeView,
            String text
    ) {
        FrameLayout slot = new FrameLayout(context);
        slot.addView(fadeView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        LinearLayout content = new LinearLayout(context);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER_HORIZONTAL);
        BasicStyle style = BasicThemeManager.style();
        int planetSize = Math.round(style.spaceXl * 1.55f);
        content.addView(loadingView, new LinearLayout.LayoutParams(planetSize, planetSize));
        label.setText(text);
        label.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textParams.topMargin = Math.round(style.spaceSm * 0.5f);
        content.addView(label, textParams);

        FrameLayout.LayoutParams contentParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
        );
        slot.addView(content, contentParams);
        return slot;
    }

    /** 头尾使用天空渐变，不使用卡片边框。 */
    private void styleIndicator(
            FrameLayout slot,
            View fadeView,
            TextView label,
            BasicPlanetLoadingView loadingView,
            boolean top
    ) {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        slot.setBackground(null);
        fadeView.setBackground(createPullFadeBackground(colors, top));
        label.setTextColor(colors.textSecondary);
        label.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        loadingView.refreshTheme();
    }

    /** 顶部/底部刷新区天空渐变，与页面背景自然衔接。 */
    private static GradientDrawable createPullFadeBackground(BasicColors colors, boolean top) {
        GradientDrawable.Orientation orientation = top
                ? GradientDrawable.Orientation.TOP_BOTTOM
                : GradientDrawable.Orientation.BOTTOM_TOP;
        return new GradientDrawable(
                orientation,
                new int[]{
                        colors.backgroundPage,
                        applyAlpha(colors.backgroundPageGradientEnd, 0.55f),
                        applyAlpha(colors.brandPrimarySubtle, 0.18f),
                        Color.TRANSPARENT
                }
        );
    }

    private static int applyAlpha(int color, float alpha) {
        int boundedAlpha = Math.max(0, Math.min(255, Math.round(alpha * 255f)));
        return Color.argb(
                boundedAlpha,
                Color.red(color),
                Color.green(color),
                Color.blue(color)
        );
    }

    /** 松手后根据拖拽距离进入刷新/加载或回到空闲。 */
    private void releasePull() {
        if (state == STATE_PULL_DOWN && pullDistance >= triggerDistance) {
            state = STATE_REFRESHING;
            pullDistance = indicatorHeight;
            headerTextView.setText("技趣星球刷新中...");
            headerTextView.setTextColor(BasicThemeManager.colors().textPrimary);
            applyPullVisual();
            if (listener != null) {
                listener.onRefresh();
            }
            return;
        }
        if (state == STATE_PULL_UP && pullDistance >= triggerDistance) {
            state = STATE_LOADING_MORE;
            pullDistance = indicatorHeight;
            footerTextView.setText("技趣星球加载中...");
            footerTextView.setTextColor(BasicThemeManager.colors().textPrimary);
            applyPullVisual();
            if (listener != null) {
                listener.onLoadMore();
            }
            return;
        }
        animateOffsetTo(0f, () -> {
            state = STATE_IDLE;
            pullDistance = 0f;
            applyPullVisual();
        });
    }

    /** 刷新/加载结束后或未达到阈值时，平滑收起偏移。 */
    private void animateOffsetTo(float target, Runnable endAction) {
        if (offsetAnimator != null) {
            offsetAnimator.cancel();
        }
        final float start = pullDistance;
        if (Math.abs(start - target) < 1f) {
            pullDistance = target;
            applyPullVisual();
            if (endAction != null) {
                endAction.run();
            }
            return;
        }
        offsetAnimator = ValueAnimator.ofFloat(start, target);
        offsetAnimator.setDuration(220L);
        offsetAnimator.addUpdateListener(animation -> {
            pullDistance = (float) animation.getAnimatedValue();
            applyPullVisual();
        });
        offsetAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                offsetAnimator = null;
                if (endAction != null) {
                    endAction.run();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                offsetAnimator = null;
            }
        });
        offsetAnimator.start();
    }

    /** 根据当前拖拽状态调整内容、头部和尾部位置。 */
    private void applyPullVisual() {
        if (headerView == null || footerView == null) {
            return;
        }
        float downOffset = (state == STATE_PULL_DOWN || state == STATE_REFRESHING) ? pullDistance : 0f;
        float upOffset = (state == STATE_PULL_UP || state == STATE_LOADING_MORE) ? pullDistance : 0f;
        if (contentView != null) {
            contentView.setTranslationY(downOffset - upOffset);
        }
        headerView.setTranslationY(-indicatorHeight + downOffset);
        footerView.setTranslationY(indicatorHeight - upOffset);

        float headerProgress = Math.min(1f, downOffset / Math.max(1f, indicatorHeight));
        float footerProgress = Math.min(1f, upOffset / Math.max(1f, indicatorHeight));
        float headerScale = 0.82f + headerProgress * 0.18f;
        float footerScale = 0.82f + footerProgress * 0.18f;
        headerLoadingView.setScaleX(headerScale);
        headerLoadingView.setScaleY(headerScale);
        footerLoadingView.setScaleX(footerScale);
        footerLoadingView.setScaleY(footerScale);

        if (state == STATE_REFRESHING) {
            headerView.setAlpha(1f);
        } else {
            headerView.setAlpha(Math.min(1f, downOffset / Math.max(1f, triggerDistance)));
        }
        if (state == STATE_LOADING_MORE) {
            footerView.setAlpha(1f);
        } else {
            footerView.setAlpha(Math.min(1f, upOffset / Math.max(1f, triggerDistance)));
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (offsetAnimator != null) {
            offsetAnimator.cancel();
            offsetAnimator = null;
        }
        super.onDetachedFromWindow();
    }
}
