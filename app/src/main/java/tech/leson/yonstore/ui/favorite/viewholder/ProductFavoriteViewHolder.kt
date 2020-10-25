package tech.leson.yonstore.ui.favorite.viewholder

import android.view.View
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.ui.base.BaseViewHolder
import tech.leson.yonstore.ui.favorite.FavoriteNavigator
import tech.leson.yonstore.ui.main.home.HomeNavigator

class ProductFavoriteViewHolder(itemView: View, favoriteNavigator: FavoriteNavigator) :
    BaseViewHolder<Product>(itemView) {

    private val mFavoriteNavigator = favoriteNavigator

    override fun onBind(data: Product) {
        itemView.setOnClickListener { mFavoriteNavigator.onProductClick(data) }
    }
}
