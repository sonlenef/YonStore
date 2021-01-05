package tech.leson.yonstore.data.model

import java.io.Serializable

data class Product(
    var id: String,
    var name: String,
    var code: String,
    var category: Category,
    var specification: String,
    var images: MutableList<ProductImage>,
    var styles: MutableList<Style>,
    var price: Double,
    var discount: Double,
    var event: Event?
) : Serializable {
    constructor() : this("",
        "",
        "",
        Category(),
        "From YonStore with love^.^",
        ArrayList(),
        ArrayList(),
        0.0,
        0.0,
        null
    )
}
