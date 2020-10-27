package tech.leson.yonstore.ui.product

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.rd.animation.type.AnimationType
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.data.model.ProductImage
import tech.leson.yonstore.databinding.ActivityProductBinding
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.adapter.ProductImgAdapter

class ProductActivity : BaseActivity<ActivityProductBinding, ProductNavigator, ProductViewModel>(),
    ProductNavigator {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, ProductActivity::class.java).also { instance = it }
        }
    }

    private val mProductImgAdapter: ProductImgAdapter by inject()

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_product
    override val viewModel: ProductViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)

        val product: Product = intent.getSerializableExtra("product") as Product
        product.let {
            tvTitle.text = it.name
            tvProductName.text = it.name
            setImages(it.images)
        }
        dataDemo()
    }

    private fun dataDemo() {
        rtProduct.rating = 3.5F
    }

    private fun setImages(images: MutableList<ProductImage>?) {
        mProductImgAdapter.addData(ProductImage())
        mProductImgAdapter.addData(ProductImage())

        slideProduct.adapter = mProductImgAdapter

        productIndicatorView.setAnimationType(AnimationType.WORM)
        productIndicatorView.radius = 4
        productIndicatorView.padding = 6
        productIndicatorView.count = mProductImgAdapter.itemCount
        productIndicatorView.selection = slideProduct.currentItem
        slideProduct.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                productIndicatorView.selection = position
                (slideProduct.getChildAt(0) as RecyclerView).overScrollMode =
                    RecyclerView.OVER_SCROLL_NEVER
            }
        })
    }

    override fun onBack() {
        finish()
    }
}
