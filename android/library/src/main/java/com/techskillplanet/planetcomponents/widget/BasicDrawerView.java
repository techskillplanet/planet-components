package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 底部抽屉面板：遮罩 + 圆角面板，简化实现。
 *
 * <p>对齐合约 Drawer（visible / title / placement / onClose / children）。</p>
 */
public class BasicDrawerView extends FrameLayout {
    public interface OnCloseListener {
        void onClose(BasicDrawerView view);
    }

    private final View maskView;
    private final LinearLayout panel;
    private final TextView titleView;
    private final TextView closeView;
    private final FrameLayout bodyContainer;
    private boolean visible;
    private String placement = "bottom";
    private OnCloseListener closeListener;

    public BasicDrawerView(Context context) {
        this(context, null);
    }

    public BasicDrawerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicDrawerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        maskView = new View(context);
        maskView.setOnClickListener(v -> close());

        panel = new LinearLayout(context);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setClickable(true);

        LinearLayout header = new LinearLayout(context);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);

        titleView = new TextView(context);
        titleView.setTypeface(Typeface.DEFAULT_BOLD);
        closeView = new TextView(context);
        closeView.setText("×");
        closeView.setGravity(Gravity.CENTER);
        closeView.setOnClickListener(v -> close());

        header.addView(titleView, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        header.addView(closeView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ScrollView scroll = new ScrollView(context);
        bodyContainer = new FrameLayout(context);
        scroll.addView(bodyContainer, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        panel.addView(header, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        panel.addView(scroll, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        addView(maskView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        LayoutParams panelParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        panelParams.gravity = Gravity.BOTTOM;
        addView(panel, panelParams);

        setVisibility(GONE);
        setClickable(true);
        refreshTheme();
    }

    public void setTitleText(CharSequence title) {
        titleView.setText(title == null ? "" : title);
        titleView.setVisibility(title == null || title.length() == 0 ? GONE : VISIBLE);
        setContentDescription(title == null || title.length() == 0 ? "Drawer" : title);
    }

    public void setPlacement(String placement) {
        this.placement = placement == null ? "bottom" : placement;
        LayoutParams panelParams = (LayoutParams) panel.getLayoutParams();
        if (panelParams == null) {
            panelParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
        if ("top".equals(this.placement)) {
            panelParams.gravity = Gravity.TOP;
        } else {
            panelParams.gravity = Gravity.BOTTOM;
        }
        panel.setLayoutParams(panelParams);
        refreshTheme();
    }

    public void setVisibleState(boolean visible) {
        this.visible = visible;
        setVisibility(visible ? VISIBLE : GONE);
    }

    public boolean isDrawerVisible() {
        return visible;
    }

    public void setBody(View body) {
        bodyContainer.removeAllViews();
        if (body != null) {
            bodyContainer.addView(body, new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }
    }

    public FrameLayout getBodyContainer() {
        return bodyContainer;
    }

    public void setOnCloseListener(OnCloseListener listener) {
        closeListener = listener;
    }

    public void close() {
        setVisibleState(false);
        if (closeListener != null) {
            closeListener.onClose(this);
        }
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        if (colors == null || style == null) {
            return;
        }
        maskView.setBackgroundColor(colors.backgroundModalScrim);
        float topLeft = "top".equals(placement) ? 0 : style.radiusXl;
        float topRight = "top".equals(placement) ? 0 : style.radiusXl;
        float bottomLeft = "top".equals(placement) ? style.radiusXl : 0;
        float bottomRight = "top".equals(placement) ? style.radiusXl : 0;
        panel.setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurfaceRaised,
                colors.borderDefault,
                style.borderHairline,
                topLeft, topRight, bottomRight, bottomLeft
        ));
        int pad = Math.round(style.spaceLg);
        panel.setPadding(pad, pad, pad, pad);
        titleView.setTextColor(colors.textPrimary);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textTitle);
        closeView.setTextColor(colors.textSecondary);
        closeView.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textLg);
        closeView.setPadding(Math.round(style.spaceSm), 0, 0, 0);
        setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
    }
}
