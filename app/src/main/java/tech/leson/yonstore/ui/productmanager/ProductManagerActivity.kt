package tech.leson.yonstore.ui.productmanager

import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_product_manager.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Product
import tech.leson.yonstore.databinding.ActivityProductManagerBinding
import tech.leson.yonstore.ui.addproduct.AddProductActivity
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.ui.editproduct.EditProductActivity
import tech.leson.yonstore.ui.scanbarcode.ScannerActivity

class ProductManagerActivity :
    BaseActivity<ActivityProductManagerBinding, ProductManagerNavigator, ProductManagerViewModel>(),
    ProductManagerNavigator {

    companion object {
        const val REQUEST_CODE = 2873
        const val PRODUCT_CODE = "PRODUCT_CODE"
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, ProductManagerActivity::class.java).also { instance = it }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_product_manager
    override val viewModel: ProductManagerViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getString(R.string.product_manager)
    }

    override fun onNewProduct() {
        edtProductCode.text.toString().trim().let {
            if (it != "") viewModel.searchProduct(it, false)
            else startActivity(AddProductActivity.getIntent(this))
        }
    }

    override fun onSearch() {
        hideKeyboard()
        edtProductCode.text.toString().trim().let {
            if (it != "") viewModel.searchProduct(it, true)
        }
    }

    override fun onScan() {
        startActivityForResult(ScannerActivity.getIntent(this), REQUEST_CODE)
    }

    override fun onProduct(product: Product) {
        val i = EditProductActivity.getIntent(this)
        i.putExtra(EditProductActivity.PRODUCT, product)
        startActivity(i)
    }

    override fun onProductEmpty() {
        Toast.makeText(this, getString(R.string.product_not_found), Toast.LENGTH_SHORT).show()
    }

    override fun newProduct() {
        val i = AddProductActivity.getIntent(this)
        if (edtProductCode.text.toString().trim() != "") {
            i.putExtra(PRODUCT_CODE, edtProductCode.text.toString().trim())
        }
        startActivity(i)
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBack() {
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == ScannerActivity.RESULT_CODE && data != null) {
                edtProductCode.setText(data.getStringExtra(ScannerActivity.BAR_CODE).toString())
            }
        }
    }
}
