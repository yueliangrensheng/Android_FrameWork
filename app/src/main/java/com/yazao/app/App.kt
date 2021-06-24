package com.yazao.app

import android.content.Context
import androidx.multidex.MultiDex
import com.yazao.base.BaseApplication
import com.yazao.lib.xlog.Log
import com.yazao.lib.xnet.NetConfig

class App : BaseApplication() {


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()

        Log.init().setLogLevel(Log.LogLevel.FULL).setMethodCount(2).hideThreadInfo()
        // 禁止 网络监听 日志的输出 默认是 false
        NetConfig.getInstance().showNetLog = false
    }


}