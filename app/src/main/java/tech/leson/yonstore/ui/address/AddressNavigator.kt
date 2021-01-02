package tech.leson.yonstore.ui.address

import tech.leson.yonstore.data.model.Address

interface AddressNavigator {
    fun onAddAddress()
    fun onAddress(address: MutableList<Address>)
    fun onMsg(msg: String)
    fun onBack()
}
