package com.techskillplanet.basiccontrols.drawable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Basic Controls 的 Drawable 工厂。
 *
 * <p>传统 Android View 体系里，圆角、边框、按压态大多通过 Drawable 完成。
 * 这个工厂把创建规则集中起来，组件只需要传入 token 解析后的颜色和尺寸即可。</p>
 */
public final class BasicDrawableFactory {
    private BasicDrawableFactory() {
        // 工具类不允许实例化。
    }

    /**
     * 创建一个矩形圆角填充 Drawable。
     *
     * @param fillColor 填充色。
     * @param radiusPx 圆角 px。
     * @return 可设置到 View background 的 Drawable。
     */
    public static GradientDrawable roundedFill(int fillColor, float radiusPx) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(fillColor);
        drawable.setCornerRadius(radiusPx);
        return drawable;
    }

    /**
     * 创建一个矩形圆角填充 + 边框 Drawable。
     *
     * @param fillColor 填充色。
     * @param strokeColor 边框色。
     * @param strokeWidthPx 边框宽度 px。
     * @param radiusPx 圆角 px。
     * @return 可设置到 View background 的 Drawable。
     */
    public static GradientDrawable roundedFillStroke(
            int fillColor,
            int strokeColor,
            float strokeWidthPx,
            float radiusPx
    ) {
        GradientDrawable drawable = roundedFill(fillColor, radiusPx);
        drawable.setStroke(Math.round(strokeWidthPx), strokeColor);
        return drawable;
    }

    /**
     * 创建圆形填充 + 边框 Drawable。
     *
     * <p>Checkbox、Radio、Switch knob 这类控件需要稳定的圆形外观，
     * 使用 OVAL shape 可以避免在不同尺寸下手动计算半径。</p>
     */
    public static GradientDrawable ovalFillStroke(
            int fillColor,
            int strokeColor,
            float strokeWidthPx
    ) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(fillColor);
        drawable.setStroke(Math.round(strokeWidthPx), strokeColor);
        return drawable;
    }

    /**
     * 创建 enabled / disabled 两态背景。
     *
     * <p>StateListDrawable 的匹配顺序是从上到下，因此必须先放特殊状态，
     * 最后再放默认状态。否则默认状态会提前命中，禁用态永远不会显示。</p>
     */
    public static StateListDrawable enabledDisabled(Drawable enabled, Drawable disabled) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{-android.R.attr.state_enabled}, disabled);
        states.addState(new int[]{}, enabled);
        return states;
    }

    /**
     * 创建 pressed / disabled / default 三态背景。
     *
     * <p>这里同样要求状态顺序明确：disabled 优先级最高，其次 pressed，
     * 最后 default。这样组件在禁用时不会再响应 pressed 视觉。</p>
     */
    public static StateListDrawable pressedEnabledDisabled(
            Drawable normal,
            Drawable pressed,
            Drawable disabled
    ) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{-android.R.attr.state_enabled}, disabled);
        states.addState(new int[]{android.R.attr.state_pressed}, pressed);
        states.addState(new int[]{}, normal);
        return states;
    }

    /**
     * 创建 selected / default 两态背景。
     *
     * @param normal 默认背景。
     * @param selected 选中背景。
     * @return 状态背景。
     */
    public static StateListDrawable selected(Drawable normal, Drawable selected) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_selected}, selected);
        states.addState(new int[]{}, normal);
        return states;
    }
}
