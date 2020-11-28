package tech.leson.yonstore.ui.main.home

import android.view.View
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.data.model.Event
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class HomeViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<HomeNavigator>(dataManager, schedulerProvider) {

    fun getData() {
        dataManager.getAllEvent()
            .addOnSuccessListener {
                val data: MutableList<Event> = ArrayList()
                for (doc in it) {
                    val event = doc.toObject(Event::class.java)
                    data.add(event)
                }
                navigator?.setEvent(data)
            }
            .addOnFailureListener {
                navigator?.onError(it.message.toString())
            }
        dataManager.getLimitCategory(8)
            .addOnSuccessListener {
                val data: MutableList<Category> = ArrayList()
                for (doc in it) {
                    val category = doc.toObject(Category::class.java)
                    category.uid = doc.id
                    data.add(category)
                }
                navigator?.setCategory(data)
            }
            .addOnFailureListener {
                navigator?.onError(it.message.toString())
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnCategoryMore -> navigator?.onMoreCategory()
            R.id.btnFlashSaleMore -> navigator?.onMoreFlashSale()
            R.id.btnMegaSaleMore -> navigator?.onMoreMegaSale()
        }
    }
}