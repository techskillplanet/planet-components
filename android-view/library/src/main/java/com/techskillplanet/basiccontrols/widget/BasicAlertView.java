package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.R;
import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 基础提示条组件。
 *
 * <p>支持 info/success/warning/error 四类语义状态。标题和正文都由组件内部
 * TextView 承载，适合表单提示、页面顶部提醒和空状态说明。</p>
 */
public class BasicAlertView extends LinearLayout {
    public static final String VARIANT_INFO = "info";
    public static final String VARIANT_SUCCESS = "success";
    public static final String VARIANT_WARNING = "warning";
    public static final String VARIANT_ERROR = "error";

    /** 提示标题。 */
    private final TextView titleView;
    /** 提示正文。 */
    private final TextView messageView;
    /** 当前语义变体。 */
    private String variant = VARIANT_INFO;
    /** 组件禁用态，通常用于只读预览。 */
    private boolean basicDisabled;

    public BasicAlertView(Context context) {
        this(context, null);
    }

    public BasicAlertView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicAlertView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
        titleView = new TextView(context);
        titleView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        messageView = new TextView(context);
        messageView.setLineSpacing(0f, 1.1f);
        addView(titleView);
        addView(messageView);
        readAttrs(attrs);
        refreshTheme();
    }

    /** 设置 info/success/warning/error 变体。 */
    public void setVariant(String variant) {
        this.variant = variant == null ? VARIANT_INFO : variant;
        refreshTheme();
    }

    /** 设置正文，和 basicText 属性保持同义。 */
    public void setBasicText(CharSequence text) {
        setMessage(text);
    }

    /** 设置提示标题。 */
    public void setTitle(CharSequence title) {
        titleView.setText(title);
    }

    /** 设置提示正文。 */
    public void setMessage(CharSequence message) {
        messageView.setText(message);
    }

    /** Alert 无业务选中态，这里保留统一协议。 */
    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    /** 设置组件禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        refreshTheme();
    }

    /** 根据语义状态刷新背景、边框和文本颜色。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int accent = resolveAccent(colors);
        int fill = VARIANT_INFO.equals(variant) ? colors.brandPrimarySubtle : colors.backgroundSurfaceRaised;
        int text = basicDisabled ? colors.textDisabled : colors.textPrimary;
        int message = basicDisabled ? colors.textDisabled : colors.textSecondary;

        setBackground(BasicDrawableFactory.roundedFillStroke(
                basicDisabled ? colors.backgroundSurfaceDisabled : fill,
                basicDisabled ? colors.borderLight : accent,
                style.borderDefault,
                style.radiusControlIsland
        ));
        setPadding(
                Math.round(style.spaceMd),
                Math.round(style.spaceMd),
                Math.round(style.spaceMd),
                Math.round(style.spaceMd)
        );
        titleView.setTextColor(text);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        messageView.setTextColor(message);
        messageView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
    }

    /** 将变体名称映射到语义色，后续换肤只需要替换 token。 */
    private int resolveAccent(BasicColors colors) {
        if (VARIANT_SUCCESS.equals(variant)) {
            return colors.statusSuccess;
        }
        if (VARIANT_WARNING.equals(variant)) {
            return colors.statusWarning;
        }
        if (VARIANT_ERROR.equals(variant) || "danger".equals(variant)) {
            return colors.statusDanger;
        }
        return colors.brandPrimary;
    }

    /** 从 XML 读取标题、正文、变体和禁用态。 */
    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String xmlVariant = array.getString(R.styleable.BasicView_basicVariant);
            String title = array.getString(R.styleable.BasicView_basicTitle);
            String message = array.getString(R.styleable.BasicView_basicMessage);
            String text = array.getString(R.styleable.BasicView_basicText);
            variant = xmlVariant == null ? VARIANT_INFO : xmlVariant;
            titleView.setText(title == null ? "" : title);
            messageView.setText(message == null ? (text == null ? "" : text) : message);
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
