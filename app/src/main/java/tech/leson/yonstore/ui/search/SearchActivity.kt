package tech.leson.yonstore.ui.search

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.navigation_search.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.databinding.ActivitySearchBinding
import tech.leson.yonstore.ui.adapter.ProductAdapter
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.product.ProductActivity
import tech.leson.yonstore.utils.OnProductClickListener

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchNavigator, SearchViewModel>(),
    SearchNavigator, OnProductClickListener {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, SearchActivity::class.java).also { instance = it }
        }
    }

    private val mProductAdapter: ProductAdapter by inject(named("vertical"))

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_search
    override val viewModel: SearchViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    override fun init() {
        viewModel.setNavigator(this)

        edtSearch.isFocusableInTouchMode = true
        edtSearch.requestFocus()

        edtSearch.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (edtSearch.text.toString().trim() != "")
                    performSearch(edtSearch.text.toString().trim())
                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })
    }

    private fun performSearch(data: String) {
        if (isNetworkConnected()) {
            hideKeyboard()
            viewModel.onSearch(data)
        }
    }

    override fun onSearched(data: MutableList<Product>) {
        mProductAdapter.addAllData(data)
        mProductAdapter.listener = this
        val layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        rcvProduct.layoutManager = layoutManager
        rcvProduct.adapter = mProductAdapter
    }

    override fun onMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(product: Product) {
        val intent = ProductActivity.getIntent(this)
        intent.putExtra("product", product)
        startActivity(intent)
    }
}
