package com.techskillplanet.planetcomponents.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.techskillplanet.planetcomponents.R;
import com.techskillplanet.planetcomponents.drawable.BasicDrawableFactory;
import com.techskillplanet.planetcomponents.theme.BasicColors;
import com.techskillplanet.planetcomponents.theme.BasicStyle;
import com.techskillplanet.planetcomponents.theme.BasicThemeManager;

/**
 * 带搜索图标的圆角搜索栏，适用于历史记录、收藏夹等列表筛选场景。
 */
public class BasicSearchBarView extends FrameLayout {
    public interface OnQueryChangeListener {
        void onQueryChanged(BasicSearchBarView view, String query);
    }

    private final LinearLayout container;
    private final ImageView iconView;
    private final EditText editText;
    private OnQueryChangeListener queryListener;

    public BasicSearchBarView(Context context) {
        this(context, null);
    }

    public BasicSearchBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicSearchBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClipChildren(true);
        setClipToPadding(true);

        container = new LinearLayout(context);
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setGravity(Gravity.CENTER_VERTICAL);

        iconView = new ImageView(context);
        iconView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        iconView.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_NO);

        editText = new EditText(context);
        editText.setSingleLine(true);
        editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setHint("搜索...");
        editText.setIncludeFontPadding(false);
        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (queryListener != null) {
                    queryListener.onQueryChanged(BasicSearchBarView.this, s == null ? "" : s.toString());
                }
            }
        });

        container.addView(iconView, new LinearLayout.LayoutParams(dp(40), LinearLayout.LayoutParams.MATCH_PARENT));
        container.addView(editText, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
        addView(container, new LayoutParams(LayoutParams.MATCH_PARENT, dp(48)));
        refreshTheme();
    }

    public EditText getEditText() {
        return editText;
    }

    public void setHint(CharSequence hint) {
        editText.setHint(hint);
    }

    public String getQuery() {
        return editText.getText() == null ? "" : editText.getText().toString();
    }

    public void setOnQueryChangeListener(OnQueryChangeListener listener) {
        queryListener = listener;
    }

    public void refreshTheme() {
        BasicColors colors = BasicThemeManager.colors();
        BasicStyle style = BasicThemeManager.style();
        if (colors == null || style == null) {
            return;
        }

        int height = Math.round(style.searchBarHeight);
        int iconWidth = Math.round(style.searchBarIconWidth);
        int padH = Math.round(style.spaceMd);

        FrameLayout.LayoutParams containerParams = (FrameLayout.LayoutParams) container.getLayoutParams();
        if (containerParams == null) {
            containerParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, height);
        }
        containerParams.height = height;
        container.setLayoutParams(containerParams);

        container.setPadding(padH, 0, padH, 0);
        container.setBackground(BasicDrawableFactory.roundedFillStroke(
                colors.backgroundSurface,
                colors.borderControl,
                style.borderHairline,
                style.radiusPill
        ));

        Drawable searchIcon = loadDrawable(R.drawable.ic_basic_search);
        if (searchIcon != null) {
            searchIcon = searchIcon.mutate();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                searchIcon.setTint(colors.textTertiary);
            }
            iconView.setImageDrawable(searchIcon);
        }

        LinearLayout.LayoutParams iconParams = (LinearLayout.LayoutParams) iconView.getLayoutParams();
        iconParams.width = iconWidth;
        iconView.setLayoutParams(iconParams);

        editText.setTextColor(colors.textPrimary);
        editText.setHintTextColor(colors.textTertiary);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textMd);
        editText.setTypeface(Typeface.DEFAULT);
        editText.setPadding(0, 0, Math.round(style.spaceSm), 0);
    }

    private Drawable loadDrawable(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getContext().getDrawable(resId);
        }
        return getResources().getDrawable(resId);
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }
}
