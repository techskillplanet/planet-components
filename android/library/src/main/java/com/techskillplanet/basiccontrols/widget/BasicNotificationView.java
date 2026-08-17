package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.R;
import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * RN Notification 的星球主题通知条。
 *
 * <p>info 使用 pageEnd，alert/warning 使用 activeFill + warning 边。</p>
 */
public class BasicNotificationView extends LinearLayout {
    private final TextView titleView;
    private final TextView messageView;
    private String variant = "info";

    public BasicNotificationView(Context context) {
        this(context, null);
    }

    public BasicNotificationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicNotificationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        titleView = new TextView(context);
        messageView = new TextView(context);
        addView(titleView);
        addView(messageView);
        readAttrs(attrs);
        refreshTheme();
    }

    public void setVariant(String variant) {
        this.variant = variant == null ? "info" : variant;
        refreshTheme();
    }

    public void setContent(CharSequence title, CharSequence message) {
        titleView.setText(title);
        messageView.setText(message);
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        boolean alert = "alert".equals(variant) || "warning".equals(variant);
        int fill = alert ? colors.activeFill : colors.backgroundPageGradientEnd;
        int stroke = alert ? colors.statusWarning : colors.borderDefault;
        setBackground(BasicDrawableFactory.roundedFillStroke(fill, stroke, style.borderHairline, dp(18)));
        int paddingH = Math.round(style.spaceMd);
        int paddingV = Math.round(style.spaceMd);
        setPadding(paddingH, paddingV, paddingH, paddingV);
        titleView.setTextColor(colors.textPrimary);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        titleView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        messageView.setTextColor(colors.textSecondary);
        messageView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        messageView.setPadding(0, dp(6), 0, 0);
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
            variant = array.getString(R.styleable.BasicView_basicVariant);
            if (variant == null) {
                variant = "info";
            }
            titleView.setText(array.getString(R.styleable.BasicView_basicTitle));
            messageView.setText(array.getString(R.styleable.BasicView_basicMessage));
        } finally {
            array.recycle();
        }
    }
}
