package tech.leson.yonstore.ui.login

import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.ActivityLoginBinding
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.main.MainActivity
import tech.leson.yonstore.ui.register.RegisterActivity
import tech.leson.yonstore.utils.AppUtils

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginNavigator, LoginViewModel>(),
    LoginNavigator {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, LoginActivity::class.java).also { instance = it }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_login
    override val viewModel: LoginViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
    }

    override fun onSignIn() {
        hideKeyboard()
        if (isNetworkConnected()) {
            val phone = edtPhone.editText?.text.toString().trim()
            val password = edtPassword.editText?.text.toString().trim()
            if (!AppUtils.isPhoneNumberValid(phone)) {
                Toast.makeText(this, getString(R.string.invalid_phone_password), Toast.LENGTH_SHORT)
                    .show()
                return
            }
            if (!phone.isBlank() && !password.isBlank()) {
                viewModel.login(phone, password)
            } else {
                Toast.makeText(this, getString(R.string.invalid_phone_password), Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(this, getString(R.string.network_not_connected), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun signInSuccess() {
        startActivity(MainActivity.getIntent(this))
        finish()
    }

    override fun onRegister() {
        startActivity(RegisterActivity.getIntent(this))
        finish()
    }

    override fun onError(err: String) {
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show()
    }
}
