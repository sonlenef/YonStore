package tech.leson.yonstore.ui.addproduct

import tech.leson.yonstore.data.model.ProductImage
import tech.leson.yonstore.data.model.Style

interface AddProductNavigator {
    fun onImage()
    fun onStyle()
    fun onAddImage(image: ProductImage)
    fun onRemoveImageSuccess(position: Int)
    fun onAddProduct()
    fun onAddProductSuccess()
    fun onCategory()
    fun addStyle(style: Style)
    fun onRemoveStyle(position: Int)
    fun onBack()
    fun onError(msg: String)
}
