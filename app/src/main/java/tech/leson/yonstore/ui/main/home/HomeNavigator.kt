package tech.leson.yonstore.ui.main.home

import tech.leson.yonstore.data.model.Product

interface HomeNavigator {
    fun onMoreCategory()
    fun onMoreFlashSale()
    fun onMoreMegaSale()
    fun onProductClick(product: Product)
}
