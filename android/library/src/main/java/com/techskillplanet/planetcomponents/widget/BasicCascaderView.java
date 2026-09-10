package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 多级级联选择器。
 *
 * <p>对齐合约 Cascader（options / value / placeholder / disabled / onChange）。
 * 选项字段：value/id、label/title/text、children。</p>
 */
public class BasicCascaderView extends LinearLayout {
    /** 级联选项。 */
    public static final class Option {
        public final String value;
        public final String label;
        public final List<Option> children;

        public Option(String value, String label, List<Option> children) {
            this.value = value == null ? "" : value;
            this.label = label == null ? value : label;
            this.children = children == null ? new ArrayList<>() : children;
        }
    }

    public interface OnChangeListener {
        void onChange(List<String> value, List<String> labels);
    }

    private final TextView triggerView;
    private final LinearLayout panel;
    private final HorizontalScrollView panelScroll;
    private final List<Option> options = new ArrayList<>();
    private final List<String> value = new ArrayList<>();
    private final List<String> draft = new ArrayList<>();
    private String placeholder = "请选择";
    private boolean open;
    private boolean basicDisabled;
    private OnChangeListener changeListener;

    public BasicCascaderView(Context context) {
        this(context, null);
    }

    public BasicCascaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicCascaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        triggerView = new TextView(context);
        triggerView.setSingleLine(true);
        triggerView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        triggerView.setGravity(Gravity.CENTER_VERTICAL);
        triggerView.setClickable(true);
        triggerView.setOnClickListener(v -> toggleOpen());

        panelScroll = new HorizontalScrollView(context);
        panelScroll.setHorizontalScrollBarEnabled(false);
        panel = new LinearLayout(context);
        panel.setOrientation(HORIZONTAL);
        panelScroll.addView(panel, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        panelScroll.setVisibility(GONE);

        addView(triggerView, new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(panelScroll, new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        refreshTheme();
    }

    /** 设置级联选项。 */
    public void setOptions(List<Option> next) {
        options.clear();
        if (next != null) {
            options.addAll(next);
        }
        refreshTheme();
    }

    /** 设置当前选中路径（value 列表）。 */
    public void setValue(List<String> next) {
        value.clear();
        if (next != null) {
            value.addAll(next);
        }
        draft.clear();
        draft.addAll(value);
        refreshTheme();
    }

    public List<String> getValue() {
        return new ArrayList<>(value);
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder == null || placeholder.length() == 0 ? "请选择" : placeholder;
        refreshTheme();
    }

    public void setOnChangeListener(OnChangeListener listener) {
        changeListener = listener;
    }

    public void setVariant(String variant) {
        refreshTheme();
    }

    public void setBasicText(CharSequence text) {
        if (text != null && text.length() > 0) {
            setPlaceholder(text.toString());
        }
    }

    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        triggerView.setEnabled(!disabled);
        if (disabled) {
            open = false;
        }
        refreshTheme();
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        List<String> labels = findPathLabels(options, value);
        boolean empty = labels.isEmpty();
        String display = empty ? placeholder : join(labels, " / ");
        triggerView.setText(display + "  ▾");
        triggerView.setTextColor(empty ? colors.textTertiary : colors.textPrimary);
        triggerView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        int padH = Math.round(style.spaceMd + 2);
        int padV = Math.round(style.spaceMd);
        triggerView.setMinHeight(Math.round(style.controlHeightButtonMedium));
        triggerView.setPadding(padH, padV, padH, padV);
        triggerView.setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderDefault,
                style.borderDefault,
                style.radiusLg
        ));
        panelScroll.setVisibility(open ? VISIBLE : GONE);
        if (open) {
            rebuildPanel();
        }
        setAlpha(basicDisabled || !isEnabled() ? 0.45f : 1f);
        setContentDescription(display);
    }

    private void toggleOpen() {
        if (basicDisabled || !isEnabled()) {
            return;
        }
        open = !open;
        if (open) {
            draft.clear();
            draft.addAll(value);
        }
        refreshTheme();
    }

    private void rebuildPanel() {
        panel.removeAllViews();
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        List<List<Option>> columns = buildColumns();
        for (int ci = 0; ci < columns.size(); ci++) {
            final int colIndex = ci;
            ScrollView colScroll = new ScrollView(getContext());
            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(VERTICAL);
            colScroll.addView(col);
            for (int oi = 0; oi < columns.get(ci).size(); oi++) {
                final Option opt = columns.get(ci).get(oi);
                TextView item = new TextView(getContext());
                item.setText(opt.label);
                item.setSingleLine(true);
                item.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                item.setPadding(Math.round(style.spaceMd), Math.round(style.spaceSm),
                        Math.round(style.spaceMd), Math.round(style.spaceSm));
                boolean active = draft.size() > colIndex && draft.get(colIndex).equals(opt.value);
                item.setTextColor(active ? colors.brandPrimary : colors.textPrimary);
                item.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
                if (active) {
                    item.setBackground(BasicDrawableFactory.roundedFill(colors.brandPrimarySubtle, style.radiusMd));
                }
                item.setOnClickListener(v -> pick(colIndex, opt));
                col.addView(item, new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            LayoutParams lp = new LayoutParams(
                    Math.round(style.controlHeightLg * 2.4f),
                    Math.round(style.controlHeightLg * 5f));
            lp.setMarginEnd(Math.round(style.spaceSm / 2f));
            panel.addView(colScroll, lp);
        }
        panel.setPadding(Math.round(style.spaceSm), Math.round(style.spaceSm),
                Math.round(style.spaceSm), Math.round(style.spaceSm));
        panel.setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundMenu,
                colors.borderLight,
                style.borderHairline,
                style.radiusLg
        ));
    }

    private List<List<Option>> buildColumns() {
        List<List<Option>> columns = new ArrayList<>();
        List<Option> level = options;
        List<String> walk = draft;
        for (int i = 0; i <= walk.size(); i++) {
            if (level == null || level.isEmpty()) {
                break;
            }
            columns.add(level);
            if (i >= walk.size()) {
                break;
            }
            String cur = walk.get(i);
            if (cur == null || cur.isEmpty()) {
                break;
            }
            Option hit = null;
            for (Option o : level) {
                if (o.value.equals(cur)) {
                    hit = o;
                    break;
                }
            }
            level = hit == null ? null : hit.children;
        }
        return columns;
    }

    private void pick(int colIndex, Option opt) {
        while (draft.size() > colIndex) {
            draft.remove(draft.size() - 1);
        }
        draft.add(opt.value);
        if (opt.children.isEmpty()) {
            value.clear();
            value.addAll(draft);
            open = false;
            List<String> labels = findPathLabels(options, value);
            refreshTheme();
            if (changeListener != null) {
                changeListener.onChange(new ArrayList<>(value), labels);
            }
        } else {
            rebuildPanel();
        }
    }

    private static List<String> findPathLabels(List<Option> options, List<String> path) {
        List<String> labels = new ArrayList<>();
        List<Option> level = options;
        for (String v : path) {
            Option hit = null;
            for (Option o : level) {
                if (o.value.equals(v)) {
                    hit = o;
                    break;
                }
            }
            if (hit == null) {
                break;
            }
            labels.add(hit.label);
            level = hit.children;
        }
        return labels;
    }

    private static String join(List<String> parts, String sep) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.size(); i++) {
            if (i > 0) {
                sb.append(sep);
            }
            sb.append(parts.get(i));
        }
        return sb.toString();
    }
}
