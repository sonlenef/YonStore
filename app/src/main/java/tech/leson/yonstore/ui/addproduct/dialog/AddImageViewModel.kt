package tech.leson.yonstore.ui.addproduct.dialog

import android.view.View
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class AddImageViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<AddImageNavigator>(dataManager, schedulerProvider) {
    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnTakePhoto -> navigator?.onTakePhoto()
            R.id.btnOpenGallery -> navigator?.onOpenGallery()
            R.id.btnCancel -> navigator?.onCancel()
        }
    }
}
