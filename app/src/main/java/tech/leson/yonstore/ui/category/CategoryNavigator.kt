package tech.leson.yonstore.ui.category

import tech.leson.yonstore.data.model.Category

interface CategoryNavigator {
    fun onGetDataSuccess(data: MutableList<Category>)
    fun onBack()
    fun onError(msg: String)
}