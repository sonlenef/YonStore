package tech.leson.yonstore.ui.paymentList

interface ListPaymentNavigator {
    fun onPayOnDelivery()
    fun onCreditCart()
    fun onPaypal()
    fun onBank()
    fun onSuccess()
    fun onMsg(msg: String)
    fun onBack()
}
