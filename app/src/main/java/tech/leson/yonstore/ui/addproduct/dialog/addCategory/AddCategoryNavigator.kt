package tech.leson.yonstore.ui.addproduct.dialog.addCategory

import tech.leson.yonstore.data.model.Category

interface AddCategoryNavigator {
    fun getCategoriesSuccess(data: MutableList<Category>)
    fun onCategorySelected(category: Category)
    fun onError(msg: String)
}
