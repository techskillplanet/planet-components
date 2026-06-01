package com.techskillplanet.basiccontrols.theme;

import android.content.Context;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Basic Controls 主题入口。
 *
 * <p>所有组件在刷新样式时都会读取这个类缓存的 {@link BasicColors} 和
 * {@link BasicStyle}。应用必须在使用组件前调用 {@link #init(Context)}，这样组件不会
 * 在运行过程中重复解析 JSON。</p>
 */
public final class BasicThemeManager {
    private static final String COLOR_ASSET = "theme/color_token.json";
    private static final String STYLE_ASSET = "theme/style_token.json";
    private static final String DEFAULT_THEME = "sky_planet_day";

    private static BasicColors colors;
    private static BasicStyle style;

    private BasicThemeManager() {
        // 工具类不允许实例化。
    }

    /**
     * 初始化默认主题。
     *
     * @param context 任意 Context。内部会转成 applicationContext，避免泄漏 Activity。
     */
    public static void init(Context context) {
        init(context, DEFAULT_THEME);
    }

    /**
     * 初始化指定主题。
     *
     * <p>当前 JSON 已经按照 themes 组织，后续换肤时只要新增 theme 对象，
     * 再传入对应 themeName 即可。</p>
     *
     * @param context 任意 Context。
     * @param themeName themes 下的主题名称。
     */
    public static void init(Context context, String themeName) {
        Context appContext = context.getApplicationContext();
        try {
            JSONObject colorRoot = new JSONObject(readAsset(appContext, COLOR_ASSET));
            JSONObject styleRoot = new JSONObject(readAsset(appContext, STYLE_ASSET));
            JSONObject colorTheme = colorRoot.getJSONObject("themes").getJSONObject(themeName);
            BasicTokenResolver colorResolver = new BasicTokenResolver(appContext, colorTheme);
            BasicTokenResolver styleResolver = new BasicTokenResolver(appContext, styleRoot);
            colors = createColors(colorResolver);
            style = createStyle(styleResolver);
        } catch (Exception error) {
            throw new IllegalStateException("BasicControls 主题初始化失败", error);
        }
    }

    /**
     * 获取当前颜色集合。
     *
     * @return 已解析颜色。
     */
    public static BasicColors colors() {
        if (colors == null) {
            throw new IllegalStateException("请先调用 BasicThemeManager.init(context)");
        }
        return colors;
    }

    /**
     * 获取当前样式集合。
     *
     * @return 已解析样式。
     */
    public static BasicStyle style() {
        if (style == null) {
            throw new IllegalStateException("请先调用 BasicThemeManager.init(context)");
        }
        return style;
    }

    /**
     * 读取 assets 文件。
     *
     * <p>这里不用第三方 IO 工具，避免为了一个小库增加依赖。</p>
     */
    private static String readAsset(Context context, String path) throws IOException {
        try (InputStream input = context.getAssets().open(path);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int count;
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
            return new String(output.toByteArray(), StandardCharsets.UTF_8);
        }
    }

    /** 把 color_token.json 的语义 token 映射成 Java 字段。 */
    private static BasicColors createColors(BasicTokenResolver r) {
        return new BasicColors(
                r.color("semantic.brand.primary"),
                r.color("semantic.brand.primaryHover"),
                r.color("semantic.brand.primaryActive"),
                r.color("semantic.brand.primarySubtle"),
                r.color("semantic.text.primary"),
                r.color("semantic.text.secondary"),
                r.color("semantic.text.tertiary"),
                r.color("semantic.text.inverse"),
                r.color("semantic.text.disabled"),
                r.color("semantic.background.page"),
                r.color("semantic.background.pageGradientEnd"),
                r.color("semantic.background.surface"),
                r.color("semantic.background.surfaceRaised"),
                r.color("semantic.background.surfaceSubtle"),
                r.color("semantic.background.surfaceDisabled"),
                r.color("semantic.background.menu"),
                r.color("semantic.background.modalScrim"),
                r.color("semantic.border.default"),
                r.color("semantic.border.light"),
                r.color("semantic.border.control"),
                r.color("semantic.border.focus"),
                r.color("semantic.border.warning"),
                r.color("semantic.border.danger"),
                r.color("semantic.status.success"),
                r.color("semantic.status.warning"),
                r.color("semantic.status.danger"),
                r.color("semantic.control.button.defaultBackground"),
                r.color("semantic.control.button.defaultText"),
                r.color("semantic.control.button.primaryBackground"),
                r.color("semantic.control.button.primaryText"),
                r.color("semantic.control.button.raisedShadow"),
                r.color("semantic.control.checkbox.background"),
                r.color("semantic.control.checkbox.checkedBackground"),
                r.color("semantic.control.radio.checkedDot"),
                r.color("semantic.control.switch.offBackground"),
                r.color("semantic.control.switch.offBorder"),
                r.color("semantic.control.switch.offText"),
                r.color("semantic.control.switch.onBackground"),
                r.color("semantic.control.switch.onBorder"),
                r.color("semantic.control.switch.onText"),
                r.color("semantic.control.switch.handleBackground"),
                r.color("semantic.control.switch.handleBorder"),
                r.color("semantic.control.switch.handleCheckedBorder"),
                r.color("semantic.control.switch.loadingSpinner"),
                r.color("semantic.control.select.selectedOptionBackground"),
                r.color("semantic.control.loading.stripePrimary"),
                r.color("semantic.control.loading.stripeSecondary"),
                r.color("semantic.control.loading.border")
        );
    }

    /** 把 style_token.json 的样式 token 映射成 Java 字段。 */
    private static BasicStyle createStyle(BasicTokenResolver r) {
        return new BasicStyle(
                r.dp("radius.sm"),
                r.dp("radius.md"),
                r.dp("radius.lg"),
                r.dp("radius.xl"),
                r.dp("radius.cardOrganic"),
                r.dp("radius.dialogOrganic"),
                r.dp("radius.controlIsland"),
                r.dp("radius.pill"),
                r.dp("border.width.default"),
                r.dp("border.width.hairline"),
                r.dp("border.width.focus"),
                r.dp("border.width.switch"),
                r.dp("size.controlHeight.sm"),
                r.dp("size.controlHeight.md"),
                r.dp("size.controlHeight.buttonMedium"),
                r.dp("size.controlHeight.lg"),
                r.dp("gap.sm"),
                r.dp("gap.md"),
                r.dp("gap.lg"),
                r.dp("gap.xl"),
                r.sp("font.size.sm"),
                r.sp("font.size.md"),
                r.sp("font.size.lg"),
                r.sp("font.size.title"),
                r.sp("font.size.dialogTitle"),
                r.dp("size.badge.height"),
                r.dp("size.chip.height"),
                r.dp("size.toast.minHeight"),
                r.dp("size.navigation.tabHeight"),
                r.dp("size.table.rowHeight"),
                r.dp("size.switch.sm.width"),
                r.dp("size.switch.sm.height"),
                r.dp("size.switch.sm.handle"),
                r.sp("size.switch.sm.innerText"),
                r.dp("size.switch.md.width"),
                r.dp("size.switch.md.height"),
                r.dp("size.switch.md.handle"),
                r.sp("size.switch.md.innerText"),
                r.dp("shadow.controlIslandLift.y"),
                r.dp("shadow.controlPressed.y"),
                r.dp("motion.interaction.pressedDropY"),
                (float) r.number("opacity.switch.enabled"),
                (float) r.number("opacity.switch.loading"),
                (float) r.number("opacity.switch.disabled"),
                Math.round(r.number("motion.switch.duration"))
        );
    }
}
