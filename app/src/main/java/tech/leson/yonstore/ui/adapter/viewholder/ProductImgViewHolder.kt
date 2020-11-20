package tech.leson.yonstore.ui.adapter.viewholder

import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_slide_product.view.*
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.ProductImage
import tech.leson.yonstore.ui.base.BaseViewHolder

class ProductImgViewHolder(itemView: View) : BaseViewHolder<ProductImage>(itemView) {
    override fun onBind(data: ProductImage) {
        Glide.with(itemView.context).load(data.imgUrl).placeholder(R.drawable.default_image)
            .into(itemView.imvProduct)
    }
}
