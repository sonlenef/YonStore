package tech.leson.yonstore.ui.listproducts

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list_products.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.databinding.ActivityListProductsBinding
import tech.leson.yonstore.ui.adapter.ProductAdapter
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.product.ProductActivity
import tech.leson.yonstore.utils.OnProductClickListener

class ListProductsActivity :
    BaseActivity<ActivityListProductsBinding, ListProductsNavigator, ListProductsViewModel>(),
    ListProductsNavigator, OnProductClickListener {

    companion object {
        const val CATEGORY = "category"
        const val CODE = "code"
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, ListProductsActivity::class.java).also { instance = it }
        }
    }

    private val mProductAdapter: ProductAdapter by inject(named("vertical"))

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_list_products
    override val viewModel: ListProductsViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)

        when (intent.getStringExtra("type")) {
            CATEGORY -> {
                val category = intent.getSerializableExtra(CATEGORY) as Category
                tvTitle.text = category.name
                viewModel.getProductsByCategory(category)
            }
            CODE -> {
                val code = intent.getStringExtra("code")
                code?.let {
                    viewModel.getProductsByCode(it)
                }
            }
        }
    }

    override fun onGetProductSuccess(data: MutableList<Product>) {
        mProductAdapter.addAllData(data)
        mProductAdapter.listener = this
        val layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        rcvProduct.layoutManager = layoutManager
        rcvProduct.adapter = mProductAdapter
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBack() {
        finish()
    }

    override fun onClick(product: Product) {
        val intent = ProductActivity.getIntent(this)
        intent.putExtra("product", product)
        intent.putExtra("inOrder", false)
        startActivity(intent)
    }
}