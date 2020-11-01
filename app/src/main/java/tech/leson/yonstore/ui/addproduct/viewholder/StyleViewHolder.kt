package tech.leson.yonstore.ui.addproduct.viewholder

import android.view.View
import kotlinx.android.synthetic.main.item_product_style.view.*
import tech.leson.yonstore.R
import tech.leson.yonstore.ui.addproduct.model.Style
import tech.leson.yonstore.ui.base.BaseViewHolder

class StyleViewHolder(itemView: View) : BaseViewHolder<Style>(itemView) {
    override fun onBind(data: Style) {
        itemView.tvSize.text = data.size
        itemView.tvColor.text = data.color
        itemView.tvQuantity.text = data.quantity.toString()

        itemView.layoutProductStyle.visibility = View.GONE
        itemView.imvShowStyle.setImageResource(R.drawable.ic_arrow_down)
        itemView.btnShowStyle.setOnClickListener {
            if (itemView.layoutProductStyle.visibility == View.VISIBLE) {
                itemView.layoutProductStyle.visibility = View.GONE
                itemView.imvShowStyle.setImageResource(R.drawable.ic_arrow_down)
            } else {
                itemView.layoutProductStyle.visibility = View.VISIBLE
                itemView.imvShowStyle.setImageResource(R.drawable.ic_arrow_up)
            }
        }
    }
}
