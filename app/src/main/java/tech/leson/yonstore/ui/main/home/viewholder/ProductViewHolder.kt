package tech.leson.yonstore.ui.main.home.viewholder

import android.view.View
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.ui.base.BaseViewHolder
import tech.leson.yonstore.ui.main.home.HomeNavigator

class ProductViewHolder(itemView: View, homeNavigator: HomeNavigator) :
    BaseViewHolder<Product>(itemView) {

    private val mHomeNavigator = homeNavigator

    override fun onBind(data: Product) {
        itemView.setOnClickListener { mHomeNavigator.onProductClick(data) }
    }
}
