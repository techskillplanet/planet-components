package com.techskillplanet.planetcomponents.system;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import com.techskillplanet.planetcomponents.theme.BasicColors;

/**
 * 传统 View 体系的 edge-to-edge 窗口配置。
 *
 * <p>不依赖 AppCompat / Material，供 Sample 与接入方 Activity 在 {@code setContentView}
 * 之后调用，让 {@link com.techskillplanet.planetcomponents.widget.BasicTopBarView}
 * 自行读取状态栏 inset 并绘制沉浸式顶栏。</p>
 */
public final class BasicEdgeToEdgeHelper {
    private BasicEdgeToEdgeHelper() {
    }

    /**
     * 启用 edge-to-edge：内容延伸到系统栏下方，由 TopBar 等组件处理 inset。
     *
     * @param activity 当前 Activity。
     * @param lightStatusBarIcons 是否使用深色状态栏图标（浅色顶栏时为 true）。
     */
    public static void applyWindow(Activity activity, boolean lightStatusBarIcons) {
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        applyStatusBarIconAppearance(window, lightStatusBarIcons);
    }

    /**
     * 根据顶栏背景亮度推断状态栏图标颜色。
     *
     * @param colors 当前主题颜色。
     * @return 浅色顶栏返回 true（深色图标），深色顶栏返回 false。
     */
    public static boolean shouldUseLightStatusBarIcons(BasicColors colors) {
        if (colors == null) {
            return true;
        }
        return relativeLuminance(colors.backgroundSurfaceRaised) >= 0.55d;
    }

    /**
     * 读取状态栏 inset，供 TopBar 计算沉浸式高度。
     *
     * @param view 任意已附着窗口的 View。
     * @param insets 系统 insets。
     * @return 状态栏高度 px。
     */
    public static int getStatusBarInset(View view, android.view.WindowInsets insets) {
        if (insets == null) {
            return 0;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return insets.getInsets(WindowInsets.Type.statusBars()).top;
        }
        return insets.getSystemWindowInsetTop();
    }

    /**
     * 读取导航栏 inset，供 BottomTab 等底部组件留出安全区。
     */
    public static int getNavigationBarInset(View view, android.view.WindowInsets insets) {
        if (insets == null) {
            return 0;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return insets.getInsets(WindowInsets.Type.navigationBars()).bottom;
        }
        return insets.getSystemWindowInsetBottom();
    }

    private static void applyStatusBarIconAppearance(Window window, boolean lightStatusBarIcons) {
        applyStatusBarIconAppearance(window, lightStatusBarIcons, false);
    }

    private static void applyStatusBarIconAppearance(
            Window window,
            boolean lightStatusBarIcons,
            boolean deferred
    ) {
        View decor = window.getDecorView();
        if (decor == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            android.view.WindowInsetsController controller = decor.getWindowInsetsController();
            if (controller == null) {
                if (!deferred) {
                    decor.post(() -> applyStatusBarIconAppearance(window, lightStatusBarIcons, true));
                }
                return;
            }
            int mask = android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;
            controller.setSystemBarsAppearance(lightStatusBarIcons ? mask : 0, mask);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = decor.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (lightStatusBarIcons) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decor.setSystemUiVisibility(flags);
        }
    }

    private static double relativeLuminance(int color) {
        double r = Color.red(color) / 255d;
        double g = Color.green(color) / 255d;
        double b = Color.blue(color) / 255d;
        return 0.2126d * r + 0.7152d * g + 0.0722d * b;
    }
}
