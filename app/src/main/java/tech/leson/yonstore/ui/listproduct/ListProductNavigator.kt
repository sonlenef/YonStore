package tech.leson.yonstore.ui.listproduct

import tech.leson.yonstore.data.model.Product

interface ListProductNavigator {
    fun onGetProductSuccess(data: MutableList<Product>)
    fun onBack()
}
