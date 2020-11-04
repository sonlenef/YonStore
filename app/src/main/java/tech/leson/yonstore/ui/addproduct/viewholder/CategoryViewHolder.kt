package tech.leson.yonstore.ui.addproduct.viewholder

import android.view.View
import kotlinx.android.synthetic.main.item_add_category.view.*
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.ui.base.BaseViewHolder

class CategoryViewHolder(itemView: View) : BaseViewHolder<Category>(itemView) {
    override fun onBind(data: Category) {
        itemView.btnCategoryItem.text = data.name
    }
}
