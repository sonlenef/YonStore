package tech.leson.yonstore.ui.addproduct

import android.graphics.Bitmap

interface AddProductNavigator {
    fun onImage()
    fun onStyle()
    fun addImageProduct(img: Bitmap)
    fun onBack()
}
