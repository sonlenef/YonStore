package tech.leson.yonstore.ui.adapter.viewholder

import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_event.view.*
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Event
import tech.leson.yonstore.ui.base.BaseViewHolder

class EventViewHolder(itemView: View) : BaseViewHolder<Event>(itemView) {
    override fun onBind(data: Event) {
        Glide.with(itemView.context).load(data.image).placeholder(R.drawable.default_image).into(itemView.imvEvent)
        itemView.tvTitleEvent.text = data.title
    }
}
