package com.yazao.base.business.net

import com.yazao.base.model.ResultData
import com.yazao.base.business.model.UpgradeBean
import retrofit2.http.*

interface ServiceApi {

    @POST(PATH_LOGIN)
    @FormUrlEncoded
    suspend fun login(@Field("username") username: String, @Field("password") password: String, @Field("rememberMe") rememberMe: Boolean = false): ResultData<String>

    @POST(PATH_REGISTER)
    @FormUrlEncoded
    suspend fun register(@FieldMap map: Map<String, String>):@JvmSuppressWildcards ResultData<String>

    @POST(PATH_UPGREADE)
    suspend fun checkUpgrade(@Header("version_code") versionCode: String): ResultData<UpgradeBean>

}