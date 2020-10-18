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
import org.koin.core.qualifier.named
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.ActivityFavoriteBinding
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.main.home.adapter.ProductAdapter
import tech.leson.yonstore.ui.main.home.model.Product

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

    private val mFevProductAdapter: ProductAdapter by inject(named("favorite"))

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
        mFevProductAdapter.addData(Product())
        mFevProductAdapter.addData(Product())
        mFevProductAdapter.addData(Product())
        mFevProductAdapter.addData(Product())
        mFevProductAdapter.addData(Product())
        mFevProductAdapter.addData(Product())
        mFevProductAdapter.addData(Product())
        mFevProductAdapter.addData(Product())
        val layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        rcvFavorite.layoutManager = layoutManager
        rcvFavorite.adapter = mFevProductAdapter
    }

    override fun onBack() {
        finish()
    }
}