package tech.leson.yonstore.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseFragmentDialog<T : ViewDataBinding, N, V : BaseViewModel<N>> :
    BottomSheetDialogFragment() {
    private var mActivity: BaseActivity<*, *, *>? = null
    private var mRootView: View? = null
    private var viewDataBinding: T? = null

    abstract val bindingVariable: Int

    @get:LayoutRes
    abstract val layoutId: Int

    abstract val viewModel: V

    abstract fun init()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *, *>) {
            val activity: BaseActivity<*, *, *> = context
            mActivity = activity
            activity.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = viewDataBinding?.root
        return mRootView
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.setVariable(bindingVariable, viewModel)
        viewDataBinding?.lifecycleOwner = this
        viewDataBinding?.executePendingBindings()
        init()
    }

    val baseActivity: BaseActivity<*, *, *>?
        get() = mActivity

    fun hideKeyboard() {
        if (mActivity != null) {
            mActivity?.hideKeyboard()
        }
    }

    val isNetworkConnected: Boolean
        get() = mActivity != null && mActivity!!.isNetworkConnected()
}
