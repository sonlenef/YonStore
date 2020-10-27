package tech.leson.yonstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.leson.yonstore.R
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.data.model.ProductImage
import tech.leson.yonstore.ui.adapter.viewholder.ProductImgViewHolder

class ProductImgAdapter(data: MutableList<ProductImage>) :
    BaseAdapter<ProductImgViewHolder, ProductImage>(data) {
    override fun addData(data: ProductImage) {
        this.data.add(data)
        notifyItemChanged(this.data.size - 1)
    }

    override fun addAllData(data: MutableList<ProductImage>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun clearData() {
        this.data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductImgViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_slide_product, parent, false))

    override fun onBindViewHolder(holder: ProductImgViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = data.size
}