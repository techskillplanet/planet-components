package com.techskillplanet.basiccontrols.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部选项面板，对齐 RN TspOptionSheet。
 *
 * <p>遮罩 rgba(23,58,98,0.32)，顶圆角 24，选项行 minH 48 / radius 14，
 * 选中行 brandPrimary 底 + 白字 + ✓。</p>
 */
public class BasicOptionSheet extends Dialog {
    public interface OnOptionSelectedListener {
        void onOptionSelected(int index, String option);
    }

    private CharSequence title = "请选择";
    private final List<String> options = new ArrayList<>();
    private int selectedIndex;
    private OnOptionSelectedListener listener;
    private View.OnClickListener cancelListener;

    public BasicOptionSheet(Context context) {
        super(context);
    }

    public void setTitleText(CharSequence title) {
        this.title = title == null ? "请选择" : title;
    }

    public void setOptions(List<String> values, int selectedIndex) {
        options.clear();
        if (values != null) {
            options.addAll(values);
        }
        this.selectedIndex = Math.max(0, Math.min(selectedIndex, Math.max(0, options.size() - 1)));
    }

    public void setOnOptionSelectedListener(OnOptionSelectedListener listener) {
        this.listener = listener;
    }

    public void setOnCancelClickListener(View.OnClickListener listener) {
        cancelListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(createRoot());
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = 0.32f;
            params.gravity = Gravity.BOTTOM;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.BOTTOM);
        }
    }

    private View createRoot() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        float topRadius = dp(24);
        LinearLayout panel = new LinearLayout(getContext());
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderDefault,
                style.borderHairline,
                topRadius,
                topRadius,
                0,
                0
        ));
        panel.setPadding(0, 0, 0, Math.round(style.spaceMd));

        LinearLayout header = new LinearLayout(getContext());
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setMinimumHeight(dp(52));
        int headerPad = Math.round(style.spaceMd);
        header.setPadding(headerPad, 0, headerPad, 0);

        TextView cancel = new TextView(getContext());
        cancel.setText("取消");
        cancel.setTypeface(Typeface.DEFAULT_BOLD);
        cancel.setTextColor(colors.brandPrimary);
        cancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        cancel.setPadding(0, Math.round(style.spaceSm), Math.round(style.spaceSm), Math.round(style.spaceSm));
        cancel.setOnClickListener(view -> {
            if (cancelListener != null) {
                cancelListener.onClick(view);
            }
            dismiss();
        });

        TextView titleView = new TextView(getContext());
        titleView.setText(title);
        titleView.setTypeface(Typeface.DEFAULT_BOLD);
        titleView.setTextColor(colors.textPrimary);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        titleView.setGravity(Gravity.CENTER);

        View spacer = new View(getContext());
        header.addView(cancel, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        header.addView(titleView, titleParams);
        header.addView(spacer, new LinearLayout.LayoutParams(dp(44), ViewGroup.LayoutParams.WRAP_CONTENT));
        panel.addView(header, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        ScrollView scroll = new ScrollView(getContext());
        LinearLayout list = new LinearLayout(getContext());
        list.setOrientation(LinearLayout.VERTICAL);
        int optionMarginH = dp(10);
        int optionRadius = dp(14);
        for (int i = 0; i < options.size(); i++) {
            final int index = i;
            final String option = options.get(i);
            boolean selected = index == selectedIndex;
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);
            row.setMinimumHeight(dp(48));
            row.setPadding(Math.round(style.spaceMd), 0, Math.round(style.spaceMd), 0);
            row.setBackground(BasicDrawableFactory.roundedFill(
                    selected ? colors.brandPrimary : Color.TRANSPARENT,
                    optionRadius
            ));
            TextView check = new TextView(getContext());
            check.setText(selected ? "✓" : "");
            check.setTextColor(selected ? colors.textInverse : colors.textPrimary);
            check.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
            check.setWidth(dp(28));
            check.setGravity(Gravity.CENTER);
            TextView label = new TextView(getContext());
            label.setText(option);
            label.setTypeface(Typeface.DEFAULT_BOLD);
            label.setTextColor(selected ? colors.textInverse : colors.textPrimary);
            label.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
            row.addView(check);
            row.addView(label, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            row.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onOptionSelected(index, option);
                }
                dismiss();
            });
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            rowParams.leftMargin = optionMarginH;
            rowParams.rightMargin = optionMarginH;
            rowParams.topMargin = Math.round(style.spaceSm / 2f);
            list.addView(row, rowParams);
        }
        scroll.addView(list, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        panel.addView(scroll, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return panel;
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getContext().getResources().getDisplayMetrics()
        ));
    }
}
