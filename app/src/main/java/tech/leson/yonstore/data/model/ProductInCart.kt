package tech.leson.yonstore.data.model

import java.io.Serializable

class ProductInCart(var product: Product, var style: Style, var qty: Int) : Serializable {
    constructor() : this(Product(), Style(), 0)
}
