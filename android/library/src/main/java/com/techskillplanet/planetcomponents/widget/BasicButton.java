package com.techskillplanet.planetcomponents.widget;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.R;
import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 岛屿风格按钮。
 *
 * <p>这个组件不继承 Button，是因为需要做 animal-island-ui 风格的“上层按钮面 +
 * 下层托起阴影”。FrameLayout 更适合管理两个层级：shadowLayer 和 faceLayer。
 * 是否显示 shadowLayer 由 style_token 的
 * {@code islandStyle.rules.buttonRaisedShadowEnabled} 控制，样式配置
 * {@code themes.island_raised / island_flat} 会覆盖该开关；阴影颜色仍读取
 * color_token 的 {@code semantic.control.button.raisedShadow}。</p>
 */
public class BasicButton extends FrameLayout {
    public static final String VARIANT_DEFAULT = "default";
    public static final String VARIANT_PRIMARY = "primary";
    public static final String VARIANT_DANGER = "danger";
    public static final String VARIANT_TEXT = "text";
    public static final String VARIANT_LINK = "link";
    public static final String VARIANT_GRADIENT = "gradient";

    /** 底部托起阴影层，模拟轻游戏感的 3D 按钮底座。 */
    private final View shadowLayer;
    /** 按钮可见内容层，负责承载 spinner、文字和按压位移。 */
    private final LinearLayout faceLayer;
    private final ButtonSpinnerView spinnerView;
    private final TextView labelView;

    private String variant = VARIANT_DEFAULT;
    private boolean basicDisabled;
    private boolean loading;

    public BasicButton(Context context) {
        this(context, null);
    }

    public BasicButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        shadowLayer = new View(context);
        faceLayer = new LinearLayout(context);
        faceLayer.setOrientation(LinearLayout.HORIZONTAL);
        faceLayer.setGravity(Gravity.CENTER);
        spinnerView = new ButtonSpinnerView(context);
        labelView = new TextView(context);
        labelView.setGravity(Gravity.CENTER);
        labelView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        int spinnerSize = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
        LinearLayout.LayoutParams spinnerLp = new LinearLayout.LayoutParams(spinnerSize, spinnerSize);
        spinnerLp.rightMargin = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
        faceLayer.addView(spinnerView, spinnerLp);
        faceLayer.addView(labelView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        spinnerView.setVisibility(GONE);
        addView(shadowLayer);
        addView(faceLayer);
        readAttrs(attrs);
        refreshTheme();
        setClickable(true);
        setFocusable(true);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        disableChildTouch(faceLayer);
        disableChildTouch(labelView);
        disableChildTouch(spinnerView);
        disableChildTouch(shadowLayer);
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        super.setOnClickListener(listener);
        disableChildTouch(faceLayer);
        disableChildTouch(labelView);
        disableChildTouch(spinnerView);
    }

