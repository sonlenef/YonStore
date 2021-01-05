package tech.leson.yonstore.ui.listOrder

import android.view.View
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Order
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class ListOrderViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<ListOrderNavigator>(dataManager, schedulerProvider) {

    fun getOrder(userId: String) {
        setIsLoading(true)
        dataManager.getOrderByUserId(userId)
            .addOnSuccessListener {
                val data: MutableList<Order> = ArrayList()
                for (doc in it) {
                    val order = doc.toObject(Order::class.java)
                    order.id = doc.id
                    data.add(order)
                }
                navigator?.setOrders(data)
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
                setIsLoading(false)
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
