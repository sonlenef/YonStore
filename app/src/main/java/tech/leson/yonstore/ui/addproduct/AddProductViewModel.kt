package tech.leson.yonstore.ui.addproduct

import android.net.Uri
import android.view.View
import androidx.lifecycle.MutableLiveData
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.data.model.ProductImage
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class AddProductViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<AddProductNavigator>(dataManager, schedulerProvider) {

    val product: MutableLiveData<Product> = MutableLiveData(Product())

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

    fun removeImage(position: Int, url: String) {
        setIsLoading(true)
        dataManager.deleteImage(url)
            .addOnSuccessListener {
                navigator?.onRemoveImageSuccess(position)
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onError(it.message.toString())
                setIsLoading(false)
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tvImage -> navigator?.onImage()
            R.id.tvStyle -> navigator?.onStyle()
            R.id.listCategory -> navigator?.onCategory()
            R.id.btnAddProduct -> navigator?.onAddProduct()
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
