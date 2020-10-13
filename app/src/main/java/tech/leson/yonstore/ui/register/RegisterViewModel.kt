package tech.leson.yonstore.ui.register

import android.view.View
import com.google.firebase.auth.FirebaseAuth
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class RegisterViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<RegisterNavigator>(dataManager, schedulerProvider) {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun register(email: String, password: String) {
        setIsLoading(true)
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            if (it.isSuccessful) {
                setIsLoading(false)
                navigator?.signUpSuccess(email, password)
            } else {
                setIsLoading(false)
                navigator?.onError(it.exception.toString())
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btnSignUp -> navigator?.onSignUp()
            R.id.btnSignIn -> navigator?.onSignIn()
        }
    }
}
