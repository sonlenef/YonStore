package tech.leson.yonstore.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product_in_cart.view.*
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.data.model.Style
import tech.leson.yonstore.data.model.User
import tech.leson.yonstore.ui.base.BaseAdapter
import tech.leson.yonstore.ui.base.BaseViewHolder
import tech.leson.yonstore.ui.main.cart.model.ProductInCart

class ProductCartAdapter(data: MutableList<ProductInCart>) :
    BaseAdapter<ProductCartAdapter.ViewHolder, ProductInCart>(data) {

    lateinit var setOnBtnProductClick: SetOnBtnProductClick
    lateinit var user: MutableLiveData<User>

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
            R.layout.item_product_in_cart, parent, false), setOnBtnProductClick, user)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount() = data.size

    class ViewHolder(
        itemView: View,
        private val setOnBtnProductClick: SetOnBtnProductClick,
        private val user: MutableLiveData<User>,
    ) :
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

            if (checkFavorite(data.product.id) != -1) {
                itemView.btnHeart.setImageDrawable(getDrawable(itemView.context,
                    R.drawable.ic_heart_red))
            } else {
                itemView.btnHeart.setImageDrawable(getDrawable(itemView.context,
                    R.drawable.ic_heart))
            }

            itemView.tvCount.text = "${data.qty}"
            itemView.btnMinus.setOnClickListener {
                if (data.qty <= 1) {
                    setOnBtnProductClick.onBtnBinClick(data)
                } else {
                    setOnBtnProductClick.onBtnMinusClick(data)
                    data.qty--
                    itemView.tvCount.text = "${data.qty}"
                }
            }
            itemView.btnPlus.setOnClickListener {
                data.qty++
                itemView.tvCount.text = "${data.qty}"
                setOnBtnProductClick.onBtnPlusClick(data)
            }
            itemView.layoutProduct.setOnClickListener {
                setOnBtnProductClick.onProductClick(data.product, data.style)
            }
            itemView.btnHeart.setOnClickListener {
                if (checkFavorite(data.product.id) != -1) {
                    user.value!!.favorite.removeAt(checkFavorite(data.product.id))
                    itemView.btnHeart.setImageDrawable(getDrawable(itemView.context,
                        R.drawable.ic_heart))
                } else {
                    user.value!!.favorite.add(0, data.product.id)
                    itemView.btnHeart.setImageDrawable(getDrawable(itemView.context,
                        R.drawable.ic_heart_red))
                }
                setOnBtnProductClick.onBtnHeartClick(data.product)
            }
            itemView.btnBin.setOnClickListener {
                setOnBtnProductClick.onBtnBinClick(data)
            }
        }

        private fun checkFavorite(productId: String) = user.value!!.favorite.indexOf(productId)
    }

    interface SetOnBtnProductClick {
        fun onBtnMinusClick(productInCart: ProductInCart)
        fun onBtnPlusClick(productInCart: ProductInCart)
        fun onProductClick(product: Product, style: Style)
        fun onBtnHeartClick(product: Product)
        fun onBtnBinClick(productInCart: ProductInCart)
    }
}
