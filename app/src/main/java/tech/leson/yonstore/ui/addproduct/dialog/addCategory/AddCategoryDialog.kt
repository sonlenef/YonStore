package tech.leson.yonstore.ui.addproduct.dialog.addCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_add_category.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.leson.yonstore.R
import tech.leson.yonstore.data.model.Category
import tech.leson.yonstore.databinding.DialogAddCategoryBinding
import tech.leson.yonstore.ui.addproduct.AddProductNavigator
import tech.leson.yonstore.ui.addproduct.adapter.AddCategoryAdapter
import tech.leson.yonstore.ui.addproduct.dialog.addStyle.AddStyleDialog
import tech.leson.yonstore.ui.base.BaseDialog
import tech.leson.yonstore.utils.NetworkUtils

class AddCategoryDialog : BaseDialog(), AddCategoryNavigator {

    companion object {
        private var instance: AddCategoryDialog? = null

        @JvmStatic
        fun newInstance() = instance ?: synchronized(this) {
            instance ?: AddCategoryDialog().also {
                it.arguments = Bundle()
                instance = it
            }
        }
    }

    private val mAddCategoryViewModel: AddCategoryViewModel by viewModel()
    private val mAddCategoryAdapter: AddCategoryAdapter by inject()
    lateinit var mAddProductNavigator: AddProductNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding: DialogAddCategoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_add_category, container, false)
        val view = binding.root

        binding.viewModel = mAddCategoryViewModel
        mAddCategoryViewModel.setNavigator(this)

        activity?.let {
            if (NetworkUtils.isNetworkConnected(it))
                mAddCategoryViewModel.getCategories()
        }

        view.rcvCategory.layoutManager = LinearLayoutManager(activity)
        mAddCategoryAdapter.addCategoryNavigator = this
        view.rcvCategory.adapter = mAddCategoryAdapter
        return view
    }

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, AddStyleDialog.TAG)
    }

    override fun getCategoriesSuccess(data: MutableList<Category>) {
        mAddCategoryAdapter.addAllData(data)
    }

    override fun onCategorySelection(category: Category) {
        dismiss()
        mAddProductNavigator.categorySelect(category)
    }

    override fun onError(msg: String) {
        dismiss()
        activity?.let {
            Toast.makeText(it, msg, Toast.LENGTH_SHORT).show()
        }
    }
}
