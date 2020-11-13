package tech.leson.yonstore.data.model

import java.io.Serializable

data class Product(
    var name: String,
    var code: String,
    var category: Category,
    var specification: String,
    var images: MutableList<ProductImage>,
    var style: MutableList<Style>,
    var price: Double,
    var event: Event?,
    var reviews: MutableList<Review>
) : Serializable {
    constructor() : this("",
        "",
        Category(),
        "From YonStore with love^.^",
        ArrayList(),
        ArrayList(),
        0.0,
        null,
        ArrayList()
    )
}
