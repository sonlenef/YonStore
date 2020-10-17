package tech.leson.yonstore.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.leson.yonstore.R
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.ui.main.home.model.Banner
import tech.leson.yonstore.ui.main.home.viewholder.SlideShowViewHolder

class SlideShowAdapter (data: MutableList<Banner>) :
    BaseAdapter<SlideShowViewHolder, Banner>(data) {

    override fun addData(data: Banner) {
        this.data.add(data)
        notifyItemChanged(this.data.size - 1)
    }

    override fun addAllData(data: MutableList<Banner>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun clearData() {
        this.data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SlideShowViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_slide, parent, false))

    override fun onBindViewHolder(holder: SlideShowViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = this.data.size
}
