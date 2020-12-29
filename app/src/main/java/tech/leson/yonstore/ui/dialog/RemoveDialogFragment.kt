package tech.leson.yonstore.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import tech.leson.yonstore.R

class RemoveDialogFragment : DialogFragment() {

    lateinit var onDialogListener: OnDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.dialog_remove_product)
                .setPositiveButton(R.string.yes
                ) { _, _ ->
                    onDialogListener.onRemove()
                }
                .setNegativeButton(R.string.cancel
                ) { _, _ ->
                    dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface OnDialogListener {
        fun onRemove()
    }
}
