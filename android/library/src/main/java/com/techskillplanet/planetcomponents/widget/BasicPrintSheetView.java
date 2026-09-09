package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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
 * A4 风格听写 / 填空打印版面。
 */
public class BasicPrintSheetView extends LinearLayout {
    private String title = "";
    private final List<String> items = new ArrayList<>();
    private int columns = 5;
    private final List<String> footerFields = new ArrayList<>(Arrays.asList("姓名", "日期", "得分"));
    private String variant = "pinyin";
    private int lastWidth;

    public BasicPrintSheetView(Context context) {
        this(context, null);
    }

    public BasicPrintSheetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicPrintSheetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        readAttrs(attrs);
        refreshTheme();
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
        refreshTheme();
    }

    public void setItems(List<String> next) {
        items.clear();
        if (next != null) {
            items.addAll(next);
        }
        refreshTheme();
    }

    public void setColumns(int columns) {
        this.columns = Math.max(1, columns);
        refreshTheme();
    }

    public void setFooterFields(List<String> fields) {
        footerFields.clear();
        if (fields != null) {
            footerFields.addAll(fields);
        }
        refreshTheme();
    }

    public void setVariant(String variant) {
        this.variant = variant == null ? "pinyin" : variant;
        refreshTheme();
    }

    public void setBasicText(CharSequence text) {
        setTitle(text == null ? "" : text.toString());
    }

    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    public void setBasicDisabled(boolean disabled) {
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
        boolean meaning = "meaning".equals(variant);
        int pad = Math.round(style.spaceMd);
        setPadding(pad, pad, pad, pad);
        setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderDefault,
                style.borderHairline,
                style.radiusLg
        ));
        removeAllViews();

        if (title.length() > 0) {
            TextView titleView = new TextView(getContext());
            titleView.setText(title);
            titleView.setGravity(Gravity.CENTER);
            titleView.setTypeface(Typeface.DEFAULT_BOLD);
            titleView.setTextColor(colors.textPrimary);
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            LayoutParams titleLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            titleLp.bottomMargin = Math.round(style.spaceMd);
            addView(titleView, titleLp);
        }

        int cols = Math.max(1, columns);
        int gap = dp(6);
        int width = Math.max(getWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredWidth());
        int cellWidth = width > 0 ? Math.max(dp(48), (width - gap * (cols - 1)) / cols) : dp(56);

        LinearLayout row = null;
        for (int i = 0; i < items.size(); i++) {
            if (i % cols == 0) {
                row = new LinearLayout(getContext());
                row.setOrientation(HORIZONTAL);
                LayoutParams rowLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i > 0) {
                    rowLp.topMargin = gap;
                }
                addView(row, rowLp);
            }
            LinearLayout cell = new LinearLayout(getContext());
            cell.setOrientation(VERTICAL);
            cell.setPadding(dp(4), dp(4), dp(4), dp(4));
            cell.setMinimumHeight(dp(52));
            cell.setBackground(BasicDrawableFactory.roundedFillStroke(
                    Color.TRANSPARENT,
                    0xFF333333,
                    1f,
                    0f
            ));

            TextView prompt = new TextView(getContext());
            prompt.setText(items.get(i));
            prompt.setGravity(Gravity.CENTER);
            prompt.setTextColor(colors.textSecondary);
            prompt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, meaning ? 12 : 11);
            if (meaning) {
                prompt.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            }
            cell.addView(prompt);

            TextView blank = new TextView(getContext());
            blank.setMinHeight(meaning ? dp(28) : dp(22));
            LayoutParams blankLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            blankLp.topMargin = dp(6);
            // 用细边框模拟填空线区域
            blank.setBackground(BasicDrawableFactory.roundedFillStroke(
                    Color.TRANSPARENT,
                    0xFF999999,
                    1f,
                    0f
            ));
            cell.addView(blank, blankLp);

            LayoutParams cellLp = new LayoutParams(cellWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i % cols > 0) {
                cellLp.leftMargin = gap;
            }
            row.addView(cell, cellLp);
        }

        if (!footerFields.isEmpty()) {
            LinearLayout footer = new LinearLayout(getContext());
            footer.setOrientation(HORIZONTAL);
            footer.setGravity(Gravity.CENTER_VERTICAL);
            LayoutParams footerLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerLp.topMargin = Math.round(style.spaceMd);
            for (int i = 0; i < footerFields.size(); i++) {
                TextView field = new TextView(getContext());
                field.setText(footerFields.get(i) + "：________");
                field.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                field.setTextColor(colors.textPrimary);
                field.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                LayoutParams fieldLp = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                footer.addView(field, fieldLp);
            }
            addView(footer, footerLp);
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
            variant = xmlVariant == null ? "pinyin" : xmlVariant;
            String xmlText = array.getString(R.styleable.BasicView_basicText);
            if (xmlText != null) {
                title = xmlText;
            }
        } finally {
            array.recycle();
        }
    }
}
