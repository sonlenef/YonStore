package tech.leson.yonstore.ui.addevent

import android.view.View
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class AddEventViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<AddEventNavigator>(dataManager, schedulerProvider) {
    override fun onClick(view: View) {
        when (view.id) {
            R.id.startTime -> navigator?.onPickStartDate()
            R.id.endTime -> navigator?.onPickEndDate()
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
