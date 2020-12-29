package tech.leson.yonstore.ui.main.cart

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_cart.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Cart
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.data.model.Style
import tech.leson.yonstore.databinding.FragmentCartBinding
import tech.leson.yonstore.ui.adapter.ProductCartAdapter
import tech.leson.yonstore.ui.base.BaseFragment
import tech.leson.yonstore.ui.dialog.RemoveDialogFragment
import tech.leson.yonstore.ui.main.MainActivity
import tech.leson.yonstore.ui.main.cart.model.ProductInCart
import tech.leson.yonstore.ui.product.ProductActivity

class CartFragment : BaseFragment<FragmentCartBinding, CartNavigator, CartViewModel>(),
    CartNavigator, ProductCartAdapter.SetOnBtnProductClick, RemoveDialogFragment.OnDialogListener {

    companion object {
        private var instance: CartFragment? = null

        @JvmStatic
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: CartFragment().also { instance = it }
        }
    }

    private val mProductCartAdapter: ProductCartAdapter by inject()

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_cart
    override val viewModel: CartViewModel by viewModel()

    private var mItemRemove: ProductInCart? = null

    override fun init() {
        viewModel.setNavigator(this)

        val layoutManager = LinearLayoutManager(activity)
        rcvProduct.layoutManager = layoutManager
        mProductCartAdapter.setOnBtnProductClick = this
        mProductCartAdapter.user = viewModel.user
        rcvProduct.adapter = mProductCartAdapter
    }

    override fun onStart() {
        super.onStart()
        mProductCartAdapter.clearData()
        viewModel.getCartProducts()
    }

    override fun addProduct(product: ProductInCart) {
        mProductCartAdapter.addData(product)
        updateCart()
    }

    override fun maxItem(productInCart: ProductInCart, positionInCart: Int) {
        mProductCartAdapter.data.indexOf(productInCart).let {
            mProductCartAdapter.data[it].qty = viewModel.user.value!!.cart[positionInCart].qty
            mProductCartAdapter.notifyItemChanged(positionInCart)
        }
    }

    override fun onMsg(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBtnMinusClick(productInCart: ProductInCart) {
        viewModel.user.value!!.cart.indexOf(Cart(productInCart.product.id,
            productInCart.style,
            productInCart.qty)).let { position ->
            if (position != -1) {
                viewModel.user.value!!.cart[position].qty--
                viewModel.updateUser()
            }
        }
    }

    override fun onBtnPlusClick(productInCart: ProductInCart) {
        viewModel.user.value!!.cart.indexOf(Cart(productInCart.product.id,
            productInCart.style,
            (productInCart.qty - 1)))
            .let { position ->
                if (position != -1) {
                    viewModel.checkRestProduct(productInCart, position)
                }
            }
    }

    override fun onProductClick(product: Product, style: Style) {
        product.styles = ArrayList()
        product.styles.add(style)
        val intent = ProductActivity.getIntent(requireActivity())
        intent.putExtra("product", product)
        startActivity(intent)
    }

    override fun onBtnHeartClick(product: Product) {
        mProductCartAdapter.data.forEachIndexed { index, productInCart ->
            if (product == productInCart.product) mProductCartAdapter.notifyItemChanged(index)
        }
        viewModel.updateUser()
    }

    override fun onBtnBinClick(productInCart: ProductInCart) {
        mItemRemove = productInCart
        val removeDialog = RemoveDialogFragment()
        removeDialog.onDialogListener = this
        removeDialog.show(childFragmentManager, "removeProduct")
    }

    override fun onRemove() {
        mItemRemove?.let {
            mProductCartAdapter.data.indexOf(mItemRemove).let { position ->
                if (position != -1) {
                    mProductCartAdapter.data.removeAt(position)
                    mProductCartAdapter.notifyItemRemoved(position)
                }
            }
            viewModel.user.value!!.cart.indexOf(Cart(it.product.id, it.style, it.qty))
                .let { position ->
                    if (position != -1) {
                        viewModel.user.value!!.cart.removeAt(position)
                        viewModel.updateUser()
                    }
                }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun updateCart() {
        mProductCartAdapter.data.let { cart ->
            var item = 0
            var price = 0.00
            var dis = 0
            var discount = 0.00
            var ship = 40.00
            var total = 0.00
            cart.forEach {
                item += it.qty
                price += it.product.price * (1 - it.product.discount) * it.qty
            }
            discount = dis * (price + ship)
            total = price - discount - ship

            rowItems.text = "Items (${item})"
            rowPrice.text = "$${price}"
            rowShip.text = "$${ship}"
            rowDis.text = "Discount (${dis}%)"
            rowDiscount.text = "$${discount}"
            rowTotal.text = "$${total}"
        }
    }
}
