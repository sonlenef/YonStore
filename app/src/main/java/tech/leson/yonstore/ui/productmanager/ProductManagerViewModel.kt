package tech.leson.yonstore.ui.productmanager

import android.view.View
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class ProductManagerViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<ProductManagerNavigator>(dataManager, schedulerProvider) {

    fun searchProduct(code: String, search: Boolean) {
        setIsLoading(true)
        dataManager.getProductByCode(code)
            .addOnSuccessListener {
                if (it.isEmpty) {
                    if (search) navigator?.onProductEmpty()
                    else navigator?.newProduct()
                } else {
                    var product: Product? = null
                    for (doc in it) {
                        product = doc.toObject(Product::class.java)
                        product.id = doc.id
                    }
                    product?.let { data ->
                        navigator?.onProduct(data)
                    }
                }
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onError(it.message.toString())
                setIsLoading(false)
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnInput -> navigator?.onSearch()
            R.id.btnScan -> navigator?.onScan()
            R.id.btnBack -> navigator?.onBack()
            R.id.btnNewProduct -> navigator?.onNewProduct()
        }
    }
}
