package tech.leson.yonstore.ui.favorite

import android.view.View
import androidx.lifecycle.MutableLiveData
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.User
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class FavoriteViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<FavoriteNavigator>(dataManager, schedulerProvider) {

    fun getFavProducts() {
        setIsLoading(true)
        dataManager.getUser(dataManager.getUserUid())
            .addOnSuccessListener {
                for (doc in it) {
                    navigator?.setFavProduct(doc.toObject(User::class.java).favorite)
                }
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onMsg(it.message.toString())
                setIsLoading(false)
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
