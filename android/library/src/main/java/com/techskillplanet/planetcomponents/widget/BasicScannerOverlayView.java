package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 扫码取景框覆盖层：半透明遮罩 + 中央透明窗口 + 四角高亮括号 + 扫描线动画。
 *
 * <p>叠加在 CameraX {@code PreviewView} 上层使用，不处理相机逻辑。</p>
 */
public class BasicScannerOverlayView extends View {
    public interface OnViewfinderTapListener {
        void onViewfinderTap(BasicScannerOverlayView view, float normalizedX, float normalizedY);
    }

    private final Paint dimPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint bracketPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint scanLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF viewfinderRect = new RectF();

    private int topChromePx;
    private int bottomChromePx;
    private float viewfinderSizeRatio = 0.72f;
    private float cornerRadiusDp = 12f;
    private float bracketLengthDp = 28f;
    private float bracketStrokeDp = 4f;
    /** 取景框外遮罩透明度 0–255，默认 0 即全透明。 */
    private int dimAlpha = 0;
    private boolean scanLineEnabled = true;
    private float scanLineProgress;
    private boolean scanFrameScheduled;
    private long scanAnimationStartMs;
    private OnViewfinderTapListener tapListener;
    private final Runnable scanFrame = new Runnable() {
        @Override
        public void run() {
            scanFrameScheduled = false;
            if (!scanLineEnabled || !isShown() || !isAttachedToWindow()) {
                return;
            }
            long elapsed = SystemClock.uptimeMillis() - scanAnimationStartMs;
            float cycle = (elapsed % 4400L) / 2200f;
            float directionProgress = cycle <= 1f ? cycle : 2f - cycle;
            scanLineProgress = 0.08f + 0.84f * directionProgress;
            invalidate();
            scheduleNextScanFrame();
        }
    };

    public BasicScannerOverlayView(Context context) {
        this(context, null);
    }

