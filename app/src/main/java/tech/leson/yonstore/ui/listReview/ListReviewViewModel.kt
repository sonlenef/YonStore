package tech.leson.yonstore.ui.listReview

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Review
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class ListReviewViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<ListReviewNavigator>(dataManager, schedulerProvider) {

    private val reviews: MutableLiveData<MutableList<Review>> = MutableLiveData(ArrayList())

    fun getReviews(productId: String) {
        setIsLoading(true)
        dataManager.getReviewByProductId(productId)
            .addOnSuccessListener {
                val data: MutableList<Review> = ArrayList()
                for (doc in it) {
                    data.add(doc.toObject(Review::class.java))
                }
                reviews.postValue(data)
                navigator?.setReviews(data)
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
                setIsLoading(false)
            }
    }

    private fun getReviewsByStart(start: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (start) {
                0 -> withContext(Dispatchers.Main) {
                    navigator?.setReviews(reviews.value!!)
                }
                1 -> {
                    val data: MutableList<Review> = ArrayList()
                    reviews.value!!.forEach {
                        if (it.rating == 1F) data.add(it)
                    }
                    withContext(Dispatchers.Main) {
                        navigator?.setReviews(data)
                    }
                }
                2 -> {
                    val data: MutableList<Review> = ArrayList()
                    reviews.value!!.forEach {
                        if (it.rating == 2F) data.add(it)
                    }
                    withContext(Dispatchers.Main) {
                        navigator?.setReviews(data)
                    }
                }
                3 -> {
                    val data: MutableList<Review> = ArrayList()
                    reviews.value!!.forEach {
                        if (it.rating == 3F) data.add(it)
                    }
                    withContext(Dispatchers.Main) {
                        navigator?.setReviews(data)
                    }
                }
                4 -> {
                    val data: MutableList<Review> = ArrayList()
                    reviews.value!!.forEach {
                        if (it.rating == 4F) data.add(it)
                    }
                    withContext(Dispatchers.Main) {
                        navigator?.setReviews(data)
                    }
                }
                5 -> {
                    val data: MutableList<Review> = ArrayList()
                    reviews.value!!.forEach {
                        if (it.rating == 5F) data.add(it)
                    }
                    withContext(Dispatchers.Main) {
                        navigator?.setReviews(data)
                    }
                }
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnAllReview -> {
                navigator?.getAllReviews()
                getReviewsByStart(0)
            }
            R.id.btnOneStart -> {
                navigator?.getReviewOneStart()
                getReviewsByStart(1)
            }
            R.id.btnTwoStart -> {
                navigator?.getReviewTwoStart()
                getReviewsByStart(2)
            }
            R.id.btnThreeStart -> {
                navigator?.getReviewThreeStart()
                getReviewsByStart(3)
            }
            R.id.btnFourStart -> {
                navigator?.getReviewFourStart()
                getReviewsByStart(4)
            }
            R.id.btnFiveStart -> {
                navigator?.getReviewFiveStart()
                getReviewsByStart(5)
            }
            R.id.btnWriteReview -> navigator?.onWriteReview()
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
