package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
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
 * 横向基础 Tabs 组件。
 *
 * <p>组件使用 HorizontalScrollView + LinearLayout 实现，避免依赖 Material 或
 * ViewPager。第一版目标是稳定、轻量、可用于传统 Android View 页面。</p>
 */
public class BasicTabsView extends HorizontalScrollView {
    /** Tab 点击回调。 */
    public interface OnTabSelectedListener {
        /** 当用户选择某个 tab 时回调。 */
        void onTabSelected(int index, String title);
    }

    /** 承载所有 tab item 的横向容器。 */
    private final LinearLayout container;
    /** 当前 tab 标题列表。 */
    private final List<String> tabs = new ArrayList<>();
    /** 当前选中的 tab 下标。 */
    private int selectedIndex;
    /** 是否禁用整个 Tabs。 */
    private boolean basicDisabled;
    /** 外部点击监听。 */
    private OnTabSelectedListener listener;

    public BasicTabsView(Context context) {
        this(context, null);
    }

    public BasicTabsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicTabsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);
        container = new LinearLayout(context);
        container.setOrientation(LinearLayout.HORIZONTAL);
        addView(container, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        readAttrs(attrs);
        if (tabs.isEmpty()) {
            tabs.addAll(Arrays.asList("概览", "组件", "主题"));
        }
        refreshTheme();
    }

    /** 设置 tab 标题列表，并重建子 View。 */
    public void setTabs(List<String> titles) {
        tabs.clear();
        if (titles != null) {
            tabs.addAll(titles);
        }
        if (selectedIndex >= tabs.size()) {
            selectedIndex = Math.max(0, tabs.size() - 1);
        }
        refreshTheme();
    }

    /** 设置 tab 选择监听。 */
    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        this.listener = listener;
    }

    /** Tabs 的 variant 第一版不改变样式，保留统一组件协议。 */
    public void setVariant(String variant) {
        refreshTheme();
    }

    /** 使用逗号分隔文本快速设置 tabs，例如 "首页,组件,主题"。 */
    public void setBasicText(CharSequence text) {
        if (text == null) {
            setTabs(new ArrayList<>());
            return;
        }
        setTabs(Arrays.asList(text.toString().split(",")));
    }

    /** 统一组件协议：true 选中第一个 tab，false 不改变当前下标。 */
    public void setSelectedState(boolean selected) {
        if (selected && !tabs.isEmpty()) {
            setSelectedIndex(0);
        }
    }

    /** 设置整体禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        refreshTheme();
    }

    /** 设置当前选中的 tab 下标。 */
    public void setSelectedIndex(int index) {
        if (index < 0 || index >= tabs.size()) {
            return;
        }
        selectedIndex = index;
        refreshTheme();
        if (listener != null) {
            listener.onTabSelected(index, tabs.get(index));
        }
    }

    /** 重新绘制全部 tab item。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        container.removeAllViews();
        container.setPadding(Math.round(style.spaceSm), Math.round(style.spaceSm),
                Math.round(style.spaceSm), Math.round(style.spaceSm));
        container.setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceSubtle,
                colors.borderLight,
                style.borderHairline,
                style.radiusPill
        ));

        for (int i = 0; i < tabs.size(); i++) {
            TextView item = createTabItem(i, tabs.get(i), colors, style);
            container.addView(item);
        }
    }

    /** 创建单个 tab item，并将 selected/disabled 状态映射到 token。 */
    private TextView createTabItem(int index, String title, BasicColors colors, BasicStyle style) {
        TextView item = new TextView(getContext());
        boolean selected = index == selectedIndex;
        int fill = selected ? colors.brandPrimary : colors.backgroundSurfaceSubtle;
        int text = selected ? colors.textInverse : colors.textSecondary;
        if (basicDisabled || !isEnabled()) {
            fill = colors.backgroundSurfaceDisabled;
            text = colors.textDisabled;
        }
        item.setText(title);
        item.setGravity(Gravity.CENTER);
        item.setTypeface(Typeface.DEFAULT, selected ? Typeface.BOLD : Typeface.NORMAL);
        item.setTextColor(text);
        item.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        item.setMinHeight(Math.round(style.tabHeight));
        item.setPadding(Math.round(style.spaceMd), 0, Math.round(style.spaceMd), 0);
        item.setBackground(BasicDrawableFactory.roundedFill(fill, style.radiusPill));
        item.setEnabled(!basicDisabled && isEnabled());
        item.setOnClickListener(view -> setSelectedIndex(index));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Math.round(style.tabHeight)
        );
        params.setMargins(0, 0, Math.round(style.spaceSm), 0);
        item.setLayoutParams(params);
        return item;
    }

    /** 从 XML 读取文本、选中态和禁用态。 */
    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String xmlText = array.getString(R.styleable.BasicView_basicText);
            if (xmlText != null) {
                tabs.addAll(Arrays.asList(xmlText.split(",")));
            }
            selectedIndex = array.getBoolean(R.styleable.BasicView_basicSelected, false) ? 0 : selectedIndex;
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
