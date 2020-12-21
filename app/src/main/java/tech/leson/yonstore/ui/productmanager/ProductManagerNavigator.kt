package tech.leson.yonstore.ui.productmanager

import tech.leson.yonstore.data.model.Product

interface ProductManagerNavigator {
    fun onNewProduct()
    fun onSearch()
    fun onScan()
    fun onProduct(product: Product)
    fun onProductEmpty()
    fun newProduct()
    fun onError(msg: String)
    fun onBack()
}
