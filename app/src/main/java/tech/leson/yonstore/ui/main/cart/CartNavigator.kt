package tech.leson.yonstore.ui.main.cart

import tech.leson.yonstore.ui.main.cart.model.ProductInCart

interface CartNavigator {
    fun addProduct(product: ProductInCart)
    fun maxItem(productInCart: ProductInCart, positionInCart: Int)
    fun updateCart()
    fun onMsg(msg: String)
}
