package tech.leson.yonstore.ui.checkOrder

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_check_order.*
import kotlinx.android.synthetic.main.item_order.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Order
import tech.leson.yonstore.databinding.ActivityCheckOrderBinding
import tech.leson.yonstore.ui.adapter.OrderAdapter
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.order.OrderDetailsActivity

class CheckOrderActivity :
    BaseActivity<ActivityCheckOrderBinding, CheckOrderNavigator, CheckOrderViewModel>(),
    CheckOrderNavigator, OrderAdapter.SetOnClickOrder {

    companion object {
        const val REQUEST_CODE = 1223
        private var instance: Intent? = null

        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, CheckOrderActivity::class.java).also { instance = it }
        }
    }

    private val mOrderAdapter: OrderAdapter by inject()

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_check_order
    override val viewModel: CheckOrderViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        onBtnSelect(0)
        viewModel.getAllOrder()

        rcvOrder.layoutManager = LinearLayoutManager(this)
        mOrderAdapter.listener = this
        rcvOrder.adapter = mOrderAdapter
    }

    override fun getAllOrder() {
        onBtnSelect(0)
    }

    override fun getOrderPending() {
        onBtnSelect(1)
    }

    override fun getOrderPacking() {
        onBtnSelect(2)
    }

    override fun getOrderShipping() {
        onBtnSelect(3)
    }

    override fun getOrderArriving() {
        onBtnSelect(4)
    }

    override fun getOrderSuccess() {
        onBtnSelect(5)
    }

    @SuppressLint("SetTextI18n")
    override fun onOrders(orders: MutableList<Order>) {
        tvTitle.text = "${orders.size} Orders"
        mOrderAdapter.addAllData(orders)
    }

    override fun onMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBack() {
        finish()
    }

    private fun onBtnSelect(btn: Int) {
        when (btn) {
            0 -> {
                btnAllOrder.isSelected = true
                btnAllOrder.setTextColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                btnPending.isSelected = false
                btnPending.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnPacking.isSelected = false
                btnPacking.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnShipping.isSelected = false
                btnShipping.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnArriving.isSelected = false
                btnArriving.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnSuccess.isSelected = false
                btnSuccess.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
            }
            1 -> {
                btnAllOrder.isSelected = false
                btnAllOrder.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnPending.isSelected = true
                btnPending.setTextColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                btnPacking.isSelected = false
                btnPacking.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnShipping.isSelected = false
                btnShipping.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnArriving.isSelected = false
                btnArriving.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnSuccess.isSelected = false
                btnSuccess.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
            }
            2 -> {
                btnAllOrder.isSelected = false
                btnAllOrder.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnPending.isSelected = false
                btnPending.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnPacking.isSelected = true
                btnPacking.setTextColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                btnShipping.isSelected = false
                btnShipping.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnArriving.isSelected = false
                btnArriving.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnSuccess.isSelected = false
                btnSuccess.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
            }
            3 -> {
                btnAllOrder.isSelected = false
                btnAllOrder.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnPending.isSelected = false
                btnPending.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnPacking.isSelected = false
                btnPacking.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnShipping.isSelected = true
                btnShipping.setTextColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                btnArriving.isSelected = false
                btnArriving.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnSuccess.isSelected = false
                btnSuccess.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
            }
            4 -> {
                btnAllOrder.isSelected = false
                btnAllOrder.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnPending.isSelected = false
                btnPending.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnPacking.isSelected = false
                btnPacking.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnShipping.isSelected = false
                btnShipping.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnArriving.isSelected = true
                btnArriving.setTextColor(ResourcesCompat.getColor(resources, R.color.blue, null))
                btnSuccess.isSelected = false
                btnSuccess.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
            }
            5 -> {
                btnAllOrder.isSelected = false
                btnAllOrder.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnPending.isSelected = false
                btnPending.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnPacking.isSelected = false
                btnPacking.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnShipping.isSelected = false
                btnShipping.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnArriving.isSelected = false
                btnArriving.setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
                btnSuccess.isSelected = true
                btnSuccess.setTextColor(ResourcesCompat.getColor(resources, R.color.blue, null))
            }
        }
    }

    override fun onClick(order: Order) {
        val i = OrderDetailsActivity.getIntent(this)
        i.putExtra("order", order)
        i.putExtra("isCheck", true)
        startActivityForResult(i, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                onBtnSelect(0)
                viewModel.getAllOrder()
            }
        }
    }
}
