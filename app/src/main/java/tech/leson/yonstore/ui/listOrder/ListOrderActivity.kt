package tech.leson.yonstore.ui.listOrder

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_order.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Order
import tech.leson.yonstore.databinding.ActivityListOrderBinding
import tech.leson.yonstore.ui.adapter.OrderAdapter
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.order.OrderDetailsActivity

class ListOrderActivity :
    BaseActivity<ActivityListOrderBinding, ListOrderNavigator, ListOrderViewModel>(),
    ListOrderNavigator, OrderAdapter.SetOnClickOrder {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, ListOrderActivity::class.java).also { instance = it }
        }
    }

    private val mOrderAdapter: OrderAdapter by inject()

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_list_order
    override val viewModel: ListOrderViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getString(R.string.order)
        intent.getStringExtra("userId")?.let {
            viewModel.getOrder(it)
        }

        rcvListOrder.layoutManager = LinearLayoutManager(this)
        mOrderAdapter.listener = this
        rcvListOrder.adapter = mOrderAdapter
    }

    override fun setOrders(orders: MutableList<Order>) {
        mOrderAdapter.addAllData(orders)
    }

    override fun onMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBack() {
        finish()
    }

    override fun onClick(order: Order) {
        val i = OrderDetailsActivity.getIntent(this)
        i.putExtra("order", order)
        startActivity(i)
    }
}
