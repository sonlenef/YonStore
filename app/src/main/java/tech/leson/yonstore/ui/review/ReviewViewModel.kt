package tech.leson.yonstore.ui.review

import android.net.Uri
import android.view.View
import androidx.lifecycle.MutableLiveData
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Review
import tech.leson.yonstore.data.model.User
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class ReviewViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<ReviewNavigator>(dataManager, schedulerProvider) {

    val user: MutableLiveData<User> = MutableLiveData()
    val review: MutableLiveData<Review> = MutableLiveData(Review())
    private val isReviewed: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getUserCurrent() {
        setIsLoading(true)
        dataManager.getUser(dataManager.getUserUid())
            .addOnSuccessListener {
                for (doc in it) {
                    val userData = doc.toObject(User::class.java)
                    userData.id = doc.id
                    user.postValue(userData)
                }
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
                setIsLoading(false)
            }
    }

    fun getMyReviewByProductId(userId: String, productId: String) {
        setIsLoading(true)
        dataManager.getMyReviewByProductId(userId, productId)
            .addOnSuccessListener {
                if (it.isEmpty) isReviewed.postValue(false)
                else isReviewed.postValue(true)
                for (doc in it) {
                    val rv = doc.toObject(Review::class.java)
                    rv.id = doc.id
                    review.postValue(rv)
                    navigator?.setReview(rv)
                }
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
                setIsLoading(false)
            }
    }

    fun saveImages(images: MutableList<Uri>) {
        setIsLoading(true)
        for (image in images) {
            dataManager.saveImage(image)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result.toString()
                        review.value?.images?.add(downloadUri)
                        navigator?.onAddImage(downloadUri)
                    } else {
                        navigator?.onMsg("Handle failures")
                    }
                    if (images.indexOf(image) == images.size - 1) setIsLoading(false)
                }
                .addOnFailureListener {
                    navigator?.onMsg(it.message.toString())
                    if (images.indexOf(image) == images.size - 1) setIsLoading(false)
                }
        }
    }

    fun saveReview() {
        setIsLoading(true)
        if (isReviewed.value!!) {
            dataManager.updateReview(review.value!!)
                .addOnSuccessListener {
                    navigator?.saveSuccess()
                    setIsLoading(false)
                }
                .addOnFailureListener {
                    navigator?.onMsg(it.message.toString())
                    setIsLoading(false)
                }
        } else {
            dataManager.createReview(review.value!!)
                .addOnSuccessListener {
                    navigator?.saveSuccess()
                    setIsLoading(false)
                }
                .addOnFailureListener {
                    navigator?.onMsg(it.message.toString())
                    setIsLoading(false)
                }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnAddPhoto -> navigator?.onAddPhoto()
            R.id.btnSave -> navigator?.onSave()
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
