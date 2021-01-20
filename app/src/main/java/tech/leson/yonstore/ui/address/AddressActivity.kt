package tech.leson.yonstore.ui.address

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Address
import tech.leson.yonstore.data.model.Order
import tech.leson.yonstore.databinding.ActivityAddressBinding
import tech.leson.yonstore.ui.adapter.AddressAdapter
import tech.leson.yonstore.ui.addaddress.AddAddressActivity
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.dialog.ConfirmDialogFragment
import tech.leson.yonstore.ui.paymentList.ListPaymentActivity

class AddressActivity : BaseActivity<ActivityAddressBinding, AddressNavigator, AddressViewModel>(),
    AddressNavigator, AddressAdapter.SetOnItemClickListener, ConfirmDialogFragment.OnDialogListener {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, AddressActivity::class.java).also { instance = it }
        }
    }

    private val mAddressAdapter: AddressAdapter by inject()

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_address
    override val viewModel: AddressViewModel by viewModel()

    private var mItemRemove: Address? = null
    private var mCurrentItem = 0
    private var isOrder = false

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getString(R.string.address)
        viewModel.getUser()

        isOrder = intent.getBooleanExtra("isOrder", false)
        if (intent.getBooleanExtra("isOrder", false)) {
            btnAddress.text = getString(R.string.next)
        }

        rcvListAddress.layoutManager = LinearLayoutManager(this)
        mAddressAdapter.setOnItemClickListener = this
        rcvListAddress.adapter = mAddressAdapter
    }

    override fun onAddAddress() {
        if (intent.getBooleanExtra("isOrder", false)) {
            val order = intent.getSerializableExtra("order") as Order
            order.address = mAddressAdapter.data[mCurrentItem]
            val i = ListPaymentActivity.getIntent(this)
            i.putExtra("isOrder", true)
            i.putExtra("order", order)
            startActivity(i)
        } else startActivity(AddAddressActivity.getIntent(this))
    }

    override fun onAddress(address: MutableList<Address>) {
        mAddressAdapter.addAllData(address)
    }

    override fun onMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBack() {
        finish()
    }

    override fun onSelected(position: Int) {
        val lastItem = mCurrentItem
        mCurrentItem = position
        mAddressAdapter.setCurrentItem(position)
        mAddressAdapter.notifyItemChanged(lastItem)
        mAddressAdapter.notifyItemChanged(mCurrentItem)
    }

    override fun onEdit(address: Address) {}

    override fun onBin(address: Address) {
        mItemRemove = address
        val removeDialog = ConfirmDialogFragment()
        removeDialog.onDialogListener = this
        removeDialog.message = getString(R.string.dialog_remove_address)
        removeDialog.show(supportFragmentManager, "removeAddress")
    }

    override fun onConfirm() {
        viewModel.user.value?.address?.remove(mItemRemove)
        viewModel.updateUser()
    }
}
