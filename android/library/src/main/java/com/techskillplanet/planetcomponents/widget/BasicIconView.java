package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.icon.PlanetIcons;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * Named Planet icon (glyph). Catalog paths live in {@link PlanetIcons}.
 * Android-private helper — not part of the 35-component contract.
 */
public class BasicIconView extends TextView {
    private String iconName = "check";

    public BasicIconView(Context context) {
        this(context, null);
    }

    public BasicIconView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
        setTypeface(Typeface.DEFAULT_BOLD);
        setIncludeFontPadding(false);
        setIconName("check");
        setIconSizeDp(20);
        refreshTheme();
    }

    public void setIconName(String name) {
        iconName = name == null || name.isEmpty() ? "check" : name;
        PlanetIcons.Spec spec = PlanetIcons.get(iconName);
        setText(spec.glyph);
        setContentDescription(iconName);
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconSizeDp(float dp) {
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
        setTextSize(TypedValue.COMPLEX_UNIT_PX, px);
    }

    public void refreshTheme() {
        setTextColor(BasicThemeManager.colors().textPrimary);
    }
}
