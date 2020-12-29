package tech.leson.yonstore.ui.favorite

import tech.leson.yonstore.data.model.Product

interface FavoriteNavigator {
    fun setFavProduct(product: Product)
    fun onProductClick(product: Product)
    fun onMsg(msg: String)
    fun onBack()
}
