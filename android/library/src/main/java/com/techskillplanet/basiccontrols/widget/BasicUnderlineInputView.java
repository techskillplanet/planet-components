package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.R;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 下划线表单输入框：上方标签 + 底部分割线，支持密码可见性切换。
 */
public class BasicUnderlineInputView extends LinearLayout {
  private final TextView labelView;
  private final FrameLayout inputRow;
  private final EditText editText;
  private final ImageView toggleView;
  private final View underlineView;
  private boolean passwordToggleEnabled;
  private boolean passwordVisible;
  private boolean basicDisabled;

  public BasicUnderlineInputView(Context context) {
    this(context, null);
  }

  public BasicUnderlineInputView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public BasicUnderlineInputView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setOrientation(VERTICAL);

    labelView = new TextView(context);
    addView(labelView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

    inputRow = new FrameLayout(context);
    LayoutParams rowParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    addView(inputRow, rowParams);

    editText = new EditText(context);
    editText.setSingleLine(true);
    editText.setInputType(InputType.TYPE_CLASS_TEXT);
    editText.setGravity(Gravity.CENTER_VERTICAL);
    editText.setBackground(null);
    editText.setTypeface(Typeface.DEFAULT);
    editText.setPadding(0, 0, 0, 0);
    inputRow.addView(editText, new FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.WRAP_CONTENT
    ));

    toggleView = new ImageView(context);
    toggleView.setVisibility(GONE);
    toggleView.setContentDescription(null);
    toggleView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    FrameLayout.LayoutParams toggleParams = new FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT
    );
    toggleParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
    inputRow.addView(toggleView, toggleParams);

    underlineView = new View(context);
    addView(underlineView, new LayoutParams(LayoutParams.MATCH_PARENT, dp(1)));
    underlineView.setMinimumHeight(dp(1));

    readAttrs(attrs);
    editText.setOnFocusChangeListener((view, hasFocus) -> refreshTheme());
    toggleView.setOnClickListener(view -> togglePasswordVisibility());
    refreshTheme();
  }

  public EditText getEditText() {
    return editText;
  }

  public void setLabel(CharSequence label) {
    labelView.setText(label);
  }

  public void setPasswordToggleEnabled(boolean enabled) {
    passwordToggleEnabled = enabled;
    if (enabled) {
      editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
      passwordVisible = false;
      applyPasswordTransformation();
    }
    toggleView.setVisibility(enabled ? VISIBLE : GONE);
    refreshTheme();
  }

  public void setBasicDisabled(boolean disabled) {
    basicDisabled = disabled;
    setEnabled(!disabled);
    editText.setEnabled(!disabled);
    toggleView.setEnabled(!disabled);
    refreshTheme();
  }

  public void refreshTheme() {
    BasicColors colors = BasicThemeManager.colors();
    BasicStyle style = BasicThemeManager.style();

    labelView.setTextColor(colors.textTertiary);
    labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
    labelView.setPadding(0, 0, 0, Math.round(style.spaceSm));

    int textColor = basicDisabled || !isEnabled() ? colors.textDisabled : colors.textPrimary;
    editText.setTextColor(textColor);
    editText.setHintTextColor(colors.textTertiary);
    editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);

    int toggleSize = Math.round(style.controlHeightMd);
    FrameLayout.LayoutParams toggleParams = (FrameLayout.LayoutParams) toggleView.getLayoutParams();
    toggleParams.width = toggleSize;
    toggleParams.height = toggleSize;
    toggleView.setLayoutParams(toggleParams);
    toggleView.setImageResource(passwordVisible ? R.drawable.ic_basic_eye_off : R.drawable.ic_basic_eye);
    toggleView.setColorFilter(colors.textTertiary);

    int underlineColor = colors.borderLight;
    int underlineHeight = dp(1);
    if (!basicDisabled && isEnabled() && editText.hasFocus()) {
      underlineColor = colors.borderFocus;
      underlineHeight = dp(2);
    }
    underlineView.setBackgroundColor(underlineColor);
    LayoutParams underlineParams = (LayoutParams) underlineView.getLayoutParams();
    underlineParams.height = underlineHeight;
    underlineView.setLayoutParams(underlineParams);

    int rowPadTop = Math.round(style.spaceSm);
    int rowPadBottom = Math.round(style.spaceSm);
    int togglePad = passwordToggleEnabled ? toggleSize : 0;
    editText.setPadding(0, rowPadTop, togglePad, rowPadBottom);
    inputRow.setMinimumHeight(Math.round(style.controlHeightLg));
  }

  private void togglePasswordVisibility() {
    passwordVisible = !passwordVisible;
    applyPasswordTransformation();
    refreshTheme();
  }

  private void applyPasswordTransformation() {
    if (!passwordToggleEnabled) {
      return;
    }
    if (passwordVisible) {
      editText.setTransformationMethod(null);
    } else {
      editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
    int end = editText.getText().length();
    editText.setSelection(end);
  }

  private void readAttrs(AttributeSet attrs) {
    if (attrs == null) {
      return;
    }
    TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
    try {
      String title = array.getString(R.styleable.BasicView_basicTitle);
      if (title != null) {
        labelView.setText(title);
      }
      String text = array.getString(R.styleable.BasicView_basicText);
      if (text != null) {
        editText.setText(text);
      }
      basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
      setEnabled(!basicDisabled);
      editText.setEnabled(!basicDisabled);
    } finally {
      array.recycle();
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
