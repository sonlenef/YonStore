package tech.leson.yonstore.ui.main.explore

import android.view.View
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class ExploreViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<ExploreNavigator>(dataManager, schedulerProvider) {

    fun getCategory() {
        dataManager.getCategoryByStyle("man")
            .addOnSuccessListener {
                val data: MutableList<Category> = ArrayList()
                for (doc in it) {
                    val category = doc.toObject(Category::class.java)
                    category.uid = doc.id
                    data.add(category)
                }
                navigator?.onManFashion(data)
            }
            .addOnFailureListener {
                navigator?.onError(it.message.toString())
            }
        dataManager.getCategoryByStyle("woman")
            .addOnSuccessListener {
                val data: MutableList<Category> = ArrayList()
                for (doc in it) {
                    val category = doc.toObject(Category::class.java)
                    category.uid = doc.id
                    data.add(category)
                }
                navigator?.onWomanFashion(data)
            }
            .addOnFailureListener {
                navigator?.onError(it.message.toString())
            }
    }

    override fun onClick(view: View) {
        //
    }
}
