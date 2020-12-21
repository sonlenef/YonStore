package tech.leson.yonstore.ui.editproduct

import android.net.Uri
import android.view.View
import androidx.lifecycle.MutableLiveData
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.data.model.ProductImage
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class EditProductViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<EditProductNavigator>(dataManager, schedulerProvider) {
    val product: MutableLiveData<Product> = MutableLiveData(Product())

    fun loadData() {
        navigator?.onImages(product.value!!.images)
        navigator?.onStyles(product.value!!.styles)
    }

    fun updateProduct() {
        setIsLoading(true)
        dataManager.updateProduct(product.value!!)
            .addOnSuccessListener {
                setIsLoading(false)
                navigator?.onSaveSuccess()
            }
            .addOnFailureListener {
                setIsLoading(false)
                navigator?.onError(it.message.toString())
            }
    }

    fun removeProduct() {
        setIsLoading(true)
        dataManager.removeProduct(product.value!!)
            .addOnSuccessListener {
                setIsLoading(false)
                navigator?.onRemoveSuccess()
            }
            .addOnFailureListener {
                setIsLoading(false)
                navigator?.onError(it.message.toString())
            }
    }

    fun saveImages(images: MutableList<Uri>) {
        setIsLoading(true)
        for (image in images) {
            dataManager.saveImage(image)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result.toString()
                        product.value?.images?.add(ProductImage(downloadUri))
                        navigator?.onAddImage(ProductImage(downloadUri))
                    } else {
                        navigator?.onError("Handle failures")
                    }
                    if (images.indexOf(image) == images.size - 1) setIsLoading(false)
                }
                .addOnFailureListener {
                    navigator?.onError(it.message.toString())
                    if (images.indexOf(image) == images.size - 1) setIsLoading(false)
                }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tvImage -> navigator?.onImage()
            R.id.tvStyle -> navigator?.onStyle()
            R.id.listCategory -> navigator?.onCategory()
            R.id.btnSaveProduct -> navigator?.onSave()
            R.id.btnRemove -> navigator?.onRemoveProduct()
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
