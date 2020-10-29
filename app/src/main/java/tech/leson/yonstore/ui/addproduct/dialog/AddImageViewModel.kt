package tech.leson.yonstore.ui.addproduct.dialog

import android.view.View
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class AddImageViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<AddImageNavigator>(dataManager, schedulerProvider) {
    override fun onClick(view: View) {
        TODO("Not yet implemented")
    }
}
