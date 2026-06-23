package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * RN PinInput 的 Android 实现，默认 6 位数字。
 */
public class BasicPinInputView extends LinearLayout {
    public interface OnPinCompleteListener {
        void onComplete(String pin);
    }

    private final EditText input;
    private int cellCount = 6;
    private OnPinCompleteListener completeListener;

    public BasicPinInputView(Context context) {
        this(context, null);
    }

    public BasicPinInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicPinInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        input = new EditText(context);
        input.setGravity(Gravity.CENTER);
        input.setSingleLine(true);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(cellCount)});
        input.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == cellCount && completeListener != null) {
                    completeListener.onComplete(s.toString());
                }
            }
            @Override public void afterTextChanged(Editable s) { }
        });
        addView(input, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        refreshTheme();
    }

    public void setCellCount(int count) {
        cellCount = Math.max(4, Math.min(8, count));
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(cellCount)});
    }

    public String getPin() {
        return input.getText().toString();
    }

    public void clearPin() {
        input.setText("");
    }

    public void setSecure(boolean secure) {
        input.setTransformationMethod(secure ? PasswordTransformationMethod.getInstance() : null);
    }

    public void setOnPinCompleteListener(OnPinCompleteListener listener) {
        completeListener = listener;
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        input.setTextColor(colors.textPrimary);
        input.setHintTextColor(colors.textTertiary);
        input.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textTitle);
        input.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        input.setLetterSpacing(0.25f);
        input.setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderDefault,
                style.borderDefault,
                style.radiusLg
        ));
        int padding = Math.round(style.spaceMd);
        input.setPadding(padding, padding, padding, padding);
    }
}
