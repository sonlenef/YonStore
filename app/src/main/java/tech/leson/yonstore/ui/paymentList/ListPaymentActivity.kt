package tech.leson.yonstore.ui.paymentList

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list_payment.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.android.ext.android.inject
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Order
import tech.leson.yonstore.databinding.ActivityListPaymentBinding
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.dialog.ConfirmDialogFragment
import tech.leson.yonstore.ui.success.SuccessActivity
import java.util.*

class ListPaymentActivity :
    BaseActivity<ActivityListPaymentBinding, ListPaymentNavigator, ListPaymentViewModel>(),
    ListPaymentNavigator, ConfirmDialogFragment.OnDialogListener {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, ListPaymentActivity::class.java).also { instance = it }
        }
    }

    private var isOrder = false
    private var mOrder: Order? = null

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_list_payment
    override val viewModel: ListPaymentViewModel by inject()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getString(R.string.payment)
        viewModel.getUserCurrent()

        isOrder = intent.getBooleanExtra("isOrder", false)
        if (!isOrder) btnPayOnDelivery.visibility = View.GONE
    }

    override fun onPayOnDelivery() {
        mOrder = (intent.getSerializableExtra("order") as Order)
        val confirmDialog = ConfirmDialogFragment()
        confirmDialog.onDialogListener = this
        confirmDialog.message = getString(R.string.dialog_confirm_order)
        confirmDialog.show(supportFragmentManager, "confirmOrder")
    }

    override fun onCreditCart() {
        onMsg(getString(R.string.coming_soon))
    }

    override fun onPaypal() {
        onMsg(getString(R.string.coming_soon))
    }

    override fun onBank() {
        onMsg(getString(R.string.coming_soon))
    }

    override fun onSuccess() {
        startActivity(Intent(this, SuccessActivity::class.java))
        finish()
    }

    override fun onMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBack() {
        finish()
    }

    override fun onConfirm() {
        mOrder?.let {
            it.date = Date().time
            viewModel.user.value!!.cart.clear()
            viewModel.user.value!!.order.add(0, it)
            viewModel.updateUser()
        }
    }
}
