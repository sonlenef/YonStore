package tech.leson.yonstore.ui.splash

import android.view.View
import com.google.firebase.auth.FirebaseAuth
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class SplashViewModel(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    auth: FirebaseAuth,
) :
    BaseViewModel<SplashNavigator>(dataManager, schedulerProvider) {

    private val mAuth = auth

    fun checkLoginState() {
        setIsLoading(true)
        if (mAuth.currentUser == null) navigator?.openLogin()
        else navigator?.openMain()
        setIsLoading(false)
    }

    override fun onClick(view: View) {}

}
