package tech.leson.yonstore.data.model

import java.io.Serializable

data class Order(
    var id: String,
    var userId: String,
    var product: MutableList<ProductInCart>,
    var address: Address,
    var status: Int,
    var date: Long,
    var dateShipping: Long,
    var shipping: String,
    var noRes: String,
) : Serializable {
    constructor() : this("", "", ArrayList(), Address(), 0, 0L, 0L, "", "")
}
