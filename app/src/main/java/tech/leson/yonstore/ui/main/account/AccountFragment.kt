package tech.leson.yonstore.ui.main.account

import android.widget.Toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.FragmentAccountBinding
import tech.leson.yonstore.ui.address.AddressActivity
import tech.leson.yonstore.ui.base.BaseFragment
import tech.leson.yonstore.ui.login.LoginActivity
import tech.leson.yonstore.ui.manager.ManagerActivity
import tech.leson.yonstore.ui.profile.ProfileActivity

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
        viewModel.getUserRole()
    }

    override fun onProfile() {
        activity?.let { startActivity(ProfileActivity.getIntent(it)) }
    }

    override fun onAddress() {
        activity?.let { startActivity(AddressActivity.getInstance(it)) }
    }

    override fun onManager() {
        activity?.let { startActivity(ManagerActivity.getIntent(it)) }
    }

    override fun onLogout() {
        activity?.let {
            startActivity(LoginActivity.getIntent(it))
            it.finish()
        }
    }

    override fun onError(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
}
