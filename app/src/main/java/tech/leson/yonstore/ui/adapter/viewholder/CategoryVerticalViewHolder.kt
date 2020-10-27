package tech.leson.yonstore.ui.adapter.viewholder

import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_category_home.view.*
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.ui.base.BaseViewHolder

class CategoryVerticalViewHolder(itemView: View) : BaseViewHolder<Category>(itemView) {
    override fun onBind(data: Category) {
        if (data.getIcon() != 0)
            Glide.with(itemView.context).load(data.getIcon()).into(itemView.imgCategory)
        itemView.tvCategoryName.text = data.name
    }
}
