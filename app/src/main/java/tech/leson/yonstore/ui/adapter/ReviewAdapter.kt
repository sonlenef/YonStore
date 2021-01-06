package tech.leson.yonstore.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_review.view.*
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Review
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.ui.base.BaseViewHolder
import java.text.SimpleDateFormat
import java.util.*

class ReviewAdapter(data: MutableList<Review>) :
    BaseAdapter<ReviewAdapter.ViewHolder, Review>(data) {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun addData(data: Review) {
        this.data.add(data)
        notifyItemChanged(this.data.size - 1)
    }

    override fun addAllData(data: MutableList<Review>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun clearData() {
        this.data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_review, parent, false), viewPool)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View, private val viewPool: RecyclerView.RecycledViewPool) :
        BaseViewHolder<Review>(itemView) {
        @SuppressLint("SimpleDateFormat")
        override fun onBind(data: Review) {
            Glide.with(itemView.context).load(data.avatar).placeholder(R.drawable.default_image)
                .into(itemView.imvAvatar)
            itemView.tvFullName.text = data.name
            itemView.rtPersonReview.rating = data.rating
            itemView.tvDescription.text = data.description
            itemView.tvDateReview.text = SimpleDateFormat("MMMM dd,yyyy").format(Date(data.time))

            itemView.rcvImgReview.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            itemView.rcvImgReview.adapter = ReviewImageAdapter(data.images)
            itemView.rcvImgReview.setRecycledViewPool(viewPool)
        }
    }
}
