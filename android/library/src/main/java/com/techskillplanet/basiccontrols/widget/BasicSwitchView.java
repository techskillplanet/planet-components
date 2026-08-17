package com.techskillplanet.basiccontrols.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.R;
import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * animal-island-ui 风格的基础开关组件。
 *
 * <p>视觉结构参考 animal-island-ui Switch：圆角轨道、粗边框、圆形 handle、
 * 轨道内文案和 loading spinner。Android 端不直接写死颜色和尺寸，而是从
 * color_token.json 与 style_token.json 读取，保证后续 Figma 和 Android 换肤时只改
 * token 文件即可。</p>
 */
public class BasicSwitchView extends LinearLayout {
    /** @deprecated Prefer {@link #VARIANT_SM}. */
    public static final String SIZE_SMALL = "small";
    /** @deprecated Prefer {@link #VARIANT_MD}. */
    public static final String SIZE_DEFAULT = "default";
    /** Contract variant md (52×28). */
    public static final String VARIANT_MD = "md";
    /** Contract variant sm (38×20). */
    public static final String VARIANT_SM = "sm";

    /** Switch 选中状态变化回调，对齐 animal-island-ui 的 onChange 语义。 */
    public interface OnCheckedChangeListener {
        /** 当开关状态变化时触发。 */
        void onCheckedChanged(BasicSwitchView view, boolean checked);
    }

    /** 左侧业务说明文字，适合设置页和表单项。 */
    private final TextView labelView;
    /** Switch 外壳，承载轨道、文案和 handle。 */
    private final FrameLayout switchFrame;
    /** 轨道内显示 on/off 文案。 */
    private final TextView innerTextView;
    /** 可滑动圆形 handle。 */
    private final LoadingHandleView handleView;

    private String size = SIZE_DEFAULT;
    private boolean basicDisabled;
    private boolean loading;
    private CharSequence checkedText = "ON";
    private CharSequence uncheckedText = "OFF";
    private OnCheckedChangeListener checkedChangeListener;

    public BasicSwitchView(Context context) {
        this(context, null);
    }

    public BasicSwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setClickable(true);
        setFocusable(true);

        labelView = new TextView(context);
        switchFrame = new FrameLayout(context);
        innerTextView = new TextView(context);
        handleView = new LoadingHandleView(context);

        innerTextView.setGravity(Gravity.CENTER);
        innerTextView.setSingleLine(true);
        innerTextView.setIncludeFontPadding(false);
        innerTextView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        switchFrame.addView(innerTextView);
        switchFrame.addView(handleView);
        innerTextView.setVisibility(GONE);
        addView(labelView);
        addView(switchFrame);

