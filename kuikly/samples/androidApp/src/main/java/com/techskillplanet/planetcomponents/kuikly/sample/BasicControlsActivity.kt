package com.techskillplanet.planetcomponents.kuikly.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Size
import androidx.appcompat.app.AppCompatActivity
import com.tencent.kuikly.core.render.android.expand.KuiklyBaseView

class BasicControlsActivity : AppCompatActivity() {
    private var kuiklyView: KuiklyBaseView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = KuiklyBaseView(this)
        kuiklyView = view
        setContentView(view)
        view.onAttach(
            "",
            intent.getStringExtra(KEY_PAGE_NAME) ?: DEFAULT_PAGE,
            mapOf("appId" to 1),
            null as Size?,
            null,
        )
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
        private const val KEY_PAGE_NAME = "pageName"
        private const val DEFAULT_PAGE = "BasicControlsSample"

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
