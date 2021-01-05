package tech.leson.yonstore.ui.main.account

interface AccountNavigator {
    fun onProfile()
    fun onOrder()
    fun onAddress()
    fun onManager()
    fun onLogout()
    fun onError(msg: String)
}
