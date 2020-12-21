package tech.leson.yonstore.ui.editproduct

import tech.leson.yonstore.data.model.ProductImage
import tech.leson.yonstore.data.model.Style

interface EditProductNavigator {
    fun onCategory()
    fun onImage()
    fun onStyle()
    fun onImages(images: MutableList<ProductImage>)
    fun onStyles(style: MutableList<Style>)
    fun onAddImage(image: ProductImage)
    fun onSave()
    fun onSaveSuccess()
    fun onRemoveProduct()
    fun onRemoveSuccess()
    fun onBack()
    fun onError(msg: String)
}
