package tech.leson.yonstore.ui.login

interface LoginNavigator {
    fun onSignIn()
    fun signInSuccess()
    fun onRegister()
    fun onError(err: String)
}
