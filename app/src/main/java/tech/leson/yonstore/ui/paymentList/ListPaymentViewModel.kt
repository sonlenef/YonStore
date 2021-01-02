package tech.leson.yonstore.ui.paymentList

import android.view.View
import androidx.lifecycle.MutableLiveData
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.User
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class ListPaymentViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<ListPaymentNavigator>(dataManager, schedulerProvider) {

    val user: MutableLiveData<User> = MutableLiveData()

    fun getUserCurrent() {
        setIsLoading(true)
        dataManager.getUser(dataManager.getUserUid())
            .addOnSuccessListener {
                for (doc in it) {
                    user.postValue(doc.toObject(User::class.java))
                }
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
                setIsLoading(false)
            }
    }

    fun updateUser() {
        setIsLoading(true)
        dataManager.updateUser(user.value!!)
            .addOnSuccessListener {
                getUserCurrent()
                navigator?.onSuccess()
                setIsLoading(false)
            }
            .addOnFailureListener {
                setIsLoading(false)
                navigator?.onMsg(it.message.toString())
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnPayOnDelivery -> navigator?.onPayOnDelivery()
            R.id.btnCreditCart -> navigator?.onCreditCart()
            R.id.btnPaypal -> navigator?.onPaypal()
            R.id.btnBank -> navigator?.onBank()
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
