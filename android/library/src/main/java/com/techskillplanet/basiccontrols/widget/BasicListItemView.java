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
 * 基础列表项组件。
 *
 * <p>适合设置页、消息列表、工具入口和结果列表。组件提供标题、描述、右侧文本，
 * 业务可以通过 setTitle/setMessage/setTrailingText 快速构造常见行项目。</p>
 */
public class BasicListItemView extends LinearLayout {
    private final TextView titleView;
    private final TextView messageView;
    private final TextView trailingView;
    private String variant = "default";
    private boolean basicDisabled;

    public BasicListItemView(Context context) {
        this(context, null);
    }

    public BasicListItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setClickable(true);
        LinearLayout textColumn = new LinearLayout(context);
        textColumn.setOrientation(VERTICAL);
        titleView = new TextView(context);
        titleView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        messageView = new TextView(context);
        trailingView = new TextView(context);
        textColumn.addView(titleView);
        textColumn.addView(messageView);
        addView(textColumn);
        addView(trailingView);
        readAttrs(attrs);
        refreshTheme();
    }

    /** 设置列表项变体，selected 会使用品牌浅底色。 */
    public void setVariant(String variant) {
        this.variant = variant == null ? "default" : variant;
        refreshTheme();
    }

    /** 设置标题，和 basicText 保持同义。 */
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

    /** 设置右侧文本。 */
    public void setTrailingText(CharSequence trailing) {
        trailingView.setText(trailing);
    }

    /** 设置选中态。 */
    public void setSelectedState(boolean selected) {
        setSelected(selected);
        refreshTheme();
    }

    /** 设置禁用态。 */
    public void setBasicDisabled(boolean disabled) {
        basicDisabled = disabled;
        setEnabled(!disabled);
        refreshTheme();
    }

    /** 刷新列表项背景、文字和布局。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        boolean selected = isSelected() || "selected".equals(variant);
        int fill = selected ? colors.selectedFill : colors.backgroundSurfaceRaised;
        int stroke = selected ? colors.statusSuccess : colors.borderDefault;
        int title = basicDisabled || !isEnabled() ? colors.textDisabled : colors.textPrimary;
        int message = basicDisabled || !isEnabled() ? colors.textDisabled : colors.textSecondary;
        int trailing = basicDisabled || !isEnabled() ? colors.textDisabled : colors.textTertiary;

        setMinimumHeight(dp(64));
        setBackground(BasicDrawableFactory.roundedFillStroke(fill, stroke, style.borderHairline, dp(18)));
        setPadding(Math.round(style.spaceMd), Math.round(style.spaceMd), Math.round(style.spaceMd), Math.round(style.spaceMd));
        setAlpha(basicDisabled || !isEnabled() ? 0.45f : 1f);
        titleView.setTextColor(title);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        messageView.setTextColor(message);
        messageView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        if (messageView.getText() != null && messageView.getText().length() > 0) {
            messageView.setPadding(0, dp(4), 0, 0);
        }
        trailingView.setTextColor(trailing);
        trailingView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        LinearLayout textColumn = (LinearLayout) titleView.getParent();
        LayoutParams textParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
        textParams.setMargins(0, 0, Math.round(style.spaceMd), 0);
        textColumn.setLayoutParams(textParams);
        trailingView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }

    /** 从 XML 读取标题、描述、选中态和禁用态。 */
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
            variant = xmlVariant == null ? "default" : xmlVariant;
            titleView.setText(title == null ? (text == null ? "" : text) : title);
            messageView.setText(message == null ? "" : message);
            basicDisabled = array.getBoolean(R.styleable.BasicView_basicDisabled, false);
            setSelected(array.getBoolean(R.styleable.BasicView_basicSelected, false));
            setEnabled(!basicDisabled);
        } finally {
            array.recycle();
        }
    }
}
