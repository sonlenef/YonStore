package tech.leson.yonstore.ui.main.offer

import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.FragmentOfferBinding
import tech.leson.yonstore.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfferFragment : BaseFragment<FragmentOfferBinding, OfferNavigator, OfferViewModel>(),
    OfferNavigator {

    companion object {
        private var instance: OfferFragment? = null

        @JvmStatic
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: OfferFragment().also { instance = it }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_offer
    override val viewModel: OfferViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
    }
}
