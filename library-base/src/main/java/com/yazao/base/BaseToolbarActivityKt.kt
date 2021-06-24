package com.yazao.base

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.appcompat.widget.Toolbar
import com.yazao.base.util.SystemBarTintManager
import com.yazao.base.weight.loading.LoadingDialog
import com.yazao.lib.xbase.BaseActivityKt


abstract class BaseToolbarActivityKt<DB : androidx.databinding.ViewDataBinding> : BaseActivityKt<DB>() {
    lateinit var systemBarTintManager: SystemBarTintManager

    private lateinit var dialogLoading: LoadingDialog

    protected open fun setToolbar(toolbar: Toolbar, title: String = "") {
        //设置Toolbar
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //添加默认的返回图标
        supportActionBar!!.setHomeButtonEnabled(true) //设置返回键可用
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        systemBarTintManager = SystemBarTintManager(this)
        systemBarTintManager.isStatusBarTintEnabled = true
        systemBarTintManager.setStatusBarColorAuto(this)
        systemBarTintManager.setStatusBarTintColor(resources.getColor(R.color.color_main))

        setRootViewFitsSystemWindows(this, true)

        initLoadingView()
    }

    private fun initLoadingView() {
        dialogLoading = LoadingDialog()
        dialogLoading.initLoadingView(this)
    }

    fun setStatusBarColor(@ColorInt color: Int) {
        if (systemBarTintManager == null) {
            systemBarTintManager = SystemBarTintManager(this)
        }
        systemBarTintManager.setStatusBarTintColor(color)
    }

    open fun setRootViewFitsSystemWindows(activity: Activity, fitSystemWindows: Boolean) {
        if (Build.VERSION.SDK_INT >= 19) {

            val contentLayout: FrameLayout = activity.findViewById(android.R.id.content)

            if (contentLayout != null) {
                if (contentLayout.childCount > 0) {
                    val childAt: View? = contentLayout.getChildAt(0)
                    if (childAt != null) {
                        childAt.fitsSystemWindows = fitSystemWindows
                    }
                }
            }
        }
    }

    protected fun showLoading() {
        dialogLoading?.showLoading()
    }

    protected fun hideLoading() {
        dialogLoading?.hideLoading()
    }

    override fun onDestroy() {
        dialogLoading?.destroyLoading()
        super.onDestroy()
    }

}