import com.tencent.kuikly.core.render.web.collection.FastMutableMap
import com.tencent.kuikly.core.render.web.ktx.SizeI
import com.tencent.kuikly.core.render.web.runtime.miniapp.MiniDocument
import com.tencent.kuikly.core.render.web.runtime.miniapp.core.App
import com.tencent.kuikly.core.render.web.runtime.miniapp.core.NativeApi
import com.tencent.kuikly.miniapp.KuiklyWebRenderViewDelegator

const val TAG = "Main"

fun main() {
    App.onShow {
        console.log(TAG, "app show")
    }

    App.onLaunch {
        console.log(TAG, "app launch")
    }

    App.onHide {
        console.log(TAG, "app hide")
    }
}

/**
 *  Mini program page entry, use renderView delegate method to initialize and create renderView
 */
@JsName(name = "renderView")
@JsExport
@ExperimentalJsExport
fun renderView(json: dynamic) {
    // View size
    var size: SizeI? = null
    if (json.width != null && json.height != null) {
        size = SizeI(json.width.unsafeCast<Int>(), json.height.unsafeCast<Int>())
    }

    val options = FastMutableMap<String, Any>(mutableMapOf<String, Any>())
    if (json.pageName != null) {
        options["pageName"] = json.pageName.unsafeCast<String>()
    }
    if (json.width != null) {
        options["width"] = json.width.unsafeCast<Int>()
    }
    if (json.height != null) {
        options["height"] = json.height.unsafeCast<Int>()
    }

    MiniDocument.initPage(options) { pageId: Int, pageName: String, paramsMap: FastMutableMap<String, Any> ->
        val systemInfo = NativeApi.plat.getSystemInfoSync()
        val isAndroid = systemInfo.platform == "android"
        val params = paramsMap["param"].unsafeCast<FastMutableMap<String, Any>>()
        params["is_wx_mp"] = "true"

        // paramsMap["platform"] = if (isAndroid) "android" else "iOS"
        paramsMap["platform"] = "miniprogram"
        paramsMap["isIOS"] = !isAndroid
        paramsMap["isIphoneX"] = !isAndroid && systemInfo.safeArea.top > 30

        KuiklyWebRenderViewDelegator().delegate.onAttach(
            pageId,
            pageName,
            paramsMap,
            size,
        )
    }
}

/**
 * Register callback methods on the mini program App object, needs to be called in the app.js of the mini program
 */
@JsName(name = "initApp")
@JsExport
@ExperimentalJsExport
fun initApp(options: dynamic = js("{}")) {
    App.initApp(options)
}
