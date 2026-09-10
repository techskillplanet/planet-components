package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.R;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 基础复选框组件。
 *
 * <p>自绘方块 + 描边对勾（对齐 Web LegalConsent 约 14–16 尺寸），避免把
 * controlHeightSm 当勾选框导致方块过大、文字对勾过小。</p>
 */
public class BasicCheckboxView extends LinearLayout {
    public interface OnCheckedChangeListener {
        void onCheckedChanged(BasicCheckboxView view, boolean checked);
    }

    private final CheckboxIndicator indicatorView;
    private final TextView labelView;
    private String variant = "default";
    private boolean basicDisabled;
    private OnCheckedChangeListener checkedChangeListener;

    public BasicCheckboxView(Context context) {
        this(context, null);
    }

    public BasicCheckboxView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicCheckboxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.TOP);
        setClickable(false);
        setFocusable(false);
        indicatorView = new CheckboxIndicator(context);
        labelView = new TextView(context);
        labelView.setIncludeFontPadding(false);
        addView(indicatorView);
        addView(labelView);
        readAttrs(attrs);
        indicatorView.setClickable(true);
        indicatorView.setOnClickListener(view -> {
            if (isEnabled()) {
                setSelectedState(!isSelected());
            }
        });
        labelView.setOnClickListener(view -> {
            if (isEnabled() && labelView.getMovementMethod() == null) {
                setSelectedState(!isSelected());
            }
        });
        refreshTheme();
    }

    public void setVariant(String variant) {
        this.variant = variant == null ? "default" : variant;
        refreshTheme();
    }

    public void setBasicText(CharSequence text) {
        labelView.setText(text);
        setContentDescription(text == null || text.length() == 0 ? "Checkbox" : text.toString());
    }

    public TextView getLabelView() {
        return labelView;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        checkedChangeListener = listener;
    }

    public void setSelectedState(boolean selected) {
        boolean changed = isSelected() != selected;
        setSelected(selected);
        refreshTheme();
        if (changed && checkedChangeListener != null) {
            checkedChangeListener.onCheckedChanged(this, selected);
        }
    }

    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        refreshTheme();
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        boolean checked = isSelected();
        int fill = checked ? colors.checkboxCheckedBackground : colors.checkboxBackground;
        int stroke = checked ? colors.checkboxCheckedBackground : colors.borderControl;
        int text = colors.textPrimary;
        if (basicDisabled || !isEnabled()) {
            fill = colors.backgroundSurfaceDisabled;
            stroke = colors.borderLight;
            text = colors.textDisabled;
        }

        // 对齐 Web .legal-consent-input（14px）
        int box = dp(14);
        float radius = dp(3);
        float strokeW = Math.max(dp(1.5f), style.borderHairline);
        indicatorView.setVisual(fill, stroke, strokeW, radius, checked, colors.textInverse);
        labelView.setTextColor(text);
        labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm > 0 ? style.textSm : style.textMd);

        LayoutParams indicatorParams = new LayoutParams(box, box);
        // 与首行文字顶对齐 / 光学居中
        int topAlign = Math.max(dp(1), Math.round((style.textSm - box) / 2f));
        indicatorParams.setMargins(0, topAlign, Math.round(style.spaceSm > 0 ? style.spaceSm : dp(8)), 0);
        indicatorView.setLayoutParams(indicatorParams);
        labelView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
        CharSequence label = labelView.getText();
        setContentDescription(label == null || label.length() == 0 ? "Checkbox" : label.toString());
        setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).setEnabled(enabled);
        }
    }

    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String text = array.getString(R.styleable.BasicView_basicText);
            String xmlVariant = array.getString(R.styleable.BasicView_basicVariant);
            variant = xmlVariant == null ? "default" : xmlVariant;
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setSelected(array.getBoolean(R.styleable.BasicView_basicSelected, false));
            labelView.setText(text == null ? "" : text);
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }

    /** 自绘勾选指示器：圆角方块 + CSS 同构描边对勾。 */
    private static final class CheckboxIndicator extends View {
        private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint checkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final RectF rect = new RectF();
        private final Path checkPath = new Path();
        private float cornerRadius;
        private boolean checked;

        CheckboxIndicator(Context context) {
            super(context);
            fillPaint.setStyle(Paint.Style.FILL);
            strokePaint.setStyle(Paint.Style.STROKE);
            checkPaint.setStyle(Paint.Style.STROKE);
            checkPaint.setStrokeCap(Paint.Cap.ROUND);
            checkPaint.setStrokeJoin(Paint.Join.ROUND);
        }

        void setVisual(int fill, int stroke, float strokeWidth, float radius, boolean on, int checkColor) {
            fillPaint.setColor(fill);
            strokePaint.setColor(stroke);
            strokePaint.setStrokeWidth(strokeWidth);
            checkPaint.setColor(checkColor);
            cornerRadius = radius;
            checked = on;
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            float inset = strokePaint.getStrokeWidth() / 2f;
            rect.set(inset, inset, getWidth() - inset, getHeight() - inset);
            canvas.drawRoundRect(rect, cornerRadius, cornerRadius, fillPaint);
            if (!checked) {
                canvas.drawRoundRect(rect, cornerRadius, cornerRadius, strokePaint);
                return;
            }
            // 对勾几何对齐 Web ::after（约占盒内 55–65%，线宽约 1.5–2dp）
            float w = getWidth();
            float h = getHeight();
            checkPaint.setStrokeWidth(Math.max(dpStroke(2f), w * 0.14f));
            checkPath.reset();
            checkPath.moveTo(w * 0.22f, h * 0.52f);
            checkPath.lineTo(w * 0.42f, h * 0.70f);
            checkPath.lineTo(w * 0.78f, h * 0.30f);
            canvas.drawPath(checkPath, checkPaint);
        }

        private float dpStroke(float value) {
            return TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    value,
                    getResources().getDisplayMetrics()
            );
        }
    }
}
