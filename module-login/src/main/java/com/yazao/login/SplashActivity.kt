package com.yazao.login

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.databinding.ViewDataBinding
import com.yazao.lib.xbase.BaseActivity


class SplashActivity() : BaseActivity<ViewDataBinding>() {

    override fun isNoTitle(): Boolean = true
    override fun isFullScreen(): Boolean = true
    override fun isFitDataBinding(): Boolean = false

    override fun getLayoutID(): Int = R.layout.activity_splash_layout

    override fun initData() {
        mHandler.sendEmptyMessageDelayed(HANDLER_WHAT_DEFALUT, 2000)
    }

    private val HANDLER_WHAT_DEFALUT: Int = 0
    private val mHandler: Handler = Handler(Looper.getMainLooper(), Handler.Callback {
        when (it.what) {
            HANDLER_WHAT_DEFALUT -> {
                //TODO  登录逻辑判断 或者 跳转到 主页面
                startActivity(Intent(SplashActivity@ this, LoginActivity::class.java))
                finish()
            }
        }
        return@Callback false
    })

    override fun onDestroy() {
        mHandler?.removeMessages(HANDLER_WHAT_DEFALUT)

        super.onDestroy()
    }

}