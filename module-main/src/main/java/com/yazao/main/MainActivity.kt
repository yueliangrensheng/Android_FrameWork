package com.yazao.main

import com.alibaba.android.arouter.facade.annotation.Route
import com.yazao.base.router.RouterPath
import com.yazao.lib.xbase.BaseActivity
import com.yazao.main.databinding.ActivityMainLayoutBinding

@Route(path = RouterPath.Main.PAGE_ACTIVITY_MAIN)
class MainActivity : BaseActivity<ActivityMainLayoutBinding>() {
    override fun getLayoutID(): Int  = R.layout.activity_main_layout

    override fun initData() {

    }

}