package tech.leson.yonstore.ui.addaddress

interface AddAddressNavigator {
    fun addAddress()
    fun addSuccess()
    fun onMsg(msg: String)
    fun onBack()
}
