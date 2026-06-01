package com.techskillplanet.basiccontrols.theme;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Token JSON 解析器。
 *
 * <p>它负责三件事：</p>
 * <ol>
 *     <li>按 path 读取 JSON 节点，例如 {@code semantic.brand.primary}。</li>
 *     <li>解析 {@code {primitive.sky.brandPrimary}} 这种引用语法。</li>
 *     <li>把颜色、dp、sp 转成 Android View 可直接使用的运行时值。</li>
 * </ol>
 *
 * <p>这个类只在主题初始化阶段使用。组件不要直接依赖它，组件应该依赖
 * {@link BasicColors} 和 {@link BasicStyle}，这样可以避免组件层散落 JSON path。</p>
 */
public final class BasicTokenResolver {
    private final JSONObject root;
    private final DisplayMetrics displayMetrics;

    /**
     * 创建一个 token 解析器。
     *
     * @param context 用于获取屏幕密度和字体缩放。
     * @param root 当前主题对象，通常是 {@code themes.sky_planet_day}。
     */
    public BasicTokenResolver(Context context, JSONObject root) {
        this.root = root;
        this.displayMetrics = context.getResources().getDisplayMetrics();
    }

    /**
     * 读取颜色 token，并解析中间可能存在的引用。
     *
     * @param path 相对当前主题根节点的路径，例如 {@code semantic.brand.primary}。
     * @return Android int 颜色。
     */
    public int color(String path) {
        String raw = string(path);
        String resolved = resolveReference(raw);
        return parseColor(resolved);
    }

    /**
     * 读取 dp token 并转换成 px。
     *
     * @param path 相对当前主题根节点的路径。
     * @return px 值，保留小数，方便 View 测量和绘制。
     */
    public float dp(String path) {
        return dp((float) number(path));
    }

    /**
     * 读取 sp token 并转换成 px。
     *
     * @param path 相对当前主题根节点的路径。
     * @return px 值，可直接传给 {@code TextView#setTextSize(COMPLEX_UNIT_PX, value)}。
     */
    public float sp(String path) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, (float) number(path), displayMetrics);
    }

    /**
     * 把一个 dp 数值转换成 px。
     *
     * @param valueDp dp 数值。
     * @return px 数值。
     */
    public float dp(float valueDp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueDp, displayMetrics);
    }

    /**
     * 按 path 读取字符串值。
     *
     * @param path JSON path。
     * @return 字符串值。
     */
    public String string(String path) {
        Object value = value(path);
        if (!(value instanceof String)) {
            throw new IllegalStateException("Token 不是字符串: " + path);
        }
        return (String) value;
    }

    /**
     * 按 path 读取数字值。
     *
     * @param path JSON path。
     * @return double 数字。
     */
    public double number(String path) {
        Object value = value(path);
        if (!(value instanceof Number)) {
            throw new IllegalStateException("Token 不是数字: " + path);
        }
        return ((Number) value).doubleValue();
    }

    /**
     * 读取任意 JSON path 对应的值。
     *
     * <p>这里使用点分路径，是为了让 Java 代码里的 path 和 JSON 层级保持一一对应。</p>
     */
    private Object value(String path) {
        try {
            String[] parts = path.split("\\.");
            JSONObject current = root;
            for (int i = 0; i < parts.length - 1; i++) {
                current = current.getJSONObject(parts[i]);
            }
            return current.get(parts[parts.length - 1]);
        } catch (JSONException error) {
            throw new IllegalStateException("找不到 token: " + path, error);
        }
    }

    /**
     * 解析 token 引用。
     *
     * <p>例如 {@code semantic.brand.primary} 的值可能是
     * {@code {primitive.sky.brandPrimary}}。组件最终需要的是颜色值，因此这里会继续
     * 查找 primitive 节点并返回真实的十六进制颜色。</p>
     */
    private String resolveReference(String raw) {
        if (raw == null || raw.length() < 3) {
            return raw;
        }
        if (!raw.startsWith("{") || !raw.endsWith("}")) {
            return raw;
        }
        String refPath = raw.substring(1, raw.length() - 1);
        String refValue = string(refPath);
        return resolveReference(refValue);
    }

    /**
     * 解析颜色字符串。
     *
     * <p>Android 原生 {@link Color#parseColor(String)} 支持 {@code #AARRGGBB}，
     * 但我们的 token 使用更直观的 {@code #RRGGBBAA}。因此 8 位颜色在这里手动解析，
     * 以便设计文件里的写法和 Android 运行时保持一致。</p>
     */
    private int parseColor(String color) {
        if (color == null || !color.startsWith("#")) {
            throw new IllegalStateException("颜色 token 格式不正确: " + color);
        }
        String clean = color.substring(1);
        if (clean.length() == 6) {
            return Color.rgb(
                    Integer.parseInt(clean.substring(0, 2), 16),
                    Integer.parseInt(clean.substring(2, 4), 16),
                    Integer.parseInt(clean.substring(4, 6), 16)
            );
        }
        if (clean.length() == 8) {
            int r = Integer.parseInt(clean.substring(0, 2), 16);
            int g = Integer.parseInt(clean.substring(2, 4), 16);
            int b = Integer.parseInt(clean.substring(4, 6), 16);
            int a = Integer.parseInt(clean.substring(6, 8), 16);
            return Color.argb(a, r, g, b);
        }
        throw new IllegalStateException("颜色 token 长度不支持: " + color);
    }
}
