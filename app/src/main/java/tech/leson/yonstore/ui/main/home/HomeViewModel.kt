package tech.leson.yonstore.ui.main.home

import android.view.View
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class HomeViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<HomeNavigator>(dataManager, schedulerProvider) {
    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnCategoryMore -> navigator?.onMoreCategory()
            R.id.btnFlashSaleMore -> navigator?.onMoreFlashSale()
            R.id.btnMegaSaleMore -> navigator?.onMoreMegaSale()
        }
    }
}