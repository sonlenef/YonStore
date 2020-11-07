package tech.leson.yonstore.data.model

import java.io.Serializable

data class Style(
    var size: String,
    var color: String,
    var quantity: Int,
    var rest: Int,
) : Serializable {
    constructor() : this("", "none", 0, 0)
}
