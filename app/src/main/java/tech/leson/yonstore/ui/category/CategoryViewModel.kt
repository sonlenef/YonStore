package tech.leson.yonstore.ui.category

import android.view.View
import tech.leson.yonstore.R
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class CategoryViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<CategoryNavigator>(dataManager, schedulerProvider) {

    fun getCategory() {
        setIsLoading(true)
        dataManager.getAllCategory()
            .addOnSuccessListener {
                val data: MutableList<Category> = ArrayList()
                for (doc in it) {
                    val category = doc.toObject(Category::class.java)
                    category.uid = doc.id
                    data.add(category)
                }
                navigator?.onGetDataSuccess(data)
                setIsLoading(false)
            }
            .addOnFailureListener {
                navigator?.onError(it.message.toString())
                setIsLoading(false)
            }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnBack -> navigator?.onBack()
        }
    }
}
