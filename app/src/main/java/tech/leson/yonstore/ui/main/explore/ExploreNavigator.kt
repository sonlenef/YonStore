package tech.leson.yonstore.ui.main.explore

import tech.leson.yonstore.data.model.Category

interface ExploreNavigator {
    fun onManFashion(data: MutableList<Category>)
    fun onWomanFashion(data: MutableList<Category>)
    fun onError(msg: String)
}
