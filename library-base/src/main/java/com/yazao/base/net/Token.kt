package com.yazao.base.net

import com.yazao.base.util.MMKVPrefs

object Token {

    const val KEY_TOKEN: String = MMKVPrefs.KEY_TOKEN

    private var _token: String? = ""

    fun isLogin(): Boolean {
        return getToken()?.isNotEmpty() == true
    }

    fun getToken(): String? {
        if (_token.isNullOrEmpty()) {
            _token = MMKVPrefs.getString(MMKVPrefs.KEY_TOKEN)
        }
        return _token
    }

    fun setToken(token: String) {
        this._token = token
        MMKVPrefs.putString(MMKVPrefs.KEY_TOKEN, token)
    }

    fun clearToken() {
        _token = ""
        MMKVPrefs.remove(MMKVPrefs.KEY_TOKEN)
    }
}