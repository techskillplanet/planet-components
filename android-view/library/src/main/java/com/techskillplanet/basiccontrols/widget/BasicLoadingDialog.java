package com.techskillplanet.basiccontrols.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
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
 * 半透明技趣星球 Loading 弹窗。
 *
 * <p>弹窗使用透明窗口背景和轻量 dim，内容面板使用 token 圆角、边框和星球动画。
 * 适合页面提交、AI 生成、主题同步等需要阻塞用户操作的短时等待场景。</p>
 */
public class BasicLoadingDialog extends Dialog {
    private CharSequence message;
    private TextView messageView;

    public BasicLoadingDialog(Context context) {
        this(context, "加载中...");
    }

    public BasicLoadingDialog(Context context, CharSequence message) {
        super(context);
        this.message = message;
    }

    /** 快速显示一个 Loading 弹窗。 */
    public static BasicLoadingDialog show(Context context, CharSequence message) {
        BasicLoadingDialog dialog = new BasicLoadingDialog(context, message);
        dialog.show();
        return dialog;
    }

    /** 设置弹窗文案。 */
    public void setMessage(CharSequence message) {
        this.message = message;
        if (messageView != null) {
            messageView.setText(message);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setContentView(createContentView());
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = 0.28f;
            window.setAttributes(params);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    /** 创建弹窗内容面板。 */
    private LinearLayout createContentView() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        LinearLayout panel = new LinearLayout(getContext());
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setGravity(Gravity.CENTER);
        panel.setPadding(Math.round(style.spaceXl), Math.round(style.spaceLg),
                Math.round(style.spaceXl), Math.round(style.spaceLg));
        panel.setMinimumWidth(Math.round(style.spaceXl * 6f));
        panel.setBackground(BasicDrawableFactory.roundedFillStroke(
                withAlpha(colors.backgroundSurfaceRaised, 235),
                withAlpha(colors.borderLight, 220),
                style.borderHairline,
                style.radiusDialogOrganic
        ));

        BasicPlanetLoadingView loadingView = new BasicPlanetLoadingView(getContext());
        int loadingSize = Math.round(style.spaceXl * 2.7f);
        panel.addView(loadingView, new LinearLayout.LayoutParams(loadingSize, loadingSize));

        messageView = new TextView(getContext());
        messageView.setText(message);
        messageView.setTextColor(colors.textPrimary);
        messageView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        messageView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        messageParams.setMargins(0, Math.round(style.spaceSm), 0, 0);
        panel.addView(messageView, messageParams);
        return panel;
    }

    /** 给 token 颜色附加透明度。 */
    private int withAlpha(int color, int alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }
}
