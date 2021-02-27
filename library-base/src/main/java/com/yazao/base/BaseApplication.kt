package com.yazao.base

import android.content.pm.ApplicationInfo
import com.alibaba.android.arouter.launcher.ARouter
import com.yazao.lib.toast.XToast
import com.yazao.lib.xbase.WBaseApplication

open class BaseApplication : WBaseApplication() {

    override fun onCreate() {
        super.onCreate()

        init()
    }

    private fun init() {
        //1.XToast
        XToast.init(this)
        //2.ARouter
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        //3.
    }

    private fun isDebug(): Boolean {
        return applicationInfo != null && applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE !== 0
    }

}