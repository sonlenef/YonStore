package tech.leson.yonstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.ui.main.home.HomeNavigator
import tech.leson.yonstore.ui.adapter.viewholder.ProductViewHolder

class ProductAdapter(data: MutableList<Product>, viewType: Int) :
    BaseAdapter<ProductViewHolder, Product>(data) {

    private val layoutViewType = viewType
    lateinit var homeNavigator: HomeNavigator

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
        if (viewType == LAYOUT_VIEW_TYPE_HORIZONTAL) ProductViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_horizontal, parent, false), homeNavigator)
        else ProductViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_vertical, parent, false), homeNavigator)

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int) = layoutViewType

    companion object {
        const val LAYOUT_VIEW_TYPE_VERTICAL = 0
        const val LAYOUT_VIEW_TYPE_HORIZONTAL = 1
    }
}
