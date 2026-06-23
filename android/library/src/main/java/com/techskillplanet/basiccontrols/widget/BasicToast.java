package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 自定义 Toast 工具。
 *
 * <p>Toast 本身不是 View 子类，这里提供静态方法创建带 token 背景的 Toast 视图。
 * 调用前同样需要先执行 BasicThemeManager.init(context)。</p>
 */
public final class BasicToast {
    private BasicToast() {
    }

    /** 使用默认 info 样式显示短 Toast。 */
    public static Toast show(Context context, CharSequence text) {
        return show(context, text, "info", Toast.LENGTH_SHORT);
    }

    /** 使用指定变体和时长显示 Toast。 */
    public static Toast show(Context context, CharSequence text, String variant, int duration) {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        TextView view = new TextView(context);
        view.setText(text);
        view.setGravity(Gravity.CENTER);
        view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        view.setMinHeight(Math.round(style.toastMinHeight));
        view.setPadding(Math.round(style.spaceLg), 0, Math.round(style.spaceLg), 0);
        int fill = resolveFill(colors, variant);
        int textColor = "warning".equals(variant) ? colors.textPrimary : colors.textInverse;
        view.setTextColor(textColor);
        view.setBackground(BasicDrawableFactory.roundedFill(fill, style.radiusPill));

        Toast toast = new Toast(context.getApplicationContext());
        toast.setDuration(duration);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, Math.round(style.spaceXl));
        toast.setView(view);
        toast.show();
        return toast;
    }

    /** 将 Toast 变体映射为语义背景色。 */
    private static int resolveFill(BasicColors colors, String variant) {
        if ("success".equals(variant)) {
            return colors.statusSuccess;
        }
        if ("warning".equals(variant)) {
            return colors.statusWarning;
        }
        if ("danger".equals(variant) || "error".equals(variant)) {
            return colors.statusDanger;
        }
        return colors.brandPrimary;
    }
}
