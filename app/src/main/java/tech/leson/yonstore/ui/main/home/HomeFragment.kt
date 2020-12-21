package tech.leson.yonstore.ui.main.home

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.rd.animation.type.AnimationType
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Banner
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.data.model.Event
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.databinding.FragmentHomeBinding
import tech.leson.yonstore.ui.adapter.CategoryAdapter
import tech.leson.yonstore.ui.adapter.ProductAdapter
import tech.leson.yonstore.ui.adapter.SlideShowAdapter
import tech.leson.yonstore.ui.base.BaseFragment
import tech.leson.yonstore.ui.category.CategoryActivity
import tech.leson.yonstore.ui.listproducts.ListProductsActivity
import tech.leson.yonstore.ui.product.ProductActivity
import tech.leson.yonstore.utils.OnCategoryClickListener
import tech.leson.yonstore.utils.OnProductClickListener

class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeNavigator, HomeViewModel>(),
    HomeNavigator, OnProductClickListener, OnCategoryClickListener, SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private var instance: HomeFragment? = null

        @JvmStatic
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: HomeFragment().also { instance = it }
        }
    }

    private val mSlideShowAdapter: SlideShowAdapter by inject()
    private val mCategoryAdapter: CategoryAdapter by inject(named("home"))
    private val mFlashSaleAdapter: ProductAdapter by inject(named("horizontal"))
    private val mMegaSaleAdapter: ProductAdapter by inject(named("horizontal"))
    private val mRecProductAdapter: ProductAdapter by inject(named("vertical"))

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)

        srLayoutHome.setOnRefreshListener(this)
        setFlashSale()
        setMegaSale()
        setRecProduct()
        viewModel.getData()
    }

    override fun setEvent(events: MutableList<Event>) {
        if (events.size > 0) {
            mSlideShowAdapter.clearData()
            mSlideShowAdapter.addAllData(events)
            pageIndicatorView.setAnimationType(AnimationType.WORM)
            slideShow.adapter = mSlideShowAdapter
            slideShow.visibility = View.VISIBLE

            pageIndicatorView.radius = 4
            pageIndicatorView.padding = 6
            pageIndicatorView.count = mSlideShowAdapter.itemCount
            pageIndicatorView.selection = slideShow.currentItem
            pageIndicatorView.visibility = View.VISIBLE
            slideShow.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    pageIndicatorView.selection = position
                    (slideShow.getChildAt(0) as RecyclerView).overScrollMode =
                        RecyclerView.OVER_SCROLL_NEVER
                }
            })
        }
    }

    override fun setCategory(categories: MutableList<Category>) {
        mCategoryAdapter.addAllData(categories)
        mCategoryAdapter.listener = this
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvCategory.layoutManager = layoutManager
        rcvCategory.adapter = mCategoryAdapter
    }

    private fun setFlashSale() {
        mFlashSaleAdapter.clearData()
        val product = Product()
        product.name = getString(R.string.name_product_demo)
        mFlashSaleAdapter.addData(product)
        mFlashSaleAdapter.addData(product)
        mFlashSaleAdapter.addData(product)
        mFlashSaleAdapter.addData(product)
        mFlashSaleAdapter.addData(product)
        mFlashSaleAdapter.addData(product)
        mFlashSaleAdapter.addData(product)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvFlashSale.layoutManager = layoutManager
        mFlashSaleAdapter.listener = this
        rcvFlashSale.adapter = mFlashSaleAdapter
    }

    private fun setMegaSale() {
        mMegaSaleAdapter.clearData()
        val product = Product()
        product.name = getString(R.string.name_product_demo)
        mMegaSaleAdapter.addData(product)
        mMegaSaleAdapter.addData(product)
        mMegaSaleAdapter.addData(product)
        mMegaSaleAdapter.addData(product)
        mMegaSaleAdapter.addData(product)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvMegaSale.layoutManager = layoutManager
        mMegaSaleAdapter.listener = this
        rcvMegaSale.adapter = mMegaSaleAdapter
    }

    private fun setRecProduct() {
        mRecProductAdapter.clearData()
        val product = Product()
        product.name = getString(R.string.name_product_demo)
        mRecProductAdapter.addData(product)
        mRecProductAdapter.addData(product)
        mRecProductAdapter.addData(product)
        mRecProductAdapter.addData(product)
        val layoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
        rcvRecProduct.layoutManager = layoutManager
        mRecProductAdapter.listener = this
        rcvRecProduct.adapter = mRecProductAdapter
    }

    override fun onMoreCategory() {
        activity?.let {
            startActivity(CategoryActivity.getIntent(it))
        }
    }

    override fun onMoreFlashSale() {}

    override fun onMoreMegaSale() {}

    override fun onError(msg: String) {
        activity?.let {
            Toast.makeText(it, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(product: Product) {
        val intent = activity?.let { ProductActivity.getIntent(it) }
        intent?.putExtra("product", product)
        startActivity(intent)
    }

    override fun onClick(category: Category) {
        activity?.let {
            val intent = ListProductsActivity.getIntent(it)
            intent.putExtra("type", ListProductsActivity.CATEGORY)
            intent.putExtra(ListProductsActivity.CATEGORY, category)
            startActivity(intent)
        }
    }

    override fun onRefresh() {
        setFlashSale()
        setMegaSale()
        setRecProduct()
        viewModel.getData()
        srLayoutHome.isRefreshing = false
    }
}
