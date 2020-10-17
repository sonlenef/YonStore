package tech.leson.yonstore.ui.main.home.viewholder

import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_category_explore.view.*
import tech.leson.yonstore.ui.base.BaseViewHolder
import tech.leson.yonstore.ui.main.home.model.Category

class CategoryHorizontalViewHolder(itemView: View) : BaseViewHolder<Category>(itemView) {
    override fun onBind(data: Category) {
        Glide.with(itemView.context).load(data.icon).into(itemView.imgCategory)
        itemView.tvCategoryName.text = data.name
    }
}
