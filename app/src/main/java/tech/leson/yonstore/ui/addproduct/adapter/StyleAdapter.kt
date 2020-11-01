package tech.leson.yonstore.ui.addproduct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.leson.yonstore.R
import tech.leson.yonstore.ui.addproduct.model.Style
import tech.leson.yonstore.ui.addproduct.viewholder.StyleViewHolder
import tech.leson.yonstore.ui.base.BaseAdapter

class StyleAdapter(data: MutableList<Style>) : BaseAdapter<StyleViewHolder, Style>(data) {
    override fun addData(data: Style) {
        for (item in this.data) {
            if (data == item) {
                return
            }
        }
        this.data.add(data)
        notifyItemChanged(this.data.size - 1)
    }

    override fun addAllData(data: MutableList<Style>) {
        with(this.data) {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StyleViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_product_style, parent, false))

    override fun onBindViewHolder(holder: StyleViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = data.size
}
