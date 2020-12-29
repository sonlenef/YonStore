package tech.leson.yonstore.ui.favorite

import android.view.View
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.data.model.User
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class FavoriteViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<FavoriteNavigator>(dataManager, schedulerProvider) {

    fun getFavProducts() {
        setIsLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            dataManager.getUser(dataManager.getUserUid())
                .addOnSuccessListener {
                    for (doc in it) {
                        doc.toObject(User::class.java).favorite.forEach { productId ->
                            getProduct(productId)
                        }
                    }
                    setIsLoading(false)
                }
                .addOnFailureListener {
                    navigator?.onMsg(it.message.toString())
                    setIsLoading(false)
                }
        }
    }

    private fun getProduct(id: String) {
        dataManager.getProductById(id)
            .addOnSuccessListener {
                val product = it.toObject(Product::class.java)
                if (product != null) {
                    product.id = it.id
                }
                navigator?.setFavProduct(product!!)
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