    /**
     * 子 View 若变成 clickable，会抢走触摸，导致没有按压动画、点击无效。
     * 所有触摸统一由 BasicButton 自己处理。
     */
    private static void disableChildTouch(View child) {
        child.setClickable(false);
        child.setLongClickable(false);
        child.setFocusable(false);
        child.setFocusableInTouchMode(false);
        child.setOnClickListener(null);
        child.setOnTouchListener(null);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isEnabled() && isClickable() && !loading) {
            return true;
        }
        return super.onInterceptTouchEvent(event);
    }

    /** 设置按钮变体，例如 primary/default/danger/text/link。 */
    public void setVariant(String variant) {
        this.variant = variant == null ? VARIANT_DEFAULT : variant;
        refreshTheme();
    }

    /** 设置按钮文案。 */
    public void setBasicText(CharSequence text) {
        labelView.setText(text);
    }

    /** Button 没有业务选中态，这里保留统一接口，方便上层按统一组件协议调用。 */
    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    /** 设置禁用态，并同步刷新颜色和交互。 */
    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        applyInteractiveState();
        refreshTheme();
    }

    /** 设置 loading 态：显示 spinner 并阻止点击。 */
    public void setLoading(boolean loading) {
        this.loading = loading;
        spinnerView.setLoading(loading);
        spinnerView.setVisibility(loading ? VISIBLE : GONE);
        applyInteractiveState();
        refreshTheme();
    }

    /** 是否处于 loading。 */
    public boolean isLoading() {
        return loading;
    }

    private void applyInteractiveState() {
        boolean inert = basicDisabled || loading;
        setEnabled(!inert);
        setClickable(!inert);
    }

    /**
     * 按当前 token 重新绘制按钮。
     *
     * <p>换肤后调用这个方法即可让已有按钮读取新主题。</p>
     */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        boolean flatVariant = VARIANT_TEXT.equals(variant) || VARIANT_LINK.equals(variant);
        int fill;
        int text;
        int stroke;
        if (VARIANT_GRADIENT.equals(variant)) {
            int gradientStart = colors.brandPrimary;
            int gradientEnd = colors.brandPrimaryHover;
            text = colors.textInverse;
            faceLayer.setBackground(BasicDrawableFactory.roundedGradientFill(
                    gradientStart,
                    gradientEnd,
                    style.radiusPill
            ));
            shadowLayer.setVisibility(GONE);
            labelView.setTextColor(text);
            labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
            spinnerView.setSpinnerColor(text);
            setAlpha(basicDisabled || !isEnabled() ? 0.45f : 1f);
            requestLayout();
            return;
        }
        if (VARIANT_PRIMARY.equals(variant)) {
            fill = colors.buttonPrimaryBackground;
            text = colors.buttonPrimaryText;
            stroke = colors.buttonPrimaryBackground;
        } else if (VARIANT_DANGER.equals(variant)) {
            fill = colors.statusDanger;
            text = colors.textInverse;
            stroke = colors.statusDanger;
        } else if (flatVariant) {
            fill = android.graphics.Color.TRANSPARENT;
            text = colors.brandPrimary;
            stroke = android.graphics.Color.TRANSPARENT;
        } else {
            fill = colors.buttonDefaultBackground;
            text = colors.buttonDefaultText;
            stroke = colors.borderControl;
        }
        if ((basicDisabled || !isEnabled()) && !flatVariant && !loading) {
            fill = colors.backgroundSurfaceDisabled;
            text = colors.textDisabled;
            stroke = colors.borderLight;
        }

        labelView.setTextColor(text);
        labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        spinnerView.setSpinnerColor(text);
        faceLayer.setBackground(BasicDrawableFactory.roundedFillStroke(
                fill,
                stroke,
                flatVariant ? 0f : style.borderDefault,
                style.radiusPill
        ));
        boolean showRaisedShadow = style.buttonRaisedShadowEnabled && !flatVariant;
        shadowLayer.setVisibility(showRaisedShadow ? VISIBLE : GONE);
        if (showRaisedShadow) {
            shadowLayer.setBackground(BasicDrawableFactory.roundedFill(
                    colors.buttonRaisedShadow,
                    style.radiusPill
            ));
        }
        setAlpha(basicDisabled || (!isEnabled() && !loading) ? 0.45f : 1f);
        requestLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled() || !isClickable() || loading) {
            return super.onTouchEvent(event);
        }
        BasicStyle style = BasicThemeManager.style();
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            faceLayer.setTranslationY(style.pressedDropY);
            return true;
        }
        if (action == MotionEvent.ACTION_UP) {
            faceLayer.setTranslationY(0f);
            performClick();
            return true;
        }
        if (action == MotionEvent.ACTION_CANCEL) {
            faceLayer.setTranslationY(0f);
            return true;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        BasicStyle style = BasicThemeManager.style();
        int minWidth = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                88,
                getResources().getDisplayMetrics()
        ));
        boolean flatVariant = VARIANT_TEXT.equals(variant) || VARIANT_LINK.equals(variant);
        int lift = style.buttonRaisedShadowEnabled && !flatVariant
                ? Math.round(style.shadowControlIslandLiftY)
                : 0;
        int desiredHeight = Math.round(style.controlHeightButtonMedium + lift);
        int width = resolveSize(Math.max(minWidth, getSuggestedMinimumWidth()), widthMeasureSpec);
        int height = resolveSize(desiredHeight, heightMeasureSpec);
        int childHeight = Math.max(0, height - lift);
        int exactWidth = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int exactChildHeight = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
        faceLayer.measure(exactWidth, exactChildHeight);
        if (style.buttonRaisedShadowEnabled && !flatVariant) {
            shadowLayer.measure(exactWidth, exactChildHeight);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        BasicStyle style = BasicThemeManager.style();
        boolean flatVariant = VARIANT_TEXT.equals(variant) || VARIANT_LINK.equals(variant);
        int width = right - left;
        int height = bottom - top;
        int lift = style.buttonRaisedShadowEnabled && !flatVariant
                ? Math.round(style.shadowControlIslandLiftY)
                : 0;
        int contentHeight = Math.max(0, height - lift);
        if (style.buttonRaisedShadowEnabled && !flatVariant) {
            shadowLayer.layout(0, lift, width, lift + contentHeight);
        }
        faceLayer.layout(0, 0, width, contentHeight);
    }

    /** 从 XML 读取统一 BasicView 属性。 */
    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String xmlVariant = array.getString(R.styleable.BasicView_basicVariant);
            String xmlText = array.getString(R.styleable.BasicView_basicText);
            variant = xmlVariant == null ? VARIANT_DEFAULT : xmlVariant;
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            if (xmlText != null) {
                labelView.setText(xmlText);
            }
            applyInteractiveState();
        } finally {
            array.recycle();
        }
    }

    /** 按钮内小型圆弧 spinner。 */
    private static final class ButtonSpinnerView extends View {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final RectF arcRect = new RectF();
        private boolean loading;
        private int spinnerColor = 0xFFFFFFFF;
        private ObjectAnimator animator;
        private float rotationValue;

        ButtonSpinnerView(Context context) {
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
            float stroke = Math.max(2f, getWidth() * 0.14f);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(stroke);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setColor(spinnerColor);
            float inset = stroke * 1.6f;
            arcRect.set(inset, inset, getWidth() - inset, getHeight() - inset);
            canvas.save();
            canvas.rotate(rotationValue, getWidth() / 2f, getHeight() / 2f);
            canvas.drawArc(arcRect, 0f, 270f, false, paint);
            canvas.restore();
        }

        @Override
        protected void onDetachedFromWindow() {
            stopSpinner();
            super.onDetachedFromWindow();
        }

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

        private void stopSpinner() {
            if (animator != null) {
                animator.cancel();
                animator = null;
            }
            rotationValue = 0f;
        }

        @SuppressWarnings("unused")
        public void setRotationValue(float rotationValue) {
            this.rotationValue = rotationValue;
        }
    }
}
