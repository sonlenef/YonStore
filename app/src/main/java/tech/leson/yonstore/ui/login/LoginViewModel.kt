package tech.leson.yonstore.ui.login

import android.view.View
import com.google.firebase.auth.FirebaseAuth
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider


class LoginViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<LoginNavigator>(dataManager, schedulerProvider) {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, password: String) {
        setIsLoading(true)
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                setIsLoading(false)
                navigator?.signInSuccess()
            } else {
                navigator?.onError(it.exception.toString())
                setIsLoading(false)
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btnSignIn -> navigator?.onSignIn()
            R.id.btnRegister -> navigator?.onRegister()
        }
    }
}
