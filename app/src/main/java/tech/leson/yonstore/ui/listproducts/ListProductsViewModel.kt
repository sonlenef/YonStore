package tech.leson.yonstore.ui.listproducts

import android.view.View
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class ListProductsViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<ListProductsNavigator>(dataManager, schedulerProvider) {

    fun getProductsByCategory(category: Category) {
        setIsLoading(true)
        dataManager.getProductByCategory(category)
            .addOnSuccessListener {
                val data: MutableList<Product> = ArrayList()
                for (doc in it) {
                    val product = doc.toObject(Product::class.java)
                    product.id = doc.id
                    data.add(product)
                }
                navigator?.onGetProductSuccess(data)
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onError(it.message.toString())
                setIsLoading(false)
            }
    }

    fun getProductsByCode(code: String) {
        setIsLoading(true)
        dataManager.getProductByCode(code)
            .addOnSuccessListener {
                val data: MutableList<Product> = ArrayList()
                for (doc in it) {
                    val product = doc.toObject(Product::class.java)
                    product.id = doc.id
                    data.add(product)
                }
                navigator?.onGetProductSuccess(data)
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onError(it.message.toString())
                setIsLoading(false)
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
