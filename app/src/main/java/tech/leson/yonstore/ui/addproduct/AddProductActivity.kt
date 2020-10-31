package tech.leson.yonstore.ui.addproduct

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.ActivityAddProductBinding
import tech.leson.yonstore.ui.addproduct.dialog.AddImageDialog
import tech.leson.yonstore.ui.base.BaseActivity

class AddProductActivity :
    BaseActivity<ActivityAddProductBinding, AddProductNavigator, AddProductViewModel>(),
    AddProductNavigator {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, AddProductActivity::class.java).also { instance = it }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_add_product
    override val viewModel: AddProductViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getString(R.string.add_product)
    }

    override fun onImage() {
        val addImageAdapter = AddImageDialog.getInstance()
        addImageAdapter.addProductNavigator = this
        if (supportFragmentManager.findFragmentByTag("addImage") == null) {
            addImageAdapter.show(supportFragmentManager, "addImage")
        }
    }

    override fun onStyle() {}

    override fun addImageProduct(img: Bitmap) {}

    override fun onBack() {
        finish()
    }
}