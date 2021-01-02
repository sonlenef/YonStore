package tech.leson.yonstore.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.item_address.view.*
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Address
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.ui.base.BaseViewHolder


class AddressAdapter(data: MutableList<Address>) :
    BaseAdapter<AddressAdapter.ViewHolder, Address>(data) {

    lateinit var setOnItemClickListener: SetOnItemClickListener
    private var itemCurrent = 0

    override fun addData(data: Address) {
        this.data.add(data)
        notifyItemChanged(this.data.size - 1)
    }

    override fun addAllData(data: MutableList<Address>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun clearData() {
        this.data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.item_address, parent, false), setOnItemClickListener, itemCurrent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = data.size

    fun setCurrentItem(position: Int) {
        itemCurrent = position
    }

    class ViewHolder(itemView: View, private val listener: SetOnItemClickListener, private val itemCurrent: Int) :
        BaseViewHolder<Address>(itemView) {
        @SuppressLint("SetTextI18n")
        override fun onBind(data: Address) {
            itemView.tvFullName.text = data.fullName
            val city: String = if (data.city == data.region)
                data.city
            else "${data.city}, ${data.region}"
            itemView.tvAddress.text =
                "${data.address1}, ${city}, ${data.nation}"
            itemView.tvPhoneNumber.text = data.phoneNumber

            itemView.btnEdit.setOnClickListener {
                listener.onEdit(data)
            }
            itemView.btnBin.setOnClickListener {
                listener.onBin(data)
            }
            itemView.itemAddress.setOnClickListener {
                listener.onSelected(absoluteAdapterPosition)
            }

            if (itemCurrent == absoluteAdapterPosition) {
                itemView.itemAddress.isSelected = true
            }
        }
    }

    interface SetOnItemClickListener {
        fun onSelected(position: Int)
        fun onEdit(address: Address)
        fun onBin(address: Address)
    }
}
