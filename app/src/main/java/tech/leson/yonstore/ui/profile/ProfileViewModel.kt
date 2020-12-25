package tech.leson.yonstore.ui.profile

import android.view.View
import androidx.lifecycle.MutableLiveData
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.User
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class ProfileViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<ProfileNavigator>(dataManager, schedulerProvider) {

    val user: MutableLiveData<User> = MutableLiveData()

    fun getUserInfo() {
        setIsLoading(true)
        dataManager.getUser(dataManager.getUserUid())
            .addOnSuccessListener {
                for (doc in it) {
                    val userData = doc.toObject(User::class.java)
                    userData.id = doc.id
                    user.postValue(userData)
                }
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onError(it.message.toString())
                setIsLoading(false)
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
