package tech.leson.yonstore.ui.category

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.navigation_header_title.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.databinding.ActivityCategoryBinding
import tech.leson.yonstore.ui.adapter.CategoryAdapter
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.listproducts.ListProductsActivity
import tech.leson.yonstore.utils.OnCategoryClickListener

class CategoryActivity :
    BaseActivity<ActivityCategoryBinding, CategoryNavigator, CategoryViewModel>(),
    CategoryNavigator, OnCategoryClickListener {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, CategoryActivity::class.java).also { instance = it }
        }
    }

    private val mCategoryAdapter: CategoryAdapter by inject(named("category"))

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_category
    override val viewModel: CategoryViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getText(R.string.category)
        viewModel.getCategory()
    }

    override fun onGetDataSuccess(data: MutableList<Category>) {
        mCategoryAdapter.clearData()
        mCategoryAdapter.addAllData(data)
        mCategoryAdapter.listener = this
        val layoutManager = LinearLayoutManager(this)
        rcvCategory.layoutManager = layoutManager
        rcvCategory.adapter = mCategoryAdapter
    }

    override fun onBack() {
        finish()
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(category: Category) {
        val intent = ListProductsActivity.getIntent(this)
        intent.putExtra("type", ListProductsActivity.CATEGORY)
        intent.putExtra(ListProductsActivity.CATEGORY, category)
        startActivity(intent)
    }
}
