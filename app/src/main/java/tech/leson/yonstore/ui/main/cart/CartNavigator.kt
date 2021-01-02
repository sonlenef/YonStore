package tech.leson.yonstore.ui.main.cart

import tech.leson.yonstore.data.model.ProductInCart

interface CartNavigator {
    fun addProduct(product: ProductInCart)
    fun maxItem(productInCart: ProductInCart, positionInCart: Int)
    fun updateCart()
    fun onCheckOut()
    fun onMsg(msg: String)
}
