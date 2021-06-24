package com.yazao.base.net

import android.os.Looper
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.yazao.base.R
import com.yazao.base.model.ResultData
import com.yazao.base.router.RouterPath
import com.yazao.lib.toast.XToast
import com.yazao.lib.xlog.Log
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit


object Client {


    private fun getHttpLogging(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message -> Log.d("response = $message") }

        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        return httpLoggingInterceptor
    }

    val mLoggingInterceptor = Interceptor { chain ->
        val request = chain.request()
        val t1 = System.nanoTime()
        val builder = StringBuilder()
        val method = request.method
        builder.append("""
    $method
    
    """.trimIndent())
        if ("GET" == method) {
            builder.append("""
    Sending request
    ${request.url}
    """.trimIndent())
        } else if ("POST" == method) {
            builder.append("""
    Sending request
    ${request.url}
    """.trimIndent())
            try {
                val body = request.body

                if (body is FormBody) {
                    if (body!!.size > 0) {
                        builder.append("?")
                        for (j in 0 until body!!.size) {
                            builder.append(body.name(j) + "=" + body.value(j))
                            if (j != body.size - 1) {
                                builder.append("&")
                            }
                        }
                    }
                }

            } catch (e: ClassCastException) {
                return@Interceptor chain.proceed(request)
            }
        }
        builder.append("\n")
        if (request.headers.size > 0) {
            builder.append("\n")
            builder.append("Headers")
            builder.append("\n")
            builder.append(request.headers)
        }


        // response
        val response = chain.proceed(request)
        val responseBody = response.body
        val rBody: String

        val source = responseBody!!.source()
        source.request(java.lang.Long.MAX_VALUE)
        val buffer = source.buffer()

        var charset: Charset? = Charsets.UTF_8
        val contentType = responseBody.contentType()
        contentType?.let {
            try {
                charset = contentType.charset(Charsets.UTF_8)
            } catch (e: UnsupportedCharsetException) {
            }
        }
        rBody = buffer.clone().readString(charset!!)

        builder.append("\n")
        builder.append("""
    Response code = ${response.code}  $rBody
    """.trimIndent())

        builder.append("\n")
        val t2 = System.nanoTime()
        builder.append("""
    Received response in ${(t2 - t1) / 1e6}ms
    
    """.trimIndent())

        Log.i(builder.toString())

        response
    }

    val headerInterceptor: Interceptor = Interceptor { chain ->

        val request: Request = chain.request()

        val header = request.newBuilder().header("platform", "Android")
        if (Token.isLogin()) {
            header.header("Cookie", "JSESSIONID=${Token.token}")
        }
        val authenticatedRequest = header.build()
        val response: Response = chain.proceed(authenticatedRequest)

        val mediaType = response.body!!.contentType()
        val content = response.body!!.string()
        response.newBuilder().body(content.toResponseBody(mediaType)).build()
    }

    private val UTF8 = Charset.forName("UTF-8")
    val tokenInterceptor: Interceptor = Interceptor { chain ->

        val request = chain.request()
        val originalResponse = chain.proceed(request)
        val responseBody = originalResponse.body
        val source = responseBody!!.source()
        source.request(Long.MAX_VALUE) // Buffer the entire body.

        val buffer: Buffer = source.buffer()
        var charset: Charset = UTF8
        val contentType = responseBody.contentType()
        if (contentType != null) {
            charset = contentType.charset(UTF8)!!
        }

        //bodyString: {"code":"2003","msg":"未登录或登录超时。请重新登录"}
        val bodyString: String = buffer.clone().readString(charset)
//        Log.i("body---------->$bodyString")

        try {

            if (bodyString.isNotEmpty() && bodyString.startsWith("[")) {
                val jsonArray: JSONArray = JSONArray(bodyString)
                if (jsonArray != null) {
                    // otherwise just pass the original response on
                    return@Interceptor originalResponse
                }
            }

            if (bodyString.isNotEmpty() && bodyString.startsWith("{")) {
                val jsonObject: JSONObject = JSONObject(bodyString)
                if (!jsonObject.has("code")) {
                    // otherwise just pass the original response on
                    return@Interceptor originalResponse
                }
            }


            /****************** token *********************/
            val data: ResultData<*> = Gson().fromJson(bodyString, ResultData::class.java)
            if (data.code === 2003) {
                //token过期
                Token.token = ""
                ARouter.getInstance().build(RouterPath.Login.PAGE_ACTIVITY_LOGIN).withBoolean(Token.KEY_TOKEN, false).navigation()
                if (Looper.myLooper() == null) {
                    Looper.prepare()
                }
                XToast.show(R.string.setting_token_invalidation)
                Looper.loop()

                // otherwise just pass the original response on
                return@Interceptor originalResponse
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // otherwise just pass the original response on
            return@Interceptor originalResponse
        }

        // otherwise just pass the original response on
        return@Interceptor originalResponse
    }

    @JvmField
    val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(tokenInterceptor)
//            .addInterceptor(getHttpLogging())
            .addInterceptor(mLoggingInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

    @JvmField
    val okHttpClient: OkHttpClient = okHttpClientBuilder
            .build()
}