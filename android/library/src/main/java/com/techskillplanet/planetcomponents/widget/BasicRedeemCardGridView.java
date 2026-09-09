package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.R;
import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 兑换商品卡片网格。
 */
public class BasicRedeemCardGridView extends LinearLayout {
    /** 兑换回调。 */
    public interface OnRedeemListener {
        void onRedeem(Item item);
    }

    /** 兑换项。 */
    public static final class Item {
        public final String id;
        public final String name;
        public final String icon;
        public final int cost;

        public Item(String id, String name, String icon, int cost) {
            this.id = id;
            this.name = name == null ? "" : name;
            this.icon = icon == null || icon.length() == 0 ? "🎁" : icon;
            this.cost = cost;
        }
    }

    private final List<Item> items = new ArrayList<>();
    private int availablePoints;
    private boolean frozen;
    private boolean basicDisabled;
    private OnRedeemListener listener;
    private int lastWidth;

    public BasicRedeemCardGridView(Context context) {
        this(context, null);
    }

    public BasicRedeemCardGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicRedeemCardGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        readAttrs(attrs);
        refreshTheme();
    }

    public void setItems(List<Item> next) {
        items.clear();
        if (next != null) {
            items.addAll(next);
        }
        refreshTheme();
    }

    public void setAvailablePoints(int availablePoints) {
        this.availablePoints = availablePoints;
        refreshTheme();
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
        refreshTheme();
    }

    public void setOnRedeemListener(OnRedeemListener listener) {
        this.listener = listener;
    }

    public void setVariant(String variant) {
        refreshTheme();
    }

    public void setBasicText(CharSequence text) {
        setContentDescription(text);
    }

    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        refreshTheme();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && w != lastWidth) {
            lastWidth = w;
            refreshTheme();
        }
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int colCount = getWidth() > 0 && getWidth() <= dp(480) ? 1 : 2;
        int gap = Math.round(style.spaceMd);
        removeAllViews();

        if (frozen) {
            TextView banner = new TextView(getContext());
            banner.setText("今日已冻结，暂不可兑换");
            banner.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            banner.setTextColor(colors.textPrimary);
            banner.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
            int pad = Math.round(style.spaceMd);
            banner.setPadding(pad, Math.round(style.spaceSm), pad, Math.round(style.spaceSm));
            banner.setBackground(BasicDrawableFactory.roundedFill(colors.activeFill, style.radiusMd));
            addView(banner, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        LinearLayout row = null;
        for (int i = 0; i < items.size(); i++) {
            if (i % colCount == 0) {
                row = new LinearLayout(getContext());
                row.setOrientation(HORIZONTAL);
                LayoutParams rowLp = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                rowLp.topMargin = gap;
                addView(row, rowLp);
            }
            Item item = items.get(i);
            LinearLayout card = buildCard(item, colors, style);
            LayoutParams cardLp = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            if (i % colCount > 0) {
                cardLp.leftMargin = gap;
            }
            row.addView(card, cardLp);
        }
        if (row != null && items.size() % colCount != 0) {
            int remain = colCount - (items.size() % colCount);
            for (int i = 0; i < remain; i++) {
                LayoutParams spacerLp = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                spacerLp.leftMargin = gap;
                row.addView(new android.view.View(getContext()), spacerLp);
            }
        }
    }

    private LinearLayout buildCard(Item item, BasicColors colors, BasicStyle style) {
        boolean insufficient = availablePoints < item.cost;
        boolean blocked = frozen || basicDisabled || insufficient;
        LinearLayout card = new LinearLayout(getContext());
        card.setOrientation(VERTICAL);
        int pad = Math.round(style.spaceMd + 2);
        card.setPadding(pad, pad, pad, pad);
        card.setMinimumHeight(dp(120));
        card.setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderDefault,
                style.borderDefault,
                style.radiusCardOrganic
        ));
        card.setAlpha(blocked ? 0.5f : 1f);
        card.setClickable(!blocked);
        card.setEnabled(!blocked);
        card.setOnClickListener(v -> {
            if (!blocked && listener != null) {
                listener.onRedeem(item);
            }
        });

        TextView icon = new TextView(getContext());
        icon.setText(item.icon);
        icon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);
        card.addView(icon);

        TextView name = new TextView(getContext());
        name.setText(item.name);
        name.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        name.setTextColor(colors.textPrimary);
        LayoutParams nameLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nameLp.topMargin = Math.round(style.spaceSm);
        card.addView(name, nameLp);

        TextView cost = new TextView(getContext());
        cost.setText(String.format(Locale.CHINA, "%d 分", item.cost));
        cost.setTypeface(Typeface.DEFAULT_BOLD);
        cost.setTextColor(colors.brandPrimary);
        LayoutParams costLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        costLp.topMargin = Math.round(style.spaceSm / 2f);
        card.addView(cost, costLp);

        if (insufficient && !frozen) {
            TextView hint = new TextView(getContext());
            hint.setText("积分不足");
            hint.setTextColor(colors.statusDanger);
            hint.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            LayoutParams hintLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            hintLp.topMargin = Math.round(style.spaceSm / 2f);
            card.addView(hint, hintLp);
        }
        return card;
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
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
