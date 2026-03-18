package com.yazao.base.util

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV

object MMKVPrefs {

    const val KEY_DOMAIN: String = "yz_server_domain"
    const val KEY_TOKEN: String = "yz_token"
    const val KEY_LOGIN_STATUS: String = "yz_login_status"
    const val KEY_USER_INFO: String = "yz_login_info"
    const val KEY_HAS_PRIVACY: String = "yz_has_privacy"


    val mmkv = MMKV.defaultMMKV()

    fun getObject(key: String, default: Any? = null): Any? {
        return when (default) {
            is Int -> mmkv.decodeInt(key, default)
            is Long -> mmkv.decodeLong(key, default)
            is Float -> mmkv.decodeFloat(key, default)
            is Double -> mmkv.decodeDouble(key, default)
            is Boolean -> mmkv.decodeBool(key, default)
            is String -> mmkv.decodeString(key, default)
            is Parcelable -> mmkv.decodeParcelable(key, default.javaClass)
            else -> {
                // 对于 Parcelable 或其他类型，需要特殊处理
                // 可以考虑存储类型信息来正确解码
                mmkv.decodeString(key, default as String?)
            }
        }
    }


    fun getString(key: String, default: String? = null): String? {
        return mmkv.decodeString(key, default)
    }

    fun getInt(key: String, default: Int = 0): Int {
        return mmkv.decodeInt(key, default)
    }

    fun getLong(key: String, default: Long = 0L): Long {
        return mmkv.decodeLong(key, default)
    }

    fun getFloat(key: String, default: Float = 0f): Float {
        return mmkv.decodeFloat(key, default)
    }

    fun getDouble(key: String, default: Double = 0.0): Double {
        return mmkv.decodeDouble(key, default)
    }

    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return mmkv.decodeBool(key, default)
    }

    fun <T : Parcelable> getParcelable(key: String, clazz: Class<T>): T? {
        return mmkv.decodeParcelable(key, clazz)
    }

    inline fun <reified T : Parcelable> getParcelableList(key: String): MutableList<T>? {
        try {
            val json = mmkv.decodeString(key) ?: return null
            val type = object : TypeToken<MutableList<T>>() {}.type
            return Gson().fromJson(json, type)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun getSet(key: String): Set<String>? {
        return mmkv.decodeStringSet(key, null)
    }

    fun getByteArray(key: String): ByteArray? {
        return mmkv.decodeBytes(key)
    }

    fun putString(key: String, value: String) {
        mmkv.encode(key, value)
    }

    fun putInt(key: String, value: Int) {
        mmkv.encode(key, value)
    }

    fun putLong(key: String, value: Long) {
        mmkv.encode(key, value)
    }

    fun putFloat(key: String, value: Float) {
        mmkv.encode(key, value)
    }

    fun putDouble(key: String, value: Double) {
        mmkv.encode(key, value)
    }

    fun putBoolean(key: String, value: Boolean) {
        mmkv.encode(key, value)
    }

    fun putParcelable(key: String, value: Parcelable?) {
        mmkv.encode(key, value)
    }

    fun <T : Parcelable> putParcelableList(key: String, valueList: MutableList<T>) {
        try {
            val json = Gson().toJson(valueList)
            mmkv.encode(key, json)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun putSet(key: String, value: Set<String>) {
        mmkv.encode(key, value)
    }

    fun putByteArray(key: String, value: ByteArray) {
        mmkv.encode(key, value)
    }

    fun remove(key: String) {
        mmkv.removeValueForKey(key)
    }

    fun clear() {
//        mmkv.clearAll()
        // 清除登录信息,除了：隐私协议
        remove(KEY_TOKEN)
        remove(KEY_LOGIN_STATUS)
        remove(KEY_USER_INFO)
    }

    fun contains(key: String): Boolean {
        return mmkv.contains(key)
    }

    fun all(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        mmkv.allKeys()?.forEach {
            map[it] = getObject(it) as Any
        }
        return map
    }

    fun size(): Long {
        return mmkv.count()
    }


}