package com.techskillplanet.planetcomponents.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.TextView;

import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基础单选确认弹窗。
 *
 * <p>用于设置项等场景：先选中一项，再点确认才生效。视觉与 {@link BasicModalDialog} 一致，
 * 选项使用 {@link BasicRadioView}，跟随主题换色。</p>
 */
public class BasicChoiceDialog extends Dialog {
    public interface OnConfirmListener {
        void onConfirm(int selectedIndex);
    }

    private CharSequence title;
    private CharSequence confirmText = "确定";
    private CharSequence cancelText = "取消";
    private final List<CharSequence> options = new ArrayList<>();
    private int selectedIndex;
    private int pendingIndex;
    private OnConfirmListener confirmListener;
    private final List<BasicRadioView> optionViews = new ArrayList<>();

    public BasicChoiceDialog(Context context) {
        super(context);
    }

    public void setTitleText(CharSequence title) {
        this.title = title;
    }

    public void setConfirmText(CharSequence confirmText) {
        this.confirmText = confirmText;
    }

    public void setCancelText(CharSequence cancelText) {
        this.cancelText = cancelText;
    }

    public void setOptions(CharSequence[] labels, int selectedIndex) {
        options.clear();
        if (labels != null) {
            options.addAll(Arrays.asList(labels));
        }
        this.selectedIndex = clampIndex(selectedIndex);
        this.pendingIndex = this.selectedIndex;
    }

    public void setOnConfirmListener(OnConfirmListener listener) {
        confirmListener = listener;
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
            params.dimAmount = 0.36f;
            window.setAttributes(params);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    @Override
    public void show() {
        pendingIndex = selectedIndex;
        refreshOptionSelection();
        super.show();
        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private View createRoot() {
        BasicStyle style = BasicThemeManager.style();
        int horizontalMargin = Math.round(style.spaceXl);

        FrameLayout root = new FrameLayout(getContext());
        LinearLayout panel = createPanel();
        FrameLayout.LayoutParams panelParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        panelParams.leftMargin = horizontalMargin;
        panelParams.rightMargin = horizontalMargin;
        root.addView(panel, panelParams);
        return root;
    }

    private LinearLayout createPanel() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        LinearLayout panel = new LinearLayout(getContext());
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(
                Math.round(style.spaceXl),
                Math.round(style.spaceXl),
                Math.round(style.spaceXl),
                Math.round(style.spaceLg)
        );
        panel.setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderLight,
                style.borderHairline,
                style.radiusDialogOrganic
        ));

        TextView titleView = new TextView(getContext());
        titleView.setText(title);
        titleView.setTextColor(colors.textPrimary);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textTitle);
        titleView.setGravity(Gravity.START);
        panel.addView(titleView);

        LinearLayout optionsContainer = new LinearLayout(getContext());
        optionsContainer.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams optionsParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        optionsParams.topMargin = Math.round(style.spaceLg);
        optionsParams.bottomMargin = Math.round(style.spaceLg);
        panel.addView(optionsContainer, optionsParams);

        optionViews.clear();
        for (int i = 0; i < options.size(); i++) {
            final int index = i;
            BasicRadioView radio = new BasicRadioView(getContext());
            radio.setBasicText(options.get(i));
            radio.setSelectedState(index == pendingIndex);
            radio.setOnClickListener(v -> selectIndex(index));
            optionViews.add(radio);

            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            if (i > 0) {
                rowParams.topMargin = Math.round(style.spaceMd);
            }
            rowParams.height = Math.round(style.controlHeightMd);
            optionsContainer.addView(radio, rowParams);
        }

        LinearLayout actions = new LinearLayout(getContext());
        actions.setOrientation(LinearLayout.HORIZONTAL);
        BasicButton cancel = new BasicButton(getContext());
        cancel.setBasicText(cancelText);
        cancel.setVariant(BasicButton.VARIANT_DEFAULT);
        cancel.setOnClickListener(view -> dismiss());
        BasicButton confirm = new BasicButton(getContext());
        confirm.setBasicText(confirmText);
        confirm.setVariant(BasicButton.VARIANT_PRIMARY);
        confirm.setOnClickListener(view -> {
            selectedIndex = pendingIndex;
            if (confirmListener != null) {
                confirmListener.onConfirm(selectedIndex);
            }
            dismiss();
        });
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f
        );
        actions.addView(cancel, buttonParams);
        LinearLayout.LayoutParams confirmParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f
        );
        confirmParams.setMargins(Math.round(style.spaceMd), 0, 0, 0);
        actions.addView(confirm, confirmParams);
        panel.addView(actions);
        return panel;
    }

    private void selectIndex(int index) {
        pendingIndex = clampIndex(index);
        refreshOptionSelection();
    }

    private void refreshOptionSelection() {
        for (int i = 0; i < optionViews.size(); i++) {
            optionViews.get(i).setSelectedState(i == pendingIndex);
        }
    }

    private int clampIndex(int index) {
        if (options.isEmpty()) {
            return 0;
        }
        if (index < 0) {
            return 0;
        }
        if (index >= options.size()) {
            return options.size() - 1;
        }
        return index;
    }
}
