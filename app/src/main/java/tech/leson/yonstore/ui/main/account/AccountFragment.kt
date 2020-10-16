package tech.leson.yonstore.ui.main.account

import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.FragmentAccountBinding
import tech.leson.yonstore.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : BaseFragment<FragmentAccountBinding, AccountNavigator, AccountViewModel>(),
    AccountNavigator {

    companion object {
        private var instance: AccountFragment? = null

        @JvmStatic
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: AccountFragment().also { instance = it }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_account
    override val viewModel: AccountViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
    }
}