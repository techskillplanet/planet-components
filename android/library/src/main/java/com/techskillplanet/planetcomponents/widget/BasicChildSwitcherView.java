package com.techskillplanet.planetcomponents.widget;

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

import com.techskillplanet.planetcomponents.R;
import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 单选儿童/对象切换器，支持 chip / tabs 变体。
 */
public class BasicChildSwitcherView extends HorizontalScrollView {
    /** 选项变更回调。 */
    public interface OnChildChangeListener {
        /** @param id 选中项 id。 */
        void onChildChange(String id);
    }

    /** 切换项数据。 */
    public static final class Item {
        public final String id;
        public final String label;
        public final String emoji;

        public Item(String id, String label) {
            this(id, label, null);
        }

        public Item(String id, String label, String emoji) {
            this.id = id == null ? "" : id;
            this.label = label == null ? "" : label;
            this.emoji = emoji;
        }
    }

    private final LinearLayout container;
    private final List<Item> items = new ArrayList<>();
    private String selectedId = "";
    private String variant = "chip";
    private boolean basicDisabled;
    private OnChildChangeListener listener;

    public BasicChildSwitcherView(Context context) {
        this(context, null);
    }

    public BasicChildSwitcherView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicChildSwitcherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);
        container = new LinearLayout(context);
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setGravity(Gravity.CENTER_VERTICAL);
        addView(container, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        readAttrs(attrs);
        refreshTheme();
    }

    /** 设置选项列表。 */
    public void setItems(List<Item> next) {
        items.clear();
        if (next != null) {
            items.addAll(next);
        }
        refreshTheme();
    }

    /** 设置当前选中 id。 */
    public void setSelectedId(String selectedId) {
        this.selectedId = selectedId == null ? "" : selectedId;
        refreshTheme();
    }

    /** 设置选择监听。 */
    public void setOnChildChangeListener(OnChildChangeListener listener) {
        this.listener = listener;
    }

    /** 设置变体：chip / tabs。 */
    public void setVariant(String variant) {
        this.variant = variant == null ? "chip" : variant;
        refreshTheme();
    }

    /** 保留统一协议。 */
    public void setBasicText(CharSequence text) {
        setContentDescription(text);
    }

    /** true 时选中第一项。 */
    public void setSelectedState(boolean selected) {
        if (selected && !items.isEmpty()) {
            setSelectedId(items.get(0).id);
        }
    }

    /** 设置禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        refreshTheme();
    }

    /** 重建子项并应用主题。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        boolean useTabs = "tabs".equals(variant);
        container.removeAllViews();
        int gap = Math.round(style.spaceSm);
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            boolean selected = String.valueOf(item.id).equals(String.valueOf(selectedId));
            TextView chip = new TextView(getContext());
            String label = item.emoji != null && item.emoji.length() > 0
                    ? item.emoji + " " + item.label
                    : item.label;
            chip.setText(label);
            chip.setSingleLine(true);
            chip.setGravity(Gravity.CENTER);
            chip.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            chip.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
            chip.setMinHeight(dp(44));
            int padH = Math.round(style.spaceMd);
            chip.setPadding(padH, 0, padH, 0);
            chip.setTextColor(selected ? colors.textInverse : colors.textSecondary);
            chip.setBackground(BasicDrawableFactory.roundedFillStroke(
                    selected ? colors.brandPrimary : colors.backgroundSurfaceRaised,
                    selected ? colors.brandPrimary : colors.borderDefault,
                    style.borderHairline,
                    useTabs ? style.radiusMd : style.radiusPill
            ));
            chip.setAlpha(basicDisabled || !isEnabled() ? 0.45f : 1f);
            chip.setEnabled(!basicDisabled);
            chip.setClickable(!basicDisabled);
            final String id = item.id;
            chip.setOnClickListener(v -> {
                if (basicDisabled) {
                    return;
                }
                setSelectedId(id);
                if (listener != null) {
                    listener.onChildChange(id);
                }
            });
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    useTabs ? 0 : ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            if (useTabs) {
                lp.weight = 1f;
            }
            if (i > 0) {
                lp.leftMargin = gap;
            }
            container.addView(chip, lp);
        }
        ViewGroup.LayoutParams containerLp = container.getLayoutParams();
        if (containerLp != null) {
            containerLp.width = useTabs
                    ? ViewGroup.LayoutParams.MATCH_PARENT
                    : ViewGroup.LayoutParams.WRAP_CONTENT;
            container.setLayoutParams(containerLp);
        }
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
            String xmlVariant = array.getString(R.styleable.BasicView_basicVariant);
            variant = xmlVariant == null ? "chip" : xmlVariant;
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
