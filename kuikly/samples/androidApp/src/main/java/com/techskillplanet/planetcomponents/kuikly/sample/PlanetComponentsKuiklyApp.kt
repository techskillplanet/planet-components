package com.techskillplanet.planetcomponents.kuikly.sample

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import com.tencent.kuikly.core.render.android.adapter.HRImageLoadOption
import com.tencent.kuikly.core.render.android.adapter.IKRImageAdapter
import com.tencent.kuikly.core.render.android.adapter.IKRLogAdapter
import com.tencent.kuikly.core.render.android.adapter.IKRRouterAdapter
import com.tencent.kuikly.core.render.android.adapter.IKRThreadAdapter
import com.tencent.kuikly.core.render.android.adapter.IKRUncaughtExceptionHandlerAdapter
import com.tencent.kuikly.core.render.android.adapter.KuiklyRenderAdapterManager
import org.json.JSONObject
import java.util.concurrent.Executors

class PlanetComponentsKuiklyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KuiklyRenderAdapterManager.krImageAdapter = ImageAdapter
        KuiklyRenderAdapterManager.krLogAdapter = LogAdapter
        KuiklyRenderAdapterManager.krUncaughtExceptionHandlerAdapter = ExceptionAdapter
        KuiklyRenderAdapterManager.krRouterAdapter = RouterAdapter
        KuiklyRenderAdapterManager.krThreadAdapter = ThreadAdapter()
    }
}

private object ImageAdapter : IKRImageAdapter {
    override fun fetchDrawable(
        imageLoadOption: HRImageLoadOption,
        callback: (drawable: Drawable?) -> Unit,
    ) {
        callback(null)
    }
}

private object LogAdapter : IKRLogAdapter {
    override val asyncLogEnable: Boolean = true
    override fun i(tag: String, msg: String) { Log.i(tag, msg) }
    override fun d(tag: String, msg: String) { Log.d(tag, msg) }
    override fun e(tag: String, msg: String) { Log.e(tag, msg) }
}

private object ExceptionAdapter : IKRUncaughtExceptionHandlerAdapter {
    override fun uncaughtException(throwable: Throwable) {
        Log.e("KRExceptionHandler", throwable.stackTraceToString())
    }
}

private object RouterAdapter : IKRRouterAdapter {
    override fun openPage(context: Context, pageName: String, pageData: JSONObject) {
        BasicControlsActivity.start(context, pageName)
    }

    override fun closePage(context: Context) {
        (context as? Activity)?.finish()
    }
}

private class ThreadAdapter : IKRThreadAdapter {
    private val executor = Executors.newFixedThreadPool(2)

    override fun executeOnSubThread(task: () -> Unit) {
        executor.execute(task)
    }

    override fun stackSize(): Long = 8L * 1024L * 1024L
}
