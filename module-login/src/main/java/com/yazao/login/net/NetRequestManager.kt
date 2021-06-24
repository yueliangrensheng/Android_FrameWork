package com.yazao.login.net

import com.yazao.base.net.BASE_URL
import com.yazao.base.net.RetrofitManager

class NetRequestManager {

    companion object {
        fun getInstance() = SingleHolder.INSTANCE
    }

    private object SingleHolder {
        val INSTANCE = NetRequestManager()
    }

    init {

    }

    object APiService : ServiceApi by RetrofitManager.create(ServiceApi::class.java, BASE_URL)


    suspend fun login(username: String, password: String, rememberMe: Boolean = false) = APiService.login(username, password, rememberMe)

    suspend fun register(map: Map<String, String>) = APiService.register(map)

    suspend fun checkUpgrade(versionCode: String) = APiService.checkUpgrade(versionCode)
}