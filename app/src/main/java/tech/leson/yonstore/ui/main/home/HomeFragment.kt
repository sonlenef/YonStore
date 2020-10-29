package tech.leson.yonstore.ui.main.home

import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.databinding.FragmentHomeBinding
import tech.leson.yonstore.ui.adapter.CategoryAdapter
import tech.leson.yonstore.ui.adapter.ProductAdapter
import tech.leson.yonstore.ui.adapter.SlideShowAdapter
import tech.leson.yonstore.ui.base.BaseFragment
import tech.leson.yonstore.ui.category.CategoryActivity
import tech.leson.yonstore.ui.product.ProductActivity
import tech.leson.yonstore.utils.OnItemClickListener

class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeNavigator, HomeViewModel>(),
    HomeNavigator, OnItemClickListener<Product> {

    companion object {
        private var instance: HomeFragment? = null

        @JvmStatic
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: HomeFragment().also { instance = it }
        }
    }

    private val mSlideShowAdapter: SlideShowAdapter by inject()
    private val mCategoryAdapter: CategoryAdapter by inject(named("home"))
    private val mFlashSaleAdapter: ProductAdapter by inject(named("flashSale"))
    private val mMegaSaleAdapter: ProductAdapter by inject(named("megaSale"))
    private val mRecProductAdapter: ProductAdapter by inject(named("vertical"))

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)

        setBanner()
        setFlashSale()
        setMegaSale()
        setRecProduct()
        viewModel.getData()
    }

    private fun setBanner() {
        mSlideShowAdapter.clearData()
        mSlideShowAdapter.addData(Banner("Hahahah"))
        mSlideShowAdapter.addData(Banner("Hahahah"))
        mSlideShowAdapter.addData(Banner("Hahahah"))
        pageIndicatorView.setAnimationType(AnimationType.WORM)
        slideShow.adapter = mSlideShowAdapter

        pageIndicatorView.radius = 4
        pageIndicatorView.padding = 6
        pageIndicatorView.count = mSlideShowAdapter.itemCount
        pageIndicatorView.selection = slideShow.currentItem
        slideShow.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                pageIndicatorView.selection = position
                (slideShow.getChildAt(0) as RecyclerView).overScrollMode =
                    RecyclerView.OVER_SCROLL_NEVER
            }
        })
    }

    override fun setCategory(categories: MutableList<Category>) {
        mCategoryAdapter.clearData()
        mCategoryAdapter.addAllData(categories)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvCategory.layoutManager = layoutManager
        rcvCategory.adapter = mCategoryAdapter
    }

    private fun setFlashSale() {
        mFlashSaleAdapter.clearData()
        mFlashSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mFlashSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mFlashSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mFlashSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mFlashSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mFlashSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvFlashSale.layoutManager = layoutManager
        mFlashSaleAdapter.onItemClickListener = this
        rcvFlashSale.adapter = mFlashSaleAdapter
    }

    private fun setMegaSale() {
        mMegaSaleAdapter.clearData()
        mMegaSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mMegaSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mMegaSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mMegaSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mMegaSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvMegaSale.layoutManager = layoutManager
        mMegaSaleAdapter.onItemClickListener = this
        rcvMegaSale.adapter = mMegaSaleAdapter
    }

    private fun setRecProduct() {
        mRecProductAdapter.clearData()
        mRecProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mRecProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mRecProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mRecProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
        val layoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
        rcvRecProduct.layoutManager = layoutManager
        mRecProductAdapter.onItemClickListener = this
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

    override fun onClick(item: Product) {
        val intent = activity?.let { ProductActivity.getIntent(it) }
        intent?.putExtra("product", item)
        startActivity(intent)
    }
}
