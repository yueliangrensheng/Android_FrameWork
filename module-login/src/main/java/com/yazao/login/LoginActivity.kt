package com.yazao.login

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.yazao.base.router.RouterPath
import com.yazao.lib.toast.XToast
import com.yazao.lib.xbase.BaseActivity
import com.yazao.login.databinding.ActivityLoginLayoutBinding

@Route(path = RouterPath.Login.PAGE_ACTIVITY_LOGIN)
class LoginActivity : BaseActivity<ActivityLoginLayoutBinding>() {

    private var isShowPwd = false

    override fun isNoTitle(): Boolean = true

    override fun isFitDataBinding(): Boolean = true

    override fun getLayoutID(): Int = R.layout.activity_login_layout

    override fun initData() {

        mDataBinding?.run {

            //show or hide password
            loginPasswordShow.setOnClickListener {
                if (isShowPwd) {
                    loginPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    loginPasswordShow.setImageDrawable(resources.getDrawable(R.mipmap.icon_eye_open))
                } else {
                    loginPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                    loginPasswordShow.setImageDrawable(resources.getDrawable(R.mipmap.icon_eye_close))
                }
                isShowPwd = !isShowPwd
                //光标至末尾
                loginPassword.setSelection(loginPassword.text.length)
            }

            //login
            loginLogin.setOnClickListener {
                login(loginUsername.text.toString(), loginPassword.text.toString())
            }
        }
    }

    private fun login(username: String, password: String) {
        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            XToast.show("输入的用户名或密码有误")
            return
        }

        //TODO login
        LoginParams.username = username
        LoginParams.token = password

        ARouter.getInstance().build(RouterPath.Main.PAGE_ACTIVITY_MAIN).navigation()
    }

}
