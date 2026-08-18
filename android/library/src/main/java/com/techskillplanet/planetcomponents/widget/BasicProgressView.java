package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 基础进度条组件。
 *
 * <p>对齐 RN TspProgress：高度 10dp，轨道 borderDefault，主填充 brandPrimary。</p>
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
        int desiredHeight = dp(10);
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
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colors.borderDefault);
        canvas.drawRoundRect(rect, radius, radius, paint);

        rect.set(0, 0, getWidth() * progress, getHeight());
        paint.setColor(resolveFill(colors));
        if (rect.width() > 0f) {
            canvas.drawRoundRect(rect, radius, radius, paint);
        }
    }

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
        return colors.brandPrimary;
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }
}
