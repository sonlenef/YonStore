package tech.leson.yonstore.ui.addproduct.dialog.addStyle

import android.view.View
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class AddStyleViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<AddStyleNavigator>(dataManager, schedulerProvider) {
    override fun onClick(view: View) {
        when (view.id) {
            R.id.tvColor -> navigator?.onSelectColor()
            R.id.btnAddStyle -> navigator?.onAddStyle()
            R.id.btnClose -> navigator?.onClose()
        }
    }
}
