package tech.leson.yonstore.ui.register

interface RegisterNavigator {
    fun signUpSuccess(email: String, password: String)
    fun onSignUp()
    fun onSignIn()
    fun onError(err: String)
}
