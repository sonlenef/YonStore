package tech.leson.yonstore.ui.main.explore

import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class ExploreViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<ExploreNavigator>(dataManager, schedulerProvider) {
}
