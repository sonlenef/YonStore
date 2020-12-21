package tech.leson.yonstore.ui.addproduct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Style
import tech.leson.yonstore.ui.addproduct.AddProductNavigator
import tech.leson.yonstore.ui.addproduct.viewholder.StyleViewHolder
import tech.leson.yonstore.ui.base.BaseAdapter

class StyleAdapter(data: MutableList<Style>) : BaseAdapter<StyleViewHolder, Style>(data) {

    lateinit var addOnStyleClickListener: OnStyleClickListener

    override fun addData(data: Style) {
        for (item in this.data) {
            if (data == item) {
                return
            } else if (item.size == data.size && item.color == data.color) {
                item.quantity += data.quantity
                notifyItemChanged(this.data.indexOf(item))
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

    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StyleViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_product_style, parent, false), addOnStyleClickListener)

    override fun onBindViewHolder(holder: StyleViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = data.size

    interface OnStyleClickListener {
        fun onRemoveStyle(position: Int)
    }
}
