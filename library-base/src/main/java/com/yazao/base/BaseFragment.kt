package com.yazao.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.yazao.base.weight.loading.LoadingDialog
import com.yazao.lib.xbase.BaseFragmentKt

abstract class BaseFragment<DB : ViewDataBinding> : BaseFragmentKt<DB>() {

    private lateinit var dialogLoading: LoadingDialog

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initLoadingView()
    }
    private fun initLoadingView() {
        dialogLoading = LoadingDialog()
        activity?.let { dialogLoading.initLoadingView(it) }
    }


    protected fun showLoading() {
        dialogLoading?.showLoading()
    }

    protected fun hideLoading() {
        dialogLoading?.hideLoading()
    }

    override fun onDestroy() {
        dialogLoading?.destroyLoading()
        super.onDestroy()
    }

}