package tech.leson.yonstore.ui.register

import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.ActivityRegisterBinding
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.login.LoginActivity
import tech.leson.yonstore.ui.verify.PhoneVerifyActivity
import tech.leson.yonstore.utils.AppUtils

class RegisterActivity :
    BaseActivity<ActivityRegisterBinding, RegisterNavigator, RegisterViewModel>(),
    RegisterNavigator {

    companion object {
        const val REQUEST_CODE = 3333
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

    override fun signUpSuccess(phone: String, password: String) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        val intent = Intent()
        intent.putExtra("phone", phone)
        intent.putExtra("password", password)
        setResult(RESULT_CODE, intent)
        finish()
    }

    override fun onSignUp() {
        if (isNetworkConnected()) {
            val fullName = edtFullName.editText?.text.toString().trim()
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
                    val intent = PhoneVerifyActivity.getIntent(this)
                    intent.putExtra("fullName", fullName)
                    intent.putExtra("phoneNumber", phone)
                    intent.putExtra("password", password)
                    startActivityForResult(intent, REQUEST_CODE)
                } else {
                    Toast.makeText(this,
                        getString(R.string.retype_password_fail),
                        Toast.LENGTH_SHORT)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == PhoneVerifyActivity.RESULT_CODE) {
            data?.let {
                val verified = it.getBooleanExtra("verified", false)
                if (verified) {
                    val name = it.getStringExtra("fullName")
                    val phone = it.getStringExtra("phone")
                    val password = it.getStringExtra("password")
                    if (name != null && phone != null && password != null) viewModel.register(name, phone, password)
                }
            }
        }
    }
}
