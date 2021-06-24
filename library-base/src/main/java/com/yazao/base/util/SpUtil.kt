package com.yazao.base.util


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Base64
import java.io.*

/**
 * Created by 19428 on 2017/6/27.
 */
@SuppressLint("ApplySharedPref")

class SpUtil {
    private var sp: SharedPreferences? = null

    private fun getSp(context: Context): SharedPreferences {
        if (sp == null) {
            sp = context.getSharedPreferences("SpUtil", Context.MODE_PRIVATE)
        }
        return this.sp!!
    }


    /**
     * 存入字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @param value   字符串的值
     */
    fun putString(context: Context, key: String, value: String) {
        val preferences = getSp(context)
        //存入数据
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    /**
     * 获取字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @return 得到的字符串
     */
    fun getString(context: Context, key: String): String {
        val preferences = getSp(context)
        return preferences.getString(key, "")!!
    }

    /**
     * 获取字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @param value   字符串的默认值
     * @return 得到的字符串
     */
    fun getString(context: Context, key: String, value: String): String? {
        val preferences = getSp(context)
        return preferences.getString(key, value)
    }

    /**
     * 保存布尔值
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    fun putBoolean(context: Context, key: String, value: Boolean) {
        val sp = getSp(context)
        val editor = sp.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    /**
     * 获取布尔值
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return 返回保存的值
     */
    fun getBoolean(context: Context, key: String, defValue: Boolean): Boolean {
        val sp = getSp(context)
        return sp.getBoolean(key, defValue)
    }

    /**
     * 保存long值
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    fun putLong(context: Context, key: String, value: Long) {
        val sp = getSp(context)
        val editor = sp.edit()
        editor.putLong(key, value)
        editor.commit()
    }

    /**
     * 获取long值
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return 保存的值
     */
    fun getLong(context: Context, key: String, defValue: Long): Long {
        val sp = getSp(context)
        return sp.getLong(key, defValue)
    }

    /**
     * 保存int值
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    fun putInt(context: Context, key: String, value: Int) {
        val sp = getSp(context)
        val editor = sp.edit()
        editor.putInt(key, value)
        editor.commit()
    }

    /**
     * 获取long值
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return 保存的值
     */
    fun getInt(context: Context, key: String, defValue: Int): Int {
        val sp = getSp(context)
        return sp.getInt(key, defValue)
    }

    /**
     * 保存对象
     *
     * @param context 上下文
     * @param key     键
     * @param obj     要保存的对象（Serializable的子类）
     * @param <T>     泛型定义
    </T> */


    fun <T : Serializable> putObject(context: Context, key: String, obj: T) {
        try {
            put(context, key, obj)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 获取对象
     *
     * @param context 上下文
     * @param key     键
     * @param <T>     指定泛型
     * @return 泛型对象
    </T> */
    fun <T : Serializable> getObject(context: Context, key: String): T? {
        try {
            return get(context, key) as T?
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 存储List集合
     *
     * @param context 上下文
     * @param key     存储的键
     * @param list    存储的集合
     */
    fun putList(context: Context, key: String, list: List<Serializable>) {
        try {
            put(context, key, list)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 获取List集合
     *
     * @param context 上下文
     * @param key     键
     * @param <E>     指定泛型
     * @return List集合
    </E> */
    fun <E : Serializable> getList(context: Context, key: String): List<E>? {
        try {
            return get(context, key) as List<E>?
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 存储Map集合
     *
     * @param context 上下文
     * @param key     键
     * @param map     存储的集合
     * @param <K>     指定Map的键
     * @param <V>     指定Map的值
    </V></K> */
    fun <K : Serializable, V : Serializable> putMap(
        context: Context,
        key: String, map: Map<K, V>
    ) {
        try {
            put(context, key, map)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun <K : Serializable, V : Serializable> getMap(
        context: Context,
        key: String
    ): Map<K, V>? {
        try {
            return get(context, key) as Map<K, V>?
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


    /**
     * 存储对象
     */
    @Throws(IOException::class)
    private fun put(context: Context, key: String, obj: Any?) {
        if (obj == null) {//判断对象是否为空
            return
        }
        val baos = ByteArrayOutputStream()
        var oos: ObjectOutputStream? = null
        oos = ObjectOutputStream(baos)
        oos.writeObject(obj)
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        val objectStr = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
        baos.close()
        oos.close()

        putString(context, key, objectStr)
    }

    /**
     * 获取对象
     */
    @Throws(IOException::class, ClassNotFoundException::class)
    private operator fun get(context: Context, key: String): Any? {
        val wordBase64 = getString(context, key)
        // 将base64格式字符串还原成byte数组
        if (TextUtils.isEmpty(wordBase64)) { //不可少，否则在下面会报java.io.StreamCorruptedException
            return null
        }
        val objBytes = Base64.decode(wordBase64.toByteArray(), Base64.DEFAULT)
        val bais = ByteArrayInputStream(objBytes)
        val ois = ObjectInputStream(bais)
        // 将byte数组转换成product对象
        val obj = ois.readObject()
        bais.close()
        ois.close()
        return obj
    }


    /**
     * 删除某条数据
     */
    fun removeData(context: Context, key: String) {
        val preferences = getSp(context)
        //存入数据
        val editor = preferences.edit()
        editor.remove(key).commit()
    }

    /**
     *清除所有缓存
     */
    fun clearData(context: Context) {
        val preferences = getSp(context)
        //存入数据
        val editor = preferences.edit()
        editor.clear().commit()
    }


    /**
     * 删除指定数据
     */
    fun deleteData(context: Context, key: String) {
        val preferences: SharedPreferences? = getSp(context)
        val editor: SharedPreferences.Editor? = preferences?.edit()
        editor?.remove(key)
        editor?.commit()
    }

}

