package com.yazao.base.router

object RouterPath {

    object Login {
        private const val LOGIN = "/login"

        /* 登录页面 */
        const val PAGE_ACTIVITY_LOGIN = "$LOGIN/LoginActivity"
    }

    object Main {
        private const val MAIN = "/main"

        /* 主页面 */
        const val PAGE_ACTIVITY_MAIN = "$MAIN/MainActivity"
    }
}