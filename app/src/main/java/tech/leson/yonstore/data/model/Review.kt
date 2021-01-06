package tech.leson.yonstore.data.model

import java.io.Serializable

class Review(
    var productId: String,
    var rating: Float,
    var description: String,
    var userId: String,
    var name: String,
    var avatar: String,
    var images: MutableList<String>,
    var time: Long,
) : Serializable {
    constructor() : this("", 5.0F, "", "", "", "", ArrayList(), 0L)
}
