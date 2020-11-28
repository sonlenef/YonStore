package tech.leson.yonstore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Event
import tech.leson.yonstore.ui.adapter.viewholder.EventViewHolder
import tech.leson.yonstore.ui.base.BaseAdapter

class EventAdapter(data: MutableList<Event>) : BaseAdapter<EventViewHolder, Event>(data) {

    override fun addData(data: Event) {
        this.data.add(data)
        notifyItemChanged(this.data.size - 1)
    }

    override fun addAllData(data: MutableList<Event>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun clearData() {
        this.data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EventViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_event, parent, false))

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = data.size
}
