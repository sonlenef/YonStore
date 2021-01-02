package tech.leson.yonstore.ui.addaddress

import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.android.ext.android.inject
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Address
import tech.leson.yonstore.databinding.ActivityAddAddressBinding
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.utils.AppUtils

class AddAddressActivity :
    BaseActivity<ActivityAddAddressBinding, AddAddressNavigator, AddAddressViewModel>(),
    AddAddressNavigator {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, AddAddressActivity::class.java).also { instance = it }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_add_address
    override val viewModel: AddAddressViewModel by inject()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getString(R.string.add_address)

        viewModel.getUserCurrent()
    }

    override fun addAddress() {
        hideKeyboard()
        val nation = edtNation.editText!!.text.toString().trim()
        val fullName = edtFullName.editText!!.text.toString().trim()
        val address1 = edtAddress1.editText!!.text.toString().trim()
        val address2 = edtAddress2.editText!!.text.toString().trim()
        val city = edtCity.editText!!.text.toString().trim()
        val region = edtRegion.editText!!.text.toString().trim()
        val zipCode = edtZipCode.editText!!.text.toString().trim()
        val phoneNumber = edtPhoneNumber.editText!!.text.toString().trim()
        if (nation == "") {
            Toast.makeText(this,
                getString(R.string.pls_enter_complete_info),
                Toast.LENGTH_SHORT).show()
            return
        } else if (fullName == "") {
            Toast.makeText(this,
                getString(R.string.pls_enter_complete_info),
                Toast.LENGTH_SHORT).show()
            return
        } else if (address1 == "") {
            Toast.makeText(this,
                getString(R.string.pls_enter_complete_info),
                Toast.LENGTH_SHORT).show()
            return
        } else if (city == "") {
            Toast.makeText(this,
                getString(R.string.pls_enter_complete_info),
                Toast.LENGTH_SHORT).show()
            return
        } else if (region == "") {
            Toast.makeText(this,
                getString(R.string.pls_enter_complete_info),
                Toast.LENGTH_SHORT).show()
            return
        } else if (zipCode == "") {
            Toast.makeText(this,
                getString(R.string.pls_enter_complete_info),
                Toast.LENGTH_SHORT).show()
            return
        } else if (phoneNumber == "") {
            Toast.makeText(this,
                getString(R.string.pls_enter_complete_info),
                Toast.LENGTH_SHORT).show()
            return
        } else if (!AppUtils.isPhoneNumberValid(phoneNumber)) {
            Toast.makeText(this, getString(R.string.invalid_phone_password), Toast.LENGTH_SHORT)
                .show()
            return
        }

        viewModel.user.value?.address?.add(0,
            Address(fullName, address1, address2, city, region, nation, zipCode, phoneNumber))
        viewModel.updateUser()
    }

    override fun addSuccess() {
        Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBack() {
        finish()
    }
}
