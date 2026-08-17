package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * RN PinInput 的 Android 实现：4–6 个独立单元格 + 透明叠加 EditText 输入。
 */
public class BasicPinInputView extends FrameLayout {
    public interface OnPinCompleteListener {
        void onComplete(String pin);
    }

    private final LinearLayout cellRow;
    private final EditText input;
    private final TextView[] cells = new TextView[6];
    private int cellCount = 4;
    private boolean secure;
    private OnPinCompleteListener completeListener;

    public BasicPinInputView(Context context) {
        this(context, null);
    }

    public BasicPinInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicPinInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        cellRow = new LinearLayout(context);
        cellRow.setOrientation(LinearLayout.HORIZONTAL);
        cellRow.setGravity(Gravity.CENTER);
        addView(cellRow, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        for (int i = 0; i < cells.length; i++) {
            TextView cell = new TextView(context);
            cell.setGravity(Gravity.CENTER);
            cell.setTypeface(Typeface.DEFAULT_BOLD);
            cell.setIncludeFontPadding(false);
            cells[i] = cell;
        }

        input = new EditText(context);
        input.setBackgroundColor(Color.TRANSPARENT);
        input.setTextColor(Color.TRANSPARENT);
        input.setCursorVisible(false);
        input.setSingleLine(true);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(cellCount)});
        input.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                syncCells();
                if (s.length() == cellCount && completeListener != null) {
                    completeListener.onComplete(s.toString());
                }
            }
            @Override public void afterTextChanged(Editable s) { }
        });
        addView(input, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setOnClickListener(v -> {
            input.requestFocus();
            input.setSelection(input.getText().length());
        });
        rebuildCells();
        refreshTheme();
    }

    public void setCellCount(int count) {
        cellCount = Math.max(4, Math.min(6, count));
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(cellCount)});
        if (input.getText().length() > cellCount) {
            input.setText(input.getText().subSequence(0, cellCount));
        }
        rebuildCells();
        refreshTheme();
    }

    public String getPin() {
        return input.getText().toString();
    }

    public void clearPin() {
        input.setText("");
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
        input.setTransformationMethod(secure ? PasswordTransformationMethod.getInstance() : null);
        syncCells();
    }

    public void setOnPinCompleteListener(OnPinCompleteListener listener) {
        completeListener = listener;
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int gap = dp(10);
        int height = dp(48);
        int radius = dp(14);
        for (int i = 0; i < cellCount; i++) {
            TextView cell = cells[i];
            cell.setTextColor(colors.textPrimary);
            cell.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textTitle);
            cell.setBackground(BasicDrawableFactory.roundedFillStroke(
                    colors.backgroundSurfaceRaised,
                    colors.borderDefault,
                    style.borderHairline,
                    radius
            ));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cell.getLayoutParams();
            if (params == null) {
                params = new LinearLayout.LayoutParams(0, height, 1f);
            } else {
                params.height = height;
            }
            params.weight = 1f;
            params.width = 0;
            params.leftMargin = i == 0 ? 0 : gap;
            cell.setLayoutParams(params);
        }
        syncCells();
    }

    private void rebuildCells() {
        cellRow.removeAllViews();
        int height = dp(48);
        int gap = dp(10);
        for (int i = 0; i < cellCount; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, height, 1f);
            params.leftMargin = i == 0 ? 0 : gap;
            cellRow.addView(cells[i], params);
        }
        syncCells();
    }

    private void syncCells() {
        CharSequence value = input.getText();
        for (int i = 0; i < cellCount; i++) {
            char ch = i < value.length() ? value.charAt(i) : 0;
            if (ch == 0) {
                cells[i].setText("");
            } else if (secure) {
                cells[i].setText("•");
            } else {
                cells[i].setText(String.valueOf(ch));
            }
        }
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }
}
