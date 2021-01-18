package tech.leson.yonstore.ui.checkOrder

import android.view.View
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Order
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class CheckOrderViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<CheckOrderNavigator>(dataManager, schedulerProvider) {

    fun getAllOrder() {
        setIsLoading(true)
        dataManager.getAllOrder()
            .addOnSuccessListener {
                val data: MutableList<Order> = ArrayList()
                for (doc in it) {
                    val order = doc.toObject(Order::class.java)
                    order.id = doc.id
                    data.add(order)
                }
                navigator?.onOrders(data)
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
                setIsLoading(false)
            }
    }

    private fun getOrderByStatus(status: Int) {
        setIsLoading(true)
        dataManager.getOrderByStatus(status)
            .addOnSuccessListener {
                val data: MutableList<Order> = ArrayList()
                for (doc in it) {
                    val order = doc.toObject(Order::class.java)
                    order.id = doc.id
                    data.add(order)
                }
                navigator?.onOrders(data)
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
                setIsLoading(false)
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnAllOrder -> {
                navigator?.getAllOrder()
                getAllOrder()
            }
            R.id.btnPending -> {
                navigator?.getOrderPending()
                getOrderByStatus(0)
            }
            R.id.btnPacking -> {
                navigator?.getOrderPacking()
                getOrderByStatus(1)
            }
            R.id.btnShipping -> {
                navigator?.getOrderShipping()
                getOrderByStatus(2)
            }
            R.id.btnArriving -> {
                navigator?.getOrderArriving()
                getOrderByStatus(3)
            }
            R.id.btnSuccess -> {
                navigator?.getOrderSuccess()
                getOrderByStatus(4)
            }
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
