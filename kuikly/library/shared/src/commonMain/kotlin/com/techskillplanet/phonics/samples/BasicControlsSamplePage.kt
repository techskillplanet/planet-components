package com.techskillplanet.phonics.samples

import com.tencent.kuikly.compose.ComposeContainer
import com.tencent.kuikly.compose.setContent
import com.tencent.kuikly.core.annotations.Page

/**
 * 基础组件预览页。
 *
 * 安全区只消费一次：优先用 Android 宿主传入的 dp，
 * 不再把 Kuikly 的 navigationBarHeight（应用顶栏高度）当成系统底栏。
 */
@Page("BasicControlsSample")
class BasicControlsSamplePage : ComposeContainer() {

    override fun willInit() {
        super.willInit()
        setContent {
            BasicControlsSample(
                statusBarHeight = resolveTopInset(),
                navigationBarHeight = resolveBottomInset(),
                initialComponent = pageData.params.optString("openComponent"),
                initialTab = pageData.params.optString("openTab"),
            )
        }
    }

    private fun resolveTopInset(): Float {
        val host = pageData.params.optDouble("hostStatusBarDp", 0.0).toFloat()
        if (host > 0f) return host.coerceIn(0f, 64f)
        val safe = pageData.safeAreaInsets.top
        if (safe > 0f) return safe.coerceIn(0f, 64f)
        return pageData.statusBarHeight.coerceIn(0f, 64f)
    }

    private fun resolveBottomInset(): Float {
        val host = pageData.params.optDouble("hostNavBarDp", 0.0).toFloat()
        if (host > 0f) return host.coerceIn(0f, 16f)
        val safe = pageData.safeAreaInsets.bottom
        if (safe > 0f) return safe.coerceIn(0f, 16f)
        return pageData.androidBottomBavBarHeight.coerceIn(0f, 16f)
    }
}
