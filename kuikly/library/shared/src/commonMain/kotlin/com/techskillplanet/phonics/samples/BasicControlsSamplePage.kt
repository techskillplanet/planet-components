package com.techskillplanet.phonics.samples

import com.tencent.kuikly.compose.ComposeContainer
import com.tencent.kuikly.compose.setContent
import com.tencent.kuikly.core.annotations.Page

/**
 * 基础组件小程序预览页。
 *
 * 这个页面把 Compose DSL 写成的 `BasicControlsSample` 注册成 Kuikly 页面，
 * 微信小程序壳通过 pageName=BasicControlsSample 找到该页面并完成渲染。
 */
@Page("BasicControlsSample")
class BasicControlsSamplePage : ComposeContainer() {

    /**
     * Kuikly 页面初始化回调。
     *
     * 在这里挂载 Compose 内容，避免小程序侧只生成 JS 包但没有可路由页面。
     */
    override fun willInit() {
        super.willInit()
        setContent {
            BasicControlsSample()
        }
    }
}
