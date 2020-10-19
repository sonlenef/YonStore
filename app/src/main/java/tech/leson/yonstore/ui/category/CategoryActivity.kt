package tech.leson.yonstore.ui.category

import android.content.Context
import android.content.Intent
import kotlinx.android.synthetic.main.navigation_header_title.*
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.ActivityCategoryBinding
import tech.leson.yonstore.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryActivity :
    BaseActivity<ActivityCategoryBinding, CategoryNavigator, CategoryViewModel>(),
    CategoryNavigator {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, CategoryActivity::class.java).also { instance = it }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_category
    override val viewModel: CategoryViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getText(R.string.category)
    }

    override fun onBack() {
        finish()
    }
}
