package tech.leson.yonstore.ui.main.account

interface AccountNavigator {
    fun onProfile()
    fun onLogout()
    fun onError(msg: String)
}
