package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 广告位容器：固定高度槽位，可嵌入 AdMob {@code AdView} 或占位文案。
 *
 * <p>业务层负责加载真实广告；未配置时显示浅色占位条。</p>
 */
public class BasicAdPlaceholderView extends FrameLayout {
    private final TextView placeholderLabel;
    private View adContentView;
    private boolean showPlaceholder = true;
    private CharSequence placeholderText = "广告";

    public BasicAdPlaceholderView(Context context) {
        this(context, null);
    }

    public BasicAdPlaceholderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicAdPlaceholderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        placeholderLabel = new TextView(context);
        placeholderLabel.setGravity(Gravity.CENTER);
        placeholderLabel.setText(placeholderText);
        addView(placeholderLabel, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        refreshTheme();
    }

    /** 嵌入真实广告 View（如 AdMob AdView），会自动隐藏占位文案。 */
    public void setAdContentView(View view) {
        if (adContentView != null) {
            removeView(adContentView);
        }
        adContentView = view;
        if (view != null) {
            addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            showPlaceholder = false;
            placeholderLabel.setVisibility(GONE);
        } else {
            showPlaceholder = true;
            placeholderLabel.setVisibility(VISIBLE);
        }
        refreshTheme();
    }

    public void setPlaceholderText(CharSequence text) {
        placeholderText = text;
        placeholderLabel.setText(text);
    }

    public void setShowPlaceholder(boolean show) {
        showPlaceholder = show;
        placeholderLabel.setVisibility(show && adContentView == null ? VISIBLE : GONE);
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        if (colors == null || style == null) {
            return;
        }
        int height = Math.max(dp(50), Math.round(style.spaceXl * 2));
        setMinimumHeight(height);
        setBackground(BasicDrawableFactory.roundedFill(
                colors.backgroundSurfaceRaised,
                style.radiusMd
        ));
        placeholderLabel.setTextColor(colors.textTertiary);
        placeholderLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        placeholderLabel.setTypeface(Typeface.DEFAULT);
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }
}
