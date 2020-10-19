package tech.leson.yonstore.data.model

import java.io.Serializable

class Product(val name: String, val images: MutableList<ProductImage>?) : Serializable