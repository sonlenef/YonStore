package tech.leson.yonstore.ui.address

import android.view.View
import androidx.lifecycle.MutableLiveData
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.User
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class AddressViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<AddressNavigator>(dataManager, schedulerProvider) {

    val user: MutableLiveData<User> = MutableLiveData()

    fun getUser() {
        dataManager.getUser(dataManager.getUserUid())
            .addOnSuccessListener {
                for (doc in it) {
                    val data = doc.toObject(User::class.java)
                    data.id = doc.id
                    navigator?.onAddress(data.address)
                    user.postValue(data)
                }
            }
            .addOnFailureListener { navigator?.onMsg(it.message.toString()) }
    }

    fun updateUser() {
        setIsLoading(true)
        dataManager.updateUser(user.value!!)
            .addOnSuccessListener {
                getUser()
                setIsLoading(false)
            }
            .addOnFailureListener {
                setIsLoading(false)
                navigator?.onMsg(it.message.toString())
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnAddress -> navigator?.onAddAddress()
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
