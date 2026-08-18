package com.techskillplanet.planetcomponents.kuikly.sample

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.View
import android.view.ViewGroup
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.tencent.kuikly.core.render.android.expand.KuiklyBaseView

class BasicControlsActivity : AppCompatActivity() {
    private var kuiklyView: KuiklyBaseView? = null
    private var pageAttached = false

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
        )
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        window.decorView.setBackgroundColor(PAGE_START)
        val view = KuiklyBaseView(this)
        view.fitsSystemWindows = false
        view.setBackgroundColor(PAGE_START)
        kuiklyView = view
        setContentView(
            view,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            ),
        )
        val content = findViewById<View>(android.R.id.content)
        content.fitsSystemWindows = false
        content.setBackgroundColor(PAGE_START)
        ViewCompat.setOnApplyWindowInsetsListener(content) { host, insets ->
            host.setPadding(0, 0, 0, 0)
            attachPage(view, insets)
            WindowInsetsCompat.CONSUMED
        }
        ViewCompat.requestApplyInsets(content)
        content.post {
            if (!pageAttached) {
                val fallback = ViewCompat.getRootWindowInsets(content) ?: WindowInsetsCompat.CONSUMED
                attachPage(view, fallback)
            }
        }
    }

    private fun attachPage(view: KuiklyBaseView, insets: WindowInsetsCompat) {
        if (pageAttached) return
        pageAttached = true
        val density = resources.displayMetrics.density.coerceAtLeast(0.5f)
        val statusDp = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top / density
        val navDp = (insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom / density)
            .coerceIn(0f, 16f)
        val windowSize = currentWindowSize()
        Log.i(TAG, "host insets statusDp=$statusDp navDp=$navDp size=${windowSize.width}x${windowSize.height} density=$density")
        view.onAttach(
            "",
            intent.getStringExtra(KEY_PAGE_NAME) ?: DEFAULT_PAGE,
            mapOf(
                "appId" to 1,
                "hostStatusBarDp" to statusDp,
                "hostNavBarDp" to navDp,
                "openComponent" to (intent.getStringExtra(KEY_OPEN_COMPONENT) ?: ""),
                "openTab" to (intent.getStringExtra(KEY_OPEN_TAB) ?: ""),
            ),
            windowSize,
            null,
        )
    }

    private fun currentWindowSize(): Size {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val bounds = windowManager.currentWindowMetrics.bounds
            return Size(bounds.width(), bounds.height())
        }
        val metrics = resources.displayMetrics
        return Size(metrics.widthPixels, metrics.heightPixels)
    }

    override fun onResume() {
        super.onResume()
        kuiklyView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        kuiklyView?.onPause()
    }

    override fun onDestroy() {
        kuiklyView?.onDetach()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "PlanetE2E"
        private const val KEY_PAGE_NAME = "pageName"
        private const val KEY_OPEN_COMPONENT = "openComponent"
        private const val KEY_OPEN_TAB = "openTab"
        private const val DEFAULT_PAGE = "BasicControlsSample"
        private const val PAGE_START = 0xFFDDF4FF.toInt()

        fun start(context: Context, pageName: String) {
            val intent = Intent(context, BasicControlsActivity::class.java)
            intent.putExtra(KEY_PAGE_NAME, pageName)
            if (context !is android.app.Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }
}
