package tech.leson.yonstore.ui.order

import android.view.View
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Order
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class OrderDetailsViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<OrderDetailsNavigator>(dataManager, schedulerProvider) {

    fun onSaveOrder(order: Order) {
        setIsLoading(true)
        dataManager.updateOrder(order)
            .addOnSuccessListener {
                navigator?.onSaveSuccess()
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
                setIsLoading(false)
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnNotifyMe -> navigator?.onBtnBottomClick()
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
