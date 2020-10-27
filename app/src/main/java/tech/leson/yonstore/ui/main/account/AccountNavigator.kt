package tech.leson.yonstore.ui.main.account

interface AccountNavigator {
    fun onProfile()
    fun onManager()
    fun onLogout()
    fun onError(msg: String)
}
