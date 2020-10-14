package tech.leson.yonstore.ui.main.cart

import android.view.View
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class CartViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<CartNavigator>(dataManager, schedulerProvider) {
    override fun onClick(view: View) {
        //
    }
}
