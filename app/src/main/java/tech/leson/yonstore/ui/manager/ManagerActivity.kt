package tech.leson.yonstore.ui.manager

import android.content.Context
import android.content.Intent
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.ActivityManagerBinding
import tech.leson.yonstore.ui.base.BaseActivity

class ManagerActivity : BaseActivity<ActivityManagerBinding, ManagerNavigator, ManagerViewModel>(),
    ManagerNavigator {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, ManagerActivity::class.java).also { instance = it }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_manager
    override val viewModel: ManagerViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getString(R.string.manager)
    }

    override fun onBack() {
        finish()
    }
}
