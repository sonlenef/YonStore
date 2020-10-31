package tech.leson.yonstore.ui.addproduct.dialog

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
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
        const val REQUEST_IMAGE_CAPTURE = 1
        const val PICK_IMAGE = 2
        private var instance: AddImageDialog? = null

        @JvmStatic
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: AddImageDialog().also { instance = it }
        }
    }

    lateinit var addProductNavigator: AddProductNavigator

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.dialog_add_image
    override val viewModel: AddImageViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
    }

    override fun onTakePhoto() {
        if (hasPermission(Manifest.permission.CAMERA)) {
            dispatchTakePictureIntent()
        } else {
            requestPermissionsSafely(Array(1) { Manifest.permission.CAMERA }, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onOpenGallery() {
        if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            dispatchPickImageIntent()
        } else {
            requestPermissionsSafely(Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE },
                REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onCancel() {
        dismiss()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun dispatchPickImageIntent() {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = "image/*"

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

        val chooserIntent = Intent.createChooser(getIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))
        startActivityForResult(chooserIntent, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            addProductNavigator.addImageProduct(imageBitmap)
        } else if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data?.clipData != null) {
                val count: Int = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri: Uri = data.clipData!!.getItemAt(i).uri
                    val imageBitmap = BitmapFactory.decodeFile(imageUri.toString())
                    addProductNavigator.addImageProduct(imageBitmap)
                }
            } else if (data?.data != null) {
                val imagePath: String? = data.data!!.path
                val imageBitmap = BitmapFactory.decodeFile(imagePath)
                addProductNavigator.addImageProduct(imageBitmap)
            }
        }
    }
}
