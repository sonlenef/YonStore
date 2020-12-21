package tech.leson.yonstore.ui.addproduct.dialog.addStyle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.dialog_add_style.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Style
import tech.leson.yonstore.databinding.DialogAddStyleBinding
import tech.leson.yonstore.ui.addproduct.popup.ColorPopup
import tech.leson.yonstore.ui.base.BaseDialog
import tech.leson.yonstore.utils.OnItemClickListener
import java.util.*


class AddStyleDialog : BaseDialog(), AddStyleNavigator, OnItemClickListener<String> {

    companion object {
        val TAG: String? = AddStyleDialog::class.java.simpleName
        private var instance: AddStyleDialog? = null

        @JvmStatic
        fun newInstance() = instance ?: synchronized(this) {
            instance ?: AddStyleDialog().also {
                it.arguments = Bundle()
                instance = it
            }
        }
    }

    private val mAddStyleViewModel: AddStyleViewModel by viewModel()
    private val mColorPopup: ColorPopup by inject()
    lateinit var addOnStyleListener: OnStyleListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding: DialogAddStyleBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_add_style, container, false)
        val view = binding.root

        binding.viewModel = mAddStyleViewModel
        mAddStyleViewModel.setNavigator(this)
        return view
    }

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, TAG)
    }

    override fun onSelectColor() {
        onColorPopup()
    }

    override fun onClose() {
        dismiss()
    }

    override fun onAddStyle() {
        val size = edtSize.text.toString().trim().toUpperCase(Locale.getDefault())
        val color = tvColor.text.toString()
        val quantityStr = edtQuantity.text.toString().trim()
        if (size != "" && color != getString(R.string.color) && quantityStr != "" && quantityStr != "0"
        ) {
            addOnStyleListener.addStyle(Style(size,
                color,
                quantityStr.toInt(),
                quantityStr.toInt()))
            hideKeyboard()
            dismiss()
        } else {
            activity?.let {
                Toast.makeText(it, getString(R.string.pls_enter_complete_info), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onClick(item: String) {
        tvColor.text = item
    }

    private fun onColorPopup() {
        mColorPopup.onItemClickListener = this
        mColorPopup.width = WindowManager.LayoutParams.WRAP_CONTENT
        mColorPopup.height = resources.displayMetrics.widthPixels / 3
        mColorPopup.isOutsideTouchable = true
        mColorPopup.isFocusable = true
        mColorPopup.showAsDropDown(tvColor, 2 * resources.displayMetrics.widthPixels / 3
                - resources.getDimensionPixelOffset(R.dimen._40sdp),
            -resources.getDimensionPixelOffset(R.dimen._32sdp))
    }

    interface OnStyleListener {
        fun addStyle(style: Style)
    }
}
