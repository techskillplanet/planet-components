package com.techskillplanet.basiccontrols.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 技趣星球条纹加载组件。
 *
 * <p>用于 AI 生成中、网络加载中、页面局部刷新等不确定时长场景。组件通过
 * ValueAnimator 推动条纹位移，进入窗口时启动，离开窗口时停止，避免后台空转。</p>
 */
public class BasicLoadingView extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rect = new RectF();
    private ValueAnimator animator;
    private float offset;

    public BasicLoadingView(Context context) {
        this(context, null);
    }

    public BasicLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /** Loading 当前不使用 variant，保留统一组件协议。 */
    public void setVariant(String variant) {
        invalidate();
    }

    /** 设置无障碍描述。 */
    public void setBasicText(CharSequence text) {
        setContentDescription(text);
    }

    /** 选中态不改变 Loading 样式，保留统一接口。 */
    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    /** 设置禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        setEnabled(!disabled);
        invalidate();
    }

    /** 重新读取主题并刷新。 */
    public void refreshTheme() {
        invalidate();
        requestLayout();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopAnimation();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        BasicStyle style = BasicThemeManager.style();
        int desiredHeight = Math.round(style.controlHeightSm);
        setMeasuredDimension(
                resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                resolveSize(desiredHeight, heightMeasureSpec)
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BasicColors colors = BasicThemeManager.colors();
        float radius = getHeight() / 2f;
        rect.set(0, 0, getWidth(), getHeight());
        paint.setColor(colors.loadingStripeSecondary);
        canvas.drawRoundRect(rect, radius, radius, paint);

        paint.setColor(isEnabled() ? colors.loadingStripePrimary : colors.textDisabled);
        float stripeWidth = Math.max(1f, getHeight() * 1.4f);
        float gap = stripeWidth * 1.6f;
        // 从负偏移开始绘制，保证动画循环时左侧不会突然留白。
        for (float x = -gap + offset; x < getWidth() + gap; x += gap) {
            rect.set(x, 0, x + stripeWidth, getHeight());
            canvas.drawRoundRect(rect, radius, radius, paint);
        }
    }

    /** 启动无限循环动画。 */
    private void startAnimation() {
        if (animator != null) {
            return;
        }
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(900L);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            float fraction = (float) animation.getAnimatedValue();
            offset = fraction * Math.max(1f, getHeight() * 2.2f);
            invalidate();
        });
        animator.start();
    }

    /** 停止动画并释放引用。 */
    private void stopAnimation() {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }
}
