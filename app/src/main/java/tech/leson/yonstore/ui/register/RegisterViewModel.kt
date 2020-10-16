package tech.leson.yonstore.ui.register

import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
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
                val user = it.result?.user

                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name).build()

                user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("TAG", "User profile updated.")
                        }
                    }
                mAuth.signOut()
                navigator?.signUpSuccess(phone, password)
                setIsLoading(false)
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
}
