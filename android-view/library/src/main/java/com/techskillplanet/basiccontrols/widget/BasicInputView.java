package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.techskillplanet.basiccontrols.R;
import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 基础输入框组件。
 *
 * <p>组件内部包了一层 FrameLayout，用外层负责 token 背景、圆角和边框，
 * 内部 EditText 只负责文本输入。这样 focus/error/disabled 的状态切换不会被系统
 * 默认 EditText 背景干扰。</p>
 */
public class BasicInputView extends FrameLayout {
    public static final String VARIANT_DEFAULT = "default";
    public static final String VARIANT_ERROR = "error";

    /** 真正承载用户输入的原生 EditText。 */
    private final EditText editText;
    /** 当前视觉变体，error 会优先显示危险色边框。 */
    private String variant = VARIANT_DEFAULT;
    /** 组件级禁用态，和 View enabled 状态保持同步。 */
    private boolean basicDisabled;

    public BasicInputView(Context context) {
        this(context, null);
    }

    public BasicInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        editText = new EditText(context);
        editText.setSingleLine(true);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setGravity(Gravity.CENTER_VERTICAL);
        editText.setBackground(null);
        editText.setTypeface(Typeface.DEFAULT);
        editText.setPadding(0, 0, 0, 0);
        addView(editText);
        readAttrs(attrs);
        editText.setOnFocusChangeListener((view, hasFocus) -> refreshTheme());
        refreshTheme();
    }

    /** 返回内部 EditText，方便业务设置 hint、inputType、selection 等原生能力。 */
    public EditText getEditText() {
        return editText;
    }

    /** 设置输入框变体，目前支持 default/error。 */
    public void setVariant(String variant) {
        this.variant = variant == null ? VARIANT_DEFAULT : variant;
        refreshTheme();
    }

    /** 设置输入框文本。 */
    public void setBasicText(CharSequence text) {
        editText.setText(text);
    }

    /** 统一组件协议里的选中态接口，输入框用 selected 存储但不额外改变样式。 */
    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    /** 设置禁用态，同时关闭内部 EditText 输入能力。 */
    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        editText.setEnabled(!disabled);
        refreshTheme();
    }

    /** 按当前 token 和交互状态重新绘制输入框。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int fill = colors.backgroundSurface;
        int stroke = colors.borderControl;
        float strokeWidth = style.borderDefault;
        int text = colors.textPrimary;

        if (VARIANT_ERROR.equals(variant)) {
            stroke = colors.borderDanger;
        } else if (editText.hasFocus()) {
            // focus 优先级高于默认态，这样键盘焦点移动时边框反馈足够明确。
            stroke = colors.borderFocus;
            strokeWidth = style.borderFocus;
        }
        if (basicDisabled || !isEnabled()) {
            fill = colors.backgroundSurfaceDisabled;
            stroke = colors.borderLight;
            text = colors.textDisabled;
        }

        setBackground(BasicDrawableFactory.roundedFillStroke(
                fill,
                stroke,
                strokeWidth,
                style.radiusControlIsland
        ));
        editText.setTextColor(text);
        editText.setHintTextColor(colors.textTertiary);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        setPadding(Math.round(style.spaceMd), 0, Math.round(style.spaceMd), 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        BasicStyle style = BasicThemeManager.style();
        int desiredHeight = Math.round(style.controlHeightLg);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = resolveSize(desiredHeight, heightMeasureSpec);
        int childWidth = Math.max(0, width - getPaddingLeft() - getPaddingRight());
        int childHeight = Math.max(0, height - getPaddingTop() - getPaddingBottom());
        editText.measure(
                MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY)
        );
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        editText.layout(
                childLeft,
                childTop,
                right - left - getPaddingRight(),
                bottom - top - getPaddingBottom()
        );
    }

    /** 从 XML 读取 BasicView 通用属性。 */
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
                editText.setText(xmlText);
            }
            setEnabled(!basicDisabled);
            editText.setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
