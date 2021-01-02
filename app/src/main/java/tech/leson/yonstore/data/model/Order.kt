package tech.leson.yonstore.data.model

import java.io.Serializable

data class Order(
    var product: MutableList<ProductInCart>,
    var address: Address,
    var status: Int,
    var date: Long,
) : Serializable {
    constructor() : this(ArrayList(), Address(), 0, 0L)
}
