package tech.leson.yonstore.ui.main.explore

import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.FragmentExploreBinding
import tech.leson.yonstore.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExploreFragment : BaseFragment<FragmentExploreBinding, ExploreNavigator, ExploreViewModel>(),
    ExploreNavigator {

    companion object {
        private var instance: ExploreFragment? = null

        @JvmStatic
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: ExploreFragment().also { instance = it }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_explore
    override val viewModel: ExploreViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
    }
}
