package tech.leson.yonstore.ui.listproducts

import tech.leson.yonstore.data.model.Product

interface ListProductsNavigator {
    fun onGetProductSuccess(data: MutableList<Product>)
    fun onError(msg: String)
    fun onBack()
}
