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
import java.util.List;
import java.util.Locale;

/**
 * 积分规则卡片网格，支持 +1 操作。
 */
public class BasicScoreRuleGridView extends LinearLayout {
    /** +1 回调。 */
    public interface OnIncrementListener {
        void onIncrement(Rule rule);
    }

    /** 规则项。 */
    public static final class Rule {
        public final String id;
        public final String name;
        public final String icon;
        public final int value;
        public final int count;
        public final Integer dailyLimit;

        public Rule(String id, String name, String icon, int value, int count, Integer dailyLimit) {
            this.id = id;
            this.name = name == null ? "" : name;
            this.icon = icon;
            this.value = value;
            this.count = count;
            this.dailyLimit = dailyLimit;
        }
    }

    private final List<Rule> rules = new ArrayList<>();
    private String columns = "auto";
    private String variant = "default";
    private boolean basicDisabled;
    private OnIncrementListener listener;
    private int lastWidth;

    public BasicScoreRuleGridView(Context context) {
        this(context, null);
    }

    public BasicScoreRuleGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicScoreRuleGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        readAttrs(attrs);
        refreshTheme();
    }

    /** 设置规则列表。 */
    public void setRules(List<Rule> next) {
        rules.clear();
        if (next != null) {
            rules.addAll(next);
        }
        refreshTheme();
    }

    /** 设置列数：1 / 2 / auto。 */
    public void setColumns(String columns) {
        this.columns = columns == null ? "auto" : columns;
        refreshTheme();
    }

    public void setOnIncrementListener(OnIncrementListener listener) {
        this.listener = listener;
    }

    public void setVariant(String variant) {
        this.variant = variant == null ? "default" : variant;
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
        boolean readOnly = "readOnly".equals(variant) || basicDisabled;
        int colCount = resolveColumns(getWidth());
        int gap = Math.round(style.spaceMd);
        removeAllViews();

        LinearLayout row = null;
        for (int i = 0; i < rules.size(); i++) {
            if (i % colCount == 0) {
                row = new LinearLayout(getContext());
                row.setOrientation(HORIZONTAL);
                LayoutParams rowLp = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                if (i > 0) {
                    rowLp.topMargin = gap;
                }
                addView(row, rowLp);
            }
            Rule rule = rules.get(i);
            LinearLayout card = buildCard(rule, colors, style, readOnly);
            LayoutParams cardLp = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            if (i % colCount > 0) {
                cardLp.leftMargin = gap;
            }
            row.addView(card, cardLp);
        }
        // 补齐最后一行空位，保证双列对齐
        if (row != null && rules.size() % colCount != 0) {
            int remain = colCount - (rules.size() % colCount);
            for (int i = 0; i < remain; i++) {
                LayoutParams spacerLp = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                spacerLp.leftMargin = gap;
                row.addView(new android.view.View(getContext()), spacerLp);
            }
        }
        setAlpha(basicDisabled || !isEnabled() ? 0.45f : 1f);
    }

    private LinearLayout buildCard(Rule rule, BasicColors colors, BasicStyle style, boolean readOnly) {
        LinearLayout card = new LinearLayout(getContext());
        card.setOrientation(VERTICAL);
        int pad = Math.round(style.spaceMd);
        card.setPadding(pad, pad, pad, pad);
        card.setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderDefault,
                style.borderHairline,
                style.radiusCardOrganic
        ));

        LinearLayout head = new LinearLayout(getContext());
        head.setOrientation(HORIZONTAL);
        head.setGravity(Gravity.CENTER_VERTICAL);
        if (rule.icon != null && rule.icon.length() > 0) {
            TextView icon = new TextView(getContext());
            icon.setText(rule.icon);
            head.addView(icon);
        }
        TextView name = new TextView(getContext());
        name.setText(rule.name);
        name.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        name.setTextColor(colors.textPrimary);
        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        name.setSingleLine(true);
        LinearLayout.LayoutParams nameLp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        nameLp.leftMargin = rule.icon != null && rule.icon.length() > 0 ? Math.round(style.spaceSm) : 0;
        head.addView(name, nameLp);
        card.addView(head);

        TextView valueView = new TextView(getContext());
        valueView.setText(rule.value > 0 ? "+" + rule.value : String.valueOf(rule.value));
        valueView.setTypeface(Typeface.DEFAULT_BOLD);
        valueView.setTextColor(rule.value >= 0 ? colors.statusSuccess : colors.statusDanger);
        valueView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        LayoutParams valueLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        valueLp.topMargin = Math.round(style.spaceSm);
        card.addView(valueView, valueLp);

        TextView meta = new TextView(getContext());
        meta.setText(rule.dailyLimit == null
                ? String.format(Locale.CHINA, "已记 %d", rule.count)
                : String.format(Locale.CHINA, "已记 %d/%d", rule.count, rule.dailyLimit));
        meta.setTextColor(colors.textTertiary);
        meta.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        card.addView(meta);

        if (!readOnly) {
            boolean atLimit = rule.dailyLimit != null && rule.count >= rule.dailyLimit;
            TextView add = new TextView(getContext());
            add.setText("+1");
            add.setGravity(Gravity.CENTER);
            add.setTypeface(Typeface.DEFAULT_BOLD);
            add.setTextColor(colors.brandDark);
            add.setMinHeight(dp(44));
            add.setBackground(BasicDrawableFactory.roundedFillStroke(
                    colors.selectedFill,
                    colors.borderDefault,
                    style.borderHairline,
                    style.radiusPill
            ));
            add.setEnabled(!atLimit && !basicDisabled);
            add.setAlpha(atLimit || basicDisabled ? 0.4f : 1f);
            add.setClickable(!atLimit && !basicDisabled);
            add.setOnClickListener(v -> {
                if (listener != null && !atLimit && !basicDisabled) {
                    listener.onIncrement(rule);
                }
            });
            LayoutParams addLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            addLp.topMargin = Math.round(style.spaceSm);
            card.addView(add, addLp);
        }
        return card;
    }

    private int resolveColumns(int widthPx) {
        if ("1".equals(columns)) {
            return 1;
        }
        if ("2".equals(columns)) {
            return 2;
        }
        if (widthPx > 0 && widthPx < dp(481)) {
            return 1;
        }
        return 2;
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
            variant = xmlVariant == null ? "default" : xmlVariant;
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
