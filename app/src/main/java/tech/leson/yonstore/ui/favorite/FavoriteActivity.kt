package tech.leson.yonstore.ui.favorite

import android.content.Context
import android.content.Intent
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.databinding.ActivityFavoriteBinding
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.favorite.adapter.ProductFavoriteAdapter
import tech.leson.yonstore.ui.product.ProductActivity

class FavoriteActivity :
    BaseActivity<ActivityFavoriteBinding, FavoriteNavigator, FavoriteViewModel>(),
    FavoriteNavigator {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, FavoriteActivity::class.java).also { instance = it }
        }
    }

    private val mFevProductAdapter: ProductFavoriteAdapter by inject()

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_favorite
    override val viewModel: FavoriteViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getText(R.string.favorite_product)
        setFevProduct()
    }

    private fun setFevProduct() {
//        mFevProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
//        mFevProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
//        mFevProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
//        mFevProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
//        mFevProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
//        mFevProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
//        mFevProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
//        mFevProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
        val layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        rcvFavorite.layoutManager = layoutManager
        mFevProductAdapter.favoriteNavigator = this
        rcvFavorite.adapter = mFevProductAdapter
    }

    override fun onProductClick(product: Product) {
        val intent = ProductActivity.getIntent(this)
        intent.putExtra("product", product)
        startActivity(intent)
    }

    override fun onBack() {
        finish()
    }
}