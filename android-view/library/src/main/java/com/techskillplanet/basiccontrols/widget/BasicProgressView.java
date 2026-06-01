package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 基础进度条组件。
 *
 * <p>用于上传、下载、文章生成进度等确定性进度场景。绘制逻辑直接使用 Canvas，
 * 避免引入 ProgressBar 默认样式带来的主题不可控问题。</p>
 */
public class BasicProgressView extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rect = new RectF();
    private float progress;
    private String variant = "primary";

    public BasicProgressView(Context context) {
        this(context, null);
    }

    public BasicProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        refreshTheme();
    }

    /** 设置进度，范围会被限制在 0 到 1。 */
    public void setProgress(float progress) {
        this.progress = Math.max(0f, Math.min(1f, progress));
        invalidate();
    }

    /** 设置颜色变体，例如 primary/success/warning/danger。 */
    public void setVariant(String variant) {
        this.variant = variant == null ? "primary" : variant;
        invalidate();
    }

    /** Progress 没有文字，这里保留统一接口。 */
    public void setBasicText(CharSequence text) {
        setContentDescription(text);
    }

    /** 选中态映射为完成态，方便声明式场景快速设置满进度。 */
    public void setSelectedState(boolean selected) {
        setProgress(selected ? 1f : 0f);
    }

    /** 设置禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        setEnabled(!disabled);
        invalidate();
    }

    /** 重新读取主题并触发绘制。 */
    public void refreshTheme() {
        invalidate();
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        BasicStyle style = BasicThemeManager.style();
        int desiredHeight = Math.round(style.spaceMd);
        setMeasuredDimension(
                resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                resolveSize(desiredHeight, heightMeasureSpec)
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        float radius = getHeight() / 2f;
        rect.set(0, 0, getWidth(), getHeight());
        paint.setColor(colors.loadingStripeSecondary);
        canvas.drawRoundRect(rect, radius, radius, paint);

        rect.set(0, 0, getWidth() * progress, getHeight());
        paint.setColor(resolveFill(colors));
        // 进度宽度为 0 时不绘制前景，避免圆角在左侧挤出一个小点。
        if (rect.width() > 0f) {
            canvas.drawRoundRect(rect, radius, radius, paint);
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(style.borderHairline);
        paint.setColor(colors.loadingBorder);
        rect.set(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rect, radius, radius, paint);
        paint.setStyle(Paint.Style.FILL);
    }

    /** 根据 variant 选择语义进度色。 */
    private int resolveFill(BasicColors colors) {
        if (!isEnabled()) {
            return colors.textDisabled;
        }
        if ("success".equals(variant)) {
            return colors.statusSuccess;
        }
        if ("warning".equals(variant)) {
            return colors.statusWarning;
        }
        if ("danger".equals(variant) || "error".equals(variant)) {
            return colors.statusDanger;
        }
        return colors.loadingStripePrimary;
    }
}
