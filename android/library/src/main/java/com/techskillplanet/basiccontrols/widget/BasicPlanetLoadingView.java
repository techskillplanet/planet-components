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
 * 技趣星球主题加载动画。
 *
 * <p>组件绘制一个蓝色小星球、环绕轨道和两颗跳动星点。下拉刷新、上拉加载、
 * 半透明 Loading 弹窗都复用这个动画，保证同一套主题在不同场景中的视觉一致。</p>
 */
public class BasicPlanetLoadingView extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF arcRect = new RectF();
    private ValueAnimator animator;
    private float progress;

    public BasicPlanetLoadingView(Context context) {
        this(context, null);
    }

    public BasicPlanetLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicPlanetLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setContentDescription("加载中");
    }

    /** 重新读取主题并刷新绘制。 */
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
        int desired = Math.round(style.spaceXl * 2.5f);
        int size = resolveSize(desired, widthMeasureSpec);
        setMeasuredDimension(size, resolveSize(size, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BasicColors colors = BasicThemeManager.colors();
        float width = getWidth();
        float height = getHeight();
        float centerX = width / 2f;
        float centerY = height / 2f;
        float radius = Math.min(width, height) * 0.22f;

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colors.brandPrimarySubtle);
        canvas.drawCircle(centerX, centerY, radius * 1.28f, paint);

        paint.setColor(colors.brandPrimary);
        canvas.drawCircle(centerX, centerY, radius, paint);

        paint.setColor(colors.brandPrimaryHover);
        canvas.drawCircle(centerX - radius * 0.32f, centerY - radius * 0.28f, radius * 0.28f, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Math.max(2f, radius * 0.14f));
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(colors.statusSuccess);
        float orbitRadius = radius * 1.55f;
        arcRect.set(centerX - orbitRadius, centerY - orbitRadius * 0.58f,
                centerX + orbitRadius, centerY + orbitRadius * 0.58f);
        canvas.save();
        canvas.rotate(progress * 360f, centerX, centerY);
        // 倾斜轨道让加载动画更接近“蓝色星球”主题，而不是普通 spinner。
        canvas.rotate(-18f, centerX, centerY);
        canvas.drawArc(arcRect, 205f, 250f, false, paint);
        canvas.restore();

        paint.setStyle(Paint.Style.FILL);
        drawStar(canvas, colors.statusWarning, centerX + radius * 1.8f, centerY - radius * 1.15f,
                radius * (0.18f + 0.08f * pulse(0f)));
        drawStar(canvas, colors.brandPrimaryHover, centerX - radius * 1.9f, centerY + radius * 1.1f,
                radius * (0.14f + 0.07f * pulse(0.45f)));
    }

    /** 绘制一颗简化四角星。 */
    private void drawStar(Canvas canvas, int color, float centerX, float centerY, float radius) {
        paint.setColor(color);
        canvas.drawCircle(centerX, centerY, radius, paint);
        canvas.drawRect(centerX - radius * 0.35f, centerY - radius * 1.35f,
                centerX + radius * 0.35f, centerY + radius * 1.35f, paint);
        canvas.drawRect(centerX - radius * 1.35f, centerY - radius * 0.35f,
                centerX + radius * 1.35f, centerY + radius * 0.35f, paint);
    }

    /** 计算星点呼吸变化。 */
    private float pulse(float offset) {
        double value = Math.sin((progress + offset) * Math.PI * 2d);
        return (float) ((value + 1d) / 2d);
    }

    /** 启动循环动画。 */
    private void startAnimation() {
        if (animator != null) {
            return;
        }
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1100L);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            progress = (float) animation.getAnimatedValue();
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
