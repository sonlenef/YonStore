package tech.leson.yonstore.ui.adapter.viewholder

import android.view.View
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.item_color.view.*
import tech.leson.yonstore.ui.adapter.ProductColorAdapter
import tech.leson.yonstore.ui.base.BaseViewHolder
import tech.leson.yonstore.ui.product.model.ProductColor

class ProductColorViewHolder(
    itemView: View,
    private val currentItem: MutableLiveData<Int>,
    val listener: ProductColorAdapter.OnStyleColorClick,
) :
    BaseViewHolder<ProductColor>(itemView) {
    override fun onBind(data: ProductColor) {
        val resourceId = itemView.context.resources.getIdentifier(data.color,
            "color",
            itemView.context.packageName)
        itemView.imvColor.setImageResource(resourceId)
        if (currentItem.value != absoluteAdapterPosition) itemView.itemSelected.visibility = View.INVISIBLE
        else itemView.itemSelected.visibility = View.VISIBLE
        itemView.imvColor.setOnClickListener {
            itemView.itemSelected.visibility = View.VISIBLE
            listener.onColorClick(absoluteAdapterPosition)
        }
    }
}
