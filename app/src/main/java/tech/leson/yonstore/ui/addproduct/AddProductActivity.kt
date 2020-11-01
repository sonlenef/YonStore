package tech.leson.yonstore.ui.addproduct

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.ActivityAddProductBinding
import tech.leson.yonstore.ui.addproduct.adapter.ImageAdapter
import tech.leson.yonstore.ui.addproduct.adapter.StyleAdapter
import tech.leson.yonstore.ui.addproduct.dialog.addImage.AddImageDialog
import tech.leson.yonstore.ui.addproduct.dialog.addStyle.AddStyleDialog
import tech.leson.yonstore.ui.addproduct.model.Image
import tech.leson.yonstore.ui.addproduct.model.Style
import tech.leson.yonstore.ui.base.BaseActivity
import tech.leson.yonstore.utils.OnItemClickListener
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddProductActivity :
    BaseActivity<ActivityAddProductBinding, AddProductNavigator, AddProductViewModel>(),
    AddProductNavigator, OnItemClickListener<Int> {

    companion object {
        const val REQUEST_PERMISSIONS_CAMERA = 1111
        const val REQUEST_PERMISSIONS_GALLERY = 2222
        const val REQUEST_TAKE_PHOTO = 3333
        const val PICK_IMAGE = 4444
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, AddProductActivity::class.java).also { instance = it }
        }
    }

    private val mImageAdapter: ImageAdapter by inject()
    private val mStyleAdapter: StyleAdapter by inject()
    private lateinit var currentPhotoPath: String

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_add_product
    override val viewModel: AddProductViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getString(R.string.add_product)

        mImageAdapter.onItemClickListener = this
        mImageAdapter.clearData()
        rcvImage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcvImage.adapter = mImageAdapter

        mStyleAdapter.clearData()
        rcvStyle.layoutManager = LinearLayoutManager(this)
        rcvStyle.adapter = mStyleAdapter
    }

    override fun onImage() {
        val addImageAdapter = AddImageDialog.getInstance()
        addImageAdapter.addProductNavigator = this
        if (supportFragmentManager.findFragmentByTag("addImage") == null) {
            addImageAdapter.show(supportFragmentManager, "addImage")
        }
    }

    override fun onStyle() {
        val addStyleDialog = AddStyleDialog.newInstance()
        addStyleDialog.addProductNavigator = this
        addStyleDialog.show(supportFragmentManager)
    }

    override fun onTakePhoto() {
        if (hasPermission(Manifest.permission.CAMERA)) {
            dispatchTakePictureIntent()
        } else {
            requestPermissionsSafely(Array(1) { Manifest.permission.CAMERA },
                REQUEST_PERMISSIONS_CAMERA)
        }
    }

    override fun addStyle(style: Style) {
        mStyleAdapter.addData(style)
    }

    override fun onOpenGallery() {
        if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            dispatchPickImageIntent()
        } else {
            requestPermissionsSafely(Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE },
                REQUEST_PERMISSIONS_GALLERY)
        }
    }

    @Suppress("NAME_SHADOWING")
    @SuppressLint("SdCardPath", "QueryPermissionsNeeded")
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "tech.leson.yonstore.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    private fun dispatchPickImageIntent() {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = "image/*"
        getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

        val chooserIntent = Intent.createChooser(getIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))
        startActivityForResult(chooserIntent, PICK_IMAGE)
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "ys_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS_CAMERA) {
            onTakePhoto()
        }
        when (requestCode) {
            REQUEST_PERMISSIONS_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    onTakePhoto()
            }
            REQUEST_PERMISSIONS_GALLERY -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    onOpenGallery()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            val imageUri = Uri.fromFile(File(currentPhotoPath))
            mImageAdapter.addData(Image(imageUri))
        } else if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data?.clipData != null) {
                val count: Int = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri: Uri = data.clipData!!.getItemAt(i).uri
                    mImageAdapter.addData(Image(imageUri))
                }
            } else if (data?.data != null) {
                val imageUri: Uri = data.data!!
                mImageAdapter.addData(Image(imageUri))
            }
        }
    }

    override fun onBack() {
        finish()
    }

    override fun onClick(item: Int) {
        mImageAdapter.removeItem(item)
    }
}
