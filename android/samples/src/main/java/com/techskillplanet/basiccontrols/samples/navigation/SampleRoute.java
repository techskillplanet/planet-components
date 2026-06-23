package com.techskillplanet.basiccontrols.samples.navigation;

/**
 * Sample 路由快照（不可变值对象）。
 * <p>
 * 表示「当前在哪一页」，不包含跳转逻辑。跳转由 {@link SampleRouter} 负责。
 * 与 React Native Sample 的 {@code { name: 'home' | 'settings' | 'detail', component? }} 对应。
 */
public final class SampleRoute {
    public static final int HOME = 0;
    public static final int SETTINGS = 1;
    public static final int DETAIL = 2;

    private final int kind;
    /** 仅 {@link #DETAIL} 时有值，例如 {@code "Button"}。 */
    private final String componentId;

    private SampleRoute(int kind, String componentId) {
        this.kind = kind;
        this.componentId = componentId;
    }

    public static SampleRoute home() {
        return new SampleRoute(HOME, null);
    }

    public static SampleRoute settings() {
        return new SampleRoute(SETTINGS, null);
    }

    public static SampleRoute detail(String componentId) {
        return new SampleRoute(DETAIL, componentId);
    }

    public int kind() {
        return kind;
    }

    public String componentId() {
        return componentId;
    }

    public boolean isHome() {
        return kind == HOME;
    }

    public boolean isSettings() {
        return kind == SETTINGS;
    }

    public boolean isDetail() {
        return kind == DETAIL;
    }
}
