package tech.leson.yonstore.ui.addevent

import android.net.Uri
import android.view.View
import androidx.lifecycle.MutableLiveData
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Event
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class AddEventViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<AddEventNavigator>(dataManager, schedulerProvider) {

    val event = MutableLiveData(Event())

    fun addEvent() {
        setIsLoading(true)
        dataManager.createEvent(event.value!!)
            .addOnSuccessListener {
                setIsLoading(false)
                navigator?.onCreateSuccess()
            }
            .addOnFailureListener {
                setIsLoading(false)
                navigator?.onError(it.message.toString())
            }
    }

    fun saveImage(image: Uri) {
        if (event.value?.image != "") removeImage(event.value?.image!!)
        setIsLoading(true)
        dataManager.saveImage(image)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result.toString()
                    event.value?.image = downloadUri
                    navigator?.onLoadImage(downloadUri)
                } else {
                    navigator?.onError("Handle failures")
                }
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onError(it.message.toString())
                setIsLoading(false)
            }
    }

    private fun removeImage(url: String) {
        setIsLoading(true)
        dataManager.deleteImage(url)
            .addOnSuccessListener {
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onError(it.message.toString())
                setIsLoading(false)
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.cvImageEvent -> navigator?.onSelectImage()
            R.id.startTime -> navigator?.onPickStartDate()
            R.id.endTime -> navigator?.onPickEndDate()
            R.id.btnConfirm -> navigator?.onConfirm()
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
