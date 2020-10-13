package tech.leson.yonstore.ui.splash

import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.ActivitySplashBinding
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashNavigator, SplashViewModel>(),
    SplashNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_splash
    override val viewModel: SplashViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        startActivity(LoginActivity.getIntent(this))
        finish()
    }
}
