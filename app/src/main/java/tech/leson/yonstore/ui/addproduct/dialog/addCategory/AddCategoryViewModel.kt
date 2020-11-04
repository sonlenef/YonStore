package tech.leson.yonstore.ui.addproduct.dialog.addCategory

import android.view.View
import tech.leson.yonstore.data.DataManager
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.ui.base.BaseViewModel
import tech.leson.yonstore.utils.rx.SchedulerProvider

class AddCategoryViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<AddCategoryNavigator>(dataManager, schedulerProvider) {
    fun getCategories() {
        dataManager.getAllCategory()
            .addOnSuccessListener {
                val data: MutableList<Category> = ArrayList()
                for (doc in it) {
                    val category = doc.toObject(Category::class.java)
                    category.uid = doc.id
                    data.add(category)
                }
                navigator?.getCategoriesSuccess(data)
            }
            .addOnFailureListener {
                navigator?.onError(it.message.toString())
            }
    }

    override fun onClick(view: View) {}
}
