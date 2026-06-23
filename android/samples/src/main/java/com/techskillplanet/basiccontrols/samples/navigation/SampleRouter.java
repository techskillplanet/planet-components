package com.techskillplanet.basiccontrols.samples.navigation;

import android.os.Bundle;

import com.techskillplanet.basiccontrols.samples.SamplePageHost;

/**
 * Sample 路由状态机：集中管理页面跳转、TopBar 标题与旋转屏恢复。
 * <p>
 * 对齐 RN {@code AppRouter.js}：home / settings 为底部 Tab，detail 为组件详情栈。
 */
public final class SampleRouter {
    private static final String STATE_KIND = "sample_route_kind";
    private static final String STATE_COMPONENT = "sample_route_component";

    private SampleRoute route = SampleRoute.home();

    public SampleRoute route() {
        return route;
    }

    public void openHome() {
        route = SampleRoute.home();
    }

    public void openSettings() {
        route = SampleRoute.settings();
    }

    public void openDetail(String componentId) {
        route = SampleRoute.detail(componentId);
    }

    /** 从组件详情返回学习 Tab（与 RN onBack 一致）。 */
    public void back() {
        if (route.isDetail()) {
            openHome();
        }
    }

    /** 仅组件详情页显示 TopBar 返回键；Tab 页通过 BottomTab 切换。 */
    public boolean showBack() {
        return route.isDetail();
    }

    /** 详情页显示组件名；home / settings Tab 共用应用标题。 */
    public String topBarTitle(SamplePageHost host) {
        if (route.isDetail()) {
            return "Tsp" + route.componentId();
        }
        return host.t("sample/app/title");
    }

    public void saveInstanceState(Bundle outState) {
        outState.putInt(STATE_KIND, route.kind());
        if (route.isDetail()) {
            outState.putString(STATE_COMPONENT, route.componentId());
        }
    }

    public void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        int kind = savedInstanceState.getInt(STATE_KIND, SampleRoute.HOME);
        if (kind == SampleRoute.SETTINGS) {
            route = SampleRoute.settings();
            return;
        }
        if (kind == SampleRoute.DETAIL) {
            String componentId = savedInstanceState.getString(STATE_COMPONENT);
            if (componentId != null && componentId.length() > 0) {
                route = SampleRoute.detail(componentId);
                return;
            }
        }
        route = SampleRoute.home();
    }
}
