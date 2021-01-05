package tech.leson.yonstore.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product_order.view.*
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.ProductInCart
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.ui.base.BaseViewHolder

class ProductOrderAdapter(data: MutableList<ProductInCart>) :
    BaseAdapter<ProductOrderAdapter.ViewHolder, ProductInCart>(data) {

    lateinit var listener: SetOnClickProduct

    override fun addData(data: ProductInCart) {
        this.data.add(data)
        notifyItemChanged(this.data.size - 1)
    }

    override fun addAllData(data: MutableList<ProductInCart>) {
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
            R.layout.item_product_order, parent, false), listener)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View, private val listener: SetOnClickProduct) :
        BaseViewHolder<ProductInCart>(itemView) {
        @SuppressLint("SetTextI18n")
        override fun onBind(data: ProductInCart) {
            if (data.product.images.size > 0)
                Glide.with(itemView.context).load(data.product.images[0].imgUrl)
                    .placeholder(R.drawable.default_image)
                    .into(itemView.imvProduct)
            itemView.tvProductName.text = data.product.name

            if (data.product.discount > 0.0) {
                itemView.tvProductPrice.text =
                    "$${data.product.price * (1 - data.product.discount)}"
                itemView.tvOldPrice.text = "$${data.product.price}"
                itemView.tvDiscount.text = "${data.product.discount * 100}% Off"
                itemView.layoutDiscount.visibility = View.VISIBLE
            } else {
                itemView.layoutDiscount.visibility = View.INVISIBLE
                itemView.tvProductPrice.text = "$${data.product.price}"
            }

            itemView.layoutProduct.setOnClickListener {
                listener.onClick(data)
            }
        }
    }

    interface SetOnClickProduct {
        fun onClick(product: ProductInCart)
    }
}
