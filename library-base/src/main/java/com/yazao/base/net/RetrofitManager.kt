package com.yazao.base.net

import com.google.gson.GsonBuilder
import com.yazao.base.net.Client.okHttpClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit


object RetrofitManager {

    private lateinit var retrofit: Retrofit

    fun <T> create(service: Class<T>, baseUrl: String, needNew: Boolean = false): T {

        if (needNew || !this::retrofit.isInitialized || retrofit == null) {

            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(LenientGsonConverterFactory.create(GsonBuilder().setLenient().create()))
                    .client(okHttpClient)
                    .build()
        }

        return retrofit.create(service) ?: throw RuntimeException("APi Service is null")
    }

    fun <T> create(service: Class<T>, baseUrl: String, okHttpClient: OkHttpClient, needNew: Boolean = false): T {

        if (needNew || !this::retrofit.isInitialized || retrofit == null) {

            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(LenientGsonConverterFactory.create(GsonBuilder().setLenient().create()))
                    .client(okHttpClient)
                    .build()
        }

        return retrofit.create(service) ?: throw RuntimeException("APi Service is null")
    }

}