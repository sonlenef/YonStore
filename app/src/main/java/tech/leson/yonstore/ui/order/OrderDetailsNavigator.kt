package tech.leson.yonstore.ui.order

interface OrderDetailsNavigator {
    fun onBtnBottomClick()
    fun onSaveSuccess()
    fun onMsg(msg: String)
    fun onBack()
}
