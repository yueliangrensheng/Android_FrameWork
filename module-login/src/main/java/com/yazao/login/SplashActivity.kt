package com.yazao.login

import android.Manifest
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import androidx.databinding.ViewDataBinding
import com.permissionx.guolindev.PermissionX
import com.yazao.base.BaseApplication
import com.yazao.base.BaseToolbarActivityKt
import com.yazao.base.util.ACacheUtil


class SplashActivity : BaseToolbarActivityKt<ViewDataBinding>() {

    override fun isNoTitle(): Boolean = true
    override fun isFullScreen(): Boolean = false
    override fun isFitDataBinding(): Boolean = false

    override fun getLayoutID(): Int = R.layout.activity_splash_layout

    override fun initData() {
        setStatusBarColor(resources.getColor(android.R.color.transparent))
        mHandler.sendEmptyMessageDelayed(HANDLER_WHAT_DEFALUT, 2000)
    }

    private val HANDLER_WHAT_DEFALUT: Int = 0
    private val mHandler: Handler = Handler(Looper.getMainLooper(), Handler.Callback {
        when (it.what) {
            HANDLER_WHAT_DEFALUT -> {
                getState()
            }
        }
        return@Callback false
    })

    override fun onDestroy() {
        mHandler?.removeMessages(HANDLER_WHAT_DEFALUT)

        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 判断app是不是第一次启动
     */
    private fun getState() {
        val firstRun = ACacheUtil.getFirstRun(true)
        if (firstRun) {
            startDialog()
        } else {
//            showPermission()
            BaseApplication.application?.initOther()
            goLogin()
        }
    }

    /**
     * 权限弹框
     */
    private fun startDialog() {
//            showPermission()
        BaseApplication.application?.initOther()
        goLogin()
    }


    private fun showPermission() {
        PermissionX.init(this)
                .permissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .explainReasonBeforeRequest()
                .onExplainRequestReason { scope, deniedList ->
                    scope.showRequestReasonDialog(deniedList, "App需要这些权限，以便更好地服务", "确定", "取消")
                }
                .onForwardToSettings { scope, deniedList ->

                }
                .request { allGranted, grantedList, deniedList ->

                }
    }

    private fun goLogin() {
        //TODO  登录逻辑判断 或者 跳转到 主页面
        startActivity(Intent(SplashActivity@ this, LoginActivity::class.java))
        finish()
    }

}