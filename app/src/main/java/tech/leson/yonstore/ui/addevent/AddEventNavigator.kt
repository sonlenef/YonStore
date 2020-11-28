package tech.leson.yonstore.ui.addevent

interface AddEventNavigator {
    fun onCreateSuccess()
    fun onSelectImage()
    fun onLoadImage(url: String)
    fun onPickStartDate()
    fun onPickEndDate()
    fun onConfirm()
    fun onBack()
    fun onError(msg: String)
}