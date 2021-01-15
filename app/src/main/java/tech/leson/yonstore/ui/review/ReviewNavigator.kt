package tech.leson.yonstore.ui.review

import tech.leson.yonstore.data.model.Review

interface ReviewNavigator {
    fun onAddPhoto()
    fun onAddImage(img: String)
    fun setReview(review: Review)
    fun onMsg(msg: String)
    fun onSave()
    fun saveSuccess()
    fun onBack()
}
