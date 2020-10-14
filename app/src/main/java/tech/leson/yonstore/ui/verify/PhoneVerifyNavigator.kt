package tech.leson.yonstore.ui.verify

interface PhoneVerifyNavigator {
    fun onResend()
    fun onConfirm()
    fun onSuccess()
    fun onError(msg: String)
    fun onBack()
}
