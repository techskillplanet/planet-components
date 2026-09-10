package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.R;
import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 可关闭 / 可点击的标签组件。
 *
 * <p>对齐合约 Tag（text / closable / selected / disabled / variant / onClose / onTap）。
 * Chip 仍用于筛选胶囊；Tag 更偏状态标记与可移除标签。</p>
 */
public class BasicTagView extends LinearLayout {
    /** 关闭回调。 */
    public interface OnCloseListener {
        void onClose(BasicTagView view);
    }

    /** 点击回调。 */
    public interface OnTapListener {
        void onTap(BasicTagView view);
    }

    private final TextView labelView;
    private final TextView closeView;
    private String variant = "default";
    private boolean closable;
    private boolean basicDisabled;
    private OnCloseListener closeListener;
    private OnTapListener tapListener;

    public BasicTagView(Context context) {
        this(context, null);
    }

    public BasicTagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setClickable(true);
        labelView = new TextView(context);
        labelView.setSingleLine(true);
        labelView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        closeView = new TextView(context);
        closeView.setText("×");
        closeView.setGravity(Gravity.CENTER);
        closeView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        closeView.setOnClickListener(v -> {
            if (basicDisabled || !isEnabled()) {
                return;
            }
            if (closeListener != null) {
                closeListener.onClose(this);
            }
        });
        addView(labelView, new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(closeView, new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOnClickListener(v -> {
            if (basicDisabled || !isEnabled()) {
                return;
            }
            if (tapListener != null) {
                tapListener.onTap(this);
            }
        });
        readAttrs(attrs);
        refreshTheme();
    }

    /** 设置变体：default / primary / success / warning / danger。 */
    public void setVariant(String variant) {
        this.variant = variant == null ? "default" : variant;
        refreshTheme();
    }

    /** 设置标签文字。 */
    public void setBasicText(CharSequence text) {
        labelView.setText(text == null ? "" : text);
        setContentDescription(text == null || text.length() == 0 ? "Tag" : text);
        refreshTheme();
    }

    /** 是否显示关闭按钮。 */
    public void setClosable(boolean closable) {
        this.closable = closable;
        refreshTheme();
    }

    /** 设置选中态。 */
    public void setSelectedState(boolean selected) {
        setSelected(selected);
        refreshTheme();
    }

    /** 设置禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        refreshTheme();
    }

    /** 设置关闭监听。 */
    public void setOnCloseListener(OnCloseListener listener) {
        closeListener = listener;
    }

    /** 设置点击监听。 */
    public void setOnTapListener(OnTapListener listener) {
        tapListener = listener;
    }

    /** 按 token 刷新外观。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int fill = colors.backgroundSurfaceRaised;
        int stroke = colors.borderDefault;
        int text = colors.textPrimary;
        if ("primary".equals(variant)) {
            fill = colors.brandPrimarySubtle;
            stroke = colors.brandPrimarySubtle;
            text = colors.brandPrimary;
        } else if ("success".equals(variant)) {
            fill = colors.selectedFill;
            stroke = colors.selectedFill;
            text = colors.statusSuccess;
        } else if ("warning".equals(variant)) {
            fill = colors.activeFill;
            stroke = colors.activeFill;
            text = colors.textPrimary;
        } else if ("danger".equals(variant) || "error".equals(variant)) {
            fill = 0xFFFFE8EB;
            stroke = 0xFFFFE8EB;
            text = colors.statusDanger;
        }
        if (isSelected()) {
            stroke = colors.statusSuccess;
        }
        if (basicDisabled || !isEnabled()) {
            fill = colors.backgroundSurfaceDisabled;
            stroke = colors.borderLight;
            text = colors.textDisabled;
        }
        int padH = Math.round(style.chipPaddingHorizontal);
        int padV = Math.round(style.spaceSm / 2f);
        setPadding(padH, padV, padV, padV);
        setBackground(BasicDrawableFactory.roundedFillStroke(
                fill, stroke, isSelected() ? style.borderDefault : style.borderHairline, style.radiusPill));
        labelView.setTextColor(text);
        labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        closeView.setTextColor(text);
        closeView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        closeView.setVisibility(closable ? View.VISIBLE : View.GONE);
        closeView.setPadding(padV, 0, padV, 0);
        setAlpha(basicDisabled || !isEnabled() ? 0.45f : 1f);
        setMinimumHeight(Math.round(style.chipHeight));
    }

    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String xmlVariant = array.getString(R.styleable.BasicView_basicVariant);
            String xmlText = array.getString(R.styleable.BasicView_basicText);
            variant = xmlVariant == null ? "default" : xmlVariant;
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setSelected(array.getBoolean(R.styleable.BasicView_basicSelected, false));
            if (xmlText != null) {
                labelView.setText(xmlText);
            }
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
