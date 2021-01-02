package tech.leson.yonstore.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import tech.leson.yonstore.R

class ConfirmDialogFragment : DialogFragment() {

    lateinit var onDialogListener: OnDialogListener
    var message: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            if (message == null) {
                builder.setMessage(R.string.dialog_remove_product)
                    .setPositiveButton(R.string.yes
                    ) { _, _ ->
                        onDialogListener.onConfirm()
                    }
                    .setNegativeButton(R.string.cancel
                    ) { _, _ ->
                        dismiss()
                    }
            } else {
                builder.setMessage(message)
                    .setPositiveButton(R.string.yes
                    ) { _, _ ->
                        onDialogListener.onConfirm()
                    }
                    .setNegativeButton(R.string.cancel
                    ) { _, _ ->
                        dismiss()
                    }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface OnDialogListener {
        fun onConfirm()
    }
}
