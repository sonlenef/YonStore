package tech.leson.yonstore.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.leson.yonstore.R
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.ui.main.home.model.Banner

class SlideShowAdapter (data: MutableList<Banner>) :
    BaseAdapter<SlideShowViewHolder, Banner>(data) {

    private var mData = data

    override fun addData(data: Banner) {
        mData.add(data)
        notifyItemChanged(mData.size - 1)
    }

    override fun addAllData(data: MutableList<Banner>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun clearData() {
        mData.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SlideShowViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_slide, parent, false))

    override fun onBindViewHolder(holder: SlideShowViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = mData.size
}
