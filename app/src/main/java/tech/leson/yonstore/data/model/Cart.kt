package tech.leson.yonstore.data.model

data class Cart(var productId: String, var style: Style, var qty: Int) {
    constructor() : this("", Style(), 0)
}
