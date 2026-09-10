package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 气泡提示：包裹触发子 View，长按或 {@link #setVisibleState(boolean)} 显示气泡。
 *
 * <p>对齐合约 Tooltip（text / placement / visible / children）。</p>
 */
public class BasicTooltipView extends FrameLayout {
    private CharSequence tipText = "";
    private String placement = "top";
    private Boolean forcedVisible;
    private PopupWindow popup;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable hideRunnable = this::dismissPopup;

    public BasicTooltipView(Context context) {
        this(context, null);
    }

    public BasicTooltipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicTooltipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        setLongClickable(true);
        setOnLongClickListener(v -> {
            if (forcedVisible == null) {
                showPopup();
                handler.removeCallbacks(hideRunnable);
                handler.postDelayed(hideRunnable, 2200);
            }
            return true;
        });
    }

    /** 设置提示文案。 */
    public void setTipText(CharSequence text) {
        tipText = text == null ? "" : text;
        if (popup != null && popup.isShowing()) {
            showPopup();
        }
    }

    /** 设置气泡方位：top / bottom / left / right（简化实现主要影响偏移）。 */
    public void setPlacement(String placement) {
        this.placement = placement == null ? "top" : placement;
    }

    /**
     * 强制显示/隐藏；传 null 恢复长按交互控制。
     */
    public void setVisibleState(Boolean visible) {
        forcedVisible = visible;
        if (Boolean.TRUE.equals(visible)) {
            showPopup();
        } else if (Boolean.FALSE.equals(visible)) {
            dismissPopup();
        }
    }

    /** 便捷：把触发内容作为唯一子 View。 */
    public void setTrigger(View trigger) {
        removeAllViews();
        if (trigger != null) {
            addView(trigger, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
    }

    public void refreshTheme() {
        if (popup != null && popup.isShowing()) {
            showPopup();
        }
    }

    private void showPopup() {
        dismissPopup();
        if (tipText.length() == 0) {
            return;
        }
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        if (colors == null || style == null) {
            return;
        }
        TextView bubble = new TextView(getContext());
        bubble.setText(tipText);
        bubble.setTextColor(colors.textInverse);
        bubble.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSm);
        bubble.setPadding(
                Math.round(style.spaceMd),
                Math.round(style.spaceSm),
                Math.round(style.spaceMd),
                Math.round(style.spaceSm)
        );
        bubble.setBackground(BasicDrawableFactory.roundedFill(colors.brandDark, style.radiusMd));
        bubble.setMaxWidth(Math.round(style.spaceXl * 10));
        bubble.measure(
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        );

        popup = new PopupWindow(bubble, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.setOutsideTouchable(true);
        popup.setTouchable(false);

        int[] loc = new int[2];
        getLocationOnScreen(loc);
        int x = loc[0];
        int y = loc[1];
        int offsetX = (getWidth() - bubble.getMeasuredWidth()) / 2;
        int offsetY;
        if ("bottom".equals(placement)) {
            offsetY = getHeight() + Math.round(style.spaceSm);
        } else if ("left".equals(placement)) {
            offsetX = -bubble.getMeasuredWidth() - Math.round(style.spaceSm);
            offsetY = (getHeight() - bubble.getMeasuredHeight()) / 2;
        } else if ("right".equals(placement)) {
            offsetX = getWidth() + Math.round(style.spaceSm);
            offsetY = (getHeight() - bubble.getMeasuredHeight()) / 2;
        } else {
            offsetY = -bubble.getMeasuredHeight() - Math.round(style.spaceSm);
        }
        if (isAttachedToWindow()) {
            popup.showAtLocation(this, Gravity.NO_GRAVITY, x + offsetX, y + offsetY);
        }
    }

    private void dismissPopup() {
        handler.removeCallbacks(hideRunnable);
        if (popup != null) {
            if (popup.isShowing()) {
                popup.dismiss();
            }
            popup = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        dismissPopup();
        super.onDetachedFromWindow();
    }
}
