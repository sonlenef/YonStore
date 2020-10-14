package tech.leson.yonstore.ui.verify

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_phone_verify.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.ActivityPhoneVerifyBinding
import tech.leson.yonstore.ui.base.BaseActivity

class PhoneVerifyActivity :
    BaseActivity<ActivityPhoneVerifyBinding, PhoneVerifyNavigator, PhoneVerifyViewModel>(),
    PhoneVerifyNavigator {

    companion object {
        const val RESULT_CODE = 4444
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, PhoneVerifyActivity::class.java).also { instance = it }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_phone_verify
    override val viewModel: PhoneVerifyViewModel by viewModel()

    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun init() {
        viewModel.setNavigator(this)

        tvTitle.text = getString(R.string.verify_phone_number)

        if (isNetworkConnected()) {
            val phone = intent.getStringExtra("phoneNumber")
            if (phone != null) viewModel.verifyPhoneNumber(this,
                phone,
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                        viewModel.signInWithPhoneAuthCredential(this@PhoneVerifyActivity, p0)
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        Toast.makeText(this@PhoneVerifyActivity, p0.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken,
                    ) {
                        storedVerificationId = verificationId
                        resendToken = token
                    }
                })
        } else {
            Toast.makeText(this, getString(R.string.network_not_connected), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onResend() {
        if (isNetworkConnected()) {
            val phone = intent.getStringExtra("phoneNumber")
            if (phone != null) viewModel.resendVerificationCode(this,
                phone,
                resendToken,
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                        viewModel.signInWithPhoneAuthCredential(this@PhoneVerifyActivity, p0)
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        Toast.makeText(this@PhoneVerifyActivity, p0.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken,
                    ) {
                        storedVerificationId = verificationId
                        resendToken = token
                    }
                })
        } else {
            Toast.makeText(this, getString(R.string.network_not_connected), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onConfirm() {
        if (isNetworkConnected()) {
            val code = edtCode.editText?.text.toString().trim()
            if (!code.isBlank()) {
                viewModel.verifyPhoneNumberWithCode(this, storedVerificationId, code)
            } else {
                Toast.makeText(this, getText(R.string.code_empty), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, getString(R.string.network_not_connected), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onSuccess() {
        val i = Intent()
        i.putExtra("verified", true)
        i.putExtra("phone", intent.getStringExtra("phoneNumber"))
        i.putExtra("password", intent.getStringExtra("password"))
        setResult(RESULT_CODE, i)
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBack() {
        finish()
    }
}