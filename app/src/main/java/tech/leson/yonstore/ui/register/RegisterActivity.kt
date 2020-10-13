package tech.leson.yonstore.ui.register

import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.ActivityRegisterBinding
import tech.leson.yonstore.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.ui.login.LoginActivity
import tech.leson.yonstore.utils.AppUtils

class RegisterActivity :
    BaseActivity<ActivityRegisterBinding, RegisterNavigator, RegisterViewModel>(),
    RegisterNavigator {

    companion object {
        const val RESULT_CODE = 2222
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, RegisterActivity::class.java).also { instance = it }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_register
    override val viewModel: RegisterViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
    }

    override fun signUpSuccess(email: String, password: String) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        val intent = Intent()
        intent.putExtra("phone", email)
        intent.putExtra("password", password)
        setResult(RESULT_CODE, intent)
        finish()
    }

    override fun onSignUp() {
        if (isNetworkConnected()) {
            val phone = edtPhone.editText?.text.toString().trim()
            val password = edtPassword.editText?.text.toString().trim()
            val passwordAgain = edtPasswordAgain.editText?.text.toString().trim()
            if (!AppUtils.isPhoneNumberValid(phone)) {
                Toast.makeText(this, getString(R.string.invalid_phone_password), Toast.LENGTH_SHORT)
                    .show()
                return
            }
            if (!phone.isBlank() && !password.isBlank()) {
                if (password == passwordAgain) {
                    viewModel.register(phone, password)
                } else {
                    Toast.makeText(this, getString(R.string.retype_password_fail), Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, getString(R.string.invalid_phone_password), Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(this, getString(R.string.network_not_connected), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onSignIn() {
        startActivity(LoginActivity.getIntent(this))
        finish()
    }

    override fun onError(err: String) {
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show()
    }
}