        readAttrs(attrs);
        setOnFocusChangeListener((view, hasFocus) -> refreshTheme());
        setOnClickListener(view -> {
            if (isEnabled() && !loading) {
                toggle();
            }
        });
        refreshTheme();
    }

    /**
     * 设置尺寸变体。
     *
     * <p>契约值：{@code md}/{@code sm}。兼容历史 {@code default}/{@code small}。</p>
     */
    public void setVariant(String variant) {
        if (SIZE_SMALL.equals(variant) || VARIANT_SM.equals(variant)) {
            size = SIZE_SMALL;
        } else {
            size = SIZE_DEFAULT;
        }
        refreshTheme();
    }

    /** 设置左侧说明文字。 */
    public void setBasicText(CharSequence text) {
        labelView.setText(text);
    }

    /** 设置开启态轨道内文案。 */
    public void setCheckedText(CharSequence text) {
        checkedText = text == null ? "" : text;
        refreshTheme();
    }

    /** 设置关闭态轨道内文案。 */
    public void setUncheckedText(CharSequence text) {
        uncheckedText = text == null ? "" : text;
        refreshTheme();
    }

    /** 设置 checked 状态变化监听。 */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        checkedChangeListener = listener;
    }

    /** 返回当前是否开启。 */
    public boolean isChecked() {
        return isSelected();
    }

    /** 切换开关状态。 */
    public void toggle() {
        setSelectedState(!isSelected());
    }

    /** 设置 loading 状态，loading 时阻止点击并在 handle 内绘制 spinner。 */
    public void setLoading(boolean loading) {
        this.loading = loading;
        handleView.setLoading(loading);
        refreshTheme();
    }

    /** 设置开关是否开启。 */
    public void setSelectedState(boolean selected) {
        boolean changed = isSelected() != selected;
        setSelected(selected);
        refreshTheme();
        if (changed && checkedChangeListener != null) {
            checkedChangeListener.onCheckedChanged(this, selected);
        }
    }

    /** 设置禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        refreshTheme();
    }

    /**
     * 按当前 token 和状态刷新视觉（扁平常规 UI：无轨内文案、无厚边框）。
     */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        SwitchMetrics metrics = metrics(style);
        boolean checked = isSelected();

        // Flat: off=borderDefault, on=brandPrimary; no stroke.
        int trackFill = checked ? colors.brandPrimary : colors.borderDefault;
        int labelText = colors.textPrimary;
        if (basicDisabled || !isEnabled()) {
            trackFill = colors.backgroundSurfaceDisabled;
            labelText = colors.textDisabled;
        }

        labelView.setTextColor(labelText);
        labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        switchFrame.setBackground(BasicDrawableFactory.roundedFill(trackFill, style.radiusPill));
        switchFrame.setAlpha(basicDisabled || !isEnabled()
                ? style.switchOpacityDisabled
                : (loading ? style.switchOpacityLoading : style.switchOpacityEnabled));

        innerTextView.setVisibility(GONE);

        handleView.setSpinnerColor(checked ? colors.textInverse : colors.brandDark);
        handleView.setBackground(BasicDrawableFactory.roundedFill(colors.switchHandleBackground, metrics.handle / 2f));

        LayoutParams labelParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
        labelParams.setMargins(0, 0, Math.round(style.spaceMd), 0);
        labelView.setLayoutParams(labelParams);
        labelView.setVisibility(labelView.getText() == null || labelView.getText().length() == 0 ? GONE : VISIBLE);
        switchFrame.setLayoutParams(new LayoutParams(metrics.width, metrics.height));

        FrameLayout.LayoutParams handleParams = new FrameLayout.LayoutParams(metrics.handle, metrics.handle);
        handleParams.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
        handleParams.leftMargin = metrics.handleMargin;
        handleView.setLayoutParams(handleParams);
        handleView.setAlpha(1f);
        float targetTranslation = checked ? metrics.width - metrics.handle - metrics.handleMargin * 2f : 0f;
        moveHandle(targetTranslation, style.switchMotionDuration);
    }

    /** 同步子 View enabled 状态。 */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).setEnabled(enabled);
        }
    }

    /** 从 XML 读取文字、尺寸、选中态和禁用态。 */
    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String text = array.getString(R.styleable.BasicView_basicText);
            String xmlVariant = array.getString(R.styleable.BasicView_basicVariant);
            size = (SIZE_SMALL.equals(xmlVariant) || VARIANT_SM.equals(xmlVariant)) ? SIZE_SMALL : SIZE_DEFAULT;
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setSelected(array.getBoolean(R.styleable.BasicView_basicSelected, false));
            labelView.setText(text == null ? "" : text);
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }

    /** 根据 size token 计算当前尺寸（扁平：inset=2）。 */
    private SwitchMetrics metrics(BasicStyle style) {
        if (SIZE_SMALL.equals(size)) {
            int handle = Math.round(style.switchSmHandle);
            int height = Math.round(style.switchSmHeight);
            int margin = Math.max(2, Math.round((height - handle) / 2f));
            return new SwitchMetrics(
                    Math.round(style.switchSmWidth),
                    height,
                    handle,
                    margin,
                    style.switchSmInnerText
            );
        }
        int handle = Math.round(style.switchMdHandle);
        int height = Math.round(style.switchMdHeight);
        int margin = Math.max(2, Math.round((height - handle) / 2f));
        return new SwitchMetrics(
                Math.round(style.switchMdWidth),
                height,
                handle,
                margin,
                style.switchMdInnerText
        );
    }

    /** 移动 handle；已布局时使用过渡动画，初次布局时直接设置最终位置。 */
    private void moveHandle(float targetTranslation, long durationMs) {
        handleView.animate().cancel();
        if (!isAttachedToWindow() || switchFrame.getWidth() == 0) {
            handleView.setTranslationX(targetTranslation);
            return;
        }
        handleView.animate()
                .translationX(targetTranslation)
                .setDuration(durationMs)
                .start();
    }

    /** Switch 尺寸聚合，避免在 refreshTheme 中反复传一组零散数字。 */
    private static final class SwitchMetrics {
        final int width;
        final int height;
        final int handle;
        final int handleMargin;
        final float innerTextSize;

        SwitchMetrics(int width, int height, int handle, int handleMargin, float innerTextSize) {
            this.width = width;
            this.height = height;
            this.handle = handle;
            this.handleMargin = handleMargin;
            this.innerTextSize = innerTextSize;
        }
    }

    /**
     * 可在 handle 中绘制 loading spinner 的小 View。
     *
     * <p>这里用 Canvas 绘制圆弧，避免为一个 loading 状态引入额外图片资源。</p>
     */
    private static final class LoadingHandleView extends View {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final RectF arcRect = new RectF();
        private boolean loading;
        private int spinnerColor;
        private ObjectAnimator animator;
        private float rotationValue;

        LoadingHandleView(Context context) {
            super(context);
        }

        void setLoading(boolean loading) {
            this.loading = loading;
            if (loading) {
                startSpinner();
            } else {
                stopSpinner();
            }
            invalidate();
        }

        void setSpinnerColor(int spinnerColor) {
            this.spinnerColor = spinnerColor;
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (!loading) {
                return;
            }
            float stroke = Math.max(2f, getWidth() * 0.12f);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(stroke);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setColor(spinnerColor);
            float inset = stroke * 1.8f;
            arcRect.set(inset, inset, getWidth() - inset, getHeight() - inset);
            canvas.save();
            canvas.rotate(rotationValue, getWidth() / 2f, getHeight() / 2f);
            canvas.drawArc(arcRect, 0f, 270f, false, paint);
            canvas.restore();
            paint.setStyle(Paint.Style.FILL);
        }

        @Override
        protected void onDetachedFromWindow() {
            stopSpinner();
            super.onDetachedFromWindow();
        }

        /** 启动 spinner 旋转动画。 */
        private void startSpinner() {
            if (animator != null) {
                return;
            }
            animator = ObjectAnimator.ofFloat(this, "rotationValue", 0f, 360f);
            animator.setDuration(600L);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(animation -> {
                rotationValue = (float) animation.getAnimatedValue();
                invalidate();
            });
            animator.start();
        }

        /** 停止 spinner 动画。 */
        private void stopSpinner() {
            if (animator != null) {
                animator.cancel();
                animator = null;
            }
            rotationValue = 0f;
        }

        /** ObjectAnimator 需要属性 setter 才能正常反射写值。 */
        @SuppressWarnings("unused")
        public void setRotationValue(float rotationValue) {
            this.rotationValue = rotationValue;
            invalidate();
        }
    }
}
