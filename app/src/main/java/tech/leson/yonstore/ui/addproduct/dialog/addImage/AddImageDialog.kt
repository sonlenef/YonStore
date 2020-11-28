package tech.leson.yonstore.ui.addproduct.dialog.addImage

import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.DialogAddImageBinding
import tech.leson.yonstore.ui.addproduct.AddProductNavigator
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

    lateinit var mOnSelectImage: OnSelectImage

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.dialog_add_image
    override val viewModel: AddImageViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
    }

    override fun onTakePhoto() {
        mOnSelectImage.onTakePhoto()
        dismiss()
    }

    override fun onOpenGallery() {
        mOnSelectImage.onOpenGallery()
        dismiss()
    }

    override fun onCancel() {
        dismiss()
    }

    interface OnSelectImage {
        fun onTakePhoto()
        fun onOpenGallery()
    }
}
