package tech.leson.yonstore.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_order.view.*
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Order
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.ui.base.BaseViewHolder
import java.text.SimpleDateFormat
import java.util.*

class OrderAdapter(data: MutableList<Order>) : BaseAdapter<OrderAdapter.ViewHolder, Order>(data) {

    lateinit var listener: SetOnClickOrder

    override fun addData(data: Order) {
        this.data.add(data)
        notifyItemChanged(this.data.size - 1)
    }

    override fun addAllData(data: MutableList<Order>) {
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
            R.layout.item_order, parent, false), listener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View, private val listener: SetOnClickOrder) :
        BaseViewHolder<Order>(itemView) {
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        override fun onBind(data: Order) {
            var items = 0
            var price = 0.00
            val time: String = SimpleDateFormat("MMMM dd,yyyy").format(Date(data.date))
            val status: String = when (data.status) {
                0 -> "Pending"
                1 -> "Packing"
                2 -> "Shipping"
                3 -> "Arriving"
                4 -> "Success"
                else -> ""
            }
            data.product.forEach {
                items += it.qty
                price += it.product.price * (1 - it.product.discount) * it.qty
            }
            itemView.tvTitle.text = data.id
            itemView.tvTime.text = "Order at YonStore: $time"
            itemView.tvStatus.text = status
            itemView.tvItems.text = "$items Items purchased"
            itemView.tvPrice.text = "$$price"

            itemView.layoutOrder.setOnClickListener {
                listener.onClick(data)
            }
        }
    }

    interface SetOnClickOrder {
        fun onClick(order: Order)
    }
}
