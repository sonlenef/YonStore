package tech.leson.yonstore.ui.addproduct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.leson.yonstore.R
import tech.leson.yonstore.ui.addproduct.model.Image
import tech.leson.yonstore.ui.addproduct.viewholder.ImageViewHolder
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.utils.OnItemClickListener

class ImageAdapter(data: MutableList<Image>) : BaseAdapter<ImageViewHolder, Image>(data) {

    lateinit var onItemClickListener: OnItemClickListener<Int>

    override fun addData(data: Image) {
        for (item in this.data) {
            if (data == item) {
                return
            }
        }
        this.data.add(data)
        notifyItemChanged(this.data.size - 1)
    }

    override fun addAllData(data: MutableList<Image>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_product_image, parent, false), onItemClickListener)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = data.size
}
