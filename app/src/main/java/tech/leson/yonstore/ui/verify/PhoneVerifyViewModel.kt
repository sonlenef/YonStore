package tech.leson.yonstore.ui.verify

import android.app.Activity
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider
import java.util.concurrent.TimeUnit

class PhoneVerifyViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<PhoneVerifyNavigator>(dataManager, schedulerProvider) {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun verifyPhoneNumber(
        activity: Activity,
        phoneNumber: String,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
    ) {
        mAuth.setLanguageCode("vn")
        var phone = StringBuilder()
        phone = phone.append("+84").append(phoneNumber.substring(1))
        PhoneAuthProvider.getInstance()
            .verifyPhoneNumber(phone.toString(), 120, TimeUnit.SECONDS, activity, callbacks)
    }

    fun resendVerificationCode(
        activity: Activity,
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
    ) {
        mAuth.setLanguageCode("vn")
        var phone = StringBuilder()
        phone = phone.append("+84").append(phoneNumber.substring(1))
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phone.toString(),
            120,
            TimeUnit.SECONDS,
            activity,
            callbacks,
            token)
    }

    fun verifyPhoneNumberWithCode(activity: Activity, verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(activity, credential)
    }

    fun signInWithPhoneAuthCredential(activity: Activity, credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    navigator?.onSuccess()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        navigator?.onError("Invalid code")
                    }
                }
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnResend -> navigator?.onResend()
            R.id.btnConfirm -> navigator?.onConfirm()
            R.id.btnBack -> navigator?.onBack()
        }
    }

}
