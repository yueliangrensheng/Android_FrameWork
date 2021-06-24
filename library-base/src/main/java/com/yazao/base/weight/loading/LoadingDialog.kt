package com.yazao.base.weight.loading

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import com.yazao.base.R
import com.yazao.dialog.XDialog

class LoadingDialog {

    private lateinit var loadingDialog: XDialog
    private lateinit var builder: XDialog.Builder

    fun initLoadingView(context: Activity) {
        builder = XDialog.Builder(context)
                .setLayoutRes(R.layout.dialog_loading_layout)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setGravity(Gravity.CENTER)
                .setSize(context.resources.getDimensionPixelOffset(R.dimen.dp_80), context.resources.getDimensionPixelOffset(R.dimen.dp_80))

        builder.getView<LVCircularRing>(R.id.loading_view).setBarColor(Color.parseColor("#60C062"))
        builder.getView<LVCircularRing>(R.id.loading_view).setViewColor(Color.argb(100, 255, 255, 255))

        loadingDialog = builder.build()

    }

    fun showLoading() {
        builder.getView<LVCircularRing>(R.id.loading_view).startAnim()
        builder?.show()
    }

    fun hideLoading() {
        builder.getView<LVCircularRing>(R.id.loading_view).stopAnim()
        builder?.hide()
    }

    fun destroyLoading() {
        loadingDialog?.onDestroy()
    }
}