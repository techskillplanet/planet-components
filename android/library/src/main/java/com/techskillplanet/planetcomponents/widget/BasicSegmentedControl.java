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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 等宽分段选择器，选中项使用品牌色渐变高亮。
 */
public class BasicSegmentedControl extends LinearLayout {
  private static final int GRADIENT_END = 0xFF9B6DFF;

  public interface OnOptionSelectedListener {
    void onOptionSelected(int index, String label, String value);
  }

  private final List<String> labels = new ArrayList<>();
  private final List<String> values = new ArrayList<>();
  private int selectedIndex;
  private boolean basicDisabled;
  private OnOptionSelectedListener listener;

  public BasicSegmentedControl(Context context) {
    this(context, null);
  }

  public BasicSegmentedControl(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public BasicSegmentedControl(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setOrientation(HORIZONTAL);
    readAttrs(attrs);
    refreshTheme();
  }

  public void setOptions(List<String> optionLabels, List<String> optionValues) {
    labels.clear();
    values.clear();
    if (optionLabels != null) {
      labels.addAll(optionLabels);
    }
    if (optionValues != null) {
      values.addAll(optionValues);
    }
    while (values.size() < labels.size()) {
      values.add(labels.get(values.size()));
    }
    if (selectedIndex >= labels.size()) {
      selectedIndex = Math.max(0, labels.size() - 1);
    }
    refreshTheme();
  }

  public void setOnOptionSelectedListener(OnOptionSelectedListener listener) {
    this.listener = listener;
  }

  public void setSelectedIndex(int index) {
    if (index < 0 || index >= labels.size()) {
      return;
    }
    selectedIndex = index;
    refreshTheme();
    if (listener != null) {
      listener.onOptionSelected(index, labels.get(index), getSelectedValue());
    }
  }

  public int getSelectedIndex() {
    return selectedIndex;
  }

  public String getSelectedValue() {
    if (selectedIndex < 0 || selectedIndex >= values.size()) {
      return "";
    }
    return values.get(selectedIndex);
  }

  public void setBasicDisabled(boolean disabled) {
    basicDisabled = disabled;
    setEnabled(!disabled);
    refreshTheme();
  }

  public void refreshTheme() {
    BasicColors colors = BasicThemeManager.colors();
    BasicStyle style = BasicThemeManager.style();
    removeAllViews();

    float radius = style.radiusPill;
    setBackground(BasicDrawableFactory.roundedFillStroke(
        colors.backgroundSurface,
        colors.borderLight,
        style.borderHairline,
        radius
    ));
    setPadding(Math.round(style.borderHairline), Math.round(style.borderHairline),
        Math.round(style.borderHairline), Math.round(style.borderHairline));

    int count = labels.size();
    for (int i = 0; i < count; i++) {
      TextView item = createSegmentItem(i, count, colors, style, radius);
      LayoutParams params = new LayoutParams(0, Math.round(style.controlHeightLg), 1f);
      addView(item, params);
    }
  }

  private TextView createSegmentItem(int index, int count, BasicColors colors, BasicStyle style, float radius) {
    TextView item = new TextView(getContext());
    boolean selected = index == selectedIndex;
    int textColor = selected ? colors.textInverse : colors.textTertiary;
    if (basicDisabled || !isEnabled()) {
      textColor = colors.textDisabled;
    }
    item.setText(labels.get(index));
    item.setGravity(Gravity.CENTER);
    item.setSingleLine(true);
    item.setTypeface(Typeface.DEFAULT, selected ? Typeface.BOLD : Typeface.NORMAL);
    item.setTextColor(textColor);
    item.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
    item.setIncludeFontPadding(false);

    if (selected && !basicDisabled && isEnabled()) {
      float innerRadius = Math.max(0, radius - style.borderHairline);
      float[] corners = segmentCornerRadii(index, count, innerRadius);
      item.setBackground(BasicDrawableFactory.roundedGradientFill(
          colors.brandPrimary,
          GRADIENT_END,
          corners[0], corners[1], corners[2], corners[3]
      ));
    } else {
      item.setBackground(null);
    }

    item.setEnabled(!basicDisabled && isEnabled());
    item.setOnClickListener(view -> setSelectedIndex(index));
    return item;
  }

  private float[] segmentCornerRadii(int index, int count, float radius) {
    float tl = index == 0 ? radius : 0f;
    float tr = index == count - 1 ? radius : 0f;
    float br = tr;
    float bl = tl;
    return new float[]{tl, tr, br, bl};
  }

  private void readAttrs(AttributeSet attrs) {
    if (attrs == null) {
      return;
    }
    TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
    try {
      String text = array.getString(R.styleable.BasicView_basicText);
      if (text != null) {
        List<String> parts = Arrays.asList(text.split(","));
        setOptions(parts, parts);
      }
      selectedIndex = array.getBoolean(R.styleable.BasicView_basicSelected, false) ? 0 : selectedIndex;
      basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
      setEnabled(!basicDisabled);
    } finally {
      array.recycle();
    }
  }
}
