package tech.leson.yonstore.utils

import tech.leson.yonstore.data.model.Category

interface OnCategoryClickListener {
    fun onClick(category: Category)
}
