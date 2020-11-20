package tech.leson.yonstore.utils

import tech.leson.yonstore.data.model.Product

interface OnProductClickListener {
    fun onClick(product: Product)
}
