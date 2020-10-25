package tech.leson.yonstore.ui.favorite

import tech.leson.yonstore.data.model.Product

interface FavoriteNavigator {
    fun onProductClick(product: Product)
    fun onBack()
}
