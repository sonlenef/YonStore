package tech.leson.yonstore.data.model

class Review(
    var rating: Float,
    var description: String,
    var userId: String,
    var images: MutableList<ReviewImg>,
    var time: String,
) {
    constructor() : this(0.0F, "", "", ArrayList(), "")
}
