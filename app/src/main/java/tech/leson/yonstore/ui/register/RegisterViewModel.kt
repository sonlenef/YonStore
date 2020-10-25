package tech.leson.yonstore.ui.register

import android.view.View
import com.google.firebase.auth.FirebaseAuth
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.User
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class RegisterViewModel(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    auth: FirebaseAuth,
) :
    BaseViewModel<RegisterNavigator>(dataManager, schedulerProvider) {

    private val mAuth = auth

    fun register(name: String, phone: String, password: String) {
        setIsLoading(true)
        mAuth.createUserWithEmailAndPassword("$phone@leson.tech", password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = User()
                user.fullName = name
                user.phoneNumber = phone
                user.accountId = it.result?.user?.uid.toString()
                dataManager.setUserUid(it.result?.user?.uid.toString())
                registerUserInfo(user)
                navigator?.signUpSuccess(phone, password)
            } else {
                setIsLoading(false)
                navigator?.onError(it.exception?.message.toString())
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnSignUp -> navigator?.onSignUp()
            R.id.btnSignIn -> navigator?.onSignIn()
        }
    }

    private fun registerUserInfo(user: User) {
        dataManager.register(user).addOnSuccessListener { setIsLoading(false) }
            .addOnFailureListener {
                navigator?.onError(it.message.toString())
                setIsLoading(false)
            }
    }
}
