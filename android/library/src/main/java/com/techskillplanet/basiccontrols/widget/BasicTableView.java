package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基础表格组件。
 *
 * <p>参考 animal-island-ui Table 的圆角外壳、表头强调、斑马纹行和空状态。
 * 适合工具清单、任务列表、配置项对比等轻量数据展示。</p>
 */
public class BasicTableView extends HorizontalScrollView {
    private final TableLayout tableLayout;
    private List<String> headers = new ArrayList<>();
    private List<List<String>> rows = new ArrayList<>();

    public BasicTableView(Context context) {
        this(context, null);
    }

    public BasicTableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);
        tableLayout = new TableLayout(context);
        addView(tableLayout);
        setData(Arrays.asList("名称", "状态", "难度"), new ArrayList<>());
    }

    /** 设置表格数据。 */
    public void setData(List<String> headers, List<List<String>> rows) {
        this.headers = headers == null ? new ArrayList<>() : headers;
        this.rows = rows == null ? new ArrayList<>() : rows;
        refreshTheme();
    }

    /** 当前不使用变体。 */
    public void setVariant(String variant) {
        refreshTheme();
    }

    /** 空实现，表格数据请使用 setData。 */
    public void setBasicText(CharSequence text) {
        setContentDescription(text);
    }

    /** 当前不使用选中态。 */
    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    /** 设置禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        setEnabled(!disabled);
        refreshTheme();
    }

    /** 重绘表格。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        tableLayout.removeAllViews();
        setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderLight,
                style.borderHairline,
                style.radiusControlIsland
        ));
        tableLayout.setPadding(Math.round(style.spaceSm), Math.round(style.spaceSm),
                Math.round(style.spaceSm), Math.round(style.spaceSm));
        tableLayout.addView(row(headers, true, 0));
        if (rows.isEmpty()) {
            tableLayout.addView(emptyRow());
        } else {
            for (int i = 0; i < rows.size(); i++) {
                tableLayout.addView(row(rows.get(i), false, i));
            }
        }
    }

    /** 创建一行。 */
    private TableRow row(List<String> values, boolean header, int index) {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        TableRow row = new TableRow(getContext());
        row.setBackground(BasicDrawableFactory.roundedFill(
                header ? colors.backgroundSurfaceSubtle : (index % 2 == 0 ? colors.backgroundSurfaceRaised : colors.backgroundSurfaceSubtle),
                style.radiusMd
        ));
        for (String value : values) {
            TextView cell = new TextView(getContext());
            cell.setText(value);
            cell.setGravity(Gravity.CENTER_VERTICAL);
            cell.setTypeface(Typeface.DEFAULT, header ? Typeface.BOLD : Typeface.NORMAL);
            cell.setTextColor(header ? colors.textPrimary : colors.textSecondary);
            cell.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
            cell.setMinWidth(Math.round(style.controlHeightLg * 2.2f));
            cell.setMinHeight(Math.round(header ? style.controlHeightMd : style.tableRowHeight));
            cell.setPadding(Math.round(style.spaceMd), 0, Math.round(style.spaceMd), 0);
            row.addView(cell);
        }
        return row;
    }

    /** 创建空状态行。 */
    private TableRow emptyRow() {
        BasicStyle style = BasicThemeManager.style();
        TableRow row = new TableRow(getContext());
        TextView empty = new TextView(getContext());
        empty.setText("暂无数据");
        empty.setGravity(Gravity.CENTER);
        empty.setTextColor(BasicThemeManager.colors().textTertiary);
        empty.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        empty.setMinHeight(Math.round(style.tableRowHeight));
        empty.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        row.addView(empty);
        return row;
    }
}
