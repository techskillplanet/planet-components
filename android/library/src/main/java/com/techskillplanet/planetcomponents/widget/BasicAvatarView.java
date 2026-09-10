package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 圆形头像：优先展示图片，否则展示姓名首字母。
 *
 * <p>对齐合约 Avatar（text / src / size / variant）。</p>
 */
public class BasicAvatarView extends FrameLayout {
    private final TextView labelView;
    private final ImageView imageView;
    private String variant = "default";
    private String sizeKey = "md";
    private CharSequence basicText = "";

    public BasicAvatarView(Context context) {
        this(context, null);
    }

    public BasicAvatarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicAvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        labelView = new TextView(context);
        labelView.setGravity(Gravity.CENTER);
        labelView.setTypeface(Typeface.DEFAULT_BOLD);
        labelView.setIncludeFontPadding(false);
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setVisibility(GONE);
        addView(labelView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(imageView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
        refreshTheme();
    }

    /** 设置变体：default / primary / subtle。 */
    public void setVariant(String variant) {
        this.variant = variant == null ? "default" : variant;
        refreshTheme();
    }

    /** 设置尺寸键：sm / md / lg。 */
    public void setSizeKey(String size) {
        this.sizeKey = size == null ? "md" : size;
        refreshTheme();
    }

    /** 设置头像文字（取前两个字符为大写首字母）。 */
    public void setBasicText(CharSequence text) {
        basicText = text == null ? "" : text;
        refreshTheme();
    }

    /** 设置头像图片；非空时优先展示图片。 */
    public void setAvatarDrawable(Drawable drawable) {
        imageView.setImageDrawable(drawable);
        imageView.setVisibility(drawable == null ? GONE : VISIBLE);
        labelView.setVisibility(drawable == null ? VISIBLE : GONE);
        refreshTheme();
    }

    /** 按 token 刷新圆形背景与文字。 */
    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        if (colors == null || style == null) {
            return;
        }
        int dim = resolveSizePx(style);
        LayoutParams params = (LayoutParams) getLayoutParams();
        if (params == null) {
            params = new LayoutParams(dim, dim);
        } else {
            params.width = dim;
            params.height = dim;
        }
        setLayoutParams(params);
        setMinimumWidth(dim);
        setMinimumHeight(dim);

        int fill;
        int textColor;
        if ("primary".equals(variant)) {
            fill = colors.brandPrimary;
            textColor = colors.textInverse;
        } else if ("subtle".equals(variant)) {
            fill = colors.brandPrimarySubtle;
            textColor = colors.brandPrimary;
        } else {
            fill = colors.backgroundSurfaceSubtle;
            textColor = colors.textPrimary;
        }
        setBackground(BasicDrawableFactory.roundedFill(fill, dim / 2f));
        String initial = initials(basicText);
        labelView.setText(initial);
        labelView.setTextColor(textColor);
        labelView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dim * 0.38f);
        setContentDescription(basicText.length() == 0 ? "Avatar" : basicText);
        setClipToOutline(false);
    }

    private int resolveSizePx(BasicStyle style) {
        if ("sm".equals(sizeKey)) {
            return Math.round(style.controlHeightSm);
        }
        if ("lg".equals(sizeKey)) {
            return Math.round(style.controlHeightLg);
        }
        return Math.round(style.controlHeightMd);
    }

    private static String initials(CharSequence text) {
        String raw = text == null ? "" : text.toString().trim();
        if (raw.isEmpty()) {
            return "?";
        }
        if (raw.length() == 1) {
            return raw.toUpperCase();
        }
        return raw.substring(0, Math.min(2, raw.length())).toUpperCase();
    }
}
