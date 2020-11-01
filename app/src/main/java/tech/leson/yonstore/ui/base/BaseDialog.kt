package tech.leson.yonstore.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

abstract class BaseDialog : DialogFragment() {
    private var mActivity: BaseActivity<*, *, *>? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *, *>) {
            val mActivity: BaseActivity<*, *, *> = context
            this.mActivity = mActivity
            mActivity.onFragmentAttached()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val dialog = Dialog(mActivity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    override fun show(fragmentManager: FragmentManager, tag: String?) {
        val transaction = fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag(tag)
        if (prevFragment != null) {
            transaction.remove(prevFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }

    fun dismissDialog(tag: String?) {
        dismiss()
        baseActivity?.onFragmentDetached(tag)
    }

    private val baseActivity: BaseActivity<*, *, *>?
        get() = mActivity

    fun hideKeyboard() {
        if (mActivity != null) {
            mActivity?.hideKeyboard()
        }
    }

    val isNetworkConnected: Boolean
        get() = mActivity != null && mActivity!!.isNetworkConnected()
}
