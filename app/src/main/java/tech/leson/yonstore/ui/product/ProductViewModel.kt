package tech.leson.yonstore.ui.product

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.data.model.User
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.ui.product.model.ProductColor
import tech.leson.yonstore.ui.product.model.ProductStyle
import tech.leson.yonstore.utils.rx.SchedulerProvider

class ProductViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<ProductNavigator>(dataManager, schedulerProvider) {

    val user: MutableLiveData<User> = MutableLiveData()
    val product: MutableLiveData<Product> = MutableLiveData()
    val averageRating = MutableLiveData(5.0F)
    val productStyles: MutableList<ProductStyle> = ArrayList()
    val favorite: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getUserCurrent() {
        setIsLoading(true)
        dataManager.getUser(dataManager.getUserUid())
            .addOnSuccessListener {
                for (doc in it) {
                    val userData = doc.toObject(User::class.java)
                    userData.id = doc.id
                    user.postValue(userData)

                    var check = 0
                    userData.favorite.forEach { item ->
                        if (item == product.value) check++
                    }

                    if (check == 0) {
                        navigator?.onUnlike()
                        favorite.postValue(false)
                    } else {
                        navigator?.onLike()
                        favorite.postValue(true)
                    }
                }
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
                setIsLoading(false)
            }
    }

    private fun changeFavorite() {
        setIsLoading(true)
        if (favorite.value!!) {
            user.value!!.favorite.remove(product.value)
        } else {
            user.value!!.favorite.add(0, product.value!!)
        }
        dataManager.updateUser(user.value!!)
            .addOnSuccessListener {
                getUserCurrent()
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
                setIsLoading(false)
            }
    }

    fun setProductStyle(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            for (x in product.styles) {
                var position = -1
                var colors: MutableList<ProductColor> = ArrayList()
                var rest = 0

                productStyles.forEachIndexed { i, style ->
                    if (style.size == x.size) {
                        position = i
                        colors = style.colors
                        colors.add(ProductColor(x.color, x.rest))
                        rest = style.rest + x.rest
                    }
                }

                if (position != -1) {
                    productStyles[position] = ProductStyle(x.size, colors, rest)
                } else {
                    colors.add(ProductColor(x.color, x.rest))
                    productStyles.add(ProductStyle(x.size, colors, x.rest))
                }
            }
            if (productStyles.size > 0) navigator?.setSize(productStyles)
        }
    }

    fun setAverageRating(product: Product) {
        var average = 5.0F
        viewModelScope.launch(Dispatchers.IO) {
            if (product.reviews.size > 0) {
                for (review in product.reviews) {
                    average = if (review == product.reviews[0]) {
                        review.rating
                    } else {
                        (average + review.rating) / 2
                    }
                }
            }
            averageRating.postValue(average)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnHeart -> {
                changeFavorite()
            }
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
