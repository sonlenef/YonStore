package tech.leson.yonstore.ui.product

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.rd.animation.type.AnimationType
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Cart
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.data.model.ProductImage
import tech.leson.yonstore.data.model.Style
import tech.leson.yonstore.databinding.ActivityProductBinding
import tech.leson.yonstore.ui.adapter.ProductColorAdapter
import tech.leson.yonstore.ui.adapter.ProductImgAdapter
import tech.leson.yonstore.ui.adapter.ProductSizeAdapter
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.product.model.ProductColor
import tech.leson.yonstore.ui.product.model.ProductStyle

class ProductActivity : BaseActivity<ActivityProductBinding, ProductNavigator, ProductViewModel>(),
    ProductNavigator, ProductSizeAdapter.OnSizeStyleClick, ProductColorAdapter.OnStyleColorClick {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, ProductActivity::class.java).also { instance = it }
        }
    }

    private val mProductImgAdapter: ProductImgAdapter by inject()
    private val mProductSizeAdapter: ProductSizeAdapter by inject()
    private val mProductColorAdapter: ProductColorAdapter by inject()

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_product
    override val viewModel: ProductViewModel by viewModel()

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun init() {
        viewModel.setNavigator(this)
        mProductSizeAdapter.listener = this
        mProductColorAdapter.listener = this

        viewModel.getUserCurrent()

        val product: Product = intent.getSerializableExtra("product") as Product
        product.let {
            viewModel.product.value = it
            tvTitle.text = it.name
            setImages(it.images)
            if (it.discount > 0.0) {
                tvProductPrice.text = "$${it.price * (1 - it.discount)}"
                tvOldPrice.text = "$${it.price}"
                tvDiscount.text = "${it.discount * 100}% Off"
                layoutDiscount.visibility = View.VISIBLE
            } else {
                layoutDiscount.visibility = View.GONE
                tvProductPrice.text = "$${it.price}"
            }
            viewModel.setProductStyle(it)
            viewModel.setAverageRating(it)
            rtProduct.rating = viewModel.averageRating.value ?: 5.0F
            rtProductReview.rating = viewModel.averageRating.value ?: 5.0F
            tvProductReview.text = "${viewModel.averageRating.value} (${it.reviews.size} review)"
            tvSpecificationText.text = it.specification
        }

        if (viewModel.favorite.value!!) btnHeart.setImageDrawable(getDrawable(R.drawable.ic_heart_red))
        else btnHeart.setImageDrawable(getDrawable(R.drawable.ic_heart))
    }

    private fun setImages(images: MutableList<ProductImage>) {

        mProductImgAdapter.addAllData(images)

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

    override fun setSize(sizes: MutableList<ProductStyle>) {
        mProductSizeAdapter.addAllData(sizes)
        mProductSizeAdapter.currentItem.postValue(0)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcvSize.layoutManager = layoutManager
        rcvSize.adapter = mProductSizeAdapter

        setColor(viewModel.productStyles[0].colors)
    }

    override fun onAddToCart() {
        val style = Style(mProductSizeAdapter.data[mProductSizeAdapter.currentItem.value!!].size,
            mProductColorAdapter.data[mProductColorAdapter.currentItem.value!!].color,
            1,
            1)
        val cart = Cart(viewModel.product.value!!.id, style, 1)
        if (isNetworkConnected()) viewModel.addToCart(cart)
        else Toast.makeText(this, getString(R.string.network_not_connected), Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onLike() {
        btnHeart.setImageDrawable(getDrawable(R.drawable.ic_heart_red))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onUnlike() {
        btnHeart.setImageDrawable(getDrawable(R.drawable.ic_heart))
    }

    override fun onMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun setColor(colors: MutableList<ProductColor>) {
        mProductColorAdapter.addAllData(colors)
        mProductColorAdapter.currentItem.postValue(0)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcvColor.layoutManager = layoutManager
        rcvColor.adapter = mProductColorAdapter
    }

    override fun onBack() {
        finish()
    }

    override fun onSizeClick(position: Int) {
        if (mProductSizeAdapter.currentItem.value == position) return
        val positionOld = mProductSizeAdapter.currentItem.value
        mProductSizeAdapter.currentItem.value = position
        mProductSizeAdapter.notifyItemChanged(positionOld!!)
        setColor(mProductSizeAdapter.data[position].colors)
    }

    override fun onColorClick(position: Int) {
        if (mProductColorAdapter.currentItem.value == position) return
        val positionOld = mProductColorAdapter.currentItem.value
        mProductColorAdapter.currentItem.value = position
        mProductColorAdapter.notifyItemChanged(positionOld!!)
    }
}
