package tech.leson.yonstore.ui.review

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
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Review
import tech.leson.yonstore.databinding.ActivityReviewBinding
import tech.leson.yonstore.ui.adapter.ReviewImageAdapter
import tech.leson.yonstore.ui.addproduct.AddProductActivity
import tech.leson.yonstore.ui.addproduct.dialog.addImage.AddImageDialog
import tech.leson.yonstore.ui.base.BaseActivity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReviewActivity : BaseActivity<ActivityReviewBinding, ReviewNavigator, ReviewViewModel>(),
    ReviewNavigator,
    AddImageDialog.OnSelectImage {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, ReviewActivity::class.java).also { instance = it }
        }
    }

    private val mReviewImageAdapter: ReviewImageAdapter by inject()
    private lateinit var currentPhotoPath: String

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_review
    override val viewModel: ReviewViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getString(R.string.write_review)

        viewModel.getUserCurrent()
        intent.getStringExtra("userId")?.let { userId ->
            intent.getStringExtra("productId")?.let { productId ->
                viewModel.getMyReviewByProductId(userId, productId)
            }
        }

        rcvImgReview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcvImgReview.adapter = mReviewImageAdapter

        when (viewModel.review.value!!.rating) {
            1F -> tvRating.text = "1/5"
            2F -> tvRating.text = "2/5"
            3F -> tvRating.text = "3/5"
            4F -> tvRating.text = "4/5"
            5F -> tvRating.text = "5/5"
        }

        rtReview.setOnRatingBarChangeListener { _, rating, _ ->
            when (rating) {
                1F -> tvRating.text = "1/5"
                2F -> tvRating.text = "2/5"
                3F -> tvRating.text = "3/5"
                4F -> tvRating.text = "4/5"
                5F -> tvRating.text = "5/5"
            }
        }
    }

    override fun onAddPhoto() {
        val addImageAdapter = AddImageDialog.getInstance()
        addImageAdapter.mOnSelectImage = this
        if (supportFragmentManager.findFragmentByTag("addImage") == null) {
            addImageAdapter.show(supportFragmentManager, "addImage")
        }
    }

    override fun onAddImage(img: String) {
        mReviewImageAdapter.addData(img)
    }

    override fun setReview(review: Review) {
        rtReview.rating = review.rating
        edtDescriptionReview.editText!!.setText(review.description)
        mReviewImageAdapter.addAllData(review.images)
        when (review.rating) {
            1F -> tvRating.text = "1/5"
            2F -> tvRating.text = "2/5"
            3F -> tvRating.text = "3/5"
            4F -> tvRating.text = "4/5"
            5F -> tvRating.text = "5/5"
        }
    }

    override fun onMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSave() {
        viewModel.review.value!!.time = Date().time
        viewModel.review.value!!.description =
            edtDescriptionReview.editText!!.text.toString().trim()
        viewModel.review.value!!.rating = rtReview.rating
        viewModel.review.value!!.avatar = viewModel.user.value!!.avatar
        viewModel.review.value!!.name = viewModel.user.value!!.fullName
        viewModel.review.value!!.userId = viewModel.user.value!!.id
        intent.getStringExtra("productId")?.let {
            viewModel.review.value!!.productId = it
        }
        viewModel.saveReview()
    }

    override fun saveSuccess() {
        onMsg(getString(R.string.success))
        finish()
    }

    override fun onBack() {
        finish()
    }

    override fun onTakePhoto() {
        if (hasPermission(Manifest.permission.CAMERA)) {
            dispatchTakePictureIntent()
        } else {
            requestPermissionsSafely(Array(1) { Manifest.permission.CAMERA },
                AddProductActivity.REQUEST_PERMISSIONS_CAMERA)
        }
    }

    override fun onOpenGallery() {
        if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            dispatchPickImageIntent()
        } else {
            requestPermissionsSafely(Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE },
                AddProductActivity.REQUEST_PERMISSIONS_GALLERY)
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
                    startActivityForResult(takePictureIntent, AddProductActivity.REQUEST_TAKE_PHOTO)
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
        startActivityForResult(chooserIntent, AddProductActivity.PICK_IMAGE)
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
        if (requestCode == AddProductActivity.REQUEST_PERMISSIONS_CAMERA) {
            onTakePhoto()
        }
        when (requestCode) {
            AddProductActivity.REQUEST_PERMISSIONS_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    onTakePhoto()
            }
            AddProductActivity.REQUEST_PERMISSIONS_GALLERY -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    onOpenGallery()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddProductActivity.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            val imageUri = Uri.fromFile(File(currentPhotoPath))
            val imagesUri = ArrayList<Uri>()
            imagesUri.add(imageUri)
            viewModel.saveImages(imagesUri)
        } else if (requestCode == AddProductActivity.PICK_IMAGE && resultCode == RESULT_OK) {
            if (data?.clipData != null) {
                val count: Int = data.clipData!!.itemCount
                val imagesUri = ArrayList<Uri>()
                for (i in 0 until count) {
                    val imageUri: Uri = data.clipData!!.getItemAt(i).uri
                    imagesUri.add(imageUri)
                }
                viewModel.saveImages(imagesUri)
            } else if (data?.data != null) {
                val imageUri: Uri = data.data!!
                val imagesUri = ArrayList<Uri>()
                imagesUri.add(imageUri)
                viewModel.saveImages(imagesUri)
            }
        }
    }
}
