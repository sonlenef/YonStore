package tech.leson.yonstore.ui.listOrder

import tech.leson.yonstore.data.model.Order

interface ListOrderNavigator {
    fun setOrders(orders: MutableList<Order>)
    fun onMsg(msg: String)
    fun onBack()
}
