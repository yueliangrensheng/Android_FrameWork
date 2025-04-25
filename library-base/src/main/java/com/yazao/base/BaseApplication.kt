package com.yazao.base

import android.content.pm.ApplicationInfo
import android.os.Process
import android.text.TextUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.bugly.crashreport.CrashReport
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.yazao.base.util.ACacheUtil
import com.yazao.lib.toast.XToast
import com.yazao.lib.xbase.WBaseApplication
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

open class BaseApplication : WBaseApplication() {


    private val APPID_BUGLY: String = "e48c08c895"
    private val APPKEY_UMENG: String = "60b58454dd01c71b57cdd47b"

    companion object {
        lateinit var application: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()

        application = this
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
        //3. ACacheUtil
        ACacheUtil.init(getContext())
    }

    fun initOther() {
        //4.Bugly
        initBugly()
        //5.Umeng
        initUMeng()
    }

    private fun initUMeng() {
        UMConfigure.init(this, APPKEY_UMENG, "Yingyongbao", UMConfigure.DEVICE_TYPE_PHONE, "")//初始化组件化基础库
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)//选择AUTO页面采集模式，统计SDK基础指标无需手动埋点可自动采集。
        UMConfigure.setLogEnabled(application.isDebug())
    }

    /**
     * Bugly 初始化
     */
    private fun initBugly() {
        val context = applicationContext
        // 获取当前包名
        val packageName = context.packageName
        // 获取当前进程名
        val processName = getProcessName(Process.myPid())
        // 设置是否为上报进程
        val strategy = CrashReport.UserStrategy(context)
        strategy.isUploadProcess = processName == null || processName == packageName
        CrashReport.initCrashReport(applicationContext, APPID_BUGLY, application.isDebug());
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName: String = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }

    fun isDebug(): Boolean {
        return applicationInfo != null && applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE !== 0
    }

}