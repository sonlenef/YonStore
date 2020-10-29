package tech.leson.yonstore.ui.adapter.viewholder

import android.view.View
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.ui.base.BaseViewHolder
import tech.leson.yonstore.ui.main.home.HomeNavigator
import tech.leson.yonstore.utils.OnItemClickListener

class ProductViewHolder(itemView: View, onItemClickListener: OnItemClickListener<Product>) :
    BaseViewHolder<Product>(itemView) {

    private val mItemClickListener = onItemClickListener

    override fun onBind(data: Product) {
        itemView.setOnClickListener { mItemClickListener.onClick(data) }
    }
}
