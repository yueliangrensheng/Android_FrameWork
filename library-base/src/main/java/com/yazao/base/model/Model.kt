package com.yazao.base.model

data class ResultData<T>(
        val code: Int,
        val msg: String,
        val data: T,
) {
    fun isSuccess() = code == 0
}