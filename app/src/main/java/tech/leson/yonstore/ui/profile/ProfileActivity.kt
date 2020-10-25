package tech.leson.yonstore.ui.profile

import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.ActivityProductBinding
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.utils.NetworkUtils

class ProfileActivity : BaseActivity<ActivityProductBinding, ProfileNavigator, ProfileViewModel>(),
    ProfileNavigator {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, ProfileActivity::class.java).also { instance = it }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_profile
    override val viewModel: ProfileViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getString(R.string.profile)

        if (NetworkUtils.isNetworkConnected(this)) viewModel.getUserInfo()
        else Toast.makeText(this, getString(R.string.network_not_connected), Toast.LENGTH_SHORT)
            .show()
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBack() {
        finish()
    }
}
