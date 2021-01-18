package tech.leson.yonstore.ui.checkOrder

import tech.leson.yonstore.data.model.Order

interface CheckOrderNavigator {
    fun getAllOrder()
    fun getOrderPending()
    fun getOrderPacking()
    fun getOrderShipping()
    fun getOrderArriving()
    fun getOrderSuccess()
    fun onOrders(orders: MutableList<Order>)
    fun onMsg(msg: String)
    fun onBack()
}
