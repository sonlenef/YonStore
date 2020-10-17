package tech.leson.yonstore.ui.main.home

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.rd.animation.type.AnimationType
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.FragmentHomeBinding
import tech.leson.yonstore.ui.base.BaseFragment
import tech.leson.yonstore.ui.main.home.adapter.CategoryAdapter
import tech.leson.yonstore.ui.main.home.adapter.ProductAdapter
import tech.leson.yonstore.ui.main.home.adapter.SlideShowAdapter
import tech.leson.yonstore.ui.main.home.model.Banner
import tech.leson.yonstore.ui.main.home.model.Category
import tech.leson.yonstore.ui.main.home.model.Product

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
    private val mCategoryAdapter: CategoryAdapter by inject()
    private val mFlashSaleAdapter: ProductAdapter by inject(qualifier = named("horizontal"))
    private val mMegaSaleAdapter: ProductAdapter by inject(qualifier = named("horizontal"))
    private val mRecProductAdapter: ProductAdapter by inject(qualifier = named("vertical"))

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
        mCategoryAdapter.addData(Category())
        mCategoryAdapter.addData(Category())
        mCategoryAdapter.addData(Category())
        mCategoryAdapter.addData(Category())
        mCategoryAdapter.addData(Category())
        mCategoryAdapter.addData(Category())
        mCategoryAdapter.addData(Category())
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvCategory.layoutManager = layoutManager
        rcvCategory.adapter = mCategoryAdapter
    }

    private fun setFlashSale() {
        mFlashSaleAdapter.addData(Product())
        mFlashSaleAdapter.addData(Product())
        mFlashSaleAdapter.addData(Product())
        mFlashSaleAdapter.addData(Product())
        mFlashSaleAdapter.addData(Product())
        mFlashSaleAdapter.addData(Product())
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvFlashSale.layoutManager = layoutManager
        rcvFlashSale.adapter = mFlashSaleAdapter
    }

    private fun setMegaSale() {
        mMegaSaleAdapter.addData(Product())
        mMegaSaleAdapter.addData(Product())
        mMegaSaleAdapter.addData(Product())
        mMegaSaleAdapter.addData(Product())
        mMegaSaleAdapter.addData(Product())
        mMegaSaleAdapter.addData(Product())
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcvMegaSale.layoutManager = layoutManager
        rcvMegaSale.adapter = mMegaSaleAdapter
    }

    private fun setRecProduct() {
        mRecProductAdapter.addData(Product())
        mRecProductAdapter.addData(Product())
        mRecProductAdapter.addData(Product())
        mRecProductAdapter.addData(Product())
        val layoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
        rcvRecProduct.layoutManager = layoutManager
        rcvRecProduct.adapter = mRecProductAdapter
    }
}
