package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 轻量文件列表上传组件（不含真实上传后端）。
 *
 * <p>对齐合约 Upload（files / multiple / disabled / accept / onChange / onRemove）。
 * 「选择文件」通过 {@link OnPickRequestListener} 交由宿主处理系统选择器。</p>
 */
public class BasicUploadView extends LinearLayout {
    /** 文件条目。 */
    public static final class FileItem {
        public final String id;
        public final String name;

        public FileItem(String id, String name) {
            this.id = id == null ? UUID.randomUUID().toString() : id;
            this.name = name == null ? "file" : name;
        }
    }

    /** 文件列表变更。 */
    public interface OnChangeListener {
        void onChange(List<FileItem> files);
    }

    /** 单文件移除。 */
    public interface OnRemoveListener {
        void onRemove(FileItem file);
    }

    /** 请求宿主打开系统文件选择器。 */
    public interface OnPickRequestListener {
        void onPickRequest(BasicUploadView view);
    }

    private final TextView triggerView;
    private final LinearLayout listContainer;
    private final TextView emptyView;
    private final List<FileItem> files = new ArrayList<>();
    private boolean multiple = true;
    private boolean basicDisabled;
    private String accept = "";
    private OnChangeListener changeListener;
    private OnRemoveListener removeListener;
    private OnPickRequestListener pickRequestListener;

    public BasicUploadView(Context context) {
        this(context, null);
    }

    public BasicUploadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicUploadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        triggerView = new TextView(context);
        triggerView.setText("选择文件");
        triggerView.setGravity(Gravity.CENTER);
        triggerView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        triggerView.setClickable(true);
        triggerView.setOnClickListener(v -> {
            if (basicDisabled || !isEnabled()) {
                return;
            }
            if (pickRequestListener != null) {
                pickRequestListener.onPickRequest(this);
            } else {
                addFile(new FileItem(null, "demo-" + (files.size() + 1) + ".txt"));
            }
        });
        emptyView = new TextView(context);
        emptyView.setText("尚未选择文件");
        emptyView.setGravity(Gravity.CENTER_VERTICAL);
        listContainer = new LinearLayout(context);
        listContainer.setOrientation(VERTICAL);
        addView(triggerView);
        addView(emptyView);
        addView(listContainer);
        refreshTheme();
    }

    /** 设置文件列表。 */
    public void setFiles(List<FileItem> next) {
        files.clear();
        if (next != null) {
            files.addAll(next);
        }
        rebuildList();
        refreshTheme();
    }

    /** 返回当前文件副本。 */
    public List<FileItem> getFiles() {
        return new ArrayList<>(files);
    }

    /** 追加或替换文件（受 multiple 约束）。 */
    public void addFile(FileItem item) {
        if (item == null || basicDisabled || !isEnabled()) {
            return;
        }
        if (!multiple) {
            files.clear();
        }
        files.add(item);
        rebuildList();
        refreshTheme();
        emitChange();
    }

    /** 以文件名追加（自动生成 id）。 */
    public void addFileName(String name) {
        addFile(new FileItem(null, name));
    }

    /** 是否允许多选。 */
    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    /** MIME / 扩展名提示（交由宿主选择器使用）。 */
    public void setAccept(String accept) {
        this.accept = accept == null ? "" : accept;
    }

    /** 返回 accept。 */
    public String getAccept() {
        return accept;
    }

    public void setOnChangeListener(OnChangeListener listener) {
        changeListener = listener;
    }

    public void setOnRemoveListener(OnRemoveListener listener) {
        removeListener = listener;
    }

    public void setOnPickRequestListener(OnPickRequestListener listener) {
        pickRequestListener = listener;
    }

    public void setVariant(String variant) {
        refreshTheme();
    }

    public void setBasicText(CharSequence text) {
        if (text != null && text.length() > 0) {
            triggerView.setText(text);
        }
    }

    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        triggerView.setEnabled(!disabled);
        refreshTheme();
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int pad = Math.round(style.spaceMd);
        setPadding(pad, pad, pad, pad);
        setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderLight,
                style.borderHairline,
                style.radiusControlIsland
        ));
        triggerView.setTextColor(colors.textInverse);
        triggerView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        triggerView.setPadding(Math.round(style.spaceLg), Math.round(style.spaceSm),
                Math.round(style.spaceLg), Math.round(style.spaceSm));
        triggerView.setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.brandPrimary, colors.brandPrimary, style.borderDefault, style.radiusPill));
        emptyView.setTextColor(colors.textTertiary);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        emptyView.setPadding(0, Math.round(style.spaceSm), 0, 0);
        boolean empty = files.isEmpty();
        emptyView.setVisibility(empty ? View.VISIBLE : View.GONE);
        listContainer.setVisibility(empty ? View.GONE : View.VISIBLE);
        setAlpha(basicDisabled || !isEnabled() ? 0.45f : 1f);
        for (int i = 0; i < listContainer.getChildCount(); i++) {
            View child = listContainer.getChildAt(i);
            if (child instanceof LinearLayout) {
                styleRow((LinearLayout) child, colors, style);
            }
        }
    }

    private void rebuildList() {
        listContainer.removeAllViews();
        BasicStyle style = BasicThemeManager.style();
        for (FileItem file : files) {
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);
            TextView name = new TextView(getContext());
            name.setText(file.name);
            name.setSingleLine(true);
            name.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            TextView remove = new TextView(getContext());
            remove.setText("×");
            remove.setGravity(Gravity.CENTER);
            remove.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            remove.setOnClickListener(v -> removeFile(file));
            row.addView(name, new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
            row.addView(remove);
            LayoutParams lp = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.topMargin = Math.round(style.spaceSm / 2f);
            listContainer.addView(row, lp);
        }
    }

    private void styleRow(LinearLayout row, BasicColors colors, BasicStyle style) {
        int padV = Math.round(style.spaceSm / 2f);
        row.setPadding(Math.round(style.spaceSm), padV, Math.round(style.spaceSm), padV);
        row.setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceSubtle,
                colors.borderLight,
                style.borderHairline,
                style.radiusMd
        ));
        if (row.getChildCount() >= 2) {
            TextView name = (TextView) row.getChildAt(0);
            TextView remove = (TextView) row.getChildAt(1);
            name.setTextColor(colors.textPrimary);
            name.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
            remove.setTextColor(colors.textSecondary);
            remove.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
            remove.setPadding(Math.round(style.spaceSm), 0, Math.round(style.spaceSm), 0);
            remove.setEnabled(!basicDisabled && isEnabled());
        }
    }

    private void removeFile(FileItem file) {
        if (basicDisabled || !isEnabled()) {
            return;
        }
        files.remove(file);
        rebuildList();
        refreshTheme();
        if (removeListener != null) {
            removeListener.onRemove(file);
        }
        emitChange();
    }

    private void emitChange() {
        if (changeListener != null) {
            changeListener.onChange(getFiles());
        }
    }
}
