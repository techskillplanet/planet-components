package com.techskillplanet.basiccontrols.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.basiccontrols.R;
import com.techskillplanet.basiccontrols.drawable.BasicDrawableFactory;
import com.techskillplanet.basiccontrols.theme.BasicColors;
import com.techskillplanet.basiccontrols.theme.BasicStyle;
import com.techskillplanet.basiccontrols.theme.BasicThemeManager;

/**
 * 基础折叠面板。
 *
 * <p>参考 animal-island-ui Collapse 的卡片式问答结构：圆角卡片、左侧圆形图标、
 * 标题行和展开内容。适合 FAQ、设置说明、表单帮助等场景。</p>
 */
public class BasicCollapseView extends LinearLayout {
    private final TextView iconView;
    private final TextView titleView;
    private final TextView messageView;
    private boolean expanded;
    private boolean basicDisabled;

    public BasicCollapseView(Context context) {
        this(context, null);
    }

    public BasicCollapseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicCollapseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        setClickable(true);
        LinearLayout header = new LinearLayout(context);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setOrientation(HORIZONTAL);
        iconView = new TextView(context);
        iconView.setGravity(Gravity.CENTER);
        iconView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        titleView = new TextView(context);
        titleView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        header.addView(iconView);
        header.addView(titleView);
        messageView = new TextView(context);
        messageView.setLineSpacing(0f, 1.35f);
        addView(header);
        addView(messageView);
        readAttrs(attrs);
        setOnClickListener(view -> {
            if (isEnabled()) {
                setExpanded(!expanded, true);
            }
        });
        refreshTheme();
    }

    /** 设置视觉变体，当前保留统一协议。 */
    public void setVariant(String variant) {
        refreshTheme();
    }

    /** 设置标题。 */
    public void setBasicText(CharSequence text) {
        titleView.setText(text);
    }

    /** 设置标题。 */
    public void setTitle(CharSequence title) {
        titleView.setText(title);
    }

    /** 设置展开内容。 */
    public void setMessage(CharSequence message) {
        messageView.setText(message);
    }

    /** 设置是否展开。 */
    public void setSelectedState(boolean selected) {
        setExpanded(selected, false);
    }

    /** 设置禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        refreshTheme();
    }

    /** 刷新主题样式。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                expanded ? colors.brandPrimary : colors.borderLight,
                style.borderHairline,
                style.radiusControlIsland
        ));
        setAlpha(basicDisabled || !isEnabled() ? 0.6f : 1f);
        setPadding(Math.round(style.spaceMd), Math.round(style.spaceMd),
                Math.round(style.spaceMd), Math.round(style.spaceMd));
        iconView.setText(expanded ? "−" : "+");
        iconView.setTextColor(colors.textInverse);
        iconView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textLg);
        iconView.setRotation(expanded ? 180f : 0f);
        iconView.setBackground(BasicDrawableFactory.ovalFillStroke(
                expanded ? colors.brandPrimaryActive : colors.brandPrimary,
                expanded ? colors.brandPrimaryActive : colors.brandPrimary,
                0f
        ));
        titleView.setTextColor(basicDisabled ? colors.textDisabled : colors.textPrimary);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textLg);
        messageView.setTextColor(basicDisabled ? colors.textDisabled : colors.textSecondary);
        messageView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        messageView.setVisibility(expanded ? VISIBLE : GONE);

        int iconSize = Math.round(style.spaceXl + style.spaceSm);
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(iconSize, iconSize);
        iconParams.setMargins(0, 0, Math.round(style.spaceMd), 0);
        iconView.setLayoutParams(iconParams);
        titleView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        messageParams.setMargins(iconSize + Math.round(style.spaceMd), Math.round(style.spaceSm), 0, 0);
        messageView.setLayoutParams(messageParams);
    }

    /** 设置展开状态，可选择是否做高度动画。 */
    private void setExpanded(boolean expanded, boolean animate) {
        this.expanded = expanded;
        if (!animate) {
            refreshTheme();
            return;
        }
        int start = getHeight();
        refreshTheme();
        measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int end = getMeasuredHeight();
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(BasicThemeManager.style().switchMotionDuration);
        animator.addUpdateListener(animation -> {
            ViewGroup.LayoutParams params = getLayoutParams();
            if (params != null) {
                params.height = (int) animation.getAnimatedValue();
                setLayoutParams(params);
            }
        });
        animator.start();
    }

    /** 从 XML 读取标题、正文和状态。 */
    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String title = array.getString(R.styleable.BasicView_basicTitle);
            String text = array.getString(R.styleable.BasicView_basicText);
            String message = array.getString(R.styleable.BasicView_basicMessage);
            titleView.setText(title == null ? (text == null ? "" : text) : title);
            messageView.setText(message == null ? "" : message);
            expanded = array.getBoolean(R.styleable.BasicView_basicSelected, false);
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
