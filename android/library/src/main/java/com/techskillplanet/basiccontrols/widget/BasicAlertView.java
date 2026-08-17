package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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
 * <p>对齐 RN TspAlert：info=pageEnd，success=selectedFill，warning=activeFill，
 * error=danger 浅透明底。</p>
 */
public class BasicAlertView extends LinearLayout {
    public static final String VARIANT_INFO = "info";
    public static final String VARIANT_SUCCESS = "success";
    public static final String VARIANT_WARNING = "warning";
    public static final String VARIANT_ERROR = "error";

    private final TextView titleView;
    private final TextView messageView;
    private String variant = VARIANT_INFO;
    private boolean basicDisabled;
    private float titleTextSizeSp = -1f;
    private float messageTextSizeSp = -1f;

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

    public void setVariant(String variant) {
        this.variant = variant == null ? VARIANT_INFO : variant;
        refreshTheme();
    }

    public void setBasicText(CharSequence text) {
        setMessage(text);
    }

    public void setTitle(CharSequence title) {
        titleView.setText(title);
        titleView.setVisibility(title == null || title.length() == 0 ? GONE : VISIBLE);
    }

    public void setMessage(CharSequence message) {
        messageView.setText(message);
        messageView.setVisibility(message == null || message.length() == 0 ? GONE : VISIBLE);
    }

    public void setTitleTextSizeSp(float sizeSp) {
        titleTextSizeSp = sizeSp;
        refreshTheme();
    }

    public void setMessageTextSizeSp(float sizeSp) {
        messageTextSizeSp = sizeSp;
        refreshTheme();
    }

    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        refreshTheme();
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int fill;
        int stroke;
        if (VARIANT_SUCCESS.equals(variant)) {
            fill = colors.selectedFill;
            stroke = colors.statusSuccess;
        } else if (VARIANT_WARNING.equals(variant)) {
            fill = colors.activeFill;
            stroke = colors.statusWarning;
        } else if (VARIANT_ERROR.equals(variant) || "danger".equals(variant)) {
            fill = Color.argb(0x1F, Color.red(colors.statusDanger), Color.green(colors.statusDanger), Color.blue(colors.statusDanger));
            stroke = colors.statusDanger;
        } else {
            fill = colors.backgroundPageGradientEnd;
            stroke = colors.borderDefault;
        }
        int text = basicDisabled ? colors.textDisabled : colors.textPrimary;
        int message = basicDisabled ? colors.textDisabled : colors.textSecondary;

        setBackground(BasicDrawableFactory.roundedFillStroke(
                basicDisabled ? colors.backgroundSurfaceDisabled : fill,
                basicDisabled ? colors.borderLight : stroke,
                style.borderHairline,
                dp(18)
        ));
        setPadding(
                Math.round(style.spaceMd),
                Math.round(style.spaceMd),
                Math.round(style.spaceMd),
                Math.round(style.spaceMd)
        );
        titleView.setTextColor(text);
        if (titleTextSizeSp > 0f) {
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSizeSp);
        } else {
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        }
        messageView.setTextColor(message);
        if (messageTextSizeSp > 0f) {
            messageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, messageTextSizeSp);
        } else {
            messageView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        }
        if (titleView.getVisibility() == VISIBLE && messageView.getVisibility() == VISIBLE) {
            messageView.setPadding(0, dp(6), 0, 0);
        } else {
            messageView.setPadding(0, 0, 0, 0);
        }
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }

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
            setTitle(title == null ? "" : title);
            setMessage(message == null ? (text == null ? "" : text) : message);
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
