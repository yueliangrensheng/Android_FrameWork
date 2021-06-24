package com.yazao.base.util

object ClickUtil {
    private var lastClickTime: Long = 0

    fun isFastDoubleClick(): Boolean {
        val time = System.currentTimeMillis()
        if (time - lastClickTime < 500) {
            return true
        }
        lastClickTime = time
        return false
    }
}