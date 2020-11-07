package tech.leson.yonstore.ui.addproduct

import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.data.model.ProductImage
import tech.leson.yonstore.ui.addproduct.model.Style

interface AddProductNavigator {
    fun onImage()
    fun onStyle()
    fun onTakePhoto()
    fun onAddImage(image: ProductImage)
    fun onRemoveImageSuccess(position: Int)
    fun onAddProduct()
    fun onCategory()
    fun categorySelect(category: Category)
    fun addStyle(style: Style)
    fun onOpenGallery()
    fun onBack()
    fun onError(msg: String)
}
