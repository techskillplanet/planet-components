package com.techskillplanet.basiccontrols.widget;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 基础模态弹窗。
 *
 * <p>参考 animal-island-ui Modal 的圆角面板、遮罩和标题/正文/操作区结构。
 * 适合确认框、说明弹窗和轻量表单容器。</p>
 */
public class BasicModalDialog extends Dialog {
    private CharSequence title;
    private CharSequence message;
    private CharSequence confirmText = "确定";
    private CharSequence cancelText = "取消";
    private View.OnClickListener confirmClickListener;

    public BasicModalDialog(Context context) {
        super(context);
    }

    /** 快速创建并展示模态弹窗。 */
    public static BasicModalDialog show(Context context, CharSequence title, CharSequence message) {
        BasicModalDialog dialog = new BasicModalDialog(context);
        dialog.setTitleText(title);
        dialog.setMessage(message);
        dialog.show();
        return dialog;
    }

    /** 设置标题。 */
    public void setTitleText(CharSequence title) {
        this.title = title;
    }

    /** 设置正文。 */
    public void setMessage(CharSequence message) {
        this.message = message;
    }

    /** 设置确认按钮文案。 */
    public void setConfirmText(CharSequence confirmText) {
        this.confirmText = confirmText;
    }

    /** 设置取消按钮文案。 */
    public void setCancelText(CharSequence cancelText) {
        this.cancelText = cancelText;
    }

    /** 设置确认按钮点击。 */
    public void setOnConfirmClickListener(View.OnClickListener listener) {
        confirmClickListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(createPanel());
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
        super.show();
        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    /** 创建弹窗面板。 */
    private LinearLayout createPanel() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        LinearLayout panel = new LinearLayout(getContext());
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(Math.round(style.spaceXl), Math.round(style.spaceXl),
                Math.round(style.spaceXl), Math.round(style.spaceLg));
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
        titleView.setGravity(Gravity.CENTER);
        panel.addView(titleView);

        TextView messageView = new TextView(getContext());
        messageView.setText(message);
        messageView.setTextColor(colors.textSecondary);
        messageView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        messageView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        messageParams.setMargins(0, Math.round(style.spaceMd), 0, Math.round(style.spaceLg));
        panel.addView(messageView, messageParams);

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
            if (confirmClickListener != null) {
                confirmClickListener.onClick(view);
            }
            dismiss();
        });
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        actions.addView(cancel, buttonParams);
        LinearLayout.LayoutParams confirmParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        confirmParams.setMargins(Math.round(style.spaceMd), 0, 0, 0);
        actions.addView(confirm, confirmParams);
        panel.addView(actions);
        return panel;
    }
}
