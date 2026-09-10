package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.R;
import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 悬浮操作按钮（圆形图标或带文字的扩展形态）。
 *
 * <p>对齐合约 Fab（icon / text / variant / disabled / onTap）。</p>
 */
public class BasicFabView extends LinearLayout {
    /** 点击回调。 */
    public interface OnTapListener {
        void onTap(BasicFabView view);
    }

    private final TextView iconView;
    private final TextView textView;
    private String variant = "primary";
    private String icon = "+";
    private boolean basicDisabled;
    private OnTapListener tapListener;

    public BasicFabView(Context context) {
        this(context, null);
    }

    public BasicFabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicFabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setClickable(true);
        setFocusable(true);
        iconView = new TextView(context);
        iconView.setGravity(Gravity.CENTER);
        iconView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textView = new TextView(context);
        textView.setSingleLine(true);
        textView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        addView(iconView);
        addView(textView);
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

    /** 设置变体：primary / default。 */
    public void setVariant(String variant) {
        this.variant = variant == null ? "primary" : variant;
        refreshTheme();
    }

    /** 设置图标文案（字符或 emoji）。 */
    public void setIcon(CharSequence icon) {
        this.icon = icon == null || icon.length() == 0 ? "+" : icon.toString();
        refreshTheme();
    }

    /** 设置扩展态文字；空字符串时为圆形 FAB。 */
    public void setBasicText(CharSequence text) {
        textView.setText(text == null ? "" : text);
        refreshTheme();
    }

    /** 设置禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        setClickable(!disabled);
        refreshTheme();
    }

    /** 选中态不改变外观。 */
    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    /** 设置点击监听。 */
    public void setOnTapListener(OnTapListener listener) {
        tapListener = listener;
    }

    /** 按 token 刷新外观。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        boolean primary = !"default".equals(variant);
        boolean extended = textView.getText() != null && textView.getText().length() > 0;
        int fill = primary ? colors.brandPrimary : colors.backgroundSurfaceRaised;
        int stroke = primary ? colors.brandPrimary : colors.borderDefault;
        int text = primary ? colors.textInverse : colors.textPrimary;
        if (basicDisabled || !isEnabled()) {
            fill = colors.backgroundSurfaceDisabled;
            stroke = colors.borderLight;
            text = colors.textDisabled;
        }
        int size = Math.round(style.controlHeightLg + style.spaceSm);
        int padH = extended ? Math.round(style.spaceLg) : 0;
        setMinimumWidth(size);
        setMinimumHeight(size);
        setPadding(padH, 0, padH, 0);
        setBackground(BasicDrawableFactory.roundedFillStroke(fill, stroke, style.borderDefault, style.radiusPill));
        iconView.setText(icon);
        iconView.setTextColor(text);
        iconView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textLg);
        LayoutParams iconLp = new LayoutParams(
                extended ? ViewGroup.LayoutParams.WRAP_CONTENT : size,
                size);
        iconView.setLayoutParams(iconLp);
        textView.setTextColor(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        textView.setVisibility(extended ? VISIBLE : GONE);
        if (extended) {
            LayoutParams textLp = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textLp.setMarginStart(Math.round(style.spaceSm));
            textView.setLayoutParams(textLp);
        }
        CharSequence label = extended ? textView.getText() : icon;
        setContentDescription(label == null || label.length() == 0 ? "Fab" : label);
        setAlpha(basicDisabled || !isEnabled() ? 0.45f : 1f);
        setElevation(Math.round(style.spaceSm));
    }

    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String xmlVariant = array.getString(R.styleable.BasicView_basicVariant);
            String xmlText = array.getString(R.styleable.BasicView_basicText);
            variant = xmlVariant == null ? "primary" : xmlVariant;
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            if (xmlText != null) {
                textView.setText(xmlText);
            }
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
