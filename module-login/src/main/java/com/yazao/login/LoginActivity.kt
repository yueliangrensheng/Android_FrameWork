package com.yazao.login

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.yazao.base.BaseToolbarActivityKt
import com.yazao.base.WebActivity
import com.yazao.base.model.ResultData
import com.yazao.base.net.Token
import com.yazao.base.net.privacyPolicyUrl
import com.yazao.base.net.serviceAgreementUrl
import com.yazao.base.router.RouterPath
import com.yazao.base.util.ACacheUtil
import com.yazao.base.util.ClickUtil
import com.yazao.lib.toast.XToast
import com.yazao.lib.xlog.Log
import com.yazao.login.databinding.ActivityLoginLayoutBinding
import com.yazao.login.net.LoginParams
import com.yazao.login.net.NetRequestManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Route(path = RouterPath.Login.PAGE_ACTIVITY_LOGIN)
class LoginActivity : BaseToolbarActivityKt<ActivityLoginLayoutBinding>() {

    private var isShowPwd = false

    private var isTokenInvalidation = true

    override fun isNoTitle(): Boolean = true

    override fun isFitDataBinding(): Boolean = true

    override fun getLayoutID(): Int = R.layout.activity_login_layout

    override fun getBundleExtras(extras: Bundle?) {
        super.getBundleExtras(extras)
        isTokenInvalidation = extras!!.getBoolean(Token.KEY_TOKEN, isTokenInvalidation)
    }

    override fun initData() {

        mDataBinding?.run {
            loginUsername.setText(ACacheUtil.getUserName())
            loginPassword.setText(ACacheUtil.getPassword())

            //show or hide password
//            loginPasswordShow.setOnClickListener {
//                if (isShowPwd) {
//                    loginPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
//                    loginPasswordShow.setImageDrawable(resources.getDrawable(R.mipmap.icon_eye_open))
//                } else {
//                    loginPassword.transformationMethod = PasswordTransformationMethod.getInstance()
//                    loginPasswordShow.setImageDrawable(resources.getDrawable(R.mipmap.icon_eye_close))
//                }
//                isShowPwd = !isShowPwd
//                //光标至末尾
//                loginPassword.setSelection(loginPassword.text.length)
//            }

            //login
            loginLogin.setOnClickListener {
                if (ClickUtil.isFastDoubleClick()) {
                    return@setOnClickListener
                }
                login(loginUsername.text.toString(), loginPassword.text.toString())
            }

            //register
            loginRegister.setOnClickListener {
                if (ClickUtil.isFastDoubleClick()) {
                    return@setOnClickListener
                }
                // register page
            }

            //隐私政策
            loginPrivacyPolicy.setOnClickListener {
                if (ClickUtil.isFastDoubleClick()) {
                    return@setOnClickListener
                }
                val intent = Intent(this@LoginActivity, WebActivity::class.java)
                intent.putExtra(WebActivity.WEB_URL, privacyPolicyUrl)
                intent.putExtra(WebActivity.WEB_TITLE, resources.getString(R.string.settings_privacy_policy))
                startActivity(intent)
            }
            //服务协议
            loginUserAgreement.setOnClickListener {
                if (ClickUtil.isFastDoubleClick()) {
                    return@setOnClickListener
                }
                val intent = Intent(this@LoginActivity, WebActivity::class.java)
                intent.putExtra(WebActivity.WEB_URL, serviceAgreementUrl)
                intent.putExtra(WebActivity.WEB_TITLE, resources.getString(R.string.settings_user_agreement))
                startActivity(intent)
            }
        }
    }

    private fun login(username: String, password: String) {
        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            XToast.show(R.string.login_username_pwd_empty)
            return
        }

        showLoading()

        launch {
            try {
                val resultData: ResultData<String> = withContext(Dispatchers.IO) {
                    NetRequestManager.getInstance().login(username, password)
                }

                hideLoading()
                if (resultData.isSuccess()) {

                    LoginParams.username = username
//                    LoginParams.token = password
                    Token.token = resultData.data
                    ACacheUtil.setUserName(username)
                    ACacheUtil.setPassword(password)

                    if (isTokenInvalidation) {
                        ARouter.getInstance().build(RouterPath.Main.PAGE_ACTIVITY_MAIN).navigation()
                    }

                    finish()

                } else {

                    XToast.show(resultData.msg)

                }

            } catch (e: Exception) {
                hideLoading()
                Log.e("server", e.message.toString())
                XToast.show(R.string.setting_server_error)
            }
        }
    }

}
