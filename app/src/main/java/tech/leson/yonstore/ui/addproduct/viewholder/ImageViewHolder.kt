package tech.leson.yonstore.ui.addproduct.viewholder

import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product_image.view.*
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.ProductImage
import tech.leson.yonstore.ui.base.BaseViewHolder
import tech.leson.yonstore.utils.OnItemClickListener

class ImageViewHolder(itemView: View, private val onItemClickListener: OnItemClickListener<Int>) :
    BaseViewHolder<ProductImage>(itemView) {
    override fun onBind(data: ProductImage) {
        Glide.with(itemView.context).load(data.imgUrl).placeholder(R.drawable.default_image)
            .into(itemView.imvProductImage)
        itemView.btnDelete.setOnClickListener { onItemClickListener.onClick(absoluteAdapterPosition) }
    }
}
