package tech.leson.yonstore.ui.addproduct.dialog

import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.DialogAddImageBinding
import tech.leson.yonstore.ui.base.BaseFragmentDialog

class AddImageDialog :
    BaseFragmentDialog<DialogAddImageBinding, AddImageNavigator, AddImageViewModel>(),
    AddImageNavigator {

    companion object {
        private var instance: AddImageDialog? = null

        @JvmStatic
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: AddImageDialog().also { instance = it }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.dialog_add_image
    override val viewModel: AddImageViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
    }

    override fun onTakePhoto() {
        TODO("Not yet implemented")
    }

    override fun onOpenGallery() {
        TODO("Not yet implemented")
    }

    override fun onCancel() {
        dismiss()
    }
}
