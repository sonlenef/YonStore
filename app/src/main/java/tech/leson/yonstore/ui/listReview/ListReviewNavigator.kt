package tech.leson.yonstore.ui.listReview

import tech.leson.yonstore.data.model.Review

interface ListReviewNavigator {
    fun getAllReviews()
    fun getReviewOneStart()
    fun getReviewTwoStart()
    fun getReviewThreeStart()
    fun getReviewFourStart()
    fun getReviewFiveStart()
    fun setReviews(reviews: MutableList<Review>)
    fun onMsg(msg: String)
    fun onBack()
}