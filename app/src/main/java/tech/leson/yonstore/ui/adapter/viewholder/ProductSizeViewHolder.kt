package tech.leson.yonstore.ui.adapter.viewholder

import android.view.View
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.item_color.view.*
import kotlinx.android.synthetic.main.item_size.view.*
import kotlinx.android.synthetic.main.item_size.view.itemSelected
import tech.leson.yonstore.ui.adapter.ProductSizeAdapter
import tech.leson.yonstore.ui.base.BaseViewHolder
import tech.leson.yonstore.ui.product.model.ProductStyle

class ProductSizeViewHolder(
    itemView: View,
    private val currentItem: MutableLiveData<Int>,
    val listener: ProductSizeAdapter.OnSizeStyleClick,
) :
    BaseViewHolder<ProductStyle>(itemView) {
    override fun onBind(data: ProductStyle) {
        itemView.tvSize.text = data.size
        if (currentItem.value != absoluteAdapterPosition) itemView.itemSelected.visibility = View.INVISIBLE
        else itemView.itemSelected.visibility = View.VISIBLE
        itemView.tvSize.setOnClickListener {
            itemView.itemSelected.visibility = View.VISIBLE
            listener.onSizeClick(absoluteAdapterPosition)
        }
    }
}
