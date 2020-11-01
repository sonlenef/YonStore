package tech.leson.yonstore.ui.addproduct

import tech.leson.yonstore.ui.addproduct.model.Style

interface AddProductNavigator {
    fun onImage()
    fun onStyle()
    fun onTakePhoto()
    fun addStyle(style: Style)
    fun onOpenGallery()
    fun onBack()
}
