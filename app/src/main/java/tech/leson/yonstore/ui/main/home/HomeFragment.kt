package tech.leson.yonstore.ui.main.home

import android.content.Intent
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
import tech.leson.yonstore.databinding.FragmentHomeBinding
import tech.leson.yonstore.ui.base.BaseFragment
import tech.leson.yonstore.ui.main.CATEGORY
import tech.leson.yonstore.ui.main.MainActivity
import tech.leson.yonstore.ui.main.home.adapter.CategoryAdapter
import tech.leson.yonstore.ui.main.home.adapter.ProductAdapter
import tech.leson.yonstore.ui.main.home.adapter.SlideShowAdapter
import tech.leson.yonstore.data.model.Banner
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.ui.product.ProductActivity

class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeNavigator, HomeViewModel>(),
    HomeNavigator {

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
        setCategory()
        setFlashSale()
        setMegaSale()
        setRecProduct()
    }

    private fun setBanner() {
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

    private fun setCategory() {
        mCategoryAdapter.addData(Category(CATEGORY.MAN_SHIRT,
            getString(R.string.man_shirt),
            R.drawable.ct_man_shirt))
        mCategoryAdapter.addData(Category(CATEGORY.WOMAN_DRESS,
            getString(R.string.woman_dress),
            R.drawable.ct_woman_dress))
        mCategoryAdapter.addData(Category(CATEGORY.MAN_WORD_EQUIPMENT,
            getString(R.string.man_work_equipment),
            R.drawable.ct_man_bag))
        mCategoryAdapter.addData(Category(CATEGORY.WOMAN_BAG,
            getString(R.string.woman_bag),
            R.drawable.ct_woman_bag))
        mCategoryAdapter.addData(Category(CATEGORY.MAN_SHOES,
            getString(R.string.man_shoes),
            R.drawable.ct_man_shoes))
        mCategoryAdapter.addData(Category(CATEGORY.WOMAN_SHOES,
            getString(R.string.woman_shoes),
            R.drawable.ct_woman_shoes))
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvCategory.layoutManager = layoutManager
        rcvCategory.adapter = mCategoryAdapter
    }

    private fun setFlashSale() {
        mFlashSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mFlashSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mFlashSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mFlashSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mFlashSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mFlashSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvFlashSale.layoutManager = layoutManager
        mFlashSaleAdapter.homeNavigator = this
        rcvFlashSale.adapter = mFlashSaleAdapter
    }

    private fun setMegaSale() {
        mMegaSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mMegaSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mMegaSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mMegaSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mMegaSaleAdapter.addData(Product(getString(R.string.name_product_demo), null))
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvMegaSale.layoutManager = layoutManager
        mMegaSaleAdapter.homeNavigator = this
        rcvMegaSale.adapter = mMegaSaleAdapter
    }

    private fun setRecProduct() {
        mRecProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mRecProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mRecProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
        mRecProductAdapter.addData(Product(getString(R.string.name_product_demo), null))
        val layoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
        rcvRecProduct.layoutManager = layoutManager
        mRecProductAdapter.homeNavigator = this
        rcvRecProduct.adapter = mRecProductAdapter
    }

    override fun onMoreCategory() {
        activity?.let {
            (it as MainActivity).onSearch()
        }
    }

    override fun onMoreFlashSale() {}

    override fun onMoreMegaSale() {}

    override fun onProductClick(product: Product) {
        val intent = activity?.let { ProductActivity.getIntent(it) }
        intent?.putExtra("product", product)
        startActivity(intent)
    }
}
