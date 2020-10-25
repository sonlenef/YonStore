package tech.leson.yonstore.ui.login

import android.view.View
import com.google.firebase.auth.FirebaseAuth
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider


class LoginViewModel(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    auth: FirebaseAuth,
) :
    BaseViewModel<LoginNavigator>(dataManager, schedulerProvider) {

    private val mAuth: FirebaseAuth = auth

    fun login(phone: String, password: String) {
        setIsLoading(true)
        mAuth.signInWithEmailAndPassword("$phone@leson.tech", password).addOnCompleteListener {
            if (it.isSuccessful) {
                dataManager.setUserUid(it.result?.user?.uid.toString())
                navigator?.signInSuccess()
                setIsLoading(false)
            } else {
                navigator?.onError(it.exception.toString())
                setIsLoading(false)
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnSignIn -> navigator?.onSignIn()
            R.id.btnRegister -> navigator?.onRegister()
        }
    }
}
