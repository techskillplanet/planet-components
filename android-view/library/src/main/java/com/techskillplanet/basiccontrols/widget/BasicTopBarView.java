package com.techskillplanet.basiccontrols.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 蓝天星球风格顶部导航栏。
 *
 * <p>固定承载页面标题和返回入口，适合单 Activity 的轻量页面流。</p>
 */
public class BasicTopBarView extends LinearLayout {
    private final TextView backView;
    private final TextView titleView;
    private final View spacer;
    private Integer barBackgroundColor;
    private Integer titleTextColor;
    private Integer backTextColor;

    public BasicTopBarView(Context context) {
        this(context, null);
    }

    public BasicTopBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicTopBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        backView = new TextView(context);
        backView.setText("←");
        backView.setGravity(Gravity.CENTER);
        backView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        backView.setIncludeFontPadding(false);
        addView(backView);

        titleView = new TextView(context);
        titleView.setGravity(Gravity.CENTER);
        titleView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        titleView.setIncludeFontPadding(false);
        addView(titleView);

        spacer = new View(context);
        addView(spacer);
        refreshTheme();
    }

    public void setTitle(CharSequence title) {
        titleView.setText(title);
    }

    public void setBackVisible(boolean visible) {
        backView.setVisibility(visible ? VISIBLE : INVISIBLE);
    }

    /** 设置导航栏背景色。未设置时使用当前主题 token。 */
    public void setBarBackgroundColor(int color) {
        barBackgroundColor = color;
        refreshTheme();
    }

    /** 设置标题和返回按钮颜色，适合深色导航栏。 */
    public void setBarTextColor(int titleColor, int backColor) {
        titleTextColor = titleColor;
        backTextColor = backColor;
        refreshTheme();
    }

    /** 恢复使用主题 token 作为导航栏文字颜色。 */
    public void clearBarTextColor() {
        titleTextColor = null;
        backTextColor = null;
        refreshTheme();
    }

    /** 恢复使用当前主题 token 作为导航栏背景。 */
    public void clearBarBackgroundColor() {
        barBackgroundColor = null;
        refreshTheme();
    }

    public void setOnBackClickListener(OnClickListener listener) {
        backView.setOnClickListener(listener);
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        setBackgroundColor(barBackgroundColor == null ? colors.backgroundSurfaceRaised : barBackgroundColor);
        setPadding(0, 0, 0, 0);

        backView.setTextColor(backTextColor == null ? colors.brandPrimary : backTextColor);
        backView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        backView.setBackgroundColor(android.graphics.Color.TRANSPARENT);

        titleView.setTextColor(titleTextColor == null ? colors.textPrimary : titleTextColor);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        titleView.setSingleLine(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int side = dp(56);
        int height = resolveSize(dp(56), heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int exactSide = MeasureSpec.makeMeasureSpec(side, MeasureSpec.EXACTLY);
        int exactHeight = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        backView.measure(exactSide, exactHeight);
        spacer.measure(exactSide, exactHeight);
        int titleWidth = Math.max(0, width - side * 2);
        titleView.measure(
                MeasureSpec.makeMeasureSpec(titleWidth, MeasureSpec.EXACTLY),
                exactHeight
        );
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = right - left;
        int side = backView.getMeasuredWidth();
        int childTop = (bottom - top - backView.getMeasuredHeight()) / 2;
        backView.layout(0, childTop, side, childTop + backView.getMeasuredHeight());
        spacer.layout(width - side, childTop, width, childTop + spacer.getMeasuredHeight());
        titleView.layout(side, childTop, width - side, childTop + titleView.getMeasuredHeight());
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }
}
