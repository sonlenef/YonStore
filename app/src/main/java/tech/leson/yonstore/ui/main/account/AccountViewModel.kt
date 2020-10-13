package tech.leson.yonstore.ui.main.account

import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class AccountViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<AccountNavigator>(dataManager, schedulerProvider) {
}
