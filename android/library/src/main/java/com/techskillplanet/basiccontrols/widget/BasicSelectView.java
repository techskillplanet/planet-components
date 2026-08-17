package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.R;
import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基础下拉选择组件。
 *
 * <p>对齐 RN TspSelect：点击触发器打开底部 {@link BasicOptionSheet}，
 * 不再使用 PopupWindow 下拉菜单。</p>
 */
public class BasicSelectView extends TextView {
    /** 选项变化回调。 */
    public interface OnOptionSelectedListener {
        /** 当用户选择某一项时触发。 */
        void onOptionSelected(int index, String option);
    }

    private final List<String> options = new ArrayList<>();
    private int selectedIndex;
    private boolean basicDisabled;
    private OnOptionSelectedListener listener;

    public BasicSelectView(Context context) {
        this(context, null);
    }

    public BasicSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER_VERTICAL);
        setSingleLine(true);
        setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        setClickable(true);
        readAttrs(attrs);
        if (options.isEmpty()) {
            options.addAll(Arrays.asList("全部", "进行中", "已完成"));
        }
        setOnClickListener(view -> {
            if (isEnabled()) {
                showSheet();
            }
        });
        refreshTheme();
    }

    /** 设置下拉选项。 */
    public void setOptions(List<String> values) {
        options.clear();
        if (values != null) {
            options.addAll(values);
        }
        if (selectedIndex >= options.size()) {
            selectedIndex = Math.max(0, options.size() - 1);
        }
        refreshTheme();
    }

    /** 设置选项回调。 */
    public void setOnOptionSelectedListener(OnOptionSelectedListener listener) {
        this.listener = listener;
    }

    /** 设置变体，当前保留统一协议。 */
    public void setVariant(String variant) {
        refreshTheme();
    }

    /** 使用逗号分隔字符串设置选项。 */
    public void setBasicText(CharSequence text) {
        if (text != null) {
            setOptions(Arrays.asList(text.toString().split(",")));
        }
    }

    /** 设置选中下标。 */
    public void setSelectedState(boolean selected) {
        if (selected && !options.isEmpty()) {
            setSelectedIndex(0);
        }
    }

    /** 设置禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        refreshTheme();
    }

    /** 设置当前选中项。 */
    public void setSelectedIndex(int index) {
        if (index < 0 || index >= options.size()) {
            return;
        }
        selectedIndex = index;
        refreshTheme();
        if (listener != null) {
            listener.onOptionSelected(index, options.get(index));
        }
    }

    /** 刷新触发器样式。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        String label = options.isEmpty() ? "请选择" : options.get(selectedIndex);
        setText(label + "  ⌄");
        setTextColor(basicDisabled || !isEnabled() ? colors.textDisabled : colors.textPrimary);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        setMinHeight(Math.round(style.controlHeightLg));
        setPadding(Math.round(style.spaceMd), 0, Math.round(style.spaceMd), 0);
        setBackground(BasicDrawableFactory.roundedFillStroke(
                basicDisabled ? colors.backgroundSurfaceDisabled : colors.backgroundSurfaceRaised,
                colors.borderDefault,
                style.borderHairline,
                style.radiusControlIsland > 0 ? Math.min(style.radiusControlIsland, dp(16)) : dp(16)
        ));
        setAlpha(basicDisabled || !isEnabled() ? 0.45f : 1f);
    }

    /** 展示底部选项面板。 */
    private void showSheet() {
        BasicOptionSheet sheet = new BasicOptionSheet(getContext());
        sheet.setTitleText("请选择");
        sheet.setOptions(new ArrayList<>(options), selectedIndex);
        sheet.setOnOptionSelectedListener((index, option) -> {
            selectedIndex = index;
            refreshTheme();
            if (listener != null) {
                listener.onOptionSelected(index, option);
            }
        });
        sheet.show();
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }

    /** 从 XML 读取选项和状态。 */
    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String text = array.getString(R.styleable.BasicView_basicText);
            if (text != null) {
                options.addAll(Arrays.asList(text.split(",")));
            }
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
