package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 嵌套树选择组件。
 *
 * <p>对齐合约 Tree（items / selectedId / expandedIds / onSelect / onExpand）。
 * 节点使用 Map 风格字段：id、label/title/text、children。</p>
 */
public class BasicTreeView extends LinearLayout {
    /** 树节点数据。 */
    public static final class Node {
        public final String id;
        public final String label;
        public final List<Node> children;

        public Node(String id, String label, List<Node> children) {
            this.id = id == null ? "" : id;
            this.label = label == null ? id : label;
            this.children = children == null ? new ArrayList<>() : children;
        }
    }

    public interface OnSelectListener {
        void onSelect(String id, Node node);
    }

    public interface OnExpandListener {
        void onExpand(List<String> expandedIds);
    }

    private final List<Node> items = new ArrayList<>();
    private final Set<String> expandedIds = new HashSet<>();
    private String selectedId = "";
    private OnSelectListener selectListener;
    private OnExpandListener expandListener;

    public BasicTreeView(Context context) {
        this(context, null);
    }

    public BasicTreeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicTreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        refreshTheme();
    }

    /** 设置树节点。 */
    public void setItems(List<Node> next) {
        items.clear();
        if (next != null) {
            items.addAll(next);
        }
        rebuild();
    }

    /** 设置选中节点 id。 */
    public void setSelectedId(String id) {
        selectedId = id == null ? "" : id;
        rebuild();
    }

    /** 设置展开节点 id 列表。 */
    public void setExpandedIds(List<String> ids) {
        expandedIds.clear();
        if (ids != null) {
            expandedIds.addAll(ids);
        }
        rebuild();
    }

    public void setOnSelectListener(OnSelectListener listener) {
        selectListener = listener;
    }

    public void setOnExpandListener(OnExpandListener listener) {
        expandListener = listener;
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
        setEnabled(!disabled);
        refreshTheme();
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int pad = Math.round(style.spaceSm);
        setPadding(pad, pad, pad, pad);
        setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderLight,
                style.borderHairline,
                style.radiusControlIsland
        ));
        rebuild();
    }

    private void rebuild() {
        removeAllViews();
        for (Node node : items) {
            addNode(node, 0);
        }
    }

    private void addNode(Node node, int depth) {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        boolean hasKids = !node.children.isEmpty();
        boolean open = expandedIds.contains(node.id);
        boolean selected = selectedId.equals(node.id);

        LinearLayout row = new LinearLayout(getContext());
        row.setOrientation(HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        int indent = Math.round(style.spaceSm + depth * style.spaceLg);
        int padV = Math.round(style.spaceSm / 2f);
        row.setPadding(indent, padV, Math.round(style.spaceSm), padV);
        if (selected) {
            row.setBackground(BasicDrawableFactory.roundedFill(colors.brandPrimarySubtle, style.radiusMd));
        }

        TextView twist = new TextView(getContext());
        twist.setGravity(Gravity.CENTER);
        twist.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        if (hasKids) {
            twist.setText(open ? "▾" : "▸");
            twist.setClickable(true);
            twist.setOnClickListener(v -> toggle(node.id));
        } else {
            twist.setText(" ");
        }
        twist.setTextColor(colors.textSecondary);
        twist.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        twist.setMinWidth(Math.round(style.spaceLg));

        TextView label = new TextView(getContext());
        label.setText(node.label);
        label.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        label.setTextColor(selected ? colors.brandPrimary : colors.textPrimary);
        label.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        label.setClickable(true);
        label.setOnClickListener(v -> {
            selectedId = node.id;
            rebuild();
            if (selectListener != null) {
                selectListener.onSelect(node.id, node);
            }
        });

        row.addView(twist);
        row.addView(label, new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        addView(row, new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        if (hasKids && open) {
            for (Node child : node.children) {
                addNode(child, depth + 1);
            }
        }
    }

    private void toggle(String id) {
        if (expandedIds.contains(id)) {
            expandedIds.remove(id);
        } else {
            expandedIds.add(id);
        }
        rebuild();
        if (expandListener != null) {
            expandListener.onExpand(new ArrayList<>(expandedIds));
        }
    }
}
