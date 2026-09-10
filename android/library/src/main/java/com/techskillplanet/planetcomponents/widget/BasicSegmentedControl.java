package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
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
 * 等宽分段选择器。
 *
 * <p>默认交互对齐主流 App（iOS Settings / 微信切换轨）：
 * 软底轨道 + 白色选中拇指 + 品牌深色字，避免整块实心品牌色「油漆桶」感。</p>
 */
public class BasicSegmentedControl extends LinearLayout {

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
    setSelectedIndex(index, true);
  }

  /** @param notify 是否回调监听（重建主题时勿重复通知）。 */
  public void setSelectedIndex(int index, boolean notify) {
    if (index < 0 || index >= labels.size()) {
      return;
    }
    boolean changed = selectedIndex != index;
    selectedIndex = index;
    refreshTheme();
    if (notify && changed && listener != null) {
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

    float radius = style.radiusLg > 0 ? style.radiusLg : Math.min(style.radiusPill, dp(14));
    // 软轨：浅底 + 轻描边（非白底硬边）
    int trackFill = colors.backgroundSurfaceSubtle != 0
        ? colors.backgroundSurfaceSubtle
        : colors.backgroundSurface;
    setBackground(BasicDrawableFactory.roundedFillStroke(
        trackFill,
        colors.borderLight,
        style.borderHairline,
        radius
    ));
    int inset = Math.max(2, Math.round(style.spaceSm > 0 ? style.spaceSm / 2f : 3));
    setPadding(inset, inset, inset, inset);

    int count = labels.size();
    for (int i = 0; i < count; i++) {
      TextView item = createSegmentItem(i, colors, style, radius, inset);
      LayoutParams params = new LayoutParams(0, Math.round(style.controlHeightMd > 0
          ? style.controlHeightMd
          : style.controlHeightLg - 4), 1f);
      addView(item, params);
    }
  }

  private TextView createSegmentItem(
      int index,
      BasicColors colors,
      BasicStyle style,
      float trackRadius,
      int inset
  ) {
    TextView item = new TextView(getContext());
    boolean selected = index == selectedIndex;
    int textColor = selected
        ? (colors.brandDark != 0 ? colors.brandDark : colors.brandPrimary)
        : colors.textSecondary;
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
      float thumbRadius = Math.max(0, trackRadius - inset);
      // 白拇指：主流分段控件选中态（非实心品牌色）
      item.setBackground(BasicDrawableFactory.roundedFillStroke(
          colors.backgroundSurfaceRaised != 0 ? colors.backgroundSurfaceRaised : colors.backgroundSurface,
          colors.borderLight,
          style.borderHairline,
          thumbRadius
      ));
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        item.setElevation(dp(1.5f));
      }
    } else {
      item.setBackground(null);
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        item.setElevation(0f);
      }
    }

    item.setEnabled(!basicDisabled && isEnabled());
    final int tapIndex = index;
    item.setOnClickListener(view -> setSelectedIndex(tapIndex, true));
    return item;
  }

  private int dp(float value) {
    return Math.round(TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value,
        getResources().getDisplayMetrics()
    ));
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
