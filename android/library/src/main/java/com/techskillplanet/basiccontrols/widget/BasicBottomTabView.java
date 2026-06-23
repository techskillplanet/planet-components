package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.i18n.BasicI18nManager;
import com.techskillplanet.basiccontrols.system.BasicEdgeToEdgeHelper;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 常见移动 App 底部 Tab 容器，适合 3-5 个一级页面，也兼容 MVP 阶段的 2 个页面。
 */
public class BasicBottomTabView extends LinearLayout {
    public interface OnTabSelectedListener {
        void onTabSelected(BasicBottomTabView view, int index, String key);
    }

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
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
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
        setPadding(
                Math.round(style.spaceMd),
                Math.round(style.spaceSm),
                Math.round(style.spaceMd),
                Math.round(style.spaceSm) + navigationBarInset
        );
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
        render();
    }

    private void render() {
        removeAllViews();
        applyContainerPadding();
        BasicStyle style = BasicThemeManager.style();
        for (int i = 0; i < tabs.size(); i++) {
            final int index = i;
            Tab tab = tabs.get(i);
            LinearLayout item = new LinearLayout(getContext());
            item.setOrientation(VERTICAL);
            item.setGravity(Gravity.CENTER);
            item.setClickable(true);
            item.setFocusable(true);
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
            label.setText(BasicI18nManager.text(tab.textKey, tab.fallback));
            item.addView(label, new LayoutParams(LayoutParams.MATCH_PARENT, dp(22)));

            LayoutParams params = new LayoutParams(0, dp(52), 1f);
            params.setMargins(dp(3), 0, dp(3), 0);
            addView(item, params);
        }
        refreshSelection();
    }

    private void refreshSelection() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            boolean selected = i == selectedIndex;
            child.setSelected(selected);
            child.setBackground(BasicDrawableFactory.roundedFill(
                    selected ? colors.brandPrimarySubtle : android.graphics.Color.TRANSPARENT,
                    style.radiusLg
            ));
            LinearLayout item = (LinearLayout) child;
            View icon = item.getChildAt(0);
            TextView label = (TextView) item.getChildAt(1);
            int textColor = selected ? colors.brandPrimaryActive : colors.textTertiary;
            if (icon instanceof TextView) {
                TextView iconText = (TextView) icon;
                iconText.setTextColor(textColor);
                iconText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            } else if (icon instanceof ImageView) {
                Drawable drawable = ((ImageView) icon).getDrawable();
                tintDrawable(drawable, textColor);
            }
            label.setTextColor(textColor);
            label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            label.setTypeface(Typeface.DEFAULT, selected ? Typeface.BOLD : Typeface.NORMAL);
        }
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable.setTint(color);
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
