package tech.leson.yonstore.ui.product

import tech.leson.yonstore.data.model.Review
import tech.leson.yonstore.ui.product.model.ProductStyle

interface ProductNavigator {
    fun setSize(sizes: MutableList<ProductStyle>)
    fun onAddToCart()
    fun onLike()
    fun onUnlike()
    fun setReviews(reviews: MutableList<Review>, averageRating: Float)
    fun reviewNone()
    fun onReviewModer()
    fun onMsg(msg: String)
    fun onBack()
}