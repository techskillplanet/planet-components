package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.i18n.BasicI18nManager;
import com.techskillplanet.planetcomponents.system.BasicEdgeToEdgeHelper;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部 Tab，对齐 Web/RN TspBottomTab：
 * 顶部分割线、选中「贴纸岛」（圆角渐变 + inset 描边）、选中色 brandDark、标签加粗。
 */
public class BasicBottomTabView extends LinearLayout {
    public interface OnTabSelectedListener {
        void onTabSelected(BasicBottomTabView view, int index, String key);
    }

    private final View topBorder;
    private final LinearLayout contentRow;
    private final List<Tab> tabs = new ArrayList<>();
    private int selectedIndex;
    private int navigationBarInset;
    private OnTabSelectedListener listener;

    public BasicBottomTabView(Context context) {
        this(context, null);
    }

    public BasicBottomTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicBottomTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        topBorder = new View(context);
        contentRow = new LinearLayout(context);
        contentRow.setOrientation(HORIZONTAL);
        contentRow.setGravity(Gravity.CENTER);
        addView(topBorder, new LayoutParams(LayoutParams.MATCH_PARENT, dp(1)));
        addView(contentRow, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        refreshTheme();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            requestApplyInsets();
            setOnApplyWindowInsetsListener(this::onApplyWindowInsetsToBottomTab);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            setOnApplyWindowInsetsListener(null);
        }
        super.onDetachedFromWindow();
    }

    private android.view.WindowInsets onApplyWindowInsetsToBottomTab(
            View view,
            android.view.WindowInsets insets
    ) {
        int inset = BasicEdgeToEdgeHelper.getNavigationBarInset(view, insets);
        if (inset != navigationBarInset) {
            navigationBarInset = inset;
            applyContainerPadding();
        }
        return insets;
    }

    private void applyContainerPadding() {
        BasicStyle style = BasicThemeManager.style();
        if (style == null) {
            return;
        }
        // 对齐 Web --sp-chrome-gap：首尾选中岛不贴屏边
        int chrome = Math.max(dp(10), Math.round(style.spaceMd));
        contentRow.setPadding(chrome, dp(6), chrome, dp(6));
        contentRow.setMinimumHeight(dp(56));
        setPadding(0, 0, 0, navigationBarInset);
    }

    public void setTabs(List<Tab> items) {
        tabs.clear();
        if (items != null) {
            tabs.addAll(items);
        }
        render();
    }

    public void setSelectedIndex(int index) {
        selectedIndex = Math.max(0, Math.min(index, Math.max(0, tabs.size() - 1)));
        refreshSelection();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        this.listener = listener;
    }

    public void refreshText() {
        render();
    }

    public void refreshTheme() {
        if (BasicThemeManager.colors() == null) {
            return;
        }
        BasicColors colors = BasicThemeManager.colors();
        setBackgroundColor(colors.backgroundSurfaceRaised);
        topBorder.setBackgroundColor(colors.borderDefault);
        render();
    }

    private void render() {
        contentRow.removeAllViews();
        applyContainerPadding();
        for (int i = 0; i < tabs.size(); i++) {
            final int index = i;
            Tab tab = tabs.get(i);
            LinearLayout item = new LinearLayout(getContext());
            item.setOrientation(VERTICAL);
            item.setGravity(Gravity.CENTER);
            item.setClickable(true);
            item.setFocusable(true);
            item.setPadding(dp(4), dp(6), dp(4), dp(6));
            item.setOnClickListener(view -> {
                if (selectedIndex != index) {
                    selectedIndex = index;
                    refreshSelection();
                }
                if (listener != null) {
                    listener.onTabSelected(this, index, tab.key);
                }
            });

            View icon;
            if (tab.iconResId != 0) {
                ImageView iconView = new ImageView(getContext());
                iconView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                Drawable drawable = loadDrawable(tab.iconResId);
                if (drawable != null) {
                    iconView.setImageDrawable(drawable.mutate());
                }
                icon = iconView;
            } else {
                TextView iconView = new TextView(getContext());
                iconView.setGravity(Gravity.CENTER);
                iconView.setText(tab.icon);
                iconView.setIncludeFontPadding(false);
                icon = iconView;
            }
            item.addView(icon, new LayoutParams(LayoutParams.MATCH_PARENT, dp(22)));

            TextView label = new TextView(getContext());
            label.setGravity(Gravity.CENTER);
            label.setSingleLine(true);
            label.setIncludeFontPadding(false);
            label.setTypeface(Typeface.DEFAULT_BOLD);
            label.setText(BasicI18nManager.text(tab.textKey, tab.fallback));
            LayoutParams labelLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            labelLp.topMargin = dp(2);
            item.addView(label, labelLp);

            contentRow.addView(item, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
        }
        refreshSelection();
    }

    private void refreshSelection() {
        BasicColors colors = BasicThemeManager.colors();
        if (colors == null) {
            return;
        }
        int selectedColor = colors.brandDark != 0 ? colors.brandDark : colors.brandPrimary;
        int idleColor = colors.textTertiary;
        for (int i = 0; i < contentRow.getChildCount(); i++) {
            View child = contentRow.getChildAt(i);
            boolean selected = i == selectedIndex;
            child.setSelected(selected);
            if (selected) {
                child.setBackground(createSelectedIsland(colors));
            } else {
                child.setBackground(null);
            }
            if (!(child instanceof LinearLayout)) {
                continue;
            }
            LinearLayout item = (LinearLayout) child;
            if (item.getChildCount() < 2) {
                continue;
            }
            View icon = item.getChildAt(0);
            TextView label = (TextView) item.getChildAt(1);
            int textColor = selected ? selectedColor : idleColor;
            if (icon instanceof TextView) {
                TextView iconText = (TextView) icon;
                iconText.setTextColor(textColor);
                iconText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            } else if (icon instanceof ImageView) {
                tintDrawable(((ImageView) icon).getDrawable(), textColor);
            }
            label.setTextColor(textColor);
            label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            label.setTypeface(Typeface.DEFAULT_BOLD);
            // 选中轻微上浮，对齐 Web icon pop
            icon.setTranslationY(selected ? -dp(1) : 0);
            icon.setScaleX(selected ? 1.08f : 1f);
            icon.setScaleY(selected ? 1.08f : 1f);
        }
    }

    /** Web 贴纸岛：白→主色浅竖向渐变 + inset 主色描边。 */
    private Drawable createSelectedIsland(BasicColors colors) {
        int soft = colors.brandPrimarySubtle != 0
                ? colors.brandPrimarySubtle
                : withAlpha(colors.brandPrimary, 26);
        GradientDrawable g = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{Color.WHITE, soft}
        );
        g.setCornerRadius(dp(16));
        g.setStroke(Math.max(1, dp(1.5f)), withAlpha(colors.brandPrimary, 107));
        return g;
    }

    private static int withAlpha(int color, int alpha) {
        return (color & 0x00FFFFFF) | ((alpha & 0xFF) << 24);
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }

    private Drawable loadDrawable(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getContext().getDrawable(resId);
        }
        return getResources().getDrawable(resId);
    }

    private void tintDrawable(Drawable drawable, int color) {
        if (drawable == null) {
            return;
        }
        Drawable d = drawable.mutate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            d.setTint(color);
        }
    }

    public static final class Tab {
        public final String key;
        public final String icon;
        public final int iconResId;
        public final String textKey;
        public final String fallback;

        public Tab(String key, String icon, String textKey, String fallback) {
            this(key, icon, 0, textKey, fallback);
        }

        public Tab(String key, int iconResId, String textKey, String fallback) {
            this(key, "", iconResId, textKey, fallback);
        }

        public Tab(String key, String icon, int iconResId, String textKey, String fallback) {
            this.key = key;
            this.icon = icon == null ? "" : icon;
            this.iconResId = iconResId;
            this.textKey = textKey;
            this.fallback = fallback;
        }
    }
}
