package tech.leson.yonstore.ui.main.account

import android.view.View
import androidx.lifecycle.MutableLiveData
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.User
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class AccountViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<AccountNavigator>(dataManager, schedulerProvider) {

    val manager: MutableLiveData<Boolean> = MutableLiveData(false)
    val userId: MutableLiveData<String> = MutableLiveData()

    fun getUserRole() {
        dataManager.getUser(dataManager.getUserUid())
            .addOnSuccessListener {
                for (doc in it) {
                    manager.postValue(doc.toObject(User::class.java).role == "admin")
                    userId.postValue(doc.id)
                }
            }
            .addOnFailureListener { navigator?.onError(it.message.toString()) }
    }

    private fun logout() {
        dataManager.logout()
        navigator?.onLogout()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnProfile -> navigator?.onProfile()
            R.id.btnOrder -> navigator?.onOrder()
            R.id.btnAddress -> navigator?.onAddress()
            R.id.btnManager -> navigator?.onManager()
            R.id.btnLogout -> logout()
        }
    }
}
