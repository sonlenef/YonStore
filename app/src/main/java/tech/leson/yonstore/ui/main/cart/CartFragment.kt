package tech.leson.yonstore.ui.main.cart

import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.FragmentCartBinding
import tech.leson.yonstore.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : BaseFragment<FragmentCartBinding, CartNavigator, CartViewModel>(),
    CartNavigator {

    companion object {
        private var instance: CartFragment? = null

        @JvmStatic
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: CartFragment().also { instance = it }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_cart
    override val viewModel: CartViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
    }
}
