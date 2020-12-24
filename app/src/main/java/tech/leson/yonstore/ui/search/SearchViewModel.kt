package tech.leson.yonstore.ui.search

import android.view.View
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class SearchViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<SearchNavigator>(dataManager, schedulerProvider) {

    fun onSearch(searchText: String) {
        setIsLoading(true)
        dataManager.searchProduct(searchText)
            .addOnSuccessListener {
                val data: MutableList<Product> = ArrayList()
                for (doc in it) {
                    val product = doc.toObject(Product::class.java)
                    product.id = doc.id
                    data.add(product)
                }
                navigator?.onSearched(data)
                setIsLoading(false)
            }
            .addOnFailureListener {
                setIsLoading(false)
                navigator?.onMsg(it.message.toString())
            }
    }

    override fun onClick(view: View) {}
}
