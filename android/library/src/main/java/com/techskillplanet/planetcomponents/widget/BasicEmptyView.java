package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.R;
import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 技趣星球空状态组件。
 *
 * <p>对齐 RN TspEmpty：50×50 / radius 18 品牌标记 + ○，容器 radius 22 / pad 24，
 * 行动按钮非全宽。</p>
 */
public class BasicEmptyView extends LinearLayout {
    private final TextView iconView;
    private final TextView titleView;
    private final TextView messageView;
    private final BasicButton actionButton;
    private boolean basicDisabled;

    public BasicEmptyView(Context context) {
        this(context, null);
    }

    public BasicEmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        iconView = new TextView(context);
        iconView.setGravity(Gravity.CENTER);
        iconView.setTypeface(Typeface.DEFAULT_BOLD);
        titleView = new TextView(context);
        titleView.setGravity(Gravity.CENTER);
        titleView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        messageView = new TextView(context);
        messageView.setGravity(Gravity.CENTER);
        actionButton = new BasicButton(context);
        actionButton.setVariant(BasicButton.VARIANT_PRIMARY);
        addView(iconView);
        addView(titleView);
        addView(messageView);
        addView(actionButton);
        readAttrs(attrs);
        refreshTheme();
    }

    /** 空状态不区分 variant，保留统一组件协议。 */
    public void setVariant(String variant) {
        refreshTheme();
    }

    /** 设置标题，和 basicText 同义。 */
    public void setBasicText(CharSequence text) {
        setTitle(text);
    }

    /** 设置标题。 */
    public void setTitle(CharSequence title) {
        titleView.setText(title);
    }

    /** 设置描述。 */
    public void setMessage(CharSequence message) {
        messageView.setText(message);
    }

    /** 设置行动按钮文字。 */
    public void setActionText(CharSequence actionText) {
        actionButton.setBasicText(actionText);
        actionButton.setVisibility(actionText == null || actionText.length() == 0 ? GONE : VISIBLE);
    }

    /** 返回行动按钮，方便业务绑定点击事件。 */
    public BasicButton getActionButton() {
        return actionButton;
    }

    /** 选中态不改变空状态样式，保留统一协议。 */
    public void setSelectedState(boolean selected) {
        setSelected(selected);
    }

    /** 设置禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        actionButton.setBasicDisabled(disabled);
        refreshTheme();
    }

    /** 刷新整体容器和内部文本样式。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        int pad = dp(24);
        setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderDefault,
                style.borderHairline,
                dp(22)
        ));
        setPadding(pad, pad, pad, pad);

        int mark = dp(50);
        iconView.setText("○");
        iconView.setTextColor(colors.textInverse);
        iconView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        iconView.setBackground(BasicDrawableFactory.roundedFill(colors.brandPrimary, dp(18)));
        LayoutParams iconParams = new LayoutParams(mark, mark);
        iconParams.setMargins(0, 0, 0, Math.round(style.spaceSm));
        iconView.setLayoutParams(iconParams);

        titleView.setTextColor(basicDisabled || !isEnabled() ? colors.textDisabled : colors.textPrimary);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        messageView.setTextColor(basicDisabled || !isEnabled() ? colors.textDisabled : colors.textSecondary);
        messageView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);

        LayoutParams messageParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        messageParams.setMargins(0, Math.round(style.spaceSm), 0, Math.round(style.spaceSm));
        messageView.setLayoutParams(messageParams);
        LayoutParams actionParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        actionParams.gravity = Gravity.CENTER_HORIZONTAL;
        actionButton.setLayoutParams(actionParams);
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }

    /** 从 XML 读取标题、描述和禁用态。 */
    private void readAttrs(AttributeSet attrs) {
        if (attrs == null) {
            titleView.setText("暂无内容");
            messageView.setText("技趣星球正在为你准备新的工具。");
            setActionText("去创建");
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicView);
        try {
            String title = array.getString(R.styleable.BasicView_basicTitle);
            String message = array.getString(R.styleable.BasicView_basicMessage);
            String text = array.getString(R.styleable.BasicView_basicText);
            titleView.setText(title == null ? (text == null ? "暂无内容" : text) : title);
            messageView.setText(message == null ? "技趣星球正在为你准备新的工具。" : message);
            setActionText("去创建");
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setEnabled(!basicDisabled);
            actionButton.setBasicDisabled(basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
