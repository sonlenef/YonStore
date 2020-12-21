package tech.leson.yonstore.ui.addevent

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_event.*
import kotlinx.android.synthetic.main.navigation_header.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.BR
import tech.leson.yonstore.R
import tech.leson.yonstore.databinding.ActivityAddEventBinding
import tech.leson.yonstore.ui.addproduct.AddProductActivity
import tech.leson.yonstore.ui.addproduct.dialog.addImage.AddImageDialog
import tech.leson.yonstore.ui.base.BaseActivity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddEventActivity :
    BaseActivity<ActivityAddEventBinding, AddEventNavigator, AddEventViewModel>(),
    AddEventNavigator, AddImageDialog.OnSelectImage {

    companion object {
        private var instance: Intent? = null

        @JvmStatic
        fun getIntent(context: Context) = instance ?: synchronized(this) {
            instance ?: Intent(context, AddEventActivity::class.java).also { instance = it }
        }
    }

    private lateinit var currentPhotoPath: String

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_add_event
    override val viewModel: AddEventViewModel by viewModel()

    override fun init() {
        viewModel.setNavigator(this)
        tvTitle.text = getString(R.string.add_event)
    }

    override fun onCreateSuccess() {
        Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSelectImage() {
        val addImageAdapter = AddImageDialog.getInstance()
        addImageAdapter.mOnSelectImage = this
        if (supportFragmentManager.findFragmentByTag("addImageEvent") == null) {
            addImageAdapter.show(supportFragmentManager, "addImageEvent")
        }
    }

    override fun onLoadImage(url: String) {
        Glide.with(this).load(url).placeholder(R.drawable.default_image).into(imvEvent)
    }

    override fun onPickStartDate() {
        pickDateTime(0)
    }

    override fun onPickEndDate() {
        pickDateTime(1)
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onConfirm() {
        when {
            edtProductName.editText?.text?.toString()?.trim() == "" -> {
                Toast.makeText(this,
                    getString(R.string.pls_enter_complete_info),
                    Toast.LENGTH_SHORT).show()
                return
            }
            viewModel.event.value?.startTime == 0L -> {
                Toast.makeText(this,
                    getString(R.string.pls_enter_complete_info),
                    Toast.LENGTH_SHORT).show()
                return
            }
            viewModel.event.value?.endTime == 0L -> {
                Toast.makeText(this,
                    getString(R.string.pls_enter_complete_info),
                    Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                viewModel.event.value?.title =
                    edtProductName.editText?.text?.toString()?.trim()!!
                viewModel.event.value?.description =
                    edtDescription.editText?.text?.toString()?.trim()!!

                viewModel.addEvent()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun pickDateTime(type: Int) {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(this, { _, year, month, day ->
            TimePickerDialog(this, { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                when (type) {
                    0 -> {
                        if (viewModel.event.value?.endTime != 0L && pickedDateTime.timeInMillis >= viewModel.event.value?.endTime!!) {
                            Toast.makeText(this, getString(R.string.time_error), Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            viewModel.event.value?.startTime = pickedDateTime.timeInMillis
                            startTime.text =
                                SimpleDateFormat("HH:mm-dd/MM/yyyy").format(Date(pickedDateTime.timeInMillis))
                        }
                    }
                    1 -> {
                        if (viewModel.event.value?.startTime != 0L && pickedDateTime.timeInMillis <= viewModel.event.value?.startTime!!) {
                            Toast.makeText(this, getString(R.string.time_error), Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            viewModel.event.value?.endTime = pickedDateTime.timeInMillis
                            endTime.text =
                                SimpleDateFormat("HH:mm-dd/MM/yyyy").format(Date(pickedDateTime.timeInMillis))
                        }
                    }
                }
            },
                startHour,
                startMinute,
                true).show()
        },
            startYear,
            startMonth,
            startDay
        ).show()
    }

    override fun onBack() {
        finish()
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
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

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"

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
            viewModel.saveImage(imageUri)
        } else if (requestCode == AddProductActivity.PICK_IMAGE && resultCode == RESULT_OK) {
            if (data?.clipData != null) {
                val imageUri: Uri = data.clipData!!.getItemAt(0).uri
                viewModel.saveImage(imageUri)
            } else if (data?.data != null) {
                val imageUri: Uri = data.data!!
                viewModel.saveImage(imageUri)
            }
        }
    }
}
