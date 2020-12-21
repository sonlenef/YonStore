package tech.leson.yonstore.ui.product

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.ui.product.model.ProductColor
import tech.leson.yonstore.ui.product.model.ProductStyle
import tech.leson.yonstore.utils.rx.SchedulerProvider

class ProductViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<ProductNavigator>(dataManager, schedulerProvider) {

    val averageRating = MutableLiveData(5.0F)
    val productStyles: MutableList<ProductStyle> = ArrayList()

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
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
