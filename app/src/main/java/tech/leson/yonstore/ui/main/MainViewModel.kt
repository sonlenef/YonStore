package tech.leson.yonstore.ui.main

import android.view.View
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class MainViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<MainNavigator>(dataManager, schedulerProvider) {

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnHeart -> navigator?.onFavorite()
            R.id.edtSearch -> navigator?.onSearch()
        }
    }
}
