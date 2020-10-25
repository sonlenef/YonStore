package tech.leson.yonstore.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.ui.favorite.FavoriteNavigator
import tech.leson.yonstore.ui.favorite.viewholder.ProductFavoriteViewHolder

class ProductFavoriteAdapter(data: MutableList<Product>) :
    BaseAdapter<ProductFavoriteViewHolder, Product>(data) {

    lateinit var favoriteNavigator: FavoriteNavigator

    override fun addData(data: Product) {
        this.data.add(data)
        notifyItemChanged(this.data.size - 1)
    }

    override fun addAllData(data: MutableList<Product>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun clearData() {
        this.data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductFavoriteViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_vertical, parent, false), favoriteNavigator)

    override fun onBindViewHolder(holder: ProductFavoriteViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int = data.size
}
