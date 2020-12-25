package tech.leson.yonstore.data.model

class Cart(var product: Product, var style: Style, var qty: Int) {
    constructor() : this(Product(), Style(), 0)
}
