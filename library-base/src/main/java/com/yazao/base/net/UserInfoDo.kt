package com.yazao.base.net

import com.yazao.base.util.MMKVPrefs


object UserInfoDo {

    fun isLogin(): Boolean {
        return Token.isLogin()
    }

    fun hasPrivacy(): Boolean {
        return MMKVPrefs.getBoolean(MMKVPrefs.KEY_HAS_PRIVACY, false)
    }

    fun setPrivacy(hasPrivacy: Boolean) {
        MMKVPrefs.putBoolean(MMKVPrefs.KEY_HAS_PRIVACY, hasPrivacy)
    }


    /**
     * 清除用户信息
     */
    fun clearUserInfo() {
        MMKVPrefs.clear()
        Token.clearToken()
    }
}