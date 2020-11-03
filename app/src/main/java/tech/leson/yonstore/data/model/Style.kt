package tech.leson.yonstore.data.model

import java.io.Serializable

data class Style(
    val size: String,
    val color: String,
    val quantity: Int,
    val rest: Int,
) : Serializable {
    constructor() : this("", "none", 0, 0)
}
