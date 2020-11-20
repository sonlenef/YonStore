package tech.leson.yonstore.ui.adapter.viewholder

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product_horizontal.view.*
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.ui.base.BaseViewHolder
import tech.leson.yonstore.utils.OnProductClickListener

class ProductHorizontalViewHolder(itemView: View, onItemClickListener: OnProductClickListener) :
    BaseViewHolder<Product>(itemView) {

    private val mItemClickListener = onItemClickListener

    @SuppressLint("SetTextI18n")
    override fun onBind(data: Product) {
        if (data.images.size > 0)
            Glide.with(itemView.context).load(data.images[0].imgUrl)
                .placeholder(R.drawable.default_image)
                .into(itemView.imvProduct)
        itemView.tvProductName.text = data.name
        if (data.event != null && data.event!!.discount >= 0.0) {
            itemView.tvProductPrice.text = "$${data.price / data.event!!.discount}"
            itemView.tvOldPrice.text = "$${data.price}"
            itemView.tvDiscount.text = "${data.event!!.discount * 100}% Off"
            itemView.layoutDiscount.visibility = View.VISIBLE
        } else {
            itemView.layoutDiscount.visibility = View.INVISIBLE
            itemView.tvProductPrice.text = "$${data.price}"
        }
        itemView.layoutProduct.setOnClickListener { mItemClickListener.onClick(data) }
    }
}
