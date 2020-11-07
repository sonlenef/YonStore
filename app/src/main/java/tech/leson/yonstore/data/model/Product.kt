package tech.leson.yonstore.data.model

import java.io.Serializable

data class Product(
    var name: String,
    val serial: String,
    val category: Category,
    val specification: String,
    val images: MutableList<ProductImage>,
    val style: MutableList<Style>,
    val price: Double,
    val discount: Int,
) : Serializable {
    constructor() : this("",
        "",
        Category(),
        "From YonStore with love^.^",
        ArrayList<ProductImage>(),
        ArrayList<Style>(),
        0.0,
        0)
}
