package com.yazao.base.util

import android.content.Context

object ACacheUtil {

    private lateinit var mContext: Context
    fun init(context: Context) {
        mContext = context
    }

    @JvmStatic
    fun setUserName(userName: String) {
        SpUtil().putString(mContext, "userName", userName)
    }

    @JvmStatic
    fun getUserName(): String {
        return SpUtil().getString(mContext, "userName")
    }

    @JvmStatic
    fun deleteUserName() {
        SpUtil().deleteData(mContext, "userName")
    }

    @JvmStatic
    fun setPassword(password: String) {
        SpUtil().putString(mContext, "password", password)
    }

    @JvmStatic
    fun getPassword(): String {
        return SpUtil().getString(mContext, "password")
    }

    @JvmStatic
    fun deletePassword() {
        SpUtil().deleteData(mContext, "password")
    }


    @JvmStatic
    fun setFirstRun(firstRun: Boolean) {
        SpUtil().putBoolean(mContext, "firstRun", firstRun)
    }

    @JvmStatic
    fun getFirstRun(defValue: Boolean = false): Boolean {
        return SpUtil().getBoolean(mContext, "firstRun", defValue)
    }

    @JvmStatic
    fun deleteFirstRun() {
        SpUtil().deleteData(mContext, "firstRun")
    }
}