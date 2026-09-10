package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 多行文本输入框。
 *
 * <p>对齐合约 TextArea（value / placeholder / rows / maxLength / disabled / onChange）。</p>
 */
public class BasicTextAreaView extends FrameLayout {
    public interface OnValueChangeListener {
        void onValueChanged(BasicTextAreaView view, String value);
    }

    private final EditText editText;
    private String variant = "default";
    private int rows = 3;
    private boolean basicDisabled;
    private boolean internalUpdate;
    private OnValueChangeListener valueChangeListener;

    public BasicTextAreaView(Context context) {
        this(context, null);
    }

    public BasicTextAreaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicTextAreaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        editText = new EditText(context);
        editText.setGravity(Gravity.TOP | Gravity.START);
        editText.setInputType(
                android.text.InputType.TYPE_CLASS_TEXT
                        | android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE
        );
        editText.setSingleLine(false);
        editText.setHorizontallyScrolling(false);
        editText.setBackground(null);
        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (internalUpdate || valueChangeListener == null) {
                    return;
                }
                valueChangeListener.onValueChanged(BasicTextAreaView.this, s == null ? "" : s.toString());
            }
        });
        addView(editText, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        refreshTheme();
    }

    public EditText getEditText() {
        return editText;
    }

    public void setVariant(String variant) {
        this.variant = variant == null ? "default" : variant;
        refreshTheme();
    }

    public void setValue(CharSequence value) {
        internalUpdate = true;
        editText.setText(value == null ? "" : value);
        editText.setSelection(editText.getText() == null ? 0 : editText.getText().length());
        internalUpdate = false;
    }

    public String getValue() {
        return editText.getText() == null ? "" : editText.getText().toString();
    }

    public void setPlaceholder(CharSequence placeholder) {
        editText.setHint(placeholder);
    }

    public void setRows(int rows) {
        this.rows = Math.max(1, rows);
        refreshTheme();
    }

    public void setMaxLength(int maxLength) {
        if (maxLength > 0) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        } else {
            editText.setFilters(new InputFilter[0]);
        }
    }

    public void setOnValueChangeListener(OnValueChangeListener listener) {
        valueChangeListener = listener;
    }

    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        editText.setEnabled(!disabled);
        editText.setFocusable(!disabled);
        editText.setFocusableInTouchMode(!disabled);
        refreshTheme();
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        if (colors == null || style == null) {
            return;
        }
        boolean disabled = basicDisabled || !isEnabled() || "disabled".equals(variant);
        boolean error = "error".equals(variant);
        int stroke = error ? colors.borderDanger : colors.borderControl;
        int fill = disabled ? colors.backgroundSurfaceDisabled : colors.backgroundSurface;
        if (disabled) {
            stroke = colors.borderLight;
        }
        int pad = Math.round(style.spaceMd);
        int lineHeight = Math.round(style.textMd * 1.4f);
        int minHeight = Math.round(style.spaceSm * 2) + lineHeight * rows + pad;
        editText.setMinHeight(minHeight);
        editText.setPadding(pad, pad, pad, pad);
        editText.setBackground(BasicDrawableFactory.roundedFillStroke(
                fill, stroke, style.borderDefault, style.radiusMd
        ));
        editText.setTextColor(disabled ? colors.textDisabled : colors.textPrimary);
        editText.setHintTextColor(colors.textTertiary);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        editText.setTypeface(Typeface.DEFAULT);
        editText.setMinLines(rows);
        setAlpha(disabled ? 0.85f : 1f);
    }
}
