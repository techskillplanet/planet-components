package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
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

import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

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

    private final LinearLayout headerView;
    private final LinearLayout footerView;
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

    public BasicRefreshLayout(Context context) {
        this(context, null);
    }

    public BasicRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        headerTextView = new TextView(context);
        footerTextView = new TextView(context);
        headerView = createIndicator(context, headerTextView, "下拉刷新");
        footerView = createIndicator(context, footerTextView, "上拉加载更多");
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
        state = STATE_IDLE;
        pullDistance = 0f;
        headerTextView.setText("下拉刷新");
        applyPullVisual();
    }

    /** 完成加载更多，收起上拉尾。 */
    public void finishLoadMore() {
        state = STATE_IDLE;
        pullDistance = 0f;
        footerTextView.setText("上拉加载更多");
        applyPullVisual();
    }

    /** 重新读取主题并刷新头尾样式。 */
    public void refreshTheme() {
        BasicStyle style = BasicThemeManager.style();
        indicatorHeight = Math.round(style.spaceXl * 3.2f);
        triggerDistance = Math.round(style.spaceXl * 2.2f);
        styleIndicator(headerView);
        styleIndicator(footerView);
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

    /** 创建带星球动画和文案的头尾提示。 */
    private LinearLayout createIndicator(Context context, TextView label, String text) {
        LinearLayout layout = new LinearLayout(context);
        layout.setGravity(Gravity.CENTER);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        BasicPlanetLoadingView loading = new BasicPlanetLoadingView(context);
        BasicStyle style = BasicThemeManager.style();
        int size = Math.round(style.spaceXl * 1.6f);
        layout.addView(loading, new LinearLayout.LayoutParams(size, size));
        label.setText(text);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textParams.setMargins(Math.round(style.spaceSm), 0, 0, 0);
        layout.addView(label, textParams);
        return layout;
    }

    /** 设置头尾背景和文字颜色。 */
    private void styleIndicator(LinearLayout indicator) {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        indicator.setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderLight,
                style.borderHairline,
                style.radiusCardOrganic
        ));
        TextView label = indicator == headerView ? headerTextView : footerTextView;
        label.setTextColor(colors.textSecondary);
        label.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
    }

    /** 松手后根据拖拽距离进入刷新/加载或回到空闲。 */
    private void releasePull() {
        if (state == STATE_PULL_DOWN && pullDistance >= triggerDistance) {
            state = STATE_REFRESHING;
            pullDistance = triggerDistance;
            headerTextView.setText("技趣星球刷新中...");
            applyPullVisual();
            if (listener != null) {
                listener.onRefresh();
            }
            return;
        }
        if (state == STATE_PULL_UP && pullDistance >= triggerDistance) {
            state = STATE_LOADING_MORE;
            pullDistance = triggerDistance;
            footerTextView.setText("技趣星球加载中...");
            applyPullVisual();
            if (listener != null) {
                listener.onLoadMore();
            }
            return;
        }
        state = STATE_IDLE;
        pullDistance = 0f;
        applyPullVisual();
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
        headerView.setAlpha(Math.min(1f, downOffset / Math.max(1f, triggerDistance)));
        footerView.setAlpha(Math.min(1f, upOffset / Math.max(1f, triggerDistance)));
    }
}
