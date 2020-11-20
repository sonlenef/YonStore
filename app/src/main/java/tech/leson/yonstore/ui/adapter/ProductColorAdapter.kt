package tech.leson.yonstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import tech.leson.yonstore.R
import tech.leson.yonstore.ui.adapter.viewholder.ProductColorViewHolder
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.ui.product.model.ProductColor

class ProductColorAdapter(data: MutableList<ProductColor>) :
    BaseAdapter<ProductColorViewHolder, ProductColor>(data) {

    lateinit var listener: OnStyleColorClick
    var currentItem = MutableLiveData(0)

    override fun addData(data: ProductColor) {
        this.data.add(data)
        notifyItemChanged(this.data.size - 1)
    }

    override fun addAllData(data: MutableList<ProductColor>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun clearData() {
        this.data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductColorViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_color, parent, false), currentItem, listener)

    override fun onBindViewHolder(holder: ProductColorViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = data.size

    interface OnStyleColorClick {
        fun onColorClick(position: Int)
    }
}
