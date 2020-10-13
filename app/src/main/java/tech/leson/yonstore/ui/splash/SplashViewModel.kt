package tech.leson.yonstore.ui.splash

import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class SplashViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<SplashNavigator>(dataManager, schedulerProvider)
