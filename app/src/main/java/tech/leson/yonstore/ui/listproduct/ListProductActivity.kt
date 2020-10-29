package tech.leson.yonstore.ui.listproduct

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list_product.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.databinding.ActivityListProductBinding
import tech.leson.yonstore.ui.adapter.ProductAdapter
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.product.ProductActivity
import tech.leson.yonstore.utils.OnItemClickListener

class ListProductActivity :
    BaseActivity<ActivityListProductBinding, ListProductNavigator, ListProductViewModel>(),
    ListProductNavigator, OnItemClickListener<Product> {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, ListProductActivity::class.java).also { instance = it }
        }
    }

    private val mProductAdapter: ProductAdapter by inject(named("vertical"))

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_list_product
    override val viewModel: ListProductViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
    }

    override fun onGetProductSuccess(data: MutableList<Product>) {
        mProductAdapter.addAllData(data)
        val layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        rcvProduct.layoutManager = layoutManager
        mProductAdapter.onItemClickListener = this
        rcvProduct.adapter = mProductAdapter
    }

    override fun onBack() {
        finish()
    }

    override fun onClick(item: Product) {
        val intent = ProductActivity.getIntent(this)
        intent.putExtra("product", item)
        startActivity(intent)
    }
}