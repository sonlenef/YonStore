package tech.leson.yonstore.ui.order

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Order
import tech.leson.yonstore.data.model.ProductInCart
import tech.leson.yonstore.databinding.ActivityOrderDetailsBinding
import tech.leson.yonstore.ui.adapter.ProductOrderAdapter
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.product.ProductActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderDetailsActivity :
    BaseActivity<ActivityOrderDetailsBinding, OrderDetailsNavigator, OrderDetailsViewModel>(),
    OrderDetailsNavigator, ProductOrderAdapter.SetOnClickProduct {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, OrderDetailsActivity::class.java).also { instance = it }
        }
    }

    private val mProductOrderAdapter: ProductOrderAdapter by inject()

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_order_details
    override val viewModel: OrderDetailsViewModel by viewModel()

    private var mOrder: Order? = null

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getString(R.string.order_details)

        (intent.getSerializableExtra("order") as Order).let {
            mOrder = it
            setPacing(it.status)

            val city: String = if (it.address.city == it.address.region)
                it.address.city
            else "${it.address.city}, ${it.address.region}"
            tvAddress.text = "${it.address.address1}, ${city}, ${it.address.nation}"

            mProductOrderAdapter.listener = this
            mProductOrderAdapter.addAllData(it.product)
            rcvProducts.layoutManager = LinearLayoutManager(this)
            rcvProducts.adapter = mProductOrderAdapter

            if (it.dateShipping == 0L) {
                tvDateShipping.text = getString(R.string.unknown)
            } else {
                tvDateShipping.text = SimpleDateFormat("MMMM dd,yyyy").format(Date(it.date))
            }

            if (it.shipping == "") {
                tvShipping.text = getString(R.string.unknown)
            } else {
                tvShipping.text = it.shipping
            }

            if (it.noRes == "") {
                tvNoResi.text = getString(R.string.unknown)
            } else {
                tvNoResi.text = it.noRes
            }

            var item = 0
            var price = 0.00
            var dis = 0
            var discount = 0.00
            var ship = 40.00
            var total = 0.00

            it.product.forEach { product ->
                item += product.qty
                price += product.product.price * (1 - product.product.discount) * product.qty
            }

            if (item == 0) {
                ship = 0.00
            }

            discount = dis * (price + ship)
            total = price - discount + ship

            rowItems.text = "Items (${item})"
            rowPrice.text = "$${price}"
            rowShip.text = "$${ship}"
            rowDis.text = "Discount (${dis}%)"
            rowDiscount.text = "$${discount}"
            rowTotal.text = "$${total}"

            if (intent.getBooleanExtra("isCheck", false)) {
                if (it.status < 4) {
                    btnNotifyMe.visibility = View.VISIBLE
                    btnNotifyMe.text = getString(R.string.next)
                } else {
                    btnNotifyMe.visibility = View.GONE
                }
            }
        }
    }

    override fun onBtnBottomClick() {
        mOrder?.let {
            if (intent.getBooleanExtra("isCheck", false)) {
                if (it.status < 4) {
                    it.status++
                    viewModel.onSaveOrder(it)
                }
            }
        }
    }

    override fun onSaveSuccess() {
        Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBack() {
        finish()
    }

    private fun setPacing(pacing: Int) {
        when (pacing) {
            0 -> {
                pacingPending.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_blue,
                    null))
                pacingPacking.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_light,
                    null))
                pacingShipping.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_light,
                    null))
                pacingArriving.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_light,
                    null))
                pacingSuccess.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_light,
                    null))

                line0.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.light, null))
                line1.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.light, null))
                line2.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.light, null))
                line3.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.light, null))
            }
            1 -> {
                pacingPending.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_blue,
                    null))
                pacingPacking.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_blue,
                    null))
                pacingShipping.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_light,
                    null))
                pacingArriving.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_light,
                    null))
                pacingSuccess.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_light,
                    null))

                line0.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                line1.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.light, null))
                line2.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.light, null))
                line3.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.light, null))
            }
            2 -> {
                pacingPending.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_blue,
                    null))
                pacingPacking.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_blue,
                    null))
                pacingShipping.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_blue,
                    null))
                pacingArriving.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_light,
                    null))
                pacingSuccess.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_light,
                    null))

                line0.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                line1.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                line2.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.light, null))
                line3.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.light, null))
            }
            3 -> {
                pacingPending.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_blue,
                    null))
                pacingPacking.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_blue,
                    null))
                pacingShipping.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_blue,
                    null))
                pacingArriving.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_blue,
                    null))
                pacingSuccess.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_light,
                    null))

                line0.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                line1.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                line2.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                line3.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.light, null))
            }
            4 -> {
                pacingPending.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_blue,
                    null))
                pacingPacking.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_blue,
                    null))
                pacingShipping.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_blue,
                    null))
                pacingArriving.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_blue,
                    null))
                pacingSuccess.setImageDrawable(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_pacing_blue,
                    null))

                line0.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                line1.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                line2.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                line3.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.blue, null))
            }
        }
    }

    override fun onClick(product: ProductInCart) {
        product.product.styles = ArrayList()
        product.product.styles.add(product.style)
        val intent = ProductActivity.getIntent(this)
        intent.putExtra("product", product.product)
        intent.putExtra("inOrder", true)
        startActivity(intent)
    }
}
