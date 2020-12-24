package tech.leson.yonstore.ui.search

import tech.leson.yonstore.data.model.Product

interface SearchNavigator {
    fun onSearched(data: MutableList<Product>)
    fun onMsg(msg: String)
}
