package com.yazao.base.net

object Token {

    const val KEY_TOKEN:String = "key_token"

    var token: String? = null
    fun isLogin(): Boolean {
        return token != null && token!!.isNotEmpty()
    }
}