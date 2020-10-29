package tech.leson.yonstore.ui.manager

import android.view.View
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class ManagerViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<ManagerNavigator>(dataManager, schedulerProvider) {
    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnEvent -> navigator?.onEvent()
            R.id.btnAddProduct -> navigator?.onAddProduct()
            R.id.btnStatistic -> navigator?.onStatistic()
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
