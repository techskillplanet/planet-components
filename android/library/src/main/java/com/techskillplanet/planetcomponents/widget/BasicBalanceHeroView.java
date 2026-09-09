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

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 可用积分英雄区，展示总分与可选拆分明细。
 */
public class BasicBalanceHeroView extends LinearLayout {
    private static final Map<String, String> BREAKDOWN_LABELS = new LinkedHashMap<>();

    static {
        BREAKDOWN_LABELS.put("balance", "余额");
        BREAKDOWN_LABELS.put("ruleScore", "规则分");
        BREAKDOWN_LABELS.put("streakBonus", "连续奖励");
        BREAKDOWN_LABELS.put("redeemTotal", "已兑换");
    }

    private int total;
    private final Map<String, Integer> breakdown = new LinkedHashMap<>();
    private String suffix = "分";
    private String variant = "default";

    public BasicBalanceHeroView(Context context) {
        this(context, null);
    }

    public BasicBalanceHeroView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicBalanceHeroView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        readAttrs(attrs);
        refreshTheme();
    }

    public void setTotal(int total) {
        this.total = total;
        refreshTheme();
    }

    public void setBreakdown(Map<String, Integer> next) {
        breakdown.clear();
        if (next != null) {
            breakdown.putAll(next);
        }
        refreshTheme();
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? "" : suffix;
        refreshTheme();
    }

    public void setVariant(String variant) {
        this.variant = variant == null ? "default" : variant;
        refreshTheme();
    }

    public void setBasicText(CharSequence text) {
        if (text != null) {
            try {
                setTotal(Integer.parseInt(text.toString().trim()));
            } catch (NumberFormatException ignored) {
                setContentDescription(text);
            }
        }
    }

    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    public void setBasicDisabled(boolean disabled) {
        setEnabled(!disabled);
        setAlpha(disabled ? 0.45f : 1f);
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        boolean compact = "compact".equals(variant);
        int pad = Math.round(style.spaceLg);
        setPadding(pad, pad, pad, pad);
        setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderDefault,
                style.borderHairline,
                dp(20)
        ));
        removeAllViews();

        TextView label = new TextView(getContext());
        label.setText("可用积分");
        label.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        label.setTextColor(colors.textSecondary);
        label.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        addView(label);

        LinearLayout totalRow = new LinearLayout(getContext());
        totalRow.setOrientation(HORIZONTAL);
        totalRow.setGravity(Gravity.BOTTOM);
        LayoutParams totalLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        totalLp.topMargin = Math.round(style.spaceSm / 2f);

        TextView totalView = new TextView(getContext());
        totalView.setText(String.format(Locale.CHINA, "%d", total));
        totalView.setTypeface(Typeface.DEFAULT_BOLD);
        totalView.setTextColor(colors.textPrimary);
        totalView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, compact ? 28 : 36);
        totalRow.addView(totalView);

        if (suffix.length() > 0) {
            TextView suffixView = new TextView(getContext());
            suffixView.setText(suffix);
            suffixView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            suffixView.setTextColor(colors.textSecondary);
            suffixView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            LinearLayout.LayoutParams suffixLp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            suffixLp.leftMargin = dp(6);
            totalRow.addView(suffixView, suffixLp);
        }
        addView(totalRow, totalLp);

        if (!breakdown.isEmpty()) {
            LinearLayout bd = new LinearLayout(getContext());
            bd.setOrientation(VERTICAL);
            LayoutParams bdLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            bdLp.topMargin = Math.round(style.spaceMd);
            for (Map.Entry<String, Integer> entry : breakdown.entrySet()) {
                LinearLayout row = new LinearLayout(getContext());
                row.setOrientation(HORIZONTAL);
                row.setGravity(Gravity.CENTER_VERTICAL);
                LayoutParams rowLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                rowLp.bottomMargin = dp(6);

                TextView key = new TextView(getContext());
                String known = BREAKDOWN_LABELS.get(entry.getKey());
                key.setText(known != null ? known : entry.getKey());
                key.setTextColor(colors.textSecondary);
                key.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                row.addView(key, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                TextView value = new TextView(getContext());
                value.setText(String.valueOf(entry.getValue()));
                value.setTypeface(Typeface.DEFAULT_BOLD);
                value.setTextColor(colors.textPrimary);
                value.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                row.addView(value);
                bd.addView(row, rowLp);
            }
            addView(bd, bdLp);
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
            variant = xmlVariant == null ? "default" : xmlVariant;
            String xmlText = array.getString(R.styleable.BasicView_basicText);
            if (xmlText != null) {
                try {
                    total = Integer.parseInt(xmlText.trim());
                } catch (NumberFormatException ignored) {
                    // keep default
                }
            }
        } finally {
            array.recycle();
        }
    }
}