    public BasicScannerOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicScannerOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        refreshTheme();
        startScanAnimation();
    }

    /** 为顶部/底部系统栏与页面控件预留高度，避免遮罩与取景框压住顶栏和底栏。 */
    public void setChromeInsets(int topPx, int bottomPx) {
        topChromePx = Math.max(0, topPx);
        bottomChromePx = Math.max(0, bottomPx);
        invalidate();
    }

    /** 设置取景框边长占短边的比例，默认 0.72。 */
    public void setViewfinderSizeRatio(float ratio) {
        viewfinderSizeRatio = Math.max(0.4f, Math.min(ratio, 0.95f));
        invalidate();
    }

    /** 设置取景框外遮罩透明度，0 为全透明，建议不超过 120。 */
    public void setDimAlpha(int alpha) {
        dimAlpha = Math.max(0, Math.min(alpha, 255));
        dimPaint.setAlpha(dimAlpha);
        invalidate();
    }

    /** 是否显示扫描线动画。 */
    public void setScanLineEnabled(boolean enabled) {
        scanLineEnabled = enabled;
        if (enabled) {
            startScanAnimation();
        } else {
            stopScanAnimation();
        }
        invalidate();
    }

    public void setOnViewfinderTapListener(OnViewfinderTapListener listener) {
        tapListener = listener;
    }

    /** 返回归一化取景框区域 (0-1)。 */
    public RectF getNormalizedViewfinderRect() {
        return new RectF(viewfinderRect);
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        if (colors == null) {
            return;
        }
        dimPaint.setColor(0xFF000000);
        dimPaint.setAlpha(dimAlpha);
        bracketPaint.setColor(colors.brandPrimary);
        bracketPaint.setStyle(Paint.Style.STROKE);
        bracketPaint.setStrokeCap(Paint.Cap.ROUND);
        scanLinePaint.setColor(colors.brandPrimary);
        scanLinePaint.setAlpha(180);
        scanLinePaint.setStrokeWidth(dp(bracketStrokeDp));
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        refreshTheme();
        if (scanLineEnabled) {
            startScanAnimation();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        stopScanAnimation();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE && scanLineEnabled) {
            startScanAnimation();
        } else if (visibility != VISIBLE) {
            stopScanAnimation();
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (!isAttachedToWindow()) {
            return;
        }
        if (isShown() && scanLineEnabled) {
            startScanAnimation();
        } else {
            stopScanAnimation();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateViewfinderRect(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        updateViewfinderRect(getWidth(), getHeight());
        float stroke = dp(bracketStrokeDp);
        bracketPaint.setStrokeWidth(stroke);
        scanLinePaint.setStrokeWidth(stroke);

        if (dimAlpha > 0) {
            float topDimEnd = Math.min(viewfinderRect.top, getHeight() - bottomChromePx);
            float bottomDimStart = Math.max(viewfinderRect.bottom, topChromePx);
            float bottomDimEnd = getHeight() - bottomChromePx;
            if (topDimEnd > topChromePx) {
                canvas.drawRect(0f, topChromePx, getWidth(), topDimEnd, dimPaint);
            }
            if (viewfinderRect.left > 0f) {
                canvas.drawRect(0f, viewfinderRect.top, viewfinderRect.left, viewfinderRect.bottom, dimPaint);
            }
            if (viewfinderRect.right < getWidth()) {
                canvas.drawRect(viewfinderRect.right, viewfinderRect.top, getWidth(), viewfinderRect.bottom, dimPaint);
            }
            if (bottomDimEnd > bottomDimStart) {
                canvas.drawRect(0f, bottomDimStart, getWidth(), bottomDimEnd, dimPaint);
            }
        }

        drawCornerBrackets(canvas);
        if (scanLineEnabled) {
            float y = viewfinderRect.top + viewfinderRect.height() * scanLineProgress;
            canvas.drawLine(viewfinderRect.left + dp(8f), y, viewfinderRect.right - dp(8f), y, scanLinePaint);
        }
    }

    private void drawCornerBrackets(Canvas canvas) {
        float len = dp(bracketLengthDp);
        float stroke = dp(bracketStrokeDp);
        float left = viewfinderRect.left;
        float top = viewfinderRect.top;
        float right = viewfinderRect.right;
        float bottom = viewfinderRect.bottom;

        canvas.drawLine(left, top + len, left, top, bracketPaint);
        canvas.drawLine(left, top, left + len, top, bracketPaint);

        canvas.drawLine(right - len, top, right, top, bracketPaint);
        canvas.drawLine(right, top, right, top + len, bracketPaint);

        canvas.drawLine(left, bottom - len, left, bottom, bracketPaint);
        canvas.drawLine(left, bottom, left + len, bottom, bracketPaint);

        canvas.drawLine(right - len, bottom, right, bottom, bracketPaint);
        canvas.drawLine(right, bottom, right, bottom - len, bracketPaint);

        bracketPaint.setStrokeWidth(stroke);
    }

    private void updateViewfinderRect(int width, int height) {
        float availableHeight = Math.max(0f, height - topChromePx - bottomChromePx);
        float size = Math.min(width, availableHeight) * viewfinderSizeRatio;
        float left = (width - size) / 2f;
        float top = topChromePx + (availableHeight - size) / 2f;
        viewfinderRect.set(left, top, left + size, top + size);
    }

    private void startScanAnimation() {
        if (!scanLineEnabled || !isAttachedToWindow() || !isShown()) {
            return;
        }
        if (scanAnimationStartMs == 0L) {
            scanAnimationStartMs = SystemClock.uptimeMillis();
        }
        scheduleNextScanFrame();
    }

    private void scheduleNextScanFrame() {
        if (scanFrameScheduled) {
            return;
        }
        scanFrameScheduled = true;
        postOnAnimation(scanFrame);
    }

    private void stopScanAnimation() {
        removeCallbacks(scanFrame);
        scanFrameScheduled = false;
        scanAnimationStartMs = 0L;
    }

    private float dp(float value) {
        BasicStyle style = BasicThemeManager.style();
        float density = getResources().getDisplayMetrics().density;
        if (style != null) {
            return value * density;
        }
        return value * density;
    }
}
