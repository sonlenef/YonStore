package tech.leson.yonstore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image_review.view.*
import tech.leson.yonstore.R
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.ui.base.BaseViewHolder

class ReviewImageAdapter(data: MutableList<String>) :
    BaseAdapter<ReviewImageAdapter.ViewHolder, String>(data) {
    override fun addData(data: String) {
        this.data.add(data)
        notifyItemChanged(this.data.size - 1)
    }

    override fun addAllData(data: MutableList<String>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun clearData() {
        this.data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_review, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : BaseViewHolder<String>(itemView) {
        override fun onBind(data: String) {
            Glide.with(itemView.context).load(data).placeholder(R.drawable.default_image)
                .into(itemView.imvImage)
        }
    }
}
