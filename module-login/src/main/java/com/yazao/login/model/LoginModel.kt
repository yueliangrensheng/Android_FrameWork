package com.yazao.login.model

data class UpgradeBean(
    var isUpdate: Int,//更新状态  0无需更新  1强制更新  2不强制更新
    var url: String
)