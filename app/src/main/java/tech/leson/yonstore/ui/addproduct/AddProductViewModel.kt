package tech.leson.yonstore.ui.addproduct

import android.view.View
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class AddProductViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<AddProductNavigator>(dataManager, schedulerProvider) {
    override fun onClick(view: View) {
        when (view.id) {
            R.id.tvImage -> navigator?.onImage()
            R.id.tvStyle -> navigator?.onStyle()
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
