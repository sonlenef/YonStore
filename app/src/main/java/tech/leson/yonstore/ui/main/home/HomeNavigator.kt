package tech.leson.yonstore.ui.main.home

import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.data.model.Product

interface HomeNavigator {
    fun onMoreCategory()
    fun onMoreFlashSale()
    fun setCategory(categories: MutableList<Category>)
    fun onMoreMegaSale()
    fun onError(msg: String)
}
