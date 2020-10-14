package tech.leson.yonstore.ui.main.offer

import android.view.View
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class OfferViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<OfferNavigator>(dataManager, schedulerProvider) {
    override fun onClick(view: View) {
        //
    }
}
