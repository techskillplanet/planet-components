package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.R;
import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 月度打卡热力图（周一为一周起始）。
 */
public class BasicCalendarHeatmapView extends LinearLayout {
    private static final String[] WEEKDAYS = {"一", "二", "三", "四", "五", "六", "日"};
    private static final String[][] LEVELS = {
            {"full", "全勤"},
            {"partial", "部分"},
            {"none", "未打"},
            {"exempt", "豁免"}
    };

    /** 日期点击回调。 */
    public interface OnDaySelectListener {
        void onSelectDay(String date, String level);
    }

    /** 单元格数据。 */
    public static final class Cell {
        public final String date;
        public final String level;

        public Cell(String date, String level) {
            this.date = date == null ? "" : date;
            this.level = level == null || level.length() == 0 ? "none" : level;
        }
    }

    private String yearMonth = "";
    private final List<Cell> cells = new ArrayList<>();
    private boolean showLegend = true;
    private OnDaySelectListener listener;
    private int lastWidth;

    public BasicCalendarHeatmapView(Context context) {
        this(context, null);
    }

    public BasicCalendarHeatmapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicCalendarHeatmapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        readAttrs(attrs);
        refreshTheme();
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth == null ? "" : yearMonth;
        refreshTheme();
    }

    public void setCells(List<Cell> next) {
        cells.clear();
        if (next != null) {
            cells.addAll(next);
        }
        refreshTheme();
    }

    public void setShowLegend(boolean showLegend) {
        this.showLegend = showLegend;
        refreshTheme();
    }

    public void setOnDaySelectListener(OnDaySelectListener listener) {
        this.listener = listener;
    }

    public void setVariant(String variant) {
        refreshTheme();
    }

    public void setBasicText(CharSequence text) {
        if (text != null) {
            setYearMonth(text.toString());
        }
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
        removeAllViews();

        int[] ym = parseYearMonth(yearMonth);
        int year = ym[0];
        int month = ym[1];
        Map<String, String> levelMap = new HashMap<>();
        for (Cell cell : cells) {
            levelMap.put(cell.date, cell.level);
        }

        if (showLegend) {
            LinearLayout legend = new LinearLayout(getContext());
            legend.setOrientation(HORIZONTAL);
            for (int i = 0; i < LEVELS.length; i++) {
                TextView item = new TextView(getContext());
                item.setText(LEVELS[i][1]);
                item.setGravity(Gravity.CENTER);
                item.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                item.setMinHeight(dp(28));
                item.setPadding(dp(10), 0, dp(10), 0);
                applyLevelColors(item, LEVELS[i][0], colors, style);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                if (i > 0) {
                    lp.leftMargin = dp(6);
                }
                legend.addView(item, lp);
            }
            addView(legend);
        }

        LinearLayout weekdays = new LinearLayout(getContext());
        weekdays.setOrientation(HORIZONTAL);
        LayoutParams weekLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        weekLp.topMargin = showLegend ? Math.round(style.spaceMd) : 0;
        for (String w : WEEKDAYS) {
            TextView day = new TextView(getContext());
            day.setText(w);
            day.setGravity(Gravity.CENTER);
            day.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            day.setTextColor(colors.textTertiary);
            day.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11);
            weekdays.addView(day, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        }
        addView(weekdays, weekLp);

        Calendar first = Calendar.getInstance();
        first.clear();
        first.set(year, month - 1, 1);
        int daysInMonth = first.getActualMaximum(Calendar.DAY_OF_MONTH);
        int startPad = (first.get(Calendar.DAY_OF_WEEK) + 5) % 7; // Monday-first

        int width = Math.max(getWidth(), getMeasuredWidth());
        int gap = dp(4);
        int cellSize = width > 0 ? Math.max(dp(28), (width - gap * 6) / 7) : dp(36);

        LinearLayout gridRow = null;
        int totalSlots = startPad + daysInMonth;

        for (int index = 0; index < totalSlots; index++) {
            if (index % 7 == 0) {
                gridRow = new LinearLayout(getContext());
                gridRow.setOrientation(HORIZONTAL);
                LayoutParams rowLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                rowLp.topMargin = index == 0 ? Math.round(style.spaceSm / 2f) : gap;
                addView(gridRow, rowLp);
            }
            if (index < startPad) {
                View empty = new View(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(cellSize, cellSize);
                if (index % 7 > 0) {
                    lp.leftMargin = gap;
                }
                gridRow.addView(empty, lp);
            } else {
                int day = index - startPad + 1;
                String date = String.format(Locale.US, "%04d-%02d-%02d", year, month, day);
                String level = levelMap.containsKey(date) ? levelMap.get(date) : "none";
                TextView cell = new TextView(getContext());
                cell.setText(String.valueOf(day));
                cell.setGravity(Gravity.CENTER);
                cell.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                cell.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                applyLevelColors(cell, level, colors, style);
                cell.setClickable(true);
                final String levelFinal = level;
                cell.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onSelectDay(date, levelFinal);
                    }
                });
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(cellSize, cellSize);
                if (index % 7 > 0) {
                    lp.leftMargin = gap;
                }
                gridRow.addView(cell, lp);
            }
        }
    }

    private void applyLevelColors(TextView view, String level, BasicColors colors, BasicStyle style) {
        int fill;
        int stroke;
        int text;
        switch (level == null ? "none" : level) {
            case "full":
                fill = colors.statusSuccess;
                stroke = colors.statusSuccess;
                text = colors.textInverse;
                break;
            case "partial":
                fill = colors.statusWarning;
                stroke = colors.statusWarning;
                text = colors.textPrimary;
                break;
            case "exempt":
                fill = colors.brandPrimary;
                stroke = colors.brandPrimary;
                text = colors.textInverse;
                break;
            default:
                fill = colors.backgroundPageGradientEnd;
                stroke = colors.borderDefault;
                text = colors.textTertiary;
                break;
        }
        view.setTextColor(text);
        view.setBackground(BasicDrawableFactory.roundedFillStroke(
                fill,
                stroke,
                style.borderHairline,
                style.radiusSm
        ));
    }

    private static int[] parseYearMonth(String raw) {
        if (raw != null && raw.length() >= 7) {
            String[] parts = raw.split("-");
            if (parts.length >= 2) {
                try {
                    int y = Integer.parseInt(parts[0]);
                    int m = Integer.parseInt(parts[1]);
                    if (m >= 1 && m <= 12) {
                        return new int[]{y, m};
                    }
                } catch (NumberFormatException ignored) {
                    // fall through
                }
            }
        }
        Calendar now = Calendar.getInstance();
        return new int[]{now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1};
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
            String xmlText = array.getString(R.styleable.BasicView_basicText);
            if (xmlText != null) {
                yearMonth = xmlText;
            }
        } finally {
            array.recycle();
        }
    }
}
