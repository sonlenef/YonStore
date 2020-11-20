package tech.leson.yonstore.ui.product

import tech.leson.yonstore.ui.product.model.ProductStyle

interface ProductNavigator {
    fun setSize(sizes: MutableList<ProductStyle>)
    fun onBack()
}